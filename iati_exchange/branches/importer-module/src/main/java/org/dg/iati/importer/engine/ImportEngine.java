package org.dg.iati.importer.engine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dg.iati.api.jaxb.iatiApiResult.IatiActivities;
import org.dg.iati.api.jaxb.iatiApiResult.IatiActivity;
import org.dg.iati.api.jaxb.iatiApiResult.Item;
import org.dg.iati.api.jaxb.iatiApiResult.RefType;
import org.dg.iati.api.jaxb.iatiImportRules.Action;
import org.dg.iati.api.jaxb.iatiImportRules.Field;
import org.dg.iati.api.jaxb.iatiImportRules.IatiImportRules;
import org.dg.iati.api.jaxb.iatiImportRules.Test;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.ConfigInstance;
import org.dg.iati.importer.engine.exception.SkipActivityException;
import org.dg.iati.importer.engine.executer.ActionExecutor;
import org.dg.iati.importer.engine.factories.ActionExecutorFactory;
import org.dg.iati.importer.engine.factories.TestProcessorFactory;
import org.dg.iati.importer.jdbc.JdbcSqlRunner;
import org.dg.iati.importer.util.ImporterConstants;

public class ImportEngine {

	private static Logger logger	= Logger.getLogger(ImportEngine.class);
	
	private IatiImportRules rules;
	private IatiActivities iatiActivities;
	private HashMap<String, Object> globalVariables;
	private HashMap<String, Object> localVariables;
	private HashMap<String, String> httpReqVariables;
	private ActionExecutorFactory aeFactory;
	
	private Map<String,String> successful			= new LinkedHashMap<String, String>();
	private List<String> failed				= new ArrayList<String>();
	
	
	public void transform() throws RuntimeException {
		String configName		= rules.getMappingName();
		JdbcSqlRunner sqlRunner = JdbcSqlRunner.getInstance( configName );
		sqlRunner.setUsername( rules.getDatadestination().getUsername() );
		sqlRunner.setPassword( rules.getDatadestination().getPassword() );
		sqlRunner.setUrl( rules.getDatadestination().getUrl() );
		
		String activityVarName = rules.getActivityVarName();
		
		try {
			sqlRunner.startTransaction();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return;
		}
		
		List<IatiActivity> activities	= this.iatiActivities.getIatiActivity();
		for (IatiActivity iatiActivity : activities) {
			String currentActivity 	= null;
			try {
				this.globalVariables.put(ImporterConstants.IMPORTER_CONFIGURATION_NAME, configName);
				Item	iatiActivityItem	= this.transformActivityToItem(iatiActivity);
				for (Item temp: iatiActivity.getItem() ) {
					if ("title".equals(temp.getRef())) {
						this.globalVariables.put(ImporterConstants.ACTIVITY_NAME, temp.getValue() );
					}
					else if ("iati-identifier".equals(temp.getRef()) ) {
						this.globalVariables.put(ImporterConstants.ACTIVITY_IATI_ID, temp.getValue() );
					}
				}
 				currentActivity		= this.globalVariables.get(ImporterConstants.ACTIVITY_NAME) + 
 						" -> " + this.globalVariables.get(ImporterConstants.ACTIVITY_IATI_ID);
 				logger.info("Processing " + currentActivity );
				this.runTests(rules.getIatiActivity().getTest(), iatiActivityItem);
				this.runActions(rules.getIatiActivity().getAction(), iatiActivityItem);
				
				for ( Field f: rules.getMapping().getField() ) {
					for ( Item iatiItem: iatiActivity.getItem() ) {
						if ( f.getRef().equals(iatiItem.getRef()) ) {
							this.localVariables.clear();
							this.runTests(f.getTest(), iatiItem);
							this.runActions(f.getAction(), iatiItem);
						}
					}
				} 
				
				sqlRunner.commit();
				this.successful.put(this.globalVariables.get(activityVarName).toString(), currentActivity);
				this.localVariables.clear();
				this.globalVariables.clear();
			}
			catch (SkipActivityException e) {
				logger.warn(e.getMessage());
				sqlRunner.rollback();
				this.failed.add(currentActivity + " - because: " + e.getMessage() );
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error("An error appeared while processing iati activity: " + this.globalVariables.get(ImporterConstants.ACTIVITY_NAME));
				sqlRunner.rollback();
				this.failed.add(currentActivity + " - because: " + e.getMessage() );
			}
			
		}
	
		
		sqlRunner.finish();
		logger.info( this.generateSummaryReport() );
		logger.info( this.generateReport() );
	}
	
