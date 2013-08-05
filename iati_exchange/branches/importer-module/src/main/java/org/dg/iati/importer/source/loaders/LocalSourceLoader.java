package org.dg.iati.importer.source.loaders;

import org.dg.iati.api.jaxb.iatiApiResult.IatiActivities;
import org.dg.iati.api.transformer.jaxb.XmlFileReader;

public class LocalSourceLoader implements SourceLoader {
	
	private XmlFileReader<IatiActivities> xmlFileReader;
	

	public LocalSourceLoader ( XmlFileReader<IatiActivities> xmlFileReader ) {
		this.xmlFileReader	= xmlFileReader;
	}



	public IatiActivities loadJaxbSource() {
		IatiActivities result	= this.xmlFileReader.load();
		return result;
	}



	/**
	 * @return the xmlFileReader
	 */
	public XmlFileReader<IatiActivities> getXmlFileReader() {
		return xmlFileReader;
	}

	

	

}
