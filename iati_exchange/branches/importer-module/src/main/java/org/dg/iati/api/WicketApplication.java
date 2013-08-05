package org.dg.iati.api;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.response.filter.AjaxServerAndClientTimeFilter;
import org.dg.iati.api.rest.MyFileResourceReference;
import org.dg.iati.api.rest.TransformationRunnerPage;
import org.dg.iati.api.rest.constants.RestConstants;
import org.dg.iati.api.rest.importing.ImportTransformationRestPage;

import com.google.code.jqwicket.JQComponentOnBeforeRenderListener;
import com.google.code.jqwicket.JQContributionConfig;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see org.dg.iati.api.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<IndexPage> getHomePage()
	{
		return IndexPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
       // getResourceSettings().setThrowExceptionOnMissingResource(false); // WHY ??
        getRequestCycleSettings().addResponseFilter(new AjaxServerAndClientTimeFilter());
        
		getDebugSettings().setAjaxDebugModeEnabled(true);
		JQContributionConfig config = new JQContributionConfig().withDefaultJQueryUi();
		config.withJQueryUiCss("css/ui-lightness/jquery-ui-1.8.19.custom.css");
		config.withJQueryUiCss("css/iati-form.css");
		getComponentPreOnBeforeRenderListeners().add(new JQComponentOnBeforeRenderListener(config));
		mountPage("/create", IndexPage.class);
		mountPage("/extract", ExtractInfoPage.class);
		mountPage("/manage", ManageSettingsPage.class);
		mountPage("/transformation/" +
				RestConstants.REST_TRASNF_ID+"/${"+RestConstants.REST_TRASNF_ID+"}/" +
				RestConstants.REST_ACTION+"/${"+RestConstants.REST_ACTION+"}/" +
						"#{"+RestConstants.REST_TRANSF_ADDITIONAL_INFORMATION+"}", TransformationRunnerPage.class);
		
		mountPage("/importing/" + 
				RestConstants.REST_TRASNF_ID+"/${"+RestConstants.REST_TRASNF_ID+"}" , ImportTransformationRestPage.class  );
		
		mountResource("/file/${file}", new MyFileResourceReference() );
	}
}
