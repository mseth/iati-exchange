package org.dg.iati.api.transformer.jaxb;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlFileReader<T> {
	
	private Class<T> clazz;
	private String filename;
	private String foldername;
	
	
	
	public XmlFileReader(Class<T> clazz, String filename) {
		this.clazz = clazz;
		this.filename = filename;
		this.foldername = XmlFileWriter.MAPPING_FOLDER;
	}
	
	
	public XmlFileReader(Class<T> clazz, String filename, String foldername) {
		this.clazz = clazz;
		this.filename = filename;
		if ( foldername != null)
			this.foldername = foldername;
		else
			this.foldername	= XmlFileWriter.MAPPING_FOLDER;
	}





	public T load() {
		try {
			JAXBContext context			= JAXBContext.newInstance(this.clazz );
			Unmarshaller unmarshaller 	= context.createUnmarshaller();
			T retObj					= (T) unmarshaller.unmarshal(new File(this.foldername + "/" + this.filename));
			return retObj;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
