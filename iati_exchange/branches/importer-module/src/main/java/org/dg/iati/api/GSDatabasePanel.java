/**
 * 
 */
package org.dg.iati.api;

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
public class GSDatabasePanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8709403743987538597L;

	public GSDatabasePanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param model
	 */
	public GSDatabasePanel(String id, IModel<?> model) {
		super(id, model);
		IatiSettings settings	= (IatiSettings) model.getObject();
		
		TextField<String> url 	= new TextField<String>("url", new PropertyModel<String>(settings, "url"));
		url.add(new TipTipBehavior(new TipTipOptions().maxWidth("auto").edgeOffset(10)));
		add(url);
        TextField<String> username 	= new TextField<String>("username", new PropertyModel<String>(settings, "username"));
        username.add(new TipTipBehavior(new TipTipOptions().maxWidth("auto").edgeOffset(10)));
        add(username);
        TextField<String> password 	= new TextField<String>("password",new PropertyModel<String>(settings, "password"));
        password.add(new TipTipBehavior(new TipTipOptions().maxWidth("auto").edgeOffset(10)));
        add(password);
	}

}

