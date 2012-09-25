/**
 * 
 */
package org.dg.iati.api.transformer;

import org.dg.iati.api.entity.IatiMappingElement;
import org.dg.iati.api.jaxb.iatiApiMapping.Field;

/**
 * @author Alex
 *
 */
public class ElementCorrelation implements ICorrelation<IatiMappingElement, Field > {

	public boolean correlated(IatiMappingElement obj1, Field obj2) {
		if ( obj1 != null && obj1.getContent() != null && obj1.getContent().getLabel() != null && obj2 != null  ) {
			return obj1.getContent().getLabel().equals(obj2.getRef() );
		}
		return false;
	}

}
