/**
 * 
 */
package org.dg.iati.api.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author dan
 *
 */
public class DataSourceConnection {

	private Connection connection = null;
	private BasicDataSource bdSource = new BasicDataSource();
	private String driverClassName="com.mysql.jdbc.Driver";//default value;
	private String baseURL="jdbc:mysql://localhost:3306/";//default value;
	private String database="";
	private String username="";
	private String password="";
	private String url=null;
	
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUrl() {
		if(url==null) return baseURL+"/"+database;
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	//TODO to be changed
	public DataSourceConnection() {
//        bdSource.setDriverClassName("com.mysql.jdbc.Driver");
//        bdSource.setUrl("jdbc:mysql://localhost:3306/student");
//        bdSource.setUsername("root");
//        bdSource.setPassword("root");
    }
	
	
	
	public DataSourceConnection(String driverClassName, String baseURL, String database, String username, String password) {
		super();
		this.driverClassName = driverClassName;
		this.baseURL = baseURL;
		this.database = database;
		this.username = username;
		this.password = password;
		populateBDSource();

	}

	public void populateBDSource() {
		// TODO Auto-generated method stub
        bdSource.setDriverClassName(driverClassName);
        bdSource.setUrl(url);
        bdSource.setUsername(username);
        bdSource.setPassword(password);
	}

	public DataSourceConnection(String driverClassName, String url,String username, String password) {
		super();
		this.driverClassName = driverClassName;
		this.username = username;
		this.password = password;
		this.url = url;
		populateBDSource();
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public BasicDataSource getBdSource() {
		return bdSource;
	}

	public void setBdSource(BasicDataSource bdSource) {
		this.bdSource = bdSource;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public String getDatatabase() {
		return database;
	}

	public void setDatatabase(String datatabase) {
		this.database = datatabase;
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

	/**
	 * 
	 */
	

    public Connection createConnection() {
        Connection con = null;
        try {
                if (connection != null) {
                        System.out.println("Can't create a New Connection");
                } else {
                        con = bdSource.getConnection();
                        System.out.println("Connection Done successfully");
                }
        } catch (Exception e) {
        		e.printStackTrace();
                System.out.println("Error Occured " + e.toString());
        }
        return con;
    }


}
