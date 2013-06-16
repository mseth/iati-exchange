package org.dg.iati.api.transformer.jaxb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dg.iati.api.util.ConfigInstance;

public class SavedMappingList {
	
	private String foldername;
	//private String extension;
	
	public SavedMappingList(){
		this.foldername = ConfigInstance.getInstance().getMappingFolder();
	//	this.extension	= XmlFileWriter.EXTENSION;
	}
	
	public SavedMappingList(String foldername) {
		super();
		if ( foldername != null )
			this.foldername = foldername;
		else
			this.foldername = ConfigInstance.getInstance().getMappingFolder();
		
//		if ( extension != null)
//			this.extension = extension;
//		else
//			this.extension	= XmlFileWriter.EXTENSION;
	}



	public List<String> showSavedMappings() {
		List<String> retList	= new ArrayList<String>();
		File f 					= new File (this.foldername + "/" + ".");
		
		File [] fileList		= f.listFiles();
		
		if ( fileList != null && fileList.length > 0) {
			for (int i = 0; i < fileList.length; i++) {
				File tempFile	= fileList[i];
				if ( tempFile.isDirectory() ) {
					String filename = tempFile.getName();
					retList.add(filename);
				}
				
			}
		}
		
		return retList;
	}
	public List<String> showExistingXslt(String mappingName) {
		String path = XmlFileUtils.generateCustomFolderPath(mappingName);
		
		List<String> retList	= new ArrayList<String>();
		File f 					= new File (path);
		
		File [] fileList		= f.listFiles();
		
		if ( fileList != null && fileList.length > 0) {
			for (int i = 0; i < fileList.length; i++) {
				File tempFile	= fileList[i];
				String filename = tempFile.getName();
				if ( filename.endsWith(".xsl") ) {
					retList.add(filename);
				}
				
			}
		}
		
		return retList;
	}
}
