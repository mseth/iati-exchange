/**
 * 
 */
package org.dg.iati.api;

import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.file.File;
import org.dg.iati.api.entity.Constants;
import org.dg.iati.api.entity.IatiMappingFile;
import org.dg.iati.api.jaxb.iatiApiMapping.IatiApiMapping;
import org.dg.iati.api.transformer.jaxb.SavedMappingList;
import org.dg.iati.api.transformer.jaxb.XmlFileReader;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.IatiUtils;

/**
 * @author dan
 *
 */
public class ManageSettingsContentPage extends Panel {

	/**
	 * @param id
	 */
	public ManageSettingsContentPage(String id) {
		super(id);
		
		
		List<String> list			= new SavedMappingList(
							IatiUtils.getPropertyValue(ConfigConstants.MAPPING_FOLDER_NAME), 
							null
						)
						.showSavedMappings();
		
		ListView<String> lView			= new ListView<String>( "linkItems", list ) {

			private static final long serialVersionUID = -6831699709874923889L;

			@Override
			protected void populateItem(ListItem<String> item) {
				String fileName		= item.getModelObject();
				String baseFileName = IatiUtils.getPropertyValue(ConfigConstants.MAPPING_FOLDER_NAME)+"/"+fileName.substring(0, fileName.indexOf(".mapping.xml"));
				
				DownloadLink setting = new DownloadLink("setting", new File(IatiUtils.getPropertyValue(ConfigConstants.MAPPING_FOLDER_NAME)+"/"+fileName), fileName);
				Label label		= new Label("linkLabel", fileName);
				setting.add(label);
				item.add(setting);
				
				//Label label		= new Label("linkLabel", fileName);
				//

				Link<String> link	= new Link<String> ("link",Model.of(fileName)){
					private static final long serialVersionUID = 6043845179197188521L;

					@Override
					public void onClick() {
						PageParameters pageParameters = new PageParameters();
						String name		= getModelObject();
						pageParameters.add("name",name);
						setResponsePage(IndexPage.class, pageParameters);
						//System.out.println("onclick pressed");
					}
					
				};
				item.add(link);
				

				String iatiFilename = baseFileName + Constants.IATI_FILE_EXTENSION;
				File iatiFile = new File(iatiFilename);
				DownloadLink iatiLink = new DownloadLink("iatiFile", iatiFile, iatiFilename);
				if(!iatiFile.exists())
					iatiLink.setEnabled(false);
				item.add(iatiLink);
				
				String xmlFilename = baseFileName + Constants.IATI_FILE_RESULT_EXTENSION;
				File xmlFile = new File(iatiFilename);
				DownloadLink xmlResultLink = new DownloadLink("xmlResultFile", xmlFile, xmlFilename);
				if(!xmlFile.exists())
					xmlResultLink.setEnabled(false);
				item.add(xmlResultLink);
				
				String xslFilename = baseFileName + Constants.IATI_FILE_TRANSFORM_EXTENSION ;
				File xslFile = new File(iatiFilename);
				DownloadLink xslLink = new DownloadLink("xslFile", xslFile, xslFilename );
				if(!xslFile.exists())
					xslLink.setEnabled(false);
				item.add(xslLink);
				
				
				Link<String> executeLink	= new Link<String> ("execute",Model.of(fileName)){
					private static final long serialVersionUID = -1788151307077243660L;

					@Override
					public void onClick() {
						XmlFileReader<IatiApiMapping> reader	= new XmlFileReader<IatiApiMapping>(IatiApiMapping.class, getModelObject());
						IatiApiMapping jaxbMapping				= reader.load();
						IatiMappingFile imf= new IatiMappingFile(jaxbMapping);
						try {
							imf.run();
						} catch (JAXBException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				};
				item.add(executeLink);

			}
			
		};
		
		add(lView);
		
	}

	/**
	 * @param id
	 * @param model
	 */
	public ManageSettingsContentPage(String id, IModel<?> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}

}
