/**
 * 
 */
package org.dg.iati.api;

import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.dg.iati.api.entity.IatiMappingElement;
import org.dg.iati.api.entity.IatiSettings;
import org.dg.iati.api.util.IatiUtils;

import com.google.code.jqwicket.ui.tabs.TabsWebMarkupContainer;

/**
 * @author dan
 *
 */
public class MappingSettings extends Panel {

	/**
	 * @param id
	 */
	public MappingSettings(String id) {
		super(id);
        
	}

	/**
	 * @param id
	 * @param model
	 */
	public MappingSettings(String id, IModel<IatiSettings> model) {
		super(id, model);

	    List<IatiMappingElement> items	=	model.getObject().getMappingItems();//IatiUtils.generateIATIElements();
	    TabsWebMarkupContainer tabs = new TabsWebMarkupContainer("mainTabs");  
	    tabs.add(new AttributeAppender("class", new Model<String>("ui-tabs-vertical ui-helper-clearfix"), " "));
	    tabs.add(new ListView<IatiMappingElement>("repeaterTabLabelItem", items) {  
			private static final long serialVersionUID = -9174869459288445406L;
			protected void populateItem(ListItem<IatiMappingElement> item) {  
				item.add(new TabLabelItem("elementId", item.getModel()));
            }  
        });
	    
	    tabs.add(new ListView<IatiMappingElement>("repeaterTabContentItem", items) {  
			private static final long serialVersionUID = 1208651114020766183L;
			protected void populateItem(ListItem<IatiMappingElement> item) {  
				item.add(new TabContentItem("elementContentId", item.getModel()));
            }  
        });
	    
        add(tabs);
	}

}
