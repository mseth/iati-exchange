package org.dg.iati.api.transformer.jaxb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SavedMappingList {
	
	private String foldername;
	private String extension;
	
	public SavedMappingList(){
		this.foldername = XmlFileWriter.MAPPING_FOLDER;
		this.extension	= XmlFileWriter.EXTENSION;
	}
	
	public SavedMappingList(String foldername, String extension) {
		super();
		if ( foldername != null )
			this.foldername = foldername;
		else
			this.foldername = XmlFileWriter.MAPPING_FOLDER;
		
		if ( extension != null)
			this.extension = extension;
		else
			this.extension	= XmlFileWriter.EXTENSION;
	}



	public List<String> showSavedMappings() {
		List<String> retList	= new ArrayList<String>();
		File f 					= new File (this.foldername + "/" + ".");
		String [] list			= f.list();
		
		if ( list != null && list.length > 0) {
			for (int i = 0; i < list.length; i++) {
				String filename = list[i];
				if ( filename.endsWith(this.extension) ) {
					retList.add(filename);
				}
				
			}
		}
		
		return retList;
	}
	
}
