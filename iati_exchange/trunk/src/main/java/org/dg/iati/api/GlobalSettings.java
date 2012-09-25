/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.dg.iati.api.entity.Constants;
import org.dg.iati.api.entity.IatiSettings;
import org.dg.iati.api.wicket.csvmapper.CsvMapperPanel;

import com.google.code.jqwicket.ui.tiptip.TipTipBehavior;
import com.google.code.jqwicket.ui.tiptip.TipTipOptions;

/**
 * @author dan
 *
 */
public class GlobalSettings extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -24585867229798998L;

	/**
	 * @param id
	 */
	public GlobalSettings(String id) {
		super(id);
		// TODO Auto-generated constructor stub

	}

	/**
	 * @param id
	 * @param rootMapping
	 */
	public GlobalSettings(String id, IModel<?> settingsModel) {
		super(id, settingsModel);
		final IatiSettings settings = (IatiSettings) settingsModel.getObject();
        
		final DropDownChoice<String> elType = new DropDownChoice<String>("datasourceType", new PropertyModel<String>(settings, "datasourceType"),Constants.IATI_DATASOURCE_TYPES);
		elType.setOutputMarkupId(true);
		add(elType);
        
        TextField<String> settingNameField 				= new TextField<String>("settingName",new PropertyModel<String>(settings, "settingName"));
        settingNameField.add(new TipTipBehavior(new TipTipOptions().maxWidth("auto").edgeOffset(10)));
        settingNameField.setOutputMarkupId(true);
        add(settingNameField);
        
        final GSDatabasePanel	dbPanel					= new GSDatabasePanel("databaseSettings", settingsModel);
        dbPanel.setVisibilityAllowed(true);
        dbPanel.setOutputMarkupPlaceholderTag(true);
        dbPanel.setOutputMarkupId(true);
        add(dbPanel);

        final GSFilePanel	filePanel					= new GSFilePanel("fileUploadSettings", settingsModel);
        filePanel.setVisibilityAllowed(true);
        filePanel.setOutputMarkupId(true);
        filePanel.setOutputMarkupPlaceholderTag(true);
        filePanel.setVisible(false);
        add(filePanel);
        
        final CsvMapperPanel csvMapperPanel				= new CsvMapperPanel("csvMapperSettings", settingsModel);
        dbPanel.setVisibilityAllowed(true);
        dbPanel.setOutputMarkupPlaceholderTag(true);
        dbPanel.setOutputMarkupId(true);
        add(csvMapperPanel);

		elType.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            /**
			 * 
			 */
			private static final long serialVersionUID = -1532123343776642032L;

			protected void onUpdate(AjaxRequestTarget target) {
				if ( Constants.IATI_DATASOURCE_FILE.equals( elType.getModelObject() ) ) {
					filePanel.setVisible(true);
					dbPanel.setVisible(false);
				}
				else {
					filePanel.setVisible(false);
					dbPanel.setVisible(true);
				} 
					
                target.add(filePanel);
                target.add(dbPanel);
            }
        });
        
//        GSOrganizationPanel	orgPanel			= new GSOrganizationPanel("organizationSettings", settingsModel);
//        add(orgPanel);
        

        
	}

}
