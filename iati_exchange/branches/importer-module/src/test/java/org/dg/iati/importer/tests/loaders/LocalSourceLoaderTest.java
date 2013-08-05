package org.dg.iati.importer.tests.loaders;

import static org.junit.Assert.*;

import org.dg.iati.api.jaxb.iatiApiResult.IatiActivities;
import org.dg.iati.api.transformer.jaxb.XmlFileReader;
import org.dg.iati.api.transformer.jaxb.XmlFileReaderAndTransformer;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.ConfigInstance;
import org.dg.iati.importer.source.loaders.LocalSourceLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LocalSourceLoaderTest {

	private XmlFileReader<IatiActivities> xmlFileReader;
	private XmlFileReaderAndTransformer<IatiActivities> xmlFileReaderAndTransformer;

	@Before
	public void setUp() throws Exception {
		xmlFileReader	= new XmlFileReader<IatiActivities>( IatiActivities.class, "output.xml", "samples" );
		String xslFile	= ConfigInstance.getInstance().get( ConfigConstants.IMPORT_XSL_FILE );
		xmlFileReaderAndTransformer	= 
				new XmlFileReaderAndTransformer<IatiActivities>( IatiActivities.class, "sample-iati-source.iati.xml", "samples/sample-import", xslFile );
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testLoadJaxbSource() {
		LocalSourceLoader sourceLoader	= new LocalSourceLoader( xmlFileReader );
		IatiActivities iatiActivities	= sourceLoader.loadJaxbSource();
		assertNotNull( "Verifying iatiActivities jaxb not null",iatiActivities );
	}
	
	@Test
	public final void testLoadJaxbSourceWithXsl() {
		LocalSourceLoader sourceLoader	= new LocalSourceLoader( xmlFileReaderAndTransformer );
		IatiActivities iatiActivities	= sourceLoader.loadJaxbSource();
		assertNotNull( "Verifying iatiActivities jaxb not null",iatiActivities );
	}

}
