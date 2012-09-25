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
public class BackwardsAttributeTransformer implements IFieldTransformer<AttributeType, IatiMappingItem> {

	public IatiMappingItem transform(AttributeType source) {
		// TODO Auto-generated method stub
		return null;
	}

	public void populate(AttributeType source, IatiMappingItem destination) {
		destination.setPrefix(source.getPrefix() );
		destination.setType( source.getType() );
		destination.setValue( source.getContent() );
		
	}

	

}
