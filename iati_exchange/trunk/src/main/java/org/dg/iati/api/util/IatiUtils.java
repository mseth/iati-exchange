/**
 * 
 */
package org.dg.iati.api.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.dg.iati.api.entity.Constants;
import org.dg.iati.api.entity.IatiMappingElement;
import org.dg.iati.api.jaxb.iatiApiResult.Item;

/**
 * @author dan
 *
 */
public class IatiUtils {

	public static String getCleanQuery(String q, String parentID){
		if(parentID!=null)
			return q.replace("$parent", parentID);
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
	        
//	        JAXBSource source 		= new JAXBSource( ctx, i );
//	        TransformerFactory tf 	= TransformerFactory.newInstance();
//	        InputStream is 			= new ByteArrayInputStream(xsl.getBytes());
//	        Transformer t 			= tf.newTransformer(new StreamSource(is));	        
//	        // run transformation
//	        t.transform(source,new StreamResult(System.out));
	        marshaller.marshal(i, System.out);
	    }
	    catch (Exception
	            e) {

	              //catch exception 
	    }
	}

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
		new IatiMappingElement("reporting-org",Arrays.asList(Constants.IATI_ATTRIBUTE_REF,Constants.IATI_ATTRIBUTE_TYPE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("title",Arrays.asList(Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("description",Arrays.asList(Constants.IATI_ATTRIBUTE_TYPE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("activity-status",Arrays.asList(Constants.IATI_ATTRIBUTE_CODE, Constants.IATI_ATTRIBUTE_LANG), true),
		new IatiMappingElement("activity-date",Arrays.asList(Constants.IATI_ATTRIBUTE_TYPE, Constants.IATI_ATTRIBUTE_ISO_DATE, Constants.IATI_ATTRIBUTE_LANG), true),
		actContacts,
		new IatiMappingElement("participating-org",Arrays.asList(Constants.IATI_ATTRIBUTE_REF, Constants.IATI_ATTRIBUTE_TYPE, Constants.IATI_ATTRIBUTE_LANG), true),
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
	
}
