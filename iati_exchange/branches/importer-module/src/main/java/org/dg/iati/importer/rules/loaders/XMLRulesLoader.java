package org.dg.iati.importer.rules.loaders;

import org.dg.iati.api.jaxb.iatiImportRules.IatiImportRules;
import org.dg.iati.api.transformer.jaxb.XmlFileReader;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.ConfigInstance;

public class XMLRulesLoader implements RulesLoader {
	
	public static final String IMPORT_RULES_EXTENSION	= "imp-mapping.xml";

	private String ruleName;
	private String folderName;
	
	public XMLRulesLoader(String ruleName) {
		this.ruleName 	= ruleName;
		this.folderName	= ConfigInstance.getInstance().get( ConfigConstants.IMPORT_MAPPING_FOLDER_NAME );
		
		this.folderName = this.folderName + "/" + ruleName ;
	}
	 
	

	public XMLRulesLoader(String ruleName, String folderName) {
		super();
		this.ruleName 	= ruleName;
		this.folderName = folderName;
	}



	@Override
	public IatiImportRules loadJaxbRules() {
		String filename		= this.ruleName	+ "." + IMPORT_RULES_EXTENSION;
		XmlFileReader<IatiImportRules> xmlFileReader	= 
				new XmlFileReader<IatiImportRules>( IatiImportRules.class, filename, this.folderName );
		IatiImportRules result	= xmlFileReader.load();
		return result;
	}

}
