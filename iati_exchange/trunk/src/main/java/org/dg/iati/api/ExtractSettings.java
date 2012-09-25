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
public class ExtractSettings extends Panel {

	/**
	 * @param id
	 */
	public ExtractSettings(String id) {
		super(id);
	}

	/**
	 * @param id
	 * @param model
	 */
	public ExtractSettings(String id, IModel<?> model) {
		super(id, model);
	}

}
