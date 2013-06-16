/**
 * 
 */
package org.dg.iati.api.transformer;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.log4j.Logger;

/**
 * @author Alex
 *
 */
public class TransformerEngine {
	
	private Object source;
	private Object destination;
	private static Logger logger 	= Logger.getLogger(TransformerEngine.class);
	
	public TransformerEngine(Object source, Object destination) {
		super();
		this.source = source;
		this.destination = destination;
	}
	
	public void transform() {
		Field[] fields					= this.source.getClass().getDeclaredFields();
		if ( fields != null && fields.length > 0 ) {
			for (int i=0; i<fields.length; i++) {
				TransformationMetadata tMetadata	= fields[i].getAnnotation(TransformationMetadata.class);
				if ( tMetadata != null ){
					
					if ( Collection.class.isAssignableFrom( fields[i].getType() )) {
						/* A collection needs to map to another collection */
						Object fieldObj					= this.getFieldObject(fields[i], this.source);
						if ( fieldObj != null ) {
							Collection coll			= (Collection<Object>) fieldObj;
							Pair<Field,Object> pair	= this.getDestinationField(tMetadata.jaxbMapping());
							if ( pair != null && pair.getTerm1() != null ) {
								try {
//									String propertyName			= pair.getTerm1().getName();
//									String readMethodName		= "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
//									PropertyDescriptor pd 		= new PropertyDescriptor(propertyName, pair.getTerm2().getClass(), readMethodName, null );
//									Collection destCollection	= (Collection) pd.getReadMethod().invoke(pair.getTerm2());
									Collection destCollection	= (Collection) this.getFieldObject(pair.getTerm1(), pair.getTerm2());
									for (Object obj : coll) {
										if ( this.isObjectEmpty(obj) )
											continue;
										if ( !TransformationConstants.NONE.equals(tMetadata.itemClassName()) ) {
											Class<?> clazz			= Class.forName(tMetadata.itemClassName());
											Object newDestination 		= clazz.newInstance();
											TransformerEngine newTE	= new TransformerEngine(obj, newDestination);
											if ( obj instanceof IChildRepresenter && this.source instanceof IChildRepresenter ) {
												IChildRepresenter child		= (IChildRepresenter) obj;
												IChildRepresenter parent	= (IChildRepresenter) this.source;
												String originalChildName	= child.getName();
												if ( parent.isRepresented(child) ) {
													child.setName(parent.getName());
													newTE		= new TransformerEngine(obj, this.destination);
													newTE.transform();
													child.setName(originalChildName);
													continue;
												}
											}
											newTE.transform();
											destCollection.add(newDestination);
										}
										else {
											Class<? extends IFieldTransformer<Object,Object>> clazz	= 
													(Class<? extends IFieldTransformer<Object,Object>>) Class.forName( tMetadata.transformerClassName() );
											IFieldTransformer<Object, Object> transformer	= clazz.newInstance();
											Object destObj		= transformer.transform(obj);
											destCollection.add(destObj);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								
							}
						}
					}
					else if ( tMetadata.isContainer() ) {
						Object fieldObj					= this.getFieldObject(fields[i], this.source);
						if ( this.isObjectEmpty(fieldObj) )
							continue;
						if ( fieldObj != null ) {
							TransformerEngine newTE	= new TransformerEngine(fieldObj, this.destination);
							newTE.transform();
						}
					}
					
					else {
						
						Pair<Field,Object> pair	= this.getDestinationField(tMetadata.jaxbMapping());
						Object fieldObj					= this.getFieldObject(fields[i], this.source);
						if ( this.isObjectEmpty(fieldObj) )
							continue;
						try {
							if ( pair != null && pair.getTerm1() != null && fieldObj != null) {
								PropertyDescriptor pd 		= new PropertyDescriptor(pair.getTerm1().getName(), pair.getTerm2().getClass() );
								
								Class<? extends IFieldTransformer<Object,Object>> clazz	= 
										(Class<? extends IFieldTransformer<Object,Object>>) Class.forName( tMetadata.transformerClassName() );
								
								IFieldTransformer<Object, Object> transformer	= clazz.newInstance();
								Object transformedObject		= transformer.transform(fieldObj);
								
								Object [] params	= { transformedObject };
								pd.getWriteMethod().invoke(pair.term2, params);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
						
			}
			
		}
	}
	
	protected Pair<Field,Object> getDestinationField (String fieldName) {
		
		String [] pathElements	= fieldName.split(TransformationConstants.PATH_SEPARATOR);

		Object iterObj			= this.destination;
		Pair<Field,Object> pair	= null;
		for ( int i=0; i<pathElements.length; i++ ) {
			try {
				Field f					= iterObj.getClass().getDeclaredField(pathElements[i]);
				pair					= new Pair<Field, Object>(f, iterObj);
				Object tempObj			= null;
				PropertyDescriptor pd	= null;
				try{
					pd 						= new PropertyDescriptor(f.getName(), iterObj.getClass() );
					tempObj					= pd.getReadMethod().invoke(iterObj, new Object[0]);
				}
				catch (IntrospectionException e) {
					logger.info(e.getMessage() );
				}
				if ( tempObj == null && i<pathElements.length-1 ) {
					tempObj				= f.getType().newInstance();
					Object [] params 	= { tempObj };
					pd.getWriteMethod().invoke(iterObj, params);
				} 
				iterObj		= tempObj;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return pair;
		
	}
	
	protected Object getFieldObject(Field field, Object parentObject) {
		try {
			PropertyDescriptor pd 	= null;
			try {
				pd 	= new PropertyDescriptor(field.getName(), parentObject.getClass() );
			} catch (IntrospectionException e) {
				logger.info("Couldn't create PropertyDescriptor for field: " + field.getName() + " and class " + 
							parentObject.getClass() + ". Trying with constructor with null as writeMethod.");
				
				String propertyName			= field.getName();
				String readMethodName		= "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
				pd 							= new PropertyDescriptor(propertyName, parentObject.getClass(), readMethodName, null );
				
			}
			Object fieldObj				= pd.getReadMethod().invoke(parentObject, new Object[0]);
			return fieldObj;
		} catch (Exception e) {
			logger.warn( e.getMessage() );
			return null;
		}
	}
	
	private boolean isObjectEmpty(Object obj) {
		if ( obj != null && obj instanceof IEmptyCheckable ) {
			return ((IEmptyCheckable)obj).isEmpty();
		}
		return false;
	}
	
	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public Object getDestination() {
		return destination;
	}

	public void setDestination(Object destination) {
		this.destination = destination;
	}






	protected class Pair<T,P> {
		 T term1;
		 P term2;
		public Pair(T term1, P term2) {
			super();
			this.term1 = term1;
			this.term2 = term2;
		}
		public T getTerm1() {
			return term1;
		}
		public void setTerm1(T term1) {
			this.term1 = term1;
		}
		public P getTerm2() {
			return term2;
		}
		public void setTerm2(P term2) {
			this.term2 = term2;
		}
		 
	}
}
