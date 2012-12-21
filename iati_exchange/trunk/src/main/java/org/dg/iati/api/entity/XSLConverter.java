/**
 * 
 */
package org.dg.iati.api.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.dg.iati.api.jaxb.iatiApiResult.IatiActivity;
import org.dg.iati.api.jaxb.iatiApiResult.Item;
import org.dg.iati.api.jaxb.iatiApiResult.RefType;

/**
 * @author dan
 *
 */
public class XSLConverter {

	/**
	 * 
	 */
	public XSLConverter() {
		// TODO Auto-generated constructor stub
	}

	private String header;
	private String footer;
	private String meta;
	private String content;
	private ArrayList<String> templates	= new ArrayList<String>();
	
	private TreeSet<String> labels	= new TreeSet<String>();
	
	public TreeSet<String> getLabels() {
		return labels;
	}
	public void setLabels(TreeSet<String> labels) {
		this.labels = labels;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<String> getTemplates() {
		return templates;
	}
	public void setTemplates(ArrayList<String> templates) {
		this.templates = templates;
	}

	public void createSkeleton(){
		this.header		= Constants.IATI_XSL_HEADER;
		this.meta 		= Constants.IATI_XSL_META;
		this.content	= Constants.IATI_XSL_CONTENT;
		this.footer		= Constants.IATI_XSL_FOOTER;
	}
	
	/**
	 *  <xsl:template match="item[@ref='sector']">
      		<xsl:variable name="code"><xsl:value-of select="./attribute[@ref='code']"/></xsl:variable>
      		<xsl:variable name="vocabulary"><xsl:value-of select="./attribute[@ref='vocabulary']"/></xsl:variable>
      		<xsl:variable name="percentage"><xsl:value-of select="./attribute[@ref='percentage']"/></xsl:variable>
      		<sector code="{$code}" vocabulary="{$vocabulary}" percentage="{$percentage}" ><xsl:value-of select="./value"/></sector>
      	</xsl:template>
	 * @param item 
	 * @param resultItem
	 * @return
	 */
	
	private String createXSLVariable(String name, String item, boolean firstLevel){
		if(firstLevel)
			return "<xsl:variable name=\""+name+"\"><xsl:value-of select=\"./attribute[@ref='"+name+"']\"/></xsl:variable>";
		else
			return  "<xsl:variable name=\""+name+"\"><xsl:value-of select=\"./item[@ref='"+item+"']/attribute[@ref='"+name+"']\"/></xsl:variable>";
	}
	
	private String createXSLElement(String name, List<RefType> attributes, List<Item> subItems, String fullName, boolean firstLevel){
		String result	= "<"+name+" ";
		boolean hasItems = false;
		if(subItems	!=	null && subItems.size()>0)
			hasItems	= true;
		for (RefType attr : attributes) {
			result	+= " "+attr.getRef()+"=\"{$"+attr.getRef()+"}\"";
		}
		result+=">";
		if(!hasItems)
			{
				//result	+= "<xsl:value-of select=\"./value\"/>";
				if(firstLevel)
					 	result	+= "<xsl:value-of select=\"./value\"/>";
				else	result	+= "<xsl:value-of select=\"./item[@ref='"+name+"']/value\"/>";
			}
		else{
			for (Item i : subItems) {
				if( !this.getLabels().contains(i.getRef()) ){
					this.getTemplates().add(createTemplate(i,name,false));
					this.getLabels().add(i.getRef());
				}
				result	+= "<xsl:call-template name=\""+fullName+"."+i.getRef()+"\"></xsl:call-template>";
			}
		}
		result	+= "</"+name+">";
		return result;
	}
	
	private String createTemplate(Item resultItem, String parent, boolean firstLevel){
		String result	= "";
		String name 	= "";
		//if(resultItem.getItem()	!=	null && resultItem.getItem().size()>0)
		if(	parent!=null )
				name		= parent+"."+resultItem.getRef();
		else name = resultItem.getRef();
		result+="<xsl:template match=\"item[@ref='"+resultItem.getRef()+"']\" name=\""+name+"\">";
		for (RefType attr : resultItem.getAttribute()) {
			result+=createXSLVariable(attr.getRef(),resultItem.getRef(), firstLevel);
		}
		result+=createXSLElement(resultItem.getRef(), resultItem.getAttribute(), resultItem.getItem(),name, firstLevel);
		result+="</xsl:template>";
		return result;
	}
	
	public void run(IatiActivity resultActivity) {
		createTemplates(resultActivity);
	}
	
	private void createTemplates(IatiActivity resultActivity) {
		for (Item i : resultActivity.getItem()) {
			if( !this.getLabels().contains(i.getRef()) ){
					this.getTemplates().add(createTemplate(i,null,true));
					this.getLabels().add(i.getRef());
			}
		}
	}
	
	private String printList(List<String> l){
		String result = "";
		for (String i : l) {
			result	+= i;
		}
		return result;
	}
	
	public String printTemplates(){
		return printList(this.getTemplates());
	}
	
	public String getXSL(){
		String result	= "";
		result +=	this.getHeader();
		result +=	this.getMeta();
		result +=	this.getContent();
		result +=	printTemplates();
		result +=	this.getFooter();
		return result;
	}
	
	public void printXSL(){
		System.out.println(getXSL());
	}
	
}
