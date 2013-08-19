/**
 * 
 */
package org.dg.iati.api;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.dg.iati.importer.pages.ManageImporterConfigurationPage;

/**
 * @author dan
 *
 */
public class HeaderPage extends Panel {

	/**
	 * @param id
	 */
	public HeaderPage(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		add(new BookmarkablePageLink("createSetLink", IndexPage.class));
		add(new BookmarkablePageLink("extractLink", ExtractInfoPage.class,null));
		add(new BookmarkablePageLink("manageLink", ManageSettingsPage.class,null));
		add(new BookmarkablePageLink("manageImportLink", ManageImporterConfigurationPage.class,null));
	}

	/**
	 * @param id
	 * @param model
	 */
	public HeaderPage(String id, IModel<?> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}

}
