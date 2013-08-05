package org.dg.iati.importer.tests.engine;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.dg.iati.api.jaxb.iatiApiResult.IatiActivities;
import org.dg.iati.api.jaxb.iatiApiResult.Item;
import org.dg.iati.api.transformer.jaxb.XmlFileReaderAndTransformer;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.ConfigInstance;
import org.dg.iati.importer.engine.ParameterTranslater;
import org.dg.iati.importer.engine.exception.NoSuchParameterAvailableException;
import org.dg.iati.importer.source.loaders.LocalSourceLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParameterTranslaterTest {

	private Logger	logger 	= Logger.getLogger(ParameterTranslaterTest.class);
	
	private String testString;
	private String resultString;
	
	private Item plannedDisbursements;

	private HashMap<String, String> httpReqParams = new HashMap<String, String>();

	private HashMap<String, Object> globalParams = new HashMap<String, Object>();
	
	private HashMap<String, Object> localParams = new HashMap<String, Object>();
	
	@Before
	public void setUp() throws Exception {
		testString		= 
				"http param1 is $R{param1}, global param1 is $G{param1}, local param1 is $L{param1}, iati param1 is $I{this.period-start.@iso-date};" 
				+ "http param2 is $R{param2}, global param2 is $G{param2}, local param2 is $L{param2}, iati param2 is $I{value}";
		resultString	= 
				"http param1 is HttpRequestParam1, global param1 is false, local param1 is 1.111, iati param1 is 2012-11-30 00:00:00;" 
				+ "http param2 is HttpRequestParam2, global param2 is 2, local param2 is LocalParam2, iati param2 is 60728919.560060382";
		
		ParameterUtil.populateHttpReqParams(httpReqParams);
		ParameterUtil.populateGlobalParams(globalParams);
		ParameterUtil.populateLocalParams(localParams);
		this.plannedDisbursements	= ParameterUtil.getFirstPlannedDisbursementItem();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testTranslate() {
		ParameterTranslater pTranslater = 
				new ParameterTranslater(this.httpReqParams, this.globalParams, this.localParams, this.plannedDisbursements);
		try {
			String transformedString	= pTranslater.translate(testString);
			if ( !this.resultString.equals(transformedString) )
				logger.error( "Transformed string was: " + transformedString );
			assertEquals("Comparing translated string to correct result string", transformedString, this.resultString);
		}
		catch ( RuntimeException e ) {
			e.printStackTrace();
			fail("Exception: " + e.getMessage() );
		}
	}

}
