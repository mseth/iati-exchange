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
public class IatiPathActionExecutor extends ActionExecutor {


	public IatiPathActionExecutor(String name, String command,
			TypeVisibility visibility, ParameterTranslater paramTranslator,
			HashMap<String, Object> globalParams,
			HashMap<String, Object> localParams) {
		super(name, command, visibility, paramTranslator, globalParams, localParams);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Object innerExecute() {
		String result 	= paramTranslator.translate(this.command);
		return result;
	}

}
