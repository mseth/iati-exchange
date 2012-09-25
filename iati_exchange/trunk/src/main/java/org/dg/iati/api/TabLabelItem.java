/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.dg.iati.api.entity.IatiMappingElement;

/**
 * @author dan
 *
 */
public class TabLabelItem extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 526346538010682467L;

	/**
	 * @param id
	 */
	public TabLabelItem(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param model
	 */
	public TabLabelItem(String id, IModel<IatiMappingElement> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
		String name = model.getObject().getContent().getLabel();
		add(new ExternalLink("iatiLabelTab", "#tabs-"+name, name));
	}

}
