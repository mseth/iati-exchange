/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.dg.iati.api.entity.IatiMappingElement;

/**
 * @author dan
 *
 */
public class IatiMappingSimpleElementPanel extends Panel {

	/**
	 * @param id
	 */
	public IatiMappingSimpleElementPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param model
	 */
	public IatiMappingSimpleElementPanel(String id, IModel<IatiMappingElement> model) {
		super(id, model);
		add(new Label("elTitle", model.getObject().getContent().getLabel()));
	    add(new IatiMappingSimpleElement("simpleElementId", model));
	}

}
