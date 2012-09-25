/**
 * 
 */
package org.dg.iati.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dg.iati.api.transformer.IChildRepresenter;
import org.dg.iati.api.transformer.IEmptyCheckable;
import org.dg.iati.api.transformer.TransformationMetadata;

/**
 * @author dan
 *
 */
public class IatiMappingElement implements IEmptyCheckable,IChildRepresenter, Serializable{

	/**
	 * 
	 */
	public IatiMappingElement() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * the query to be displayed in case there is a select to be added
	 */	
	@TransformationMetadata ( jaxbMapping = "query:content" )
	private String query							=	null;
	
	/**
	 * this is content (value) of the element
	 */
	@TransformationMetadata ( isContainer = true )
	private IatiMappingItem content					=	null;
	
	@TransformationMetadata ( jaxbMapping = "ref" )
	private String name							=	null;
	
	@TransformationMetadata ( jaxbMapping = "attribute", transformerClassName = "org.dg.iati.api.transformer.AttributeTransformer",
								backwardsTransformerClassName = "org.dg.iati.api.transformer.BackwardsAttributeTransformer",
								correlationClassName = "org.dg.iati.api.transformer.AttributeCorrelation" )
	private ArrayList<IatiMappingItem> attributes	=	new ArrayList<IatiMappingItem>();
	
	@TransformationMetadata ( jaxbMapping = "field", itemClassName = "org.dg.iati.api.jaxb.iatiApiMapping.Field",
			correlationClassName = "org.dg.iati.api.transformer.ElementCorrelation" )
	private ArrayList<IatiMappingElement> subItems	=	new ArrayList<IatiMappingElement>();
 
	public boolean hasItems(){
		if(subItems!=null && subItems.size()>0) return true;
		return false;
	}
	
	public ArrayList<IatiMappingElement> getSubItems() {
		return subItems;
	}

	public void setSubItems(ArrayList<IatiMappingElement> subItems) {
		this.subItems = subItems;
	}

	public IatiMappingElement(String query, IatiMappingItem content, ArrayList<IatiMappingItem> attributes) {
		super();
		this.query		= query;
		this.content 	= content;
		this.attributes = attributes;
	}

	public IatiMappingElement(String label) {
		super();
		this.content = new IatiMappingItem(label);
	}
	
	/**
	 * 
	 * @param label
	 * @param attributes
	 * @param listType
	 * listType = false - list of IatiMappingItem
	 * listType = true - list of Strings
	 */
	public IatiMappingElement(String label, List<?> attributes, boolean isStringList) {
		super();
		this.content = new IatiMappingItem(label);
		if(!isStringList)
			this.attributes = (ArrayList<IatiMappingItem>) attributes;
		else{
			this.attributes	= new ArrayList<IatiMappingItem>();
			if(attributes!=null)
				for (String s : (List<String>)attributes) {
					IatiMappingItem atr = new IatiMappingItem(s);
					this.attributes.add(atr);
				}
		}
	}
	
	public IatiMappingElement(String label, List<?> subItems, List<?> attributes, boolean isStringList) {
		super();
		this.content = new IatiMappingItem(label);
		if(!isStringList)
			this.attributes = (ArrayList<IatiMappingItem>) attributes;
		else{
			this.attributes	= new ArrayList<IatiMappingItem>();
			if(attributes!=null)
				for (String s : (List<String>)attributes) {
					IatiMappingItem atr = new IatiMappingItem(s);
					this.attributes.add(atr);
				}
		}
	   this.subItems	= new ArrayList<IatiMappingElement>();
	   if(subItems!=null)
		   this.getSubItems().addAll((Collection<? extends IatiMappingElement>) subItems);
	}
	
	public IatiMappingElement(String label, String query) {
		super();
		this.content 	= new IatiMappingItem(label);
		this.query 		=	query;
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public IatiMappingItem getContent() {
		return content;
	}

	public void setContent(IatiMappingItem content) {
		this.content = content;
	}

	public ArrayList<IatiMappingItem> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<IatiMappingItem> attributes) {
		this.attributes = attributes;
	}

	public boolean isRepresented(IChildRepresenter child) {
		if ( child.getName().equals(this.getName()+".content") )
			return true;
		else 
			return false;
	}

	public String getName() {
		if ( this.content != null ) {
			return this.content.getLabel();
		}
		return "";
	}

	public boolean isEmpty() {
		if ( this.getQuery() != null && this.getQuery().length() > 0 )
			return false;
		
		if ( this.getContent() != null && !this.getContent().isEmpty() ){
			return false;
		}
			
		if ( this.getAttributes() != null ) {
			for (IatiMappingItem imItem : this.getAttributes()) {
				if ( !imItem.isEmpty() )
					return false;
			}
		}
		if ( this.getSubItems() != null ) {
			for (IatiMappingElement imElem: this.getSubItems() ) {
				if ( !imElem.isEmpty() )
					return false;
			}
		}
		
		
		return true;
	}

	public void setName(String name) {
		if ( this.content != null ) {
			this.content.setLabel(name);
		}
	}
	
	
}
