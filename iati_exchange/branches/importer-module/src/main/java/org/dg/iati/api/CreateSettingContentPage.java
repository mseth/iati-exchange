/**
 * 
 */
package org.dg.iati.api;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.dg.iati.api.entity.Constants;
import org.dg.iati.api.entity.IatiSettings;
import org.dg.iati.api.file.SaveFileUpload;
import org.dg.iati.api.file.exception.CannotSaveUploadedFileException;
import org.dg.iati.api.forms.SaveXMLForm;
import org.dg.iati.api.jaxb.iatiApiMapping.IatiApiMapping;
import org.dg.iati.api.transformer.BackwardsTransformerEngine;
import org.dg.iati.api.transformer.TransformerEngine;
import org.dg.iati.api.transformer.jaxb.XmlFileWriter;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.IatiUtils;

import com.google.code.jqwicket.JQBehavior;
import com.google.code.jqwicket.ui.tabs.TabsWebMarkupContainer;
/**
 * @author dan
 *
 */
public class CreateSettingContentPage extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2414786748936968165L;
	private IatiSettings settings;

	/**
	 * @param id
	 */
	public CreateSettingContentPage(String id, IatiApiMapping jaxbObject) {
		super(id);
		this.settings	= new IatiSettings();
	    //settings.setMappingItems(IatiUtils.generateStaticIATIElements());
		try {
			settings.setMappingItems(IatiUtils.generateIATIElements());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ( jaxbObject != null) {
			BackwardsTransformerEngine bte	= new BackwardsTransformerEngine(this.settings, jaxbObject);
			bte.transform();
		}
		final SaveXMLForm tf = new SaveXMLForm("createXMLForm", new CompoundPropertyModel<IatiSettings>(settings));
		tf.setMultiPart(true);
		add(tf);

		TabsWebMarkupContainer tabs = new TabsWebMarkupContainer("mainTabs");
		tabs.add(new JQBehavior(Constants.UI_TABS_JQUERY));
		tf.add(tabs);
		
		final GlobalSettings gs	= new GlobalSettings("globalSettings", Model.of(settings));
		gs.setOutputMarkupId(true);
		tabs.add(gs);


	    AjaxButton submitButton = new AjaxButton("ajaxSubmit", tf)
        {
            /**
			 * 
			 */
			private static final long serialVersionUID = 3629310526532211586L;

			@Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
            	//System.out.println("OK - On submit" + settings.getSettingName());
            	IatiApiMapping jaxbRoot		= new IatiApiMapping();
            	if ( settings.getCsvMapperFileUpload() != null && settings.getCsvMapperFileUpload().size() > 0 ) {
            		try {
            			String csvMapperPath	= 
            					new SaveFileUpload(settings.getCsvMapperFileUpload().get(0), IatiUtils.getPropertyValue(ConfigConstants.CSV_MAPPING_FOLDER_NAME) ).save();
            			settings.setCsvMapperFilename(csvMapperPath);
            			
            		}
            		catch (CannotSaveUploadedFileException ex){
            			ex.printStackTrace();
            		}
            	}
            	TransformerEngine tEngine	= new TransformerEngine(settings, jaxbRoot);
            	tEngine.transform();
            	String allowWSFilename = IatiUtils.getPropertyValue("allow-ws-filename");
            	if(allowWSFilename!=null && "false".compareTo(allowWSFilename) == 0)
            		jaxbRoot.setMappingName(jaxbRoot.getMappingName().replaceAll(" ", "_"));
            	XmlFileWriter<IatiApiMapping> writer		= 
            			new XmlFileWriter<IatiApiMapping>(jaxbRoot, jaxbRoot.getMappingName(), XmlFileWriter.EXTENSION );
            	writer.persist();
            	
            	target.add(gs);
            	//System.out.println("Done");
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form)
            {
            	System.out.println("ERROR" + settings.getSettingName());
            }
        };
        submitButton.setDefaultFormProcessing(true);
        submitButton.setOutputMarkupId(true);
        
        tf.add(submitButton);
	    tf.setOutputMarkupId(true);
		
	
		GlobalQueries gq	= new GlobalQueries("globalQueries", Model.of(settings));
		tabs.add(gq);

		MappingSettings ms	= new MappingSettings("mappingSettings", Model.of(settings));
		tabs.add(ms);
		
		OrganizationSettings os	= new OrganizationSettings("organizationSettings", Model.of(settings));
		tabs.add(os);
//		
//		ExtractSettings es	= new ExtractSettings("extract");
//		tabs.add(es);
	}

	/**
	 * @param id
	 */
	public CreateSettingContentPage(String id) {
		this(id, null);
	}

}
