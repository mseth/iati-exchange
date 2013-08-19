/**
 * 
 */
package org.dg.iati.importer.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.dg.iati.api.FooterPage;
import org.dg.iati.api.HeaderPage;
import org.dg.iati.importer.pageentities.GlobalSettingsImporter;
import org.dg.iati.importer.pageentities.dao.GlobalSettingsImporterDao;

/**
 * @author alex
 *
 */
public class EditImporterConfigurationPage extends WebPage {

	/**
	 * 
	 */
	public EditImporterConfigurationPage() {
		GlobalSettingsImporter gs	= new GlobalSettingsImporter();
		Model<GlobalSettingsImporter> model	= Model.of(gs);
		
		EditImporterConfigurationPanel contentPage = new EditImporterConfigurationPanel("contentPage", model);
		this.initialize(contentPage);
	}

	/**
	 * @param model
	 */
	public EditImporterConfigurationPage(IModel<?> model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parameters
	 */
	public EditImporterConfigurationPage(PageParameters parameters) {
		String configurationName	= parameters.get("name").toString();
		GlobalSettingsImporter gs	= new GlobalSettingsImporterDao(configurationName).get();
		Model<GlobalSettingsImporter> model	= Model.of(gs);
		
		EditImporterConfigurationPanel contentPage = new EditImporterConfigurationPanel("contentPage", model);
		this.initialize(contentPage);
	}

	
	private void initialize (EditImporterConfigurationPanel contentPage) {
		HeaderPage headerPage = new HeaderPage("headerPage");
		add(headerPage);
		add(contentPage);
		FooterPage footerPage = new FooterPage("footerPage");
		add(footerPage);
	}
	
}
