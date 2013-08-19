package org.dg.iati.importer.source.loaders;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileExistsException;
import org.dg.iati.api.transformer.jaxb.SavedMappingList;
import org.dg.iati.api.transformer.jaxb.XmlFileReaderAndTransformer;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.ConfigInstance;
import org.dg.iati.importer.rules.loaders.XMLRulesLoader;

public class SourcesManager {
	private String mappingName;
	
	public SourcesManager(String mappingName) {
		this.mappingName = mappingName;
	}

	public List<String> getSources() {
		List<String> ret		= new ArrayList<String>();
		SavedMappingList sml	= new SavedMappingList(ConfigInstance.getInstance().get( ConfigConstants.IMPORT_MAPPING_FOLDER_NAME ) );
		
		List<String> allFiles	= sml.showFileList(mappingName);
		for (String temp:allFiles) {
			if ( !temp.endsWith(XMLRulesLoader.IMPORT_RULES_EXTENSION) 
					&& !temp.startsWith(XmlFileReaderAndTransformer.CUSTOM_PREFIX) ) {
				ret.add(temp);
			}
		}
		return ret;
	}
	
	public void deleteSource(String filename) {
		String folder	= ConfigInstance.getInstance().get( ConfigConstants.IMPORT_MAPPING_FOLDER_NAME );
		folder += "/" + this.mappingName;
		File file	= new File( folder + "/" + filename );
		if ( file.exists() ) {
			file.delete();
		}
	}
	
	public void saveSource(String filename, InputStream is) throws IOException {
		FileOutputStream fos	= null;
		ReadableByteChannel rbc	= null;
		try {
			String folder	= ConfigInstance.getInstance().get( ConfigConstants.IMPORT_MAPPING_FOLDER_NAME );
			folder += "/" + this.mappingName;
			File destinationFile	= new File( folder + "/" + filename );
			if ( destinationFile.exists() ) {
				throw new FileExistsException(filename);
			}
			else {
				destinationFile.createNewFile();
				fos	= new FileOutputStream(destinationFile);
				rbc = Channels.newChannel( is );
				fos.getChannel().transferFrom(rbc, 0, Integer.MAX_VALUE);
			}
		}catch (IOException e) {
			throw e; 
		}
		finally {
			if ( rbc != null )
				rbc.close();
			if ( fos != null )
				fos.close();
		}
	}
}
