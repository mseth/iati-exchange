/**
 * 
 */
package org.dg.iati.api.entity;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.dg.iati.api.jaxb.iatiApiMapping.Datasource;
import org.dg.iati.api.jaxb.iatiApiMapping.Field;
import org.dg.iati.api.jaxb.iatiApiMapping.IatiApiMapping;
import org.dg.iati.api.jaxb.iatiApiResult.IatiActivities;
import org.dg.iati.api.jaxb.iatiApiResult.IatiActivity;
import org.dg.iati.api.jaxb.iatiApiResult.Item;
import org.dg.iati.api.jaxb.iatiApiResult.ObjectFactory;
import org.dg.iati.api.transformer.jaxb.IATIFileWriter;
import org.dg.iati.api.transformer.jaxb.XmlFileWriter;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.IatiUtils;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author dan
 *
 */
public class IatiMappingFile {

	/**
	 * field that contains the information regarding the mapping
	 */

	public static final Logger logger			= Logger.getLogger(IatiMappingFile.class);
	
	private IatiApiMapping mappingFile = null;
	private DataSourceConnection ds = null;
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet activitiesRS = null; 
	private ResultSet globalRS = null;
	private ArrayList<String> activitiesIDs = new ArrayList<String>();
	private String currentActivityID = null;
	
	private IatiMappedValue mappingValues = new IatiMappedValue();
	
	private InputStream inputStream = null;
	
	private String uniqueIdentifier	= null;
	
