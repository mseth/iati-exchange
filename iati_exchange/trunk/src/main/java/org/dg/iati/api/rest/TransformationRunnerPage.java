/**
 * 
 */
package org.dg.iati.api.rest;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters.NamedPair;
import org.dg.iati.api.rest.action.Transform;

/**
 * @author Alex
 *
 */
public class TransformationRunnerPage extends RestBasicPage {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4265199386937658708L;

	public TransformationRunnerPage(final PageParameters pp) {
		
		System.out.println("Start");
		
		List<NamedPair> npList	= pp.getAllNamed();
		
		ServletContext sContext			= ((WebApplication)getSession().getApplication()).getServletContext();
		ServletWebRequest request		= (ServletWebRequest)getRequest();
		Transform transform		= new Transform(sContext, request.getContainerRequest().getServerName(), 
				request.getContainerRequest().getServerPort());
		
		for (NamedPair np: npList) {
			transform.addParams(np.getKey(), np.getValue());
			System.out.println(np.getKey() + " - " + np.getValue());
		}
		
		Answer a	= transform.execute();
		this.setXmlFilePath(transform.getDirectResponseFilePath());
		this.setDefaultModel(new Model<Answer>(a));
		
		
		System.out.println("ENDs");
	}

}
