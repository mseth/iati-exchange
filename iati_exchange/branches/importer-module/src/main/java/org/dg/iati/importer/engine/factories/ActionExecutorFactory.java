/**
 * 
 */
package org.dg.iati.importer.engine.factories;

import java.util.HashMap;

import org.dg.iati.api.jaxb.iatiImportRules.Action;
import org.dg.iati.api.jaxb.iatiImportRules.TypeMode;
import org.dg.iati.importer.engine.ParameterTranslater;
import org.dg.iati.importer.engine.executer.ActionExecutor;
import org.dg.iati.importer.engine.executer.ConstantActionExecutor;
import org.dg.iati.importer.engine.executer.IatiPathActionExecutor;
import org.dg.iati.importer.engine.executer.LogActionExecutor;
import org.dg.iati.importer.engine.executer.NothingActionExecutor;
import org.dg.iati.importer.engine.executer.PreparedActionExecutor;
import org.dg.iati.importer.engine.executer.SkipActivityActionExecutor;
import org.dg.iati.importer.engine.executer.SqlActionExecutor;

/**
 * @author alex
 *
 */
public class ActionExecutorFactory {
	
	private HashMap<String, Object> globalParams; 

	private HashMap<String, Object> localParams;
	
	public ActionExecutorFactory(HashMap<String, Object> globalParams,
			HashMap<String, Object> localParams) {
		super();
		this.globalParams = globalParams;
		this.localParams = localParams;
	}
	

	public ActionExecutor getActionExecutorInstance(Action rulesAction, ParameterTranslater paramTranslator) {
		ActionExecutor ret	= null;
		
		if ( TypeMode.IATI_PATH.equals( rulesAction.getMode() ) || TypeMode.EXISTENT.equals( rulesAction.getMode() ) ) {
			ret		= new IatiPathActionExecutor(rulesAction.getName(), rulesAction.getContent(), 
				rulesAction.getVisibility(), paramTranslator, globalParams, localParams);
		}
		else if ( TypeMode.CONSTANT.equals( rulesAction.getMode() ) ) {
			ret		= new ConstantActionExecutor(rulesAction.getName(), rulesAction.getContent(), 
					rulesAction.getVisibility(), paramTranslator, globalParams, localParams);
		}
		else if ( TypeMode.SQL.equals( rulesAction.getMode() ) ) {
			ret		= new SqlActionExecutor(rulesAction.getName(), rulesAction.getContent(), 
					rulesAction.getVisibility(), paramTranslator, globalParams, localParams);
		}
		else if ( TypeMode.NOTHING.equals( rulesAction.getMode() ) ) {
			ret		= new NothingActionExecutor(rulesAction.getName(), rulesAction.getContent(), 
					rulesAction.getVisibility(), paramTranslator, globalParams, localParams);
		}
		else if ( TypeMode.PREPARED_ACTION.equals( rulesAction.getMode() ) ) {
			ret		= new PreparedActionExecutor(rulesAction.getName(), rulesAction.getContent(), 
					rulesAction.getVisibility(), paramTranslator, globalParams, localParams);
		}
		else if ( TypeMode.SKIP_ACTIVITY.equals( rulesAction.getMode() ) ) {
			ret		= new SkipActivityActionExecutor(rulesAction.getName(), rulesAction.getContent(), 
					rulesAction.getVisibility(), paramTranslator, globalParams, localParams);
		}
		else if ( TypeMode.LOG.equals( rulesAction.getMode() ) ) {
			ret		= new LogActionExecutor(rulesAction.getName(), rulesAction.getContent(), 
					rulesAction.getVisibility(), paramTranslator, globalParams, localParams);
		}
		
		return ret;
	}

}
