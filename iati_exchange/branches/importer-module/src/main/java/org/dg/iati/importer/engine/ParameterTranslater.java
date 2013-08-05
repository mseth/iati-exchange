package org.dg.iati.importer.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dg.iati.api.jaxb.iatiApiResult.Item;
import org.dg.iati.api.jaxb.iatiApiResult.RefType;
import org.dg.iati.importer.engine.exception.NoSuchParameterAvailableException;


public class ParameterTranslater {
	private Logger logger	= Logger.getLogger(ParameterTranslater.class);
	
	private HashMap<String, String> httpReqVariables;
	private HashMap<String, Object> globalVariables;
	private HashMap<String, Object> localVariables;
	private Item item;
	
	private Pattern iatiPattern;
	private Pattern httpReqPattern;
	private Pattern globalPattern;
	private Pattern localPattern;
	
	
	
	public ParameterTranslater(HashMap<String, String> httpReqVariables,
			HashMap<String, Object> globalVariables, HashMap<String, Object> localVariables, Item item) {
		super();
		this.httpReqVariables = httpReqVariables;
		this.globalVariables = globalVariables;
		this.localVariables = localVariables;
		this.item = item;
		
		String iatiRegex		= "(\\$I\\{([^}]+)\\})";
		this.iatiPattern		= Pattern.compile( iatiRegex );
		
		String httpReqRegex		= "(\\$R\\{([^}]+)\\})";
		this.httpReqPattern		= Pattern.compile( httpReqRegex );
		
		String globalRegex		= "(\\$G\\{([^}]+)\\})";
		this.globalPattern		= Pattern.compile( globalRegex);
		
		String localRegex		= "(\\$L\\{([^}]+)\\})";
		this.localPattern		= Pattern.compile( localRegex);
	}

	/**
	 * 
	 * @param originalCommand original command that may contain parameters, for example $G{globalVariable}
	 * @return the command with all the parameters replaced with their values
	 * @throws NoSuchParameterAvailableException in case the specific parameter cannot be found in its respective scope
	 */
	public String translate( final String originalCommand ) {
		String result = originalCommand;
		
		result	= matchHttpParams(result);
		result	= matchGlobalParams(result);
		result	= matchLocalParams(result);
		result	= matchIatiParams(result);
		
		return result;
	}
	
	private String matchHttpParams (String command) {
		StringBuffer resultBuffer	= new StringBuffer();
		Matcher m	= this.httpReqPattern.matcher(command);
		while ( m.find() ) {
			String parameter	= m.group(2);
			String replacement	= this.httpReqVariables.get( parameter );
			if ( replacement == null ){
				// throw new NoSuchParameterAvailableException( "HTTP Request Parameter: " + parameter +  " - was not found");
				replacement = "";
				logger.warn("HTTP Request Parameter: " + parameter +  " - was not found");
			}
			m.appendReplacement(resultBuffer, replacement);
		}
		m.appendTail(resultBuffer);
		return resultBuffer.toString();
	} 
	
	private String matchGlobalParams (String command) {
		StringBuffer resultBuffer	= new StringBuffer();
		Matcher m	= this.globalPattern.matcher(command);
		while ( m.find() ) {
			String parameter	= m.group(2);
			Object replacement	= this.globalVariables.get( parameter );
			if ( replacement == null )
				throw new NoSuchParameterAvailableException( "Global Parameter: " + parameter +  " - was not found");
			m.appendReplacement(resultBuffer, replacement.toString() );
		}
		m.appendTail(resultBuffer);
		return resultBuffer.toString();
	}
	
	private String matchLocalParams (String command) {
		StringBuffer resultBuffer	= new StringBuffer();
		Matcher m	= this.localPattern.matcher(command);
		while ( m.find() ) {
			String parameter	= m.group(2);
			Object replacement	= this.localVariables.get( parameter );
			if ( replacement == null )
				throw new NoSuchParameterAvailableException( "Local Parameter: " + parameter +  " - was not found");
			m.appendReplacement(resultBuffer, replacement.toString() );
		}
		m.appendTail(resultBuffer);
		return resultBuffer.toString();
	} 
	
	private String matchIatiParams (String command) {
		StringBuffer resultBuffer	= new StringBuffer();
		Matcher m	= this.iatiPattern.matcher(command);
		while ( m.find() ) {
			String parameter	= m.group();
			String replacement	= this.getIatiValue(parameter);
			if ( replacement == null )
				throw new NoSuchParameterAvailableException( "IATI Parameter: " + parameter 
						+  " - was not found for item " + this.item.getRef() );
			m.appendReplacement(resultBuffer, replacement );
		}
		m.appendTail(resultBuffer);
		return resultBuffer.toString();
	}

	private String getIatiValue(String parameter) {
		if (parameter != null && parameter.length() > 0 ) {
			Matcher m	= this.iatiPattern.matcher(parameter);
			if ( m.find() ) {
				String path		= m.group(2); 
				String [] parts	= path.split("\\.");
				if ( parts != null && parts.length > 0 ) {
					List<String> partsList	= new ArrayList<String>( Arrays.asList(parts) );
					String result	= this.processPath( this.item, partsList );
					return result;
				}
			}
		}
		return null;
	}

	private String processPath(Item item, List<String> partsList) {
		String part	= partsList.remove(0);
		if ( part.startsWith("@")) {
			List<RefType> attributes	= item.getAttribute();
			String attributeName		= part.substring(1);
			if ( attributes != null && attributeName.length() > 0 ) {
				for (RefType attribute : attributes) {
					if ( attributeName.equals( attribute.getRef() ) ) {
						
						return attribute.getContent() != null ? attribute.getContent().trim() : null;
					}
						
				}
			}
		}
		else {
			if ( "this".equals( part ) ) {
				if ( partsList.size() == 0 ) {
					return item.getValue() != null ? item.getValue().trim() : null ;
				}
				else
					return processPath(item, partsList);
			}
			List<Item> childItems	= item.getItem();
			if ( childItems != null) {
				for (Item childItem : childItems) {
					if ( part.equals( childItem.getRef() ) ) {
						if ( partsList.size() == 0 )
							return childItem.getValue() != null ? childItem.getValue().trim() : null ;
						else
							return processPath(childItem, partsList);
					}
				}
			}
		}
		return null;
	} 
}
