package org.dg.iati.importer.tests.loaders;

import static org.junit.Assert.*;

import org.dg.iati.api.jaxb.iatiImportRules.IatiImportRules;
import org.dg.iati.importer.rules.loaders.XMLRulesLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RulesLoaderTest {

	private String xmlRulesSampleFilename;
	private String folderName;
	private XMLRulesLoader xmlRulesLoader;
	
	@Before
	public void setUp() throws Exception {
		this.xmlRulesSampleFilename	= "import-rules-sample";
		this.folderName				= "samples";
		this.xmlRulesLoader	= new XMLRulesLoader( this.xmlRulesSampleFilename, this.folderName );
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testLoadJaxbSource() {
		IatiImportRules rules	= this.xmlRulesLoader.loadJaxbRules();
		assertNotNull( "Rules not null", rules );
		assertNotNull( "Rules names not null", rules.getMappingName() );
		assertNotNull( "Datasource not null", rules.getDatasource() );
	}

}
