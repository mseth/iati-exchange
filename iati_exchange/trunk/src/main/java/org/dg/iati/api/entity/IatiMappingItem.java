/**
 * 
 */
package org.dg.iati.api.entity;

import java.io.Serializable;

import org.dg.iati.api.transformer.IEmptyCheckable;
import org.dg.iati.api.transformer.TransformationMetadata;

/**
 * @author dan
 *
 */
public class IatiMappingItem implements IEmptyCheckable,Serializable {

	/**
	 * Iati label - the name from IATI Schema
	 */
	@TransformationMetadata ( jaxbMapping = "ref" )
	private String label	=	null;

	/**
	 * Possible values: column or constant
	 * CONTENT_TYPE_COLUMN = "column";
	 * CONTENT_TYPE_CONSTANT = "constant";
	 */
	@TransformationMetadata ( jaxbMapping = "content:type" )
	private String type		=	null;

	/**
	 * prefix that will be added to the value
	 */
	@TransformationMetadata ( jaxbMapping = "content:prefix" )
	private String prefix	=	null;
	
	/**
	 * the value of the attribute: it can be a constant value or it can be a column name from a query
	 */
	@TransformationMetadata ( jaxbMapping = "content:content" )
	private String value	=	null;
	
	public IatiMappingItem() {
		// TODO Auto-generated constructor stub
	}

	public IatiMappingItem(String label) {
		this();
		this.label 	= label;
		this.type	= Constants.CONTENT_TYPE_CONSTANT;	
	}

	
	public IatiMappingItem(String label, String type, String prefix, String value) {
		super();
		this.label = label;
		this.type = type;
		this.prefix = prefix;
		this.value = value;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getPrefix() {
		return prefix;
	}


	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

	public boolean isEmpty() {
		if ( this.value != null && this.value.length() > 0 )
			return false;
		return true;
	}



}
