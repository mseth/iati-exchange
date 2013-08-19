/**
 * 
 */
package org.dg.iati.importer.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.dg.iati.api.entity.Constants;
import org.dg.iati.api.entity.DataSourceConnection;
import org.dg.iati.importer.engine.exception.SkipActivityException;

/**
 * @author alex
 *
 */
public class JdbcSqlRunner {
	private static Logger logger 	= Logger.getLogger(JdbcSqlRunner.class);
	
	public static Hashtable<String, JdbcSqlRunner> instances	= new Hashtable<String, JdbcSqlRunner>();
	
	private String configName;
	
	private String username;
	
	private String password;
	
	private String url;
	
	private Connection connection;
	private DataSourceConnection dataSourceConnection = null;
	
	private JdbcSqlRunner(String configName) {
		this.configName	= configName;
	}

	public static JdbcSqlRunner getInstance(String configName) {
		JdbcSqlRunner runner	= instances.get(configName);
		if ( runner == null ) {
			runner	= new JdbcSqlRunner(configName);
			instances.put(configName, runner);
		}
		return runner;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public void startTransaction() throws SQLException {
		if ( this.dataSourceConnection == null ) {
			this.dataSourceConnection	= new DataSourceConnection(Constants.POSGRES_DRIVER_CLASS_NAME,
					Constants.POSGRES_PREFIX_URL+url, username, password);
			this.connection	= this.dataSourceConnection.createConnection();
			this.connection.setAutoCommit(false);
		}
	}
	
	public void finish() {
		try {
			if (this.connection != null)
				this.connection.close();
			this.dataSourceConnection	= null;
			instances.remove(this.configName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void commit() {
		try {
			this.connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void rollback() {
		try {
			this.connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public Object runSql(String sql) {
		try {
			logger.debug("Executing query: " + sql);
			Statement st	= connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if ( sql.toLowerCase().trim().startsWith("insert") || sql.toLowerCase().trim().startsWith("update") 
					|| sql.toLowerCase().trim().startsWith("delete") ) {
					st.executeUpdate(sql);
			}
			else {
				ResultSet rs	= st.executeQuery(sql);
				if ( rs.next() ) {
					Object ret	= rs.getObject(1);
					rs.close();
					return ret;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			SkipActivityException skipException = new SkipActivityException(e);
			throw skipException;
		}
		return null;
	}

	

}
