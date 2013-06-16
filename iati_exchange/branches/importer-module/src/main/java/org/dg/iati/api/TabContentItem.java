/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.dg.iati.api.entity.IatiMappingElement;

/**
 * @author dan
 *
 */
public class TabContentItem extends Panel {

	/**
	 * @param id
	 */
	public TabContentItem(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param model
	 */
	public TabContentItem(String id, IModel<IatiMappingElement> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
		WebMarkupContainer tabLabel = new WebMarkupContainer("tabLabel");
		tabLabel.add(new AttributeModifier("id", Model.of("tabs-"+model.getObject().getContent().getLabel())));
		boolean isComplex = model.getObject().hasItems();
		if(isComplex)
			tabLabel.add(new IatiMappingComplexElement("elementTabId",model));
		else
			tabLabel.add(new IatiMappingSimpleElement("elementTabId",model));
		add(tabLabel);
	}

}
