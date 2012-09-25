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
public class AttributeTransformer implements IFieldTransformer<IatiMappingItem, AttributeType> {

	public AttributeType transform(IatiMappingItem source) {
		AttributeType ret	= new AttributeType();
		
		ret.setPrefix( source.getPrefix() );
		ret.setRef( source.getLabel() );
		ret.setType( source.getType() );
		ret.setContent( source.getValue() );
		
		return ret;
		
	}

	public void populate(IatiMappingItem source, AttributeType destination) {
		// TODO Auto-generated method stub
		
	}

}
