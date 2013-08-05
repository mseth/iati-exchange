/**
 * 
 */
package org.dg.iati.api.rest.importing;

import java.util.HashMap;
import java.util.List;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters.NamedPair;
import org.dg.iati.api.rest.Answer;
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
			ImportEngine engine		= mainImporter.setup();
			engine.setHttpReqVariables(this.httpReqParams);
			
			engine.transform();
			
			Answer a = new Answer();
			a.setId( this.httpReqParams.get(RestConstants.REST_TRASNF_ID) );
			a.setStatus( engine.generateSummaryReport() );
			a.setResult( engine.generateReport() );
			
			this.setDefaultModel(new Model<Answer>(a));
			
		}
	}

}
