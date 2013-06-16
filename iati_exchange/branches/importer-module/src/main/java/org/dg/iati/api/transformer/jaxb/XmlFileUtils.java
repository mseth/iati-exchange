package org.dg.iati.api.transformer.jaxb;

import org.dg.iati.api.entity.Constants;
import org.dg.iati.api.rest.constants.RestConstants;
import org.dg.iati.api.util.ConfigInstance;

public class XmlFileUtils {

	public static String generateCustomFolderPath(String mappingName) {
		int indexOut		= mappingName.indexOf(RestConstants.OUT_FILENAME_PREFIX);
		if (indexOut > 0)
			mappingName	= mappingName.substring(0, indexOut);
		
		int indexExt	= mappingName.indexOf(Constants.IATI_FILE_MAPPING_EXTENSION);
		if ( indexExt > 0 ) 
			mappingName	= mappingName.substring(0, indexExt);
		ConfigInstance config 	= ConfigInstance.getInstance();
		return config.getMappingFolder() + "/" + mappingName	;
	}
}
