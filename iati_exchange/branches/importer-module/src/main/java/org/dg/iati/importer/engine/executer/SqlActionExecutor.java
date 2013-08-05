/**
 * 
 */
package org.dg.iati.importer.engine.executer;

import java.util.HashMap;

import org.dg.iati.api.jaxb.iatiImportRules.TypeVisibility;
import org.dg.iati.importer.engine.ParameterTranslater;
import org.dg.iati.importer.jdbc.JdbcSqlRunner;
import org.dg.iati.importer.util.ImporterConstants;

/**
 * @author alex
 *
 */
public class SqlActionExecutor extends ActionExecutor {

	public SqlActionExecutor(String name, String command,
			TypeVisibility visibility, ParameterTranslater paramTranslator,
			HashMap<String, Object> globalParams,
			HashMap<String, Object> localParams) {
		super(name, command, visibility, paramTranslator, globalParams, localParams);
	}

	/* (non-Javadoc)
	 * @see org.dg.iati.importer.engine.executer.ActionExecutor#innerExecute()
	 */
	@Override
	protected Object innerExecute() {
		Object result		= null;
		String sql 			= paramTranslator.translate(this.command);
		String configName	= (String) globalParams.get(ImporterConstants.IMPORTER_CONFIGURATION_NAME);
		JdbcSqlRunner sqlRunner = JdbcSqlRunner.getInstance(configName);
		result				= sqlRunner.runSql(sql);
		
		return result;
	}
	
	

}
