/**
 * 
 */
package org.dg.iati.importer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dg.iati.api.jaxb.iatiApiResult.IatiActivities;
import org.dg.iati.api.jaxb.iatiImportRules.IatiImportRules;
import org.dg.iati.api.util.ConfigInstance;
import org.dg.iati.importer.engine.ImportEngine;
import org.dg.iati.importer.engine.factories.ActionExecutorFactory;
import org.dg.iati.importer.rules.loaders.RulesLoader;
import org.dg.iati.importer.rules.loaders.XMLRulesLoader;
import org.dg.iati.importer.source.loaders.SourceLoader;
import org.dg.iati.importer.source.loaders.SourceLoaderFactory;
import org.dg.iati.importer.source.loaders.LocalSourceLoader;

/**
 * @author Alex Gartner
 *
 */
public class Main {
	
	private ImportEngine engine;
	private String ruleName = "test";
	private HashMap<String, String> httpReqParams = new HashMap<String, String>();

	private HashMap<String, Object> globalParams = new HashMap<String, Object>();
	
	private HashMap<String, Object> localParams = new HashMap<String, Object>();
	
	public static void main(String [] args) {
		Main main	= new Main("nepal-import");
		ImportEngine engine	= main.setup();
		engine.transform();
	
	}
	
	public Main(String ruleName) {
		engine 			= new ImportEngine();
		this.ruleName	= ruleName;
	}
	
	public ImportEngine setup() {
		RulesLoader rulesLoader	= new XMLRulesLoader( this.ruleName );
		//httpReqParams.put("del-period-start", "2011-07-15");
		//httpReqParams.put("del-period-end", "2012-07-15");
		
		IatiImportRules rules 	= rulesLoader.loadJaxbRules();
		
		SourceLoader srcLoader	= new SourceLoaderFactory( rules ).getSourceLoaderInstace();
		
		IatiActivities sourceData 	= srcLoader.loadJaxbSource();
		
		ActionExecutorFactory aeFactory	= new ActionExecutorFactory(globalParams, localParams);
		
		engine.setRules( rules );
		engine.setSource( sourceData );
		engine.setAeFactory(aeFactory);
		engine.setGlobalVariables(globalParams);
		engine.setLocalVariables(localParams);
		engine.setHttpReqVariables(httpReqParams);
		//engine.transform();
		return engine;
	}
	
}
