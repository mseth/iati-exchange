/**
 * 
 */
package org.dg.iati.importer.engine.executer;

import java.util.HashMap;

import org.dg.iati.api.jaxb.iatiImportRules.TypeVisibility;
import org.dg.iati.importer.engine.ParameterTranslater;

/**
 * @author alex
 *
 */
public class PreparedActionExecutor extends ActionExecutor {

	public PreparedActionExecutor(String name, String command,
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
		return null;
	}

}
