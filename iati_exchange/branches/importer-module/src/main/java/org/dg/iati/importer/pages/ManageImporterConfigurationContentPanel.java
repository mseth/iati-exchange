package org.dg.iati.importer.pages;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.dg.iati.api.ManageSettingsPage;
import org.dg.iati.api.transformer.jaxb.SavedMappingList;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.ConfigInstance;

public class ManageImporterConfigurationContentPanel extends Panel {

	public ManageImporterConfigurationContentPanel(String id) {
		super(id);
		
		List<String> listOfConfigs		= new SavedMappingList(ConfigInstance.getInstance().get( ConfigConstants.IMPORT_MAPPING_FOLDER_NAME ) ).showSavedMappings();
		
		ListView<String> listOfConfigsW	= new ListView<String>("configuration-list", listOfConfigs ) {
			@Override
			protected void populateItem(ListItem<String> item) {
				String configName		= item.getModelObject();
				Label label		= new Label( "name", configName );
				
				Link<String> link = new Link<String>("edit-link", Model.of(configName) ) {

					/**
					 * 
					 */
					private static final long serialVersionUID = -5271241719336837142L;

					@Override
					public void onClick() {
						PageParameters pageParameters = new PageParameters();
						String name		= getModelObject();
						System.out.println("!!! " + name);
						pageParameters.add("name",name);
						setResponsePage(EditImporterConfigurationPage.class, pageParameters);
						
					}
					
				};
				
				item.add(label);
				item.add(link);
				
			}
		};
		
		this.add(listOfConfigsW);
		
	}
	
	/**
	 * @param id
	 * @param model
	 */
	public ManageImporterConfigurationContentPanel(String id, IModel<?> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}

}
