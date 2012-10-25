/**
 * 
 */
package org.dg.iati.api.entity;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.dg.iati.api.transformer.TransformationMetadata;

/**
 * @author dan
 *
 */
public class IatiSettings implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1478493555549558239L;
	
	@TransformationMetadata ( jaxbMapping = "datasource:type" )
	private String datasourceType	= Constants.IATI_DATASOURCE_MYSQL;
	private String databaseType;
	private InputStream file;
	private InputStream fileInput;
	public InputStream getFileInput() {
		return fileInput;
	}



	public void setFileInput(InputStream fileInput) {
		this.fileInput = fileInput;
	}



	private String fileType;
	@TransformationMetadata ( jaxbMapping = "datasource:username" )
	private String username;
	@TransformationMetadata ( jaxbMapping = "datasource:password" )
	private String password;
	@TransformationMetadata ( jaxbMapping = "datasource:url" )
	private String url;
	@TransformationMetadata ( jaxbMapping = "generatedDatetime:content" )
	private String generatedTime;
	@TransformationMetadata ( jaxbMapping = "generatedDatetime:type" )
	private String generatedTimeType;
	
	@TransformationMetadata ( jaxbMapping = "globalSettings:organizationName" )
	private String orgName;
	@TransformationMetadata ( jaxbMapping = "globalSettings:organizationType" )
	private String orgType;
	@TransformationMetadata ( jaxbMapping = "globalSettings:organizationRef" )
	private String orgRef;
	@TransformationMetadata ( jaxbMapping = "globalSettings:defaultCurrency" )
	private String defaultCurrency;
	@TransformationMetadata ( jaxbMapping = "globalSettings:defaultLanguage" )
	private String defaultLanguage;
	
	@TransformationMetadata ( jaxbMapping = "mappingName" )
	private String settingName	= "";
	private String test;
	
	@TransformationMetadata ( jaxbMapping = "csvMappingFilename" )
	private String csvMapperFilename;
	private transient List<FileUpload> csvMapperFileUpload;
	
	@TransformationMetadata ( jaxbMapping = "mapping:field", itemClassName = "org.dg.iati.api.jaxb.iatiApiMapping.Field", 
			correlationClassName = "org.dg.iati.api.transformer.ElementCorrelation" )
	private List<IatiMappingElement> mappingItems = new ArrayList<IatiMappingElement>();
	
	@TransformationMetadata ( jaxbMapping = "globalQuery:content" )
	private String globalQuery;
	
	@TransformationMetadata ( jaxbMapping = "iatiActivity:content" )
	private String globalQueryIDs;
	
	@TransformationMetadata ( jaxbMapping = "globalQuery:type" )
	private String globalQueryType	= Constants.IATI_GLOBALQUERY_TYPE_SELECT;
	
	   
	public String getGlobalQuery() {
		return globalQuery;
	}



	public void setGlobalQuery(String globalQuery) {
		this.globalQuery = globalQuery;
	}



	public String getGlobalQueryIDs() {
		return globalQueryIDs;
	}



	public void setGlobalQueryIDs(String globalQueryIDs) {
		this.globalQueryIDs = globalQueryIDs;
	}



	public List<IatiMappingElement> getMappingItems() {
		return mappingItems;
	}



	public void setMappingItems(List<IatiMappingElement> mappingItems) {
		this.mappingItems = mappingItems;
	}



	public String getTest() {
		return test;
	}



	public void setTest(String test) {
		this.test = test;
	}



	public String getSettingName() {
		return settingName;
	}



	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}



	public String getDatasourceType() {
		return datasourceType;
	}



	public void setDatasourceType(String datasourceType) {
		this.datasourceType = datasourceType;
	}



	public String getDatabaseType() {
		return databaseType;
	}



	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}



	public InputStream getFile() {
		return file;
	}



	public void setFile(InputStream file) {
		this.file = file;
	}



	public String getFileType() {
		return fileType;
	}



	public void setFileType(String fileType) {
		this.fileType = fileType;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}


	public String getGeneratedTime() {
		return generatedTime;
	}



	public void setGeneratedTime(String generatedTime) {
		this.generatedTime = generatedTime;
	}



	public String getGeneratedTimeType() {
		return generatedTimeType;
	}



	public void setGeneratedTimeType(String generatedTimeType) {
		this.generatedTimeType = generatedTimeType;
	}



	public String getOrgName() {
		return orgName;
	}



	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}



	public String getOrgType() {
		return orgType;
	}



	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}



	public String getOrgRef() {
		return orgRef;
	}



	public void setOrgRef(String orgRef) {
		this.orgRef = orgRef;
	}



	public String getDefaultCurrency() {
		return defaultCurrency;
	}



	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}



	public String getDefaultLanguage() {
		return defaultLanguage;
	}



	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}
	


	public String getCsvMapperFilename() {
		return csvMapperFilename;
	}



	public void setCsvMapperFilename(String csvMapperFilename) {
		this.csvMapperFilename = csvMapperFilename;
	}

	
	public List<FileUpload> getCsvMapperFileUpload() {
		return csvMapperFileUpload;
	}



	public void setCsvMapperFileUpload(List<FileUpload> csvMapperFileUpload) {
		this.csvMapperFileUpload = csvMapperFileUpload;
	}



	public IatiSettings() {
		// TODO Auto-generated constructor stub
	}
	

}
