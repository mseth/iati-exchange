package org.dg.iati.api.wicket.csvmapper;

import java.util.List;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.dg.iati.api.entity.IatiSettings;

public class CsvMapperPanel extends Panel {
	public CsvMapperPanel(String id, IModel<?> model) {
		super(id, model);
		IatiSettings settings	= (IatiSettings) model.getObject();
		
		TextField<String> csvMapperFilename 	= new TextField<String>("csvMapperFilename", new PropertyModel<String>(settings, "csvMapperFilename"));	
		add(csvMapperFilename);
		
		IModel<List<FileUpload>> imodel			= new PropertyModel<List<FileUpload>> (model,"csvMapperFileUpload");
		FileUploadField fuf			= new FileUploadField("csvMapperFileUpload", imodel);
		add(fuf);
		
		
	}
}
