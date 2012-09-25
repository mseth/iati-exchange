/**
 * 
 */
package org.dg.iati.api.transformer;

import org.dg.iati.api.entity.IatiMappingItem;
import org.dg.iati.api.jaxb.iatiApiMapping.AttributeType;


/**
 * @author Alex
 *
 */
public class AttributeCorrelation implements ICorrelation<IatiMappingItem, AttributeType> {

	public boolean correlated(IatiMappingItem obj1, AttributeType obj2) {
		if ( obj1 != null && obj1.getLabel() != null && obj2 != null && obj1.getLabel().equals(obj2.getRef()) )
			return true;
		return false;
	}

}
