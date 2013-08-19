package org.dg.iati.importer.pageentities.dao;

import org.dg.iati.api.jaxb.iatiImportRules.IatiImportRules;
import org.dg.iati.importer.pageentities.GlobalSettingsImporter;
import org.dg.iati.importer.rules.loaders.RulesLoader;
import org.dg.iati.importer.rules.loaders.XMLRulesLoader;
import org.dg.iati.importer.rules.persister.XmlRulesPersister;

public class GlobalSettingsImporterDao {

	private String ruleName;
	public GlobalSettingsImporterDao(String ruleName) {
		this.ruleName	= ruleName;
	}
	
	public GlobalSettingsImporter get() {
		GlobalSettingsImporter ret	= new GlobalSettingsImporter();
		RulesLoader rulesLoader	= new XMLRulesLoader( this.ruleName );
		IatiImportRules rules 	= rulesLoader.loadJaxbRules();
		
		ret.setPassword( rules.getDatadestination().getPassword() );
		ret.setUsername( rules.getDatadestination().getUsername() );
		ret.setUrl( rules.getDatadestination().getUrl() );
		ret.setFilename( rules.getDatasource().getUrl() );
		ret.setMappingName(this.ruleName);
		
		return ret;
	}
	
	public void set(GlobalSettingsImporter gsi) {
		RulesLoader rulesLoader	= new XMLRulesLoader( this.ruleName );
		IatiImportRules rules 	= rulesLoader.loadJaxbRules();
		rules.getDatadestination().setPassword(gsi.getPassword());
		rules.getDatadestination().setUsername(gsi.getUsername());
		rules.getDatadestination().setUrl(gsi.getUrl());
		rules.getDatasource().setUrl(gsi.getFilename());
		
		XmlRulesPersister persister	= new XmlRulesPersister(this.ruleName);
		persister.persist(rules);
		
	}

}
