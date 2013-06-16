/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author dan
 *
 */
public class ManageSettingsPage extends WebPage {

	/**
	 * 
	 */
	public ManageSettingsPage() {
		// TODO Auto-generated constructor stub
		HeaderPage headerPage = new HeaderPage("headerPage");
		add(headerPage);
		ManageSettingsContentPage contentPage = new ManageSettingsContentPage("contentPage");
		add(contentPage);
		FooterPage footerPage = new FooterPage("footerPage");
		add(footerPage);
	}

	/**
	 * @param model
	 */
	public ManageSettingsPage(IModel<?> model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parameters
	 */
	public ManageSettingsPage(PageParameters parameters) {
		super(parameters);
	}
	
}
