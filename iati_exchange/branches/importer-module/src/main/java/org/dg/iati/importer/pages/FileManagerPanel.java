/**
 * 
 */
package org.dg.iati.importer.pages;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileExistsException;
import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.dg.iati.importer.pageentities.GlobalSettingsImporter;
import org.dg.iati.importer.source.loaders.SourcesManager;

/**
 * @author Alex
 *
 */
public class FileManagerPanel extends Panel {
	private static Logger logger	= Logger.getLogger(FileManagerPanel.class);

	FileUploadField uploadField		= null;
	String mappingName				= null;
	ListView<String> listOfFilesW 	= null;
	SourcesManager  srcManager		= null;
	
	/**
	 * @param id
	 */
	public FileManagerPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param model
	 */
	public FileManagerPanel(String id, IModel<GlobalSettingsImporter> model) {
		super(id, model);
		this.initialize(id, model);
	}
	
	private void initialize(String id, IModel<GlobalSettingsImporter> model) {
		this.mappingName					= model.getObject().getMappingName();

		this.setOutputMarkupId(true);
		this.srcManager						= new SourcesManager(mappingName);
		
		listOfFilesW						= new ListView<String>("file-list", this.getSourcesNames()  ){
			@Override
			protected void populateItem(ListItem<String> item) {
				Label label 	= new Label("src-file-name", item.getModelObject() );
				item.add(label);
				
				AjaxLink<String> deleteLink	= new AjaxLink<String>("delete-link", Model.of(item.getModelObject())) {

					@Override
					public void onClick(AjaxRequestTarget target) {
						String mappingName	= this.getModelObject();
						try {
							srcManager.deleteSource(mappingName);
							updateInterface(target, mappingName);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				item.add(deleteLink);
			}
		};
		listOfFilesW.setOutputMarkupId(true);
		add(listOfFilesW);
		
		uploadField		= new FileUploadField("file");
		add(uploadField);
		
		AjaxButton ajaxUploadBtn	= new AjaxButton("ajax-submit") {
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				String srcFileName	= uploadField.getFileUpload().getClientFileName();
				try {
					srcManager.saveSource(srcFileName, uploadField.getFileUpload().getInputStream() );
					updateInterface(target, srcFileName);
				} catch (FileExistsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				logger.error("Error occured while uploading a file");
				
			}
		};
		add(ajaxUploadBtn);
	}
	
	private List<String> getSourcesNames() {
		if (this.srcManager == null) 
			this.srcManager = new SourcesManager( this.mappingName );
		List<String> listOfFiles			= this.srcManager.getSources();
		return listOfFiles;
	}
	
	private void updateInterface(AjaxRequestTarget target, String srcFileName) throws IOException {
		List<String> sourceNames	= getSourcesNames();
		listOfFilesW.setList( sourceNames );
		
		EditImporterConfigurationPanel panel = this.findParent(EditImporterConfigurationPanel.class);
		panel.getDropDown().setChoices(sourceNames);
		target.add(panel.getDropDown());
		target.add(listOfFilesW.getParent());
	}
	
}
