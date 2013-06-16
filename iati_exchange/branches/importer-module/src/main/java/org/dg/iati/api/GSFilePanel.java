/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.dg.iati.api.entity.Constants;
import org.dg.iati.api.entity.IatiSettings;

/**
 * @author dan
 *
 */
public class GSFilePanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8303483441801123552L;

	/**
	 * @param id
	 */
	public GSFilePanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param model
	 */
	public GSFilePanel(String id, IModel<?> model) {
		super(id, model);
		IatiSettings settings = (IatiSettings) model.getObject();
		
		DropDownChoice<String> elType = new DropDownChoice<String>("fileType", new PropertyModel<String>(settings, "fileType"),Constants.IATI_FILE_TYPES);
		elType.setOutputMarkupId(true);
		elType.setVisibilityAllowed(true);
		add(elType);
		
		add(new FileUploadField("fileInput"));
		
	}

}
