/**
 * 
 */
package org.dg.iati.importer.pageentities;

import java.io.Serializable;

/**
 * @author alex
 *
 */
public class GlobalSettingsImporter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7356993220947288819L;
	
	
	private String mappingName;
	private String username;
	private String password;
	private String url;
	private String filename;
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * @return the mappingName
	 */
	public String getMappingName() {
		return mappingName;
	}
	/**
	 * @param mappingName the mappingName to set
	 */
	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}
	
	

}
