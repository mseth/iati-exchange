/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.dg.iati.api.entity.IatiMappingElement;
import org.dg.iati.api.entity.IatiMappingItem;

import com.google.code.jqwicket.ui.tiptip.TipTipBehavior;
import com.google.code.jqwicket.ui.tiptip.TipTipOptions;

/**
 * @author dan
 *
 */
public class IatiMappingSimpleElement extends Panel {

	/**
	 * @param id
	 */
	public IatiMappingSimpleElement(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param model
	 */
	public IatiMappingSimpleElement(String id, final IModel<IatiMappingElement> model) {
		super(id, model);
		
		final IatiMappingElement el = model.getObject();
		//add(new Label("elTitle", el.getContent().getLabel()));
		TextArea<String> elQuery 	= new TextArea<String>("elQuery",new PropertyModel<String>(model, "query"));
		elQuery.add(new TipTipBehavior(new TipTipOptions().maxWidth("auto").edgeOffset(10)));
		add(elQuery);
		
		IatiMappingItemPanel item = new IatiMappingItemPanel("item", new PropertyModel<IatiMappingItem>(model, "content"), "Content");
		add(item);
		
		//attributes list
		ListView<IatiMappingItem> attributesList = new ListView<IatiMappingItem>("attributesRepeater", el.getAttributes()) {  
			private static final long serialVersionUID = -6765479995837605654L;
			protected void populateItem(ListItem<IatiMappingItem> item) {
				String attrName 			= item.getModel().getObject().getLabel();
				IatiMappingItemPanel attr 	= new IatiMappingItemPanel("itemAttribute",item.getModel(), attrName);
                item.add(attr);
            }
        };
		add(attributesList);

		
	}

}
