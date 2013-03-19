package org.dg.iati.api.util;

import org.dg.iati.api.transformer.jaxb.XmlFileWriter;

public class ConfigInstance {
	
	private static ConfigInstance singleton	= null;
	
	private String mappingFolder;

	private ConfigInstance() {
		this.mappingFolder	= IatiUtils.getPropertyValue(ConfigConstants.MAPPING_FOLDER_NAME);
		if (this.mappingFolder == null) 
			this.mappingFolder	= XmlFileWriter.MAPPING_FOLDER;
	}
	
	public static ConfigInstance getInstance() {
		if ( singleton == null ) {
			singleton	= new ConfigInstance();
		}
		return singleton;
	}

	/**
	 * @return the mappingFolder
	 */
	public String getMappingFolder() {
		return mappingFolder;
	}

	/**
	 * @param mappingFolder the mappingFolder to set
	 */
	public void setMappingFolder(String mappingFolder) {
		this.mappingFolder = mappingFolder;
	}
	
	

}
