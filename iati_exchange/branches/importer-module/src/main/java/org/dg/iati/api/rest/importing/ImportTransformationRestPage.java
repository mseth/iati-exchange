/**
 * 
 */
package org.dg.iati.api.rest.importing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters.NamedPair;
import org.dg.iati.api.rest.Answer;
import org.dg.iati.api.rest.ImportAnswer;
import org.dg.iati.api.rest.RestBasicPage;
import org.dg.iati.api.rest.constants.RestConstants;
import org.dg.iati.importer.Main;
import org.dg.iati.importer.engine.ImportEngine;

/**
 * @author alex
 *
 */
public class ImportTransformationRestPage extends RestBasicPage {

	private HashMap<String, String> httpReqParams ;
	/**
	 * 
	 */
	public ImportTransformationRestPage(final PageParameters pp) {
		
		this.httpReqParams = new HashMap<String, String>();
		
		List<NamedPair> npList	= pp.getAllNamed();
		for (NamedPair np: npList) {
			this.httpReqParams.put(np.getKey(), np.getValue());
			System.out.println(np.getKey() + " - " + np.getValue());
		}
		
		String id	= this.httpReqParams.get(RestConstants.REST_TRASNF_ID);
		
		if (id != null) {
			Main mainImporter		= new Main(id);
			ImportEngine engine		= mainImporter.setup( this.httpReqParams );
			// engine.setHttpReqVariables(this.httpReqParams);
			
			engine.transform();
			
			ImportAnswer a = new ImportAnswer();
			a.setId( this.httpReqParams.get(RestConstants.REST_TRASNF_ID) );
			a.setSummary( engine.generateSummaryReport() );
			a.setSuccessful( engine.generateSuccessfulReportList() );
			a.setFailed( engine.getFailed() );
			
			this.setDefaultModel(new Model<ImportAnswer>(a));
			
		}
	}

}
