/**
 * 
 */
package org.dg.iati.importer.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.dg.iati.api.ExtractInfoContentPage;
import org.dg.iati.api.FooterPage;
import org.dg.iati.api.HeaderPage;

/**
 * @author alex
 *
 */
public class ManageImporterConfigurationPage extends WebPage {

	/**
	 * 
	 */
	public ManageImporterConfigurationPage() {
		HeaderPage headerPage = new HeaderPage("headerPage");
		add(headerPage);
		ManageImporterConfigurationContentPanel contentPage = new ManageImporterConfigurationContentPanel("contentPage");
		add(contentPage);
		FooterPage footerPage = new FooterPage("footerPage");
		add(footerPage);
	}

	/**
	 * @param model
	 */
	public ManageImporterConfigurationPage(IModel<?> model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parameters
	 */
	public ManageImporterConfigurationPage(PageParameters parameters) {
		super(parameters);
		// TODO Auto-generated constructor stub
	}

}
