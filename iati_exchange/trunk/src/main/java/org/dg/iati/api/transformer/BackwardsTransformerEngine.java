/**
 * 
 */
package org.dg.iati.api.transformer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.log4j.Logger;

/**
 * @author Alex
 *
 */
public class BackwardsTransformerEngine extends TransformerEngine {
	
	private static Logger logger 	= Logger.getLogger(BackwardsTransformerEngine.class);

	/**
	 * @param source
	 * @param destination
	 */
	public BackwardsTransformerEngine(Object source, Object destination) {
		super(source, destination);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected Pair<Field,Object> getDestinationField (String fieldName) {
		
		String [] pathElements	= fieldName.split(TransformationConstants.PATH_SEPARATOR);

		Object iterObj			= this.getDestination();
		Pair<Field,Object> pair	= null;
		for ( int i=0; i<pathElements.length; i++ ) {
			try {
				Field f					= iterObj.getClass().getDeclaredField(pathElements[i]);
				pair					= new Pair<Field, Object>(f, iterObj);
				Object tempObj			= this.getFieldObject(f, iterObj);
//				try {
//					PropertyDescriptor pd 	= new PropertyDescriptor(f.getName(), iterObj.getClass() );
//					tempObj					= pd.getReadMethod().invoke(iterObj, new Object[0]);
//				}
//				catch (IntrospectionException e) {
//					logger.info(e.getMessage() );
//				}
				
				if ( tempObj == null ) {
					return null;
				} 
				iterObj		= tempObj;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return pair;
		
	}
	
	@Override
	public void transform() {
		Field[] fields					= this.getSource().getClass().getDeclaredFields();
		if ( fields != null && fields.length > 0 ) {
			for (int i=0; i<fields.length; i++) {
				TransformationMetadata tMetadata	= fields[i].getAnnotation(TransformationMetadata.class);
				if ( tMetadata != null ) {
					if ( Collection.class.isAssignableFrom( fields[i].getType() )) {
						/* A collection needs to map to another collection */
						Pair<Field,Object> pair	= this.getDestinationField(tMetadata.jaxbMapping());
						if ( pair != null && pair.getTerm1() != null ) {
							try {
								Collection collDest				= (Collection)this.getFieldObject(pair.getTerm1(), pair.getTerm2() );
								Collection collSrc				= (Collection)this.getFieldObject(fields[i], this.getSource() ) ;
								Class correlClazz				= Class.forName(tMetadata.correlationClassName() );
								ICorrelation<Object, Object> correlation	= 
																	(ICorrelation<Object, Object>)correlClazz.newInstance();
								if ( collDest != null && collSrc != null ) {
									for (Object jaxbObj : collDest) {
										for (Object modelObj : collSrc) {
											boolean correlationExists 	= false;
											String childRepresenterName	= null;
											Object targetJaxbObject		= jaxbObj;
											if (  this.getSource() instanceof IChildRepresenter && modelObj instanceof IChildRepresenter ) {
												IChildRepresenter parent 	= (IChildRepresenter)this.getSource();
												IChildRepresenter child		= (IChildRepresenter) modelObj;
												if ( parent.isRepresented(child) ){
													targetJaxbObject		= this.getDestination();
													correlationExists		= true;
													childRepresenterName	= child.getName();
												}
											}
											correlationExists = correlationExists ? correlationExists : correlation.correlated(modelObj, jaxbObj);
											if ( correlationExists ) {
												if ( TransformationConstants.NONE.equals(tMetadata.backwardsTransformerClassName()) ) {
													BackwardsTransformerEngine newBTE	= new BackwardsTransformerEngine(modelObj, targetJaxbObject);
													newBTE.transform();
												}
												else {
													Class<? extends IFieldTransformer<Object,Object>> clazz	= 
															(Class<? extends IFieldTransformer<Object,Object>>) Class.forName( tMetadata.backwardsTransformerClassName() );
													IFieldTransformer<Object, Object> transformer	= clazz.newInstance();
													
													transformer.populate(targetJaxbObject, modelObj);
													
												}
												if ( childRepresenterName != null ) {
													((IChildRepresenter)modelObj).setName(childRepresenterName);
												}
											}
										}
									}
								}
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					else if ( tMetadata.isContainer() ) {
						Object fieldObj					= this.getFieldObject(fields[i], this.getSource());
						if ( fieldObj != null ) {
							BackwardsTransformerEngine newBTE	= new BackwardsTransformerEngine(fieldObj, this.getDestination() );
							newBTE.transform();
						}
					}
					else 
						if ( !TransformationConstants.NONE.equals(tMetadata.jaxbMapping()) ){
							Pair<Field,Object> pair	= this.getDestinationField(tMetadata.jaxbMapping());
							if ( pair != null && pair.getTerm1() != null ) {
								Object fieldObj			= this.getFieldObject(pair.getTerm1(), pair.getTerm2() );
								try {
									if ( fieldObj != null) {
										PropertyDescriptor pd 		= new PropertyDescriptor(fields[i].getName(), fields[i].getDeclaringClass() );
										
										String className			= TransformationConstants.NONE.equals(tMetadata.backwardsTransformerClassName())?
																tMetadata.transformerClassName():tMetadata.backwardsTransformerClassName();
										
										Class<? extends IFieldTransformer<Object,Object>> clazz	= 
												(Class<? extends IFieldTransformer<Object,Object>>) Class.forName( className );
										
										IFieldTransformer<Object, Object> transformer	= clazz.newInstance();
										Object transformedObject		= transformer.transform(fieldObj);
										
										Object [] params	= { transformedObject };
										pd.getWriteMethod().invoke(this.getSource(), params);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
						}
				}
			}
			
		}
	}

}
