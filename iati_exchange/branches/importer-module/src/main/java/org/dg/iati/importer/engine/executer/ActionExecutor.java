package org.dg.iati.importer.engine.executer;

import java.util.HashMap;

import org.dg.iati.api.jaxb.iatiImportRules.TypeVisibility;
import org.dg.iati.importer.engine.ParameterTranslater;

public abstract class ActionExecutor {
	
	protected String name;
	protected String command;
	protected TypeVisibility visibility;
	
	protected HashMap<String, Object> globalParams;
	
	protected HashMap<String, Object> localParams;
	
	protected ParameterTranslater paramTranslator;
	
	private boolean executed	= false;
	
	
	public ActionExecutor(String name, String command, TypeVisibility visibility, ParameterTranslater paramTranslator,
			HashMap<String, Object> globalParams, HashMap<String, Object> localParams) {
		this.name 				= name;
		this.command			= command;
		this.visibility 		= visibility;
		this.paramTranslator	= paramTranslator;
		this.globalParams		= globalParams;
		this.localParams		= localParams;
	}
	
	
	public Object execute() {
		Object result 	= this.innerExecute();
		this.publishResult(result);
		this.executed	= true;
		return result;
	}
	
	protected abstract Object innerExecute();


	protected void publishResult (Object result) {
		if ( this.name != null && result != null) {
			if ( TypeVisibility.GLOBAL.equals(this.visibility) ) {
				globalParams.put(this.name, result);
			}
			else if ( TypeVisibility.LOCAL.equals(this.visibility) ) {
				localParams.put(this.name, result);
			}
		}
	}


	/**
	 * @return the executed
	 */
	public boolean isExecuted() {
		return executed;
	}

}
