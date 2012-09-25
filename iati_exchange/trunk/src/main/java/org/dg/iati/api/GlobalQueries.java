/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @author dan
 *
 */
public class GlobalQueries extends Panel {

	/**
	 * @param id
	 */
	public GlobalQueries(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param model
	 */
	public GlobalQueries(String id, IModel<?> settingsModel) {
		super(id, settingsModel);
		// TODO Auto-generated constructor stub
        GlobalQueriesPanel	orgPanel			= new GlobalQueriesPanel("globalQueries", settingsModel);
        add(orgPanel);
	}

}
