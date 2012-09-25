/**
 * 
 */
package org.dg.iati.api.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dan
 *
 */
public class Constants {
	
	public final static String MYSQL_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	public final static String MYSQL_PREFIX_URL = "jdbc:mysql://";
	public final static String POSGRES_DRIVER_CLASS_NAME = "org.postgresql.Driver";
	public final static String POSGRES_PREFIX_URL = "jdbc:postgresql://";
	
	public final static String IATI_FILE_RESULT_EXTENSION = ".out.xml";
	public final static String IATI_FILE_TRANSFORM_EXTENSION = ".xsl";
	public final static String IATI_FILE_EXTENSION = ".iati.xml";
	
	public final static String IATI_API_MAPPING_JAXB = "org.dg.iati.api.jaxb.iatiApiMapping";
	public final static String CONTENT_TYPE_COLUMN = "column";
	public final static String CONTENT_TYPE_CONSTANT = "constant";
	public final static String CONTENT_TYPE_SELECT = "select";
	public final static	List<String> CONTENT_TYPE_LIST = Arrays.asList(CONTENT_TYPE_CONSTANT,CONTENT_TYPE_COLUMN);
	
	public final static String IATI_ATTRIBUTE_REF = "ref";
	public final static String IATI_ATTRIBUTE_LANG = "xml:lang";
	public final static String IATI_ATTRIBUTE_VERSION = "version";
	public final static String IATI_ATTRIBUTE_DEFAULT_CURRENCY = "default-currency";
	public final static String IATI_ATTRIBUTE_HIERARCHY = "hierarchy";
	public final static String IATI_ATTRIBUTE_LAST_UPDATED_DATETIME = "last-updated-datetime";
	public final static String IATI_ATTRIBUTE_TYPE = "type";
	public final static String IATI_ATTRIBUTE_OWNER_REF = "owner-ref";
	public final static String IATI_ATTRIBUTE_OWNER_NAME = "owner-name";
	public final static String IATI_ATTRIBUTE_CODE = "code";
	public final static String IATI_ATTRIBUTE_ISO_DATE = "iso-date";
	public final static String IATI_ATTRIBUTE_ROLE = "role";
	public final static String IATI_ATTRIBUTE_PERCENTAGE = "percentage";
	public final static String IATI_ATTRIBUTE_COUNTRY = "country";
	public final static String IATI_ATTRIBUTE_ADM1 = "adm1";
	public final static String IATI_ATTRIBUTE_ADM2 = "adm2";
	public final static String IATI_ATTRIBUTE_LATITUDE = "latitude";
	public final static String IATI_ATTRIBUTE_LONGITUDE = "longitude";
	public final static String IATI_ATTRIBUTE_PRECISION = "precision";
	public final static String IATI_ATTRIBUTE_GAZETTEER_REF = "gazetteer-ref";
	public final static String IATI_ATTRIBUTE_VOCABULARY = "vocabulary";
	public final static String IATI_ATTRIBUTE_SIGNIFICANCE = "significance";
	public final static String IATI_ATTRIBUTE_VALUE_DATE = "value-date";
	public final static String IATI_ATTRIBUTE_CURRENCY = "currency";
	public final static String IATI_ATTRIBUTE_UPDATED = "updated";
	public final static String IATI_ATTRIBUTE_PROVIDER_ACTIVITY_ID = "provider-activity-id";
	public final static String IATI_ATTRIBUTE_RECEIVER_ACTIVITY_ID = "receiver-activity-id";
	public final static String IATI_ATTRIBUTE_URL = "url";
	public final static String IATI_ATTRIBUTE_FORMAT = "format";
	public final static String IATI_ATTRIBUTE_ATTACHED = "attached";
	public final static String IATI_ATTRIBUTE_AGGREGATION_STATUS = "aggregation-status";
	public final static String IATI_ATTRIBUTE_MEASURE = "measure";
	public final static String IATI_ATTRIBUTE_ASCENDING = "ascending";
	public final static String IATI_ATTRIBUTE_YEAR = "year";
	public final static String IATI_ATTRIBUTE_VALUE = "value";
	//public final static String IATI_ATTRIBUTE_ = "";
	

	//XSL Converter
	public final static String IATI_XSL_HEADER					= "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">";
	public final static String IATI_XSL_META 					= "<xsl:output method=\"xml\" indent=\"yes\"/>";
	public final static String IATI_XSL_CONTENT					= "<xsl:template match=\"/\"><iati-activities><xsl:for-each select=\"iati-activities//iati-activity\"><iati-activity><xsl:apply-templates/></iati-activity></xsl:for-each></iati-activities></xsl:template>";
	public final static String IATI_XSL_FOOTER					= "</xsl:stylesheet>";

	public final static List<String> IATI_LANG					= Arrays.asList("English", "French", "Spanish");
	public final static List<String> IATI_LANG_KEY				= Arrays.asList("en", "fr", "es");
	
	public final static List<String> IATI_CURRENCIES			= Arrays.asList("USD", "GBP", "EUR");
	
	public final static String IATI_PRESET_TIME					= "Preset";
	public final static String IATI_CURRENT_TIME				= "Current";
	public final static List<String> IATI_GENERATED_TIME_TYPE	= Arrays.asList(IATI_CURRENT_TIME, IATI_PRESET_TIME);
	
	
	public final static String IATI_DATASOURCE_FILE				= "file";
	public final static String IATI_DATASOURCE_MYSQL			= "mysql";
	public final static String IATI_DATASOURCE_POSTGRES			= "postgres";
	public final static List<String> IATI_DATASOURCE_TYPES		= Arrays.asList(IATI_DATASOURCE_MYSQL,IATI_DATASOURCE_POSTGRES,IATI_DATASOURCE_FILE);


	public final static String IATI_FILE_CSV		= "CSV";
	public final static String IATI_FILE_XML		= "XML";
	public final static List<String> IATI_FILE_TYPES	= Arrays.asList(IATI_FILE_CSV,IATI_FILE_XML);
	
	public final static String UI_TABS_JQUERY = "$(function() {$('#tabs').tabs().addClass('ui-tabs-vertical ui-helper-clearfix');$('#tabs li').removeClass('ui-corner-top').addClass('ui-corner-left');});";
	public static final String CONFIG_FILE = "config.properties";
	
}
