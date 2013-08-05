package org.dg.iati.importer.tests.engine;

import java.util.HashMap;

import org.dg.iati.api.jaxb.iatiApiResult.IatiActivities;
import org.dg.iati.api.jaxb.iatiApiResult.Item;
import org.dg.iati.api.transformer.jaxb.XmlFileReaderAndTransformer;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.ConfigInstance;
import org.dg.iati.importer.source.loaders.LocalSourceLoader;

public class ParameterUtil {

	public static void populateHttpReqParams(HashMap<String, String> httpReqParams) {
		httpReqParams.put("param1", "HttpRequestParam1");
		httpReqParams.put("param2", "HttpRequestParam2");
		
	}
	
	public static void populateGlobalParams(HashMap<String, Object> globalParams) {
		globalParams.put("param1", false );
		globalParams.put("param2", 2);
	}
	
	public static Item getFirstPlannedDisbursementItem () {
		String xslFile	= ConfigInstance.getInstance().get( ConfigConstants.IMPORT_XSL_FILE );
		XmlFileReaderAndTransformer<IatiActivities> xmlFileReaderAndTransformer = 
				new XmlFileReaderAndTransformer<IatiActivities>( IatiActivities.class, "sample-iati-source.iati.xml", "samples/sample-import", xslFile );
		LocalSourceLoader sourceLoader	= new LocalSourceLoader( xmlFileReaderAndTransformer );
		IatiActivities iatiActivities	= sourceLoader.loadJaxbSource();
		for ( Item temp: iatiActivities.getIatiActivity().get(0).getItem() ) {
			if ( "planned-disbursement".equals( temp.getRef() ) ) {
				return temp;
			}
		}
		return null;
	}

	public static void populateLocalParams(HashMap<String, Object> localParams) {
		localParams.put("param1", 1.111);
		localParams.put("param2", "LocalParam2");
		
	}

}
