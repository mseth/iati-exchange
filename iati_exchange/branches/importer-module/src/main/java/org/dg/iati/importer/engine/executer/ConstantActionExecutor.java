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
public class ConstantActionExecutor extends ActionExecutor {


	public ConstantActionExecutor(String name, String command,
			TypeVisibility visibility, ParameterTranslater paramTranslator,
			HashMap<String, Object> globalParams,
			HashMap<String, Object> localParams) {
		super(name, command, visibility, paramTranslator, globalParams, localParams);
	}

	/* (non-Javadoc)
	 * @see org.dg.iati.importer.engine.executer.ActionExecutor#innerExecute(java.lang.String, java.lang.String)
	 */
	@Override
	protected Object innerExecute() {
		return this.command;
	}

}
