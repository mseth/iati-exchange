/**
 * 
 */
package org.dg.iati.api.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dg.iati.api.entity.Constants;
import org.dg.iati.api.entity.IatiMappingElement;
import org.dg.iati.api.jaxb.iatiApiResult.IatiActivities;
import org.dg.iati.api.jaxb.iatiApiResult.IatiActivity;
import org.dg.iati.api.jaxb.iatiApiResult.Item;
import org.dg.iati.api.jaxb.iatiApiResult.RefType;

/**
 * @author dan
 *
 */
public class IatiUtils {

	public static String getCleanQuery(String q, String parentID, HashMap<String, String> params){
		//String result = q;
		if(parentID!=null)
			q	= q.replace("$parent", parentID);
		if(params != null && params.size()>0){
			{
				for(Map.Entry<String, String> entry: params.entrySet())
					{
						q	= q.replaceAll("\\$\\{"+entry.getKey()+"\\}", entry.getValue());
					}
			}
		}
		return q;
	}
	
	
	public static String getPropertyValue(String property){
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(Constants.CONFIG_FILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(property);
	}
	
	public static void itemResultToXml ( Item i ) {
	    try {
	        JAXBContext ctx = JAXBContext.newInstance(Item.class);
	        Marshaller marshaller = ctx.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        marshaller.marshal(i, System.out);
	    }
	    catch (Exception
	            e) {

	              //catch exception 
	    }
	}
	
	public static void printXML ( Object i, String xsl ) {
	    try {
	        JAXBContext ctx = JAXBContext.newInstance(i.getClass());
	        Marshaller marshaller = ctx.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        
	        marshaller.marshal(i, System.out);
	    }
	    catch (Exception
	            e) {

	              //catch exception 
	    }
	}
/*
	public static List<IatiMappingElement> generateIATIElements() {
		IatiMappingElement   actContacts	=	new IatiMappingElement("contact-info",
				Arrays.asList(new IatiMappingElement("contact-info.content",null, true),
						new IatiMappingElement("organisation",null, true),
						new IatiMappingElement("person-name",null, true),
						new IatiMappingElement("telephone",null, true),
						new IatiMappingElement("email",null, true),
						new IatiMappingElement("mailing-address",null, true)),	
						null, true);

		IatiMappingElement   actLocation	=	new IatiMappingElement("location",
				Arrays.asList(new IatiMappingElement("location.content",Arrays.asList(Constants.IATI_ATTRIBUTE_PERCENTAGE), true),
						new IatiMappingElement("location-type",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE,Constants.IATI_ATTRIBUTE_LANG), true),
						new IatiMappingElement("name",Arrays.asList(Constants.IATI_ATTRIBUTE_LANG), true),
						new IatiMappingElement("description",Arrays.asList(Constants.IATI_ATTRIBUTE_LANG), true),
						new IatiMappingElement("administrative",Arrays.asList(Constants.IATI_ATTRIBUTE_COUNTRY,Constants.IATI_ATTRIBUTE_ADM1,Constants.IATI_ATTRIBUTE_ADM2), true),
						new IatiMappingElement("coordinates",Arrays.asList(Constants.IATI_ATTRIBUTE_LATITUDE,Constants.IATI_ATTRIBUTE_LONGITUDE,Constants.IATI_ATTRIBUTE_PRECISION), true),
						new IatiMappingElement("gazetteer-entry",Arrays.asList(Constants.IATI_ATTRIBUTE_GAZETTEER_REF), true)),
						null, true);
		
		IatiMappingElement   actBudget	=	new IatiMappingElement("budget",
				Arrays.asList(new IatiMappingElement("budget.content",Arrays.asList(Constants.IATI_ATTRIBUTE_TYPE), true),
						new IatiMappingElement("period-start",Arrays.asList(Constants.IATI_ATTRIBUTE_ISO_DATE), true),
						new IatiMappingElement("period-end",Arrays.asList(Constants.IATI_ATTRIBUTE_ISO_DATE), true),
						new IatiMappingElement("value",Arrays.asList(Constants.IATI_ATTRIBUTE_VALUE_DATE, Constants.IATI_ATTRIBUTE_CURRENCY), true)),
						null, true);
		IatiMappingElement   actPlannedDisb	=	new IatiMappingElement("planned-disbursement",
				Arrays.asList(new IatiMappingElement("planned-disbursement.content",Arrays.asList(Constants.IATI_ATTRIBUTE_UPDATED), true),
						new IatiMappingElement("period-start",Arrays.asList(Constants.IATI_ATTRIBUTE_ISO_DATE), true),
						new IatiMappingElement("period-end",Arrays.asList(Constants.IATI_ATTRIBUTE_ISO_DATE), true),
						new IatiMappingElement("value",Arrays.asList(Constants.IATI_ATTRIBUTE_VALUE_DATE, Constants.IATI_ATTRIBUTE_CURRENCY), true)),
						null, true);
		
		IatiMappingElement   actTransaction	=	new IatiMappingElement("transaction",
				Arrays.asList(new IatiMappingElement("transaction.content",Arrays.asList(Constants.IATI_ATTRIBUTE_REF), true),
						new IatiMappingElement("transaction-type",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE), true),
						new IatiMappingElement("provider-org",Arrays.asList(Constants.IATI_ATTRIBUTE_REF, Constants.IATI_ATTRIBUTE_PROVIDER_ACTIVITY_ID), true),
						new IatiMappingElement("receiver-org",Arrays.asList(Constants.IATI_ATTRIBUTE_REF, Constants.IATI_ATTRIBUTE_RECEIVER_ACTIVITY_ID), true),
						new IatiMappingElement("value",Arrays.asList(Constants.IATI_ATTRIBUTE_CURRENCY, Constants.IATI_ATTRIBUTE_VALUE_DATE), true),
						new IatiMappingElement("description",Arrays.asList(Constants.IATI_ATTRIBUTE_LANG), true),
						new IatiMappingElement("transaction-date",Arrays.asList(Constants.IATI_ATTRIBUTE_ISO_DATE), true),
						new IatiMappingElement("flow-type",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE,Constants.IATI_ATTRIBUTE_LANG), true),
						new IatiMappingElement("finance-type",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE,Constants.IATI_ATTRIBUTE_LANG), true),
						new IatiMappingElement("aid-type",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE,Constants.IATI_ATTRIBUTE_LANG), true),
						new IatiMappingElement("disbursement-channel",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE), true),
						new IatiMappingElement("tied-status",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE,Constants.IATI_ATTRIBUTE_LANG), true)),
						null, true);
		
		IatiMappingElement   actDocs	=	new IatiMappingElement("document-link",
				Arrays.asList(new IatiMappingElement("document-link.content",Arrays.asList(Constants.IATI_ATTRIBUTE_URL, Constants.IATI_ATTRIBUTE_LANG), true),
						new IatiMappingElement("language",null, true),
						new IatiMappingElement("category",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE,Constants.IATI_ATTRIBUTE_LANG), true),
						new IatiMappingElement("title",Arrays.asList(Constants.IATI_ATTRIBUTE_LANG), true)),
						Arrays.asList(Constants.IATI_ATTRIBUTE_URL, Constants.IATI_ATTRIBUTE_LANG), true);
		
		List<IatiMappingElement> items = Arrays.asList(
		new IatiMappingElement("iati-identifier",null, true),
		new IatiMappingElement("other-identifier",Arrays.asList(Constants.IATI_ATTRIBUTE_OWNER_REF, Constants.IATI_ATTRIBUTE_OWNER_NAME), true),
		new IatiMappingElement("reporting-org",Arrays.asList(Constants.IATI_ATTRIBUTE_REF, Constants.IATI_ATTRIBUTE_TYPE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("title",Arrays.asList(Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("description",Arrays.asList(Constants.IATI_ATTRIBUTE_TYPE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("activity-status",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("activity-date",Arrays.asList(Constants.IATI_ATTRIBUTE_TYPE, Constants.IATI_ATTRIBUTE_ISO_DATE, Constants.IATI_ATTRIBUTE_LANG), true),
		actContacts,
		new IatiMappingElement("participating-org",Arrays.asList(Constants.IATI_ATTRIBUTE_REF, Constants.IATI_ATTRIBUTE_ROLE, Constants.IATI_ATTRIBUTE_TYPE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("recipient-country",Arrays.asList(Constants.IATI_ATTRIBUTE_PERCENTAGE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("recipient-region",Arrays.asList(Constants.IATI_ATTRIBUTE_PERCENTAGE, Constants.IATI_ATTRIBUTE_LANG), true),
		actLocation,
		new IatiMappingElement("sector",Arrays.asList(Constants.IATI_ATTRIBUTE_VOCABULARY, Constants.IATI_ATTRIBUTE_CODE, Constants.IATI_ATTRIBUTE_PERCENTAGE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("policy-marker",Arrays.asList(Constants.IATI_ATTRIBUTE_SIGNIFICANCE, Constants.IATI_ATTRIBUTE_VOCABULARY, Constants.IATI_ATTRIBUTE_CODE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("collaboration-type",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("default-flow-type",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("default-finance-type",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("default-aid-type",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("default-tied-status",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE, Constants.IATI_ATTRIBUTE_LANG), true),
		actBudget,
		actPlannedDisb,
		actTransaction,
		actDocs,
		new IatiMappingElement("activity-website",null, true),
		new IatiMappingElement("related-activity",Arrays.asList(Constants.IATI_ATTRIBUTE_TYPE,Constants.IATI_ATTRIBUTE_REF, Constants.IATI_ATTRIBUTE_LANG), true)
		);
		return items;
	}
	*/


	public static List<IatiMappingElement> generateIATIElements() throws JAXBException, IOException {
		// TODO Auto-generated method stub
		List<IatiMappingElement> items = new ArrayList<IatiMappingElement>();
        Properties prop = new Properties();
        prop.load(new FileInputStream(Constants.CONFIG_FILE));
		InputStream inputStream = new FileInputStream(prop.getProperty("ui-config-file"));//Constants.CONFIG_UI_FILE);
		
		IatiActivities rf;
		JAXBContext jc 	= JAXBContext.newInstance(Constants.IATI_API_RESULT_JAXB);
        Unmarshaller m 	= jc.createUnmarshaller();
        rf 	= (IatiActivities) m.unmarshal(inputStream);
		
        IatiActivity config = rf.getIatiActivity().get(0);
        for (Iterator<Item> it = config.getItem().iterator(); it.hasNext();) {
			Item item 				= (Item) it.next();
			IatiMappingElement ime 	= buildIatiMappingElement(item);
			items.add(ime);
		}
		return items;
	}


	private static IatiMappingElement buildIatiMappingElement(Item item) {
		String label = item.getRef();
		ArrayList<String> attrList = null;
		if(item.getAttribute()!=null && item.getAttribute().size()>0){
			attrList = new ArrayList<String>();
			for (Iterator<RefType> jt = item.getAttribute().iterator(); jt.hasNext();) {
				RefType attr = (RefType) jt.next();
				attrList.add(attr.getRef());
			}
		}
		ArrayList<IatiMappingElement> subItems = null;
		if(item.getItem()!=null && item.getItem().size()>0){
			subItems = new ArrayList<IatiMappingElement>();
			subItems.add(new IatiMappingElement(item.getRef()+".content", null, attrList));
			for (Iterator<Item> jt = item.getItem().iterator(); jt.hasNext();) {
				Item subItem = (Item) jt.next();
				//subItems.add(attr.getRef());
				subItems.add(buildIatiMappingElement(subItem));
			}
		}
		
		IatiMappingElement ime = new IatiMappingElement(label, subItems, attrList);
		return ime;
	}
	
	
	
	
}
