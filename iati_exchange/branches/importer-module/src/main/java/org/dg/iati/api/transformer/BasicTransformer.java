/**
 * 
 */
package org.dg.iati.api.transformer;

/**
 * @author Alex
 *
 */
public class BasicTransformer implements IFieldTransformer<Object, Object> {

	public Object transform(Object source) {
		return source;
	}

	public void populate(Object source, Object destination) {
		// TODO Auto-generated method stub
		
	}

	
	
}
