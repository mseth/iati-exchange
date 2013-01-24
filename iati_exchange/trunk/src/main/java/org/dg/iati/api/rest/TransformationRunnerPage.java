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
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.IatiUtils;

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
		
		String serverName				= IatiUtils.getPropertyValue(ConfigConstants.PUBLIC_SERVER_NAME);
		if ( serverName == null ) {
			serverName		= request.getContainerRequest().getServerName() + "${port}";
		}
		
		int serverPort					= request.getContainerRequest().getServerPort();
		String serverPortStr			= IatiUtils.getPropertyValue(ConfigConstants.PUBLIC_SERVER_PORT);
		if ( serverPortStr != null ) {
			try {
				serverPort	= Integer.parseInt(serverPortStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		Transform transform		= new Transform(sContext, serverName, serverPort);
		
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
