package org.dg.iati.api.transformer.jaxb;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

public class XmlFileReader<T> {
	
	private final Logger logger = Logger.getLogger( XmlFileReader.class );
	
	protected Class<T> clazz;
	protected String filename;
	protected String foldername;
	
	
	
	public XmlFileReader(Class<T> clazz, String filename) {
		this.clazz = clazz;
		this.filename = filename;
		this.foldername = XmlFileUtils.generateCustomFolderPath(filename);
	}
	
	
	public XmlFileReader(Class<T> clazz, String filename, String foldername) {
		this.clazz = clazz;
		this.filename = filename;
		if ( foldername != null)
			this.foldername = foldername;
		else
			this.foldername	= XmlFileUtils.generateCustomFolderPath(filename);
	}


	protected Unmarshaller createJAXBUnmarshaller () throws JAXBException {
		JAXBContext context			= JAXBContext.newInstance(this.clazz );
		Unmarshaller unmarshaller 	= context.createUnmarshaller();
		return unmarshaller;
	}


	public T load() {
		try {
			Unmarshaller unmarshaller 	= this.createJAXBUnmarshaller();
			File sourceFileObj			= new File(this.foldername + "/" + this.filename);
			logger.info("File " + sourceFileObj.getAbsolutePath() + " exists: " + sourceFileObj.exists() );
			T retObj					= (T) unmarshaller.unmarshal( sourceFileObj );
			return retObj;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}


	/**
	 * @return the foldername
	 */
	public String getFoldername() {
		return foldername;
	}
	
	
	
}
