/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.dg.iati.api.entity.IatiSettings;

/**
 * @author dan
 *
 */
public class OrganizationSettings extends Panel {

	/**
	 * @param id
	 */
	public OrganizationSettings(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param model
	 */
	public OrganizationSettings(String id, IModel<?> settingsModel) {
		super(id, settingsModel);
		// TODO Auto-generated constructor stub
		
        GSOrganizationPanel	orgPanel			= new GSOrganizationPanel("organizationSettings", settingsModel);
        add(orgPanel);
	}

}
