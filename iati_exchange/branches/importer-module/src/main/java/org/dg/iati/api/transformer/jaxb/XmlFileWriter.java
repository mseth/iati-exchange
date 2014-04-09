package org.dg.iati.api.transformer.jaxb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.dg.iati.api.entity.Constants;

public class XmlFileWriter<T>{
	
	public static final Logger logger			= Logger.getLogger(XmlFileWriter.class);
	
	public static final String EXTENSION		= ".mapping.xml";
	public static final String MAPPING_FOLDER	= "mapping";
			
	private T mainJaxbObject;
	private String filename;
	private String extension;
	private String foldername;
	
	public XmlFileWriter(T mainJaxbObject, String filename) {
		super();
		this.mainJaxbObject = mainJaxbObject;
		this.filename = filename;
		this.extension	= XmlFileWriter.EXTENSION;
		this.foldername = XmlFileUtils.generateCustomFolderPath(filename);
	}
	
	
	
	public XmlFileWriter(T mainJaxbObject, String filename, String extension) {
		super();
		this.mainJaxbObject = mainJaxbObject;
		this.filename = filename;
		this.extension = extension;
		this.foldername = XmlFileUtils.generateCustomFolderPath(filename);
	}
	
	public XmlFileWriter(T mainJaxbObject, String filename, String extension,
			String foldername) {
		super();
		this.mainJaxbObject = mainJaxbObject;
		this.filename = filename;
		this.extension = extension;
		this.foldername = foldername;
	}



	public void persist () {
		try {
			
			File folder				= new File(Constants.APP_PATH+this.foldername);
			if ( !folder.isDirectory() && !folder.mkdir() ) {
				logger.error("Cannot create mapping folder !!");
				return;
			}
			File file				= new File (folder, filename+this.extension);
			
			JAXBContext context		= JAXBContext.newInstance(mainJaxbObject.getClass() );
			Marshaller marshaller = context.createMarshaller();
		    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		    
		    marshaller.marshal(this.mainJaxbObject, new FileOutputStream(file));
		    logger.info("File "+filename+extension+ " written OK!");

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
