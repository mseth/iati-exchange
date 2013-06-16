/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;

import com.google.code.jqwicket.JQComponentOnBeforeRenderListener;
import com.google.code.jqwicket.JQContributionConfig;

/**
 * @author dan
 *
 */
public class ExtractInfoPage extends WebPage {

	/**
	 * 
	 */
	public ExtractInfoPage() {
		// TODO Auto-generated constructor stub
		HeaderPage headerPage = new HeaderPage("headerPage");
		add(headerPage);
		ExtractInfoContentPage contentPage = new ExtractInfoContentPage("contentPage");
		add(contentPage);
		FooterPage footerPage = new FooterPage("footerPage");
		add(footerPage);
	}

	/**
	 * @param model
	 */
	public ExtractInfoPage(IModel<?> model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parameters
	 */
	public ExtractInfoPage(PageParameters parameters) {
		super(parameters);
	}
	
}