	private boolean hasMapping(){
		return mappingValues.getItems().size()>0;
	}
	
	
	/**
	 * @return the uniqueIdentifier
	 */
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	/**
	 * @param uniqueIdentifier the uniqueIdentifier to set
	 */
	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}




	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	public String getCurrentActivityID() {
		return currentActivityID;
	}


	public void setCurrentActivityID(String currentActivityID) {
		this.currentActivityID = currentActivityID;
	}


	/**
	 * 
	 */
	public IatiMappingFile() {
		// TODO Auto-generated constructor stub
	}
	
	public IatiMappingFile(InputStream input){
		this.inputStream = input;
	}

	public IatiMappingFile(IatiApiMapping mappingFile) {
		super();
		this.mappingFile = mappingFile;
		if(this.ds == null) 
			this.ds = new DataSourceConnection();
	}
	
	public IatiMappingFile(IatiApiMapping mappingFile, DataSourceConnection ds) {
		super();
		this.mappingFile = mappingFile;
		this.ds = ds;
	}
	
	public void createConnectionEnvironment(){
		//(String driverClassName, String baseURL, String database, String username, String password) {
		Datasource uploadedDS = this.getMappingFile().getDatasource();
		String driverClassName = null;
		String url = null;
		if("mysql".compareTo(uploadedDS.getType()) == 0)
			{
				driverClassName = Constants.MYSQL_DRIVER_CLASS_NAME;
				url=Constants.MYSQL_PREFIX_URL+uploadedDS.getUrl();
			}
		
		if("postgres".compareTo(uploadedDS.getType()) == 0)
			{
				driverClassName = Constants.POSGRES_DRIVER_CLASS_NAME;
				url=Constants.POSGRES_PREFIX_URL+uploadedDS.getUrl();
			}

		
		this.ds = new DataSourceConnection(driverClassName,url,uploadedDS.getUsername(), uploadedDS.getPassword());
		
	}
	
	public void processMapping(IatiActivity resultActivity,  HashMap<String,String> params) throws SQLException {
		List<Field> mappedFieldsList = getMappedFieldsList();
		for (Iterator<Field> it = mappedFieldsList.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			IatiMappingFieldWorker f = new IatiMappingFieldWorker(field, globalRS, con, currentActivityID, this.getMappingValues(), params);
			//f.printContent();
			ArrayList<Item> resultItemList = f.getResultItemList();
			if(resultItemList!=null)
				resultActivity.getItem().addAll(resultItemList);
		}
	}

	
    public void openConnection() throws SQLException {
    	con = ds.createConnection();
    	stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    }
    
    public boolean existConnection(){
    	return con!=null;
    }
    
    public void closeConnection() throws SQLException{
    	con.close();
    	stmt.close();
    }

    public ResultSet executeQuery(String q) throws SQLException{
    	return stmt.executeQuery(q);
    }
    
    public void closeFullConnection(ResultSet rs) throws SQLException{
	    con.close();
	    stmt.close();
	    rs.close();
    }

	public void executeGlobalQueries() throws SQLException {
		// TODO Auto-generated method stub
		if(!existConnection())
			openConnection();
		activitiesRS 	= 	executeQuery(this.mappingFile.getIatiActivity().getContent());
//		globalRS		=	con.createStatement().executeQuery(this.mappingFile.getGlobalQuery().getContent());
	}
    
	public void populateActivityIDs() throws SQLException{
		activitiesIDs = new ArrayList<String>();
		if(activitiesRS ==  null) return;
		while (activitiesRS.next()) {
			activitiesIDs.add(activitiesRS.getString(1));
		}
	}
	
	public void executeGlobalQuery(String parentID, HashMap<String,String> params) throws SQLException{
		String q = null;
		q = IatiUtils.getCleanQuery(this.mappingFile.getGlobalQuery().getContent(), parentID, params);
		globalRS	=	con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(q);
	}
	
	
	public ArrayList<String> getActivitiesIDs() {
		return activitiesIDs;
	}


	public void setActivitiesIDs(ArrayList<String> activitiesIDs) {
		this.activitiesIDs = activitiesIDs;
	}


	public ResultSet getGlobalRS() {
		return globalRS;
	}


	public void setGlobalRS(ResultSet globalRS) {
		this.globalRS = globalRS;
	}


	public Statement getStmt() {
		return stmt;
	}


	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}


	public ResultSet getActivitiesRS() {
		return activitiesRS;
	}


	public void setActivitiesRS(ResultSet activitiesRS) {
		this.activitiesRS = activitiesRS;
	}


	public Connection getCon() {
		return con;
	}


	public void setCon(Connection con) {
		this.con = con;
	}


	public IatiMappingFile(DataSourceConnection ds) {
		super();
		this.ds = ds;
	}


	public DataSourceConnection getDs() {
		return ds;
	}


	public void setDs(DataSourceConnection ds) {
		this.ds = ds;
	}

	public IatiApiMapping getMappingFile() {
		return mappingFile;
	}

	public void setMappingFile(IatiApiMapping mappingFile) {
		this.mappingFile = mappingFile;
	}

	public List<Field> getMappedFieldsList(){
		return this.getMappingFile().getMapping().getField();
	}


	public void run(HashMap<String, String> params) throws JAXBException, SQLException, TransformerException  {
		if(mappingFile == null)
		{
			JAXBContext jc 	= JAXBContext.newInstance(Constants.IATI_API_MAPPING_JAXB);
	        Unmarshaller m 	= jc.createUnmarshaller();
	        mappingFile 	= (IatiApiMapping) m.unmarshal(inputStream);
		}
        this.createConnectionEnvironment();
        try {
        	this.processCSVFile();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        this.openConnection();
        this.executeGlobalQueries();
        this.populateActivityIDs();
        
		ObjectFactory o = new ObjectFactory();
		IatiActivities rootResult = o.createIatiActivities();
		XSLConverter xslConv = new XSLConverter();
		xslConv.createSkeleton();
		
		boolean xslRun = false;
		
        
		for (Iterator<String> it = this.getActivitiesIDs().iterator(); it.hasNext();) {
        	
			String id = (String) it.next();
			ObjectFactory of = new ObjectFactory();
			IatiActivity resultActivity = of.createIatiActivity();
			
			this.executeGlobalQuery(id, params);
			this.setCurrentActivityID(id);
			this.getGlobalRS().first();
			this.processMapping(resultActivity, params);
			if(!xslRun)
				{
					xslConv.run(resultActivity);
					xslRun=true;
				}
			rootResult.getIatiActivity().add(resultActivity);
		}
		String fileName		= IatiMappingFile.generateResultFileName(mappingFile.getMappingName(), this.uniqueIdentifier );
		
    	IATIFileWriter xslWriter		= new IATIFileWriter(fileName,xslConv.getXSL());
    	xslWriter.persist();

    	XmlFileWriter<IatiActivities> writer		= new XmlFileWriter<IatiActivities>(rootResult, fileName, Constants.IATI_FILE_RESULT_EXTENSION);
    	writer.persist();
    	
    	Source xmlSource 	=	new StreamSource(new File(XmlFileWriter.MAPPING_FOLDER+"/"+fileName + Constants.IATI_FILE_RESULT_EXTENSION));
    	Source xsltSource 	= 	new StreamSource(new File(XmlFileWriter.MAPPING_FOLDER+"/"+fileName + Constants.IATI_FILE_TRANSFORM_EXTENSION));
    	Result result		=	new StreamResult(new File(IatiMappingFile.generateFinalIatiFilePath(fileName)));
    	 
    	// create an instance of TransformerFactory
    	javax.xml.transform.TransformerFactory transFact 	= javax.xml.transform.TransformerFactory.newInstance( );
  	    javax.xml.transform.Transformer trans 				= transFact.newTransformer(xsltSource);
  	    trans.transform(xmlSource, result);
  	    logger.info("File "+mappingFile.getMappingName() + Constants.IATI_FILE_EXTENSION+ " written OK!");
    	
	}


	//CSV header file
	// iati-path, local-value, iati-value
	private void processCSVFile() throws IOException {

		if(mappingFile.getCsvMappingFilename() == null || 
				"".compareTo(mappingFile.getCsvMappingFilename()) == 0 ||
				! hasMapping()) 
			return;
		
		CSVReader reader = new CSVReader(new FileReader(mappingFile.getCsvMappingFilename()),',', '\"');
		try {
			List<String[]> mappingList = reader.readAll();
			boolean firstLine = true;
			for (Iterator<String[]> it = mappingList.iterator(); it.hasNext();) {
				String[] line = (String[]) it.next();
				if(firstLine){
					firstLine = false;
					continue;
				}
				this.getMappingValues().add(line);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("ok");
	}


	public IatiMappedValue getMappingValues() {
		return mappingValues;
	}


	public void setMappingValues(IatiMappedValue mappingValues) {
		this.mappingValues = mappingValues;
	}
	
	
	public static String generateResultFileName(String mappingName, String queryUniqueIdentifier) {
		return 
			(queryUniqueIdentifier==null?mappingName:mappingName+"-"+queryUniqueIdentifier);
	}
	
	public static String generateFinalIatiFilePath(String fileName){
		return 
				IatiUtils.getPropertyValue(ConfigConstants.MAPPING_FOLDER_NAME)+"/"+fileName + Constants.IATI_FILE_EXTENSION;
	}
	
}
