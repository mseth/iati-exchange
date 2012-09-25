/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.dg.iati.api.jaxb.iatiApiMapping.IatiApiMapping;
import org.dg.iati.api.transformer.BackwardsTransformerEngine;
import org.dg.iati.api.transformer.jaxb.XmlFileReader;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.IatiUtils;

/**
 * @author dan
 *
 */
public class IndexPage extends WebPage {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6344332033079286920L;

	public IndexPage(final PageParameters parameters) {

		HeaderPage headerPage = new HeaderPage("headerPage");
		add(headerPage);
		StringValue nameValue		= parameters.get("name");
		CreateSettingContentPage contentPage = null;
		if (nameValue != null&& nameValue.toString() != null ) {
			XmlFileReader<IatiApiMapping> reader	= 
					new XmlFileReader<IatiApiMapping>(IatiApiMapping.class, nameValue.toString(),
							IatiUtils.getPropertyValue(ConfigConstants.MAPPING_FOLDER_NAME) );
			
			IatiApiMapping jaxbMapping				= reader.load();
			contentPage = new CreateSettingContentPage( "contentPage", jaxbMapping );
		}
		else
			contentPage = new CreateSettingContentPage( "contentPage");
				
		add(contentPage);
		FooterPage footerPage = new FooterPage("footerPage");
		add(footerPage);
		
		//System.out.println("testing");
    }
    
}
