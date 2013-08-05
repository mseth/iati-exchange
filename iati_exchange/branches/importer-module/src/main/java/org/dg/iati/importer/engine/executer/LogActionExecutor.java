/**
 * 
 */
package org.dg.iati.importer.engine.executer;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.dg.iati.api.jaxb.iatiImportRules.TypeVisibility;
import org.dg.iati.importer.engine.ParameterTranslater;

/**
 * @author alex
 *
 */
public class LogActionExecutor extends ActionExecutor {
	private Logger logger 	= Logger.getLogger(LogActionExecutor.class);
	/**
	 * @param name
	 * @param command
	 * @param visibility
	 * @param paramTranslator
	 * @param globalParams
	 * @param localParams
	 */
	public LogActionExecutor(String name, String command,
			TypeVisibility visibility, ParameterTranslater paramTranslator,
			HashMap<String, Object> globalParams,
			HashMap<String, Object> localParams) {
		super(name, command, visibility, paramTranslator, globalParams,
				localParams);
	}

	/* (non-Javadoc)
	 * @see org.dg.iati.importer.engine.executer.ActionExecutor#innerExecute()
	 */
	@Override
	protected Object innerExecute() {
		String message		= paramTranslator.translate(this.command);
		logger.info(message);
		return message;
	}

}
