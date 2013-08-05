package org.dg.iati.importer.tests.loaders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.dg.iati.api.jaxb.iatiImportRules.IatiImportRules;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.ConfigInstance;
import org.dg.iati.importer.rules.loaders.XMLRulesLoader;
import org.dg.iati.importer.source.loaders.LocalSourceLoader;
import org.dg.iati.importer.source.loaders.SourceLoader;
import org.dg.iati.importer.source.loaders.SourceLoaderFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SourceLoaderFactoryTest {

	private String xmlRulesSampleFilename;
	private String folderName;
	private XMLRulesLoader xmlRulesLoader;
	private IatiImportRules rules;
	private String baseImportMappingFolder;

	@Before
	public void setUp() throws Exception {
		this.xmlRulesSampleFilename		= "import-rules-sample";
		this.folderName					= "samples";
		this.xmlRulesLoader				= new XMLRulesLoader(this.xmlRulesSampleFilename, this.folderName);
		this.rules						= this.xmlRulesLoader.loadJaxbRules();
		this.baseImportMappingFolder	= ConfigInstance.getInstance().get( ConfigConstants.IMPORT_MAPPING_FOLDER_NAME );
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetSourceLoaderInstace() {
		try {
			SourceLoader srcLoader	= new SourceLoaderFactory( this.rules ).getSourceLoaderInstace();
			assertNotNull( "Verifying source loader is not null", srcLoader );
			assertTrue( "source loader is of type file", srcLoader instanceof LocalSourceLoader );
			assertNotNull( "Verify XmlFileReader not null", ( (LocalSourceLoader) srcLoader).getXmlFileReader() );
			assertEquals( "Verify correct folder", 
					this.baseImportMappingFolder + "/" + this.rules.getMappingName(), 
					((LocalSourceLoader) srcLoader).getXmlFileReader().getFoldername() );
			assertEquals( "Filename from rules is same as file name used in source loader", 
					this.rules.getDatasource().getUrl(), ( (LocalSourceLoader) srcLoader).getXmlFileReader().getFilename() );
		}
		catch ( RuntimeException e ) {
			e.printStackTrace();
			fail( e.getMessage() );
		}
	}

}