	private void runActions(List<Action> action, Item iatiItem) {
		for (Action a: action ) {
			ParameterTranslater paramTranslator	= new ParameterTranslater(httpReqVariables, globalVariables, localVariables, iatiItem);
			ActionExecutor aExec	= aeFactory.getActionExecutorInstance(a, paramTranslator);
			aExec.execute();
		}
		
	}

	private void runTests(List<Test> tests, Item iatiItem) {
		for (Test test : tests ) {
			TestProcessor tp	= 
					TestProcessorFactory.createTestProcessorInstance(test, iatiItem, 
							httpReqVariables, globalVariables, localVariables);
			tp.run();
		}
	}

	private Item transformActivityToItem(IatiActivity iatiActivity) {
		Item iatiActivityItem	= new Item();
		iatiActivityItem.setRef("iati-activity");
		iatiActivityItem.getItem().addAll( iatiActivity.getItem() );
		
		RefType defaultCurrencyAttr	= new RefType();
		defaultCurrencyAttr.setRef("default-currency");
		defaultCurrencyAttr.setContent( iatiActivity.getDefaultCurrency() );
		
		RefType hierarchyAttr	= new RefType();
		hierarchyAttr.setRef("hierarchy");
		hierarchyAttr.setContent( iatiActivity.getHierarchy() );
		
		RefType langAttr	= new RefType();
		langAttr.setRef("lang");
		langAttr.setContent( iatiActivity.getLang() );
		
		iatiActivityItem.getAttribute().add(defaultCurrencyAttr);
		iatiActivityItem.getAttribute().add(hierarchyAttr);
		iatiActivityItem.getAttribute().add(langAttr);
		
		
		return iatiActivityItem;
	}
	
	
	public String generateSummaryReport() {
		return "A total of " + this.successful.size() + " were imported. " +
				"A total of " + this.failed.size() + " failed to import.";
	}
	
	public String generateReport() {
		StringBuffer sBuffer 	= new StringBuffer();
		sBuffer.append("Succesfully imported activities: \n");
		for (Entry<String, String> e: this.successful.entrySet() ) {
			String name 		= e.getValue();
			String activityId	= e.getKey();
			String viewUrl		= ConfigInstance.getInstance().get( ConfigConstants.IMPORT_VIEW_URL ).replace("${activity-id}", activityId);
			String editUrl		= ConfigInstance.getInstance().get( ConfigConstants.IMPORT_EDIT_URL ).replace("${activity-id}", activityId);
			
			sBuffer.append(" -- " + name);
			sBuffer.append(" - view: " + viewUrl );
			sBuffer.append(" - edit: " + editUrl + "\n" );
		}
		
		sBuffer.append("\n Failed activities: \n");
		for (String name : this.failed) {
			sBuffer.append(" -- " + name + "\n");
		}
		return sBuffer.toString();
	}
	
	
	/**
	 * @param globalVariables the globalVariables to set
	 */
	public void setGlobalVariables(HashMap<String, Object> globalVariables) {
		this.globalVariables = globalVariables;
	}

	/**
	 * @param localVariables the localVariables to set
	 */
	public void setLocalVariables(HashMap<String, Object> localVariables) {
		this.localVariables = localVariables;
	}

	/**
	 * @param httpReqVariables the httpReqVariables to set
	 */
	public void setHttpReqVariables(HashMap<String, String> httpReqVariables) {
		this.httpReqVariables = httpReqVariables;
	}

	public void setRules(IatiImportRules rules) {
		this.rules	= rules;
	}

	public void setSource(IatiActivities iatiActivities) {
		this.iatiActivities		= iatiActivities;
	}

	/**
	 * @param aeFactory the aeFactory to set
	 */
	public void setAeFactory(ActionExecutorFactory aeFactory) {
		this.aeFactory = aeFactory;
	}


	/**
	 * @return the successful
	 */
	public Map<String, String> getSuccessful() {
		return successful;
	}

	/**
	 * @return the failed
	 */
	public List<String> getFailed() {
		return failed;
	}

	


}
