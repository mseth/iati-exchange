/**
 * 
 */
package org.dg.iati.api.forms;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Bytes;
import org.dg.iati.api.entity.IatiSettings;

/**
 * @author dan
 *
 */
public class SaveXMLForm extends Form {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -5080745970446167221L;
	private IatiSettings o;
	
	public IatiSettings getO() {
		return o;
	}

	public void setO(IatiSettings o) {
		this.o = o;
	}

	/**
	 * @param id
	 * @param model
	 */
	public SaveXMLForm(String id, IModel<?> model) {
		super(id, model);
		this.o	= (IatiSettings) model.getObject();
        // set this form to multipart mode (allways needed for uploads!)
        setMultiPart(true);
        setMaxSize(Bytes.kilobytes(10000));
	}

     public SaveXMLForm(String name)
     {
         super(name);

         // set this form to multipart mode (allways needed for uploads!)
         setMultiPart(true);

         // Add one file input field
         //add(fileUploadField = new FileUploadField("fileInput"));

         // Set maximum size to 100K for demo purposes
         setMaxSize(Bytes.kilobytes(10000));
     }

     /**
      * @see org.apache.wicket.markup.html.form.Form#onSubmit()
      */
     @Override
     protected void onSubmit()
     {
        // System.out.println("Form Submitted OK" + this.getO().getSettingName());
     }

}
