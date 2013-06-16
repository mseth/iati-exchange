/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.dg.iati.api.entity.Constants;
import org.dg.iati.api.entity.IatiMappingItem;

import com.google.code.jqwicket.ui.tiptip.TipTipBehavior;
import com.google.code.jqwicket.ui.tiptip.TipTipOptions;

/**
 * @author dan
 *
 */
public class IatiMappingItemPanel extends Panel {

	/**
	 * @param id
	 */
	public IatiMappingItemPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param model
	 */
	public IatiMappingItemPanel(String id, IModel<IatiMappingItem> model, String elName) {
		super(id, model);
		
		Label elLabel = new Label("elLabel", elName);
		add(elLabel);
		
		TextField<String> elPrefix = new TextField<String>("elPrefix", new PropertyModel<String>(model, "prefix"));
		elPrefix.add(new TipTipBehavior(new TipTipOptions().maxWidth("auto").edgeOffset(10)));  
		add(elPrefix);
		
		TextField<String> elValue = new TextField<String>("elValue", new PropertyModel<String>(model, "value"));
		elValue.add(new TipTipBehavior(new TipTipOptions().maxWidth("auto").edgeOffset(10)));
		add(elValue);

		DropDownChoice<String> elType = new DropDownChoice<String>("elType", new PropertyModel<String>(model, "type"), Constants.CONTENT_TYPE_LIST);
		add(elType);
	}

}
