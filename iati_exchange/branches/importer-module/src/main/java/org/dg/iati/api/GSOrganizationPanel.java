/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.dg.iati.api.entity.Constants;
import org.dg.iati.api.entity.IatiSettings;

/**
 * @author dan
 *
 */
public class GSOrganizationPanel extends Panel {

	private static final long serialVersionUID = -802974147029268794L;

	/**
	 * @param id
	 */
	public GSOrganizationPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param settingsModel
	 */
	public GSOrganizationPanel(String id, IModel<?> settingsModel) {
		super(id, settingsModel);
		IatiSettings settings = (IatiSettings) settingsModel.getObject();
		
		TextField<String> orgName 	= new TextField<String>("orgName", new PropertyModel<String>(settings, "orgName"));
		add(orgName);

		TextField<String> orgType 	= new TextField<String>("orgType", new PropertyModel<String>(settings, "orgType"));
		add(orgType);
		
		TextField<String> orgRef 	= new TextField<String>("orgRef", new PropertyModel<String>(settings, "orgRef"));
		add(orgRef);
		
		final DropDownChoice<String> defaultCurrency = new DropDownChoice<String>("defCurrency", new PropertyModel<String>(settings, "defaultCurrency"),
				Constants.IATI_CURRENCIES);
		defaultCurrency.setOutputMarkupId(true);
		add(defaultCurrency);
		
		final DropDownChoice<String> defaultLanguage = new DropDownChoice<String>("defLanguage", new PropertyModel<String>(settings, "defaultLanguage"),
				Constants.IATI_LANG_KEY);
		defaultLanguage.setOutputMarkupId(true);
		add(defaultLanguage);

		final DropDownChoice<String> generatedTime = new DropDownChoice<String>("generatedTime", new PropertyModel<String>(settings, "generatedTime"),
				Constants.IATI_GENERATED_TIME_TYPE);
		generatedTime.setOutputMarkupId(true);
		add(generatedTime);

		
	}

}
