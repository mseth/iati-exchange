/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.dg.iati.api.entity.IatiSettings;

import com.google.code.jqwicket.ui.tiptip.TipTipBehavior;
import com.google.code.jqwicket.ui.tiptip.TipTipOptions;

/**
 * @author dan
 *
 */
public class GlobalQueriesPanel extends Panel {

	/**
	 * @param id
	 */
	public GlobalQueriesPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param model
	 */
	public GlobalQueriesPanel(String id, IModel<?> model) {
		super(id, model);
		IatiSettings settings	= (IatiSettings) model.getObject();
		
		TextArea<String> globalQuery 		= new TextArea<String>("globalQuery", new PropertyModel<String>(settings, "globalQuery"));
		globalQuery.add(new TipTipBehavior(new TipTipOptions().maxWidth("auto").edgeOffset(10)));
		add(globalQuery);
		
		TextArea<String> globalQueryIDs 	= new TextArea<String>("globalQueryIDs", new PropertyModel<String>(settings, "globalQueryIDs"));
		globalQueryIDs.add(new TipTipBehavior(new TipTipOptions().maxWidth("auto").edgeOffset(10)));
		add(globalQueryIDs);
	}

}
