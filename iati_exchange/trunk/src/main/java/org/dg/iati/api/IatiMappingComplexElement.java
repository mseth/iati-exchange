/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.dg.iati.api.entity.IatiMappingElement;

import com.google.code.jqwicket.ui.accordion.AccordionWebMarkupContainer;

/**
 * @author dan
 *
 */
public class IatiMappingComplexElement extends Panel {

	/**
	 * @param id
	 */
	public IatiMappingComplexElement(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param model
	 */
	public IatiMappingComplexElement(String id, IModel<IatiMappingElement> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
		
      AccordionWebMarkupContainer accordion = new AccordionWebMarkupContainer("accordion");  

      accordion.add(new ListView<IatiMappingElement>("repeaterSubItems", model.getObject().getSubItems()) {  
			private static final long serialVersionUID = -9174869459288445406L;
			protected void populateItem(ListItem<IatiMappingElement> item) {  
              item.add(new IatiMappingSimpleElementPanel("subItemId", item.getModel()));
          }  
      });  

      add(accordion);
		
	}

}
