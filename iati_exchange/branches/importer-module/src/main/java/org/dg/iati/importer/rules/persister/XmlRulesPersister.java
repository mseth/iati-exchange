/**
 * 
 */
package org.dg.iati.importer.rules.persister;

import org.dg.iati.api.jaxb.iatiImportRules.IatiImportRules;
import org.dg.iati.api.transformer.jaxb.XmlFileWriter;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.ConfigInstance;
import org.dg.iati.importer.rules.loaders.XMLRulesLoader;

/**
 * @author alex
 *
 */
public class XmlRulesPersister {

	private String ruleName;
	private String folderName;
	
	public XmlRulesPersister(String ruleName) {
		this.ruleName 	= ruleName;
		this.folderName	= ConfigInstance.getInstance().get( ConfigConstants.IMPORT_MAPPING_FOLDER_NAME );
		
		this.folderName = this.folderName + "/" + ruleName ;
	}
	 
	

	public XmlRulesPersister(String ruleName, String folderName) {
		super();
		this.ruleName 	= ruleName;	
		this.folderName = folderName;
	}
	
	public void persist(IatiImportRules rules) {
		XmlFileWriter<IatiImportRules> fileWriter	= new XmlFileWriter<IatiImportRules>(rules, 
				this.ruleName, "."+XMLRulesLoader.IMPORT_RULES_EXTENSION, this.folderName);
		fileWriter.persist();
	}

	
}
