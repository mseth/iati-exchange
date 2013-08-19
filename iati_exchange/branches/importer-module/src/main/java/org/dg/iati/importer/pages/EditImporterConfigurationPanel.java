/**
 * 
 */
package org.dg.iati.importer.pages;


import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.dg.iati.api.GSDatabasePanel;
import org.dg.iati.api.entity.Constants;
import org.dg.iati.importer.pageentities.GlobalSettingsImporter;
import org.dg.iati.importer.pageentities.dao.GlobalSettingsImporterDao;
import org.dg.iati.importer.source.loaders.SourcesManager;

import com.google.code.jqwicket.JQBehavior;
import com.google.code.jqwicket.ui.tabs.TabsWebMarkupContainer;

/**
 * @author alex
 *
 */
public class EditImporterConfigurationPanel extends Panel {

	private Form<GlobalSettingsImporter> form	= null;
	private DropDownChoice<String> dropDown		= null;	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2649637905354428625L;

	/**
	 * @param id
	 */
	public EditImporterConfigurationPanel(String id) {
		super(id);
	}

	/**
	 * @param id
	 * @param model
	 */
	public EditImporterConfigurationPanel(String id, IModel<GlobalSettingsImporter> model) {
		super(id, model);
		this.setOutputMarkupId(true);
		
		SourcesManager sManager					= new SourcesManager( model.getObject().getMappingName() );
		List<String> sourcesList				= sManager.getSources();
				
		this.form 				= new Form<GlobalSettingsImporter>("edit-importer-config-form", model);
		add(form);
		
		TabsWebMarkupContainer tabs = new TabsWebMarkupContainer("mainTabs");
		tabs.add(new JQBehavior(Constants.UI_TABS_JQUERY));
		form.add(tabs);
		
		final GSDatabasePanel<GlobalSettingsImporter>	dbPanel		= 
				new GSDatabasePanel<GlobalSettingsImporter>("database-settings", model);
		dbPanel.setVisibilityAllowed(true);
		dbPanel.setOutputMarkupPlaceholderTag(true);
		dbPanel.setOutputMarkupId(true);
		tabs.add(dbPanel);
		
		this.dropDown			= new DropDownChoice<String>("filename-select", 
				new PropertyModel<String>(model, "filename"), sourcesList);
		dropDown.setOutputMarkupId(true);
		tabs.add(dropDown);
		
		FileManagerPanel filePanel	= new FileManagerPanel("file-upload", model);
		tabs.add(filePanel);
		
		Button submitButton		= new Button("submit-button") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7852362133499273760L;

			@Override
			public void onSubmit() {
				System.out.println("!!" + ((GlobalSettingsImporter)getForm().getModelObject()).getUsername());
				
				GlobalSettingsImporter gsi	= form.getModelObject();
				GlobalSettingsImporterDao gsiDao	= new GlobalSettingsImporterDao(gsi.getMappingName());
				gsiDao.set(gsi);
				
				setResponsePage(ManageImporterConfigurationPage.class);
			}
		};
		form.add(submitButton);
		
	}

	/**
	 * @return the dropDown
	 */
	public DropDownChoice<String> getDropDown() {
		return dropDown;
	}

	

}
