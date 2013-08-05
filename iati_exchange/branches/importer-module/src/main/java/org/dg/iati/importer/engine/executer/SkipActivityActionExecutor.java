/**
 * 
 */
package org.dg.iati.importer.engine.executer;

import java.util.HashMap;

import org.dg.iati.api.jaxb.iatiImportRules.TypeVisibility;
import org.dg.iati.importer.engine.ParameterTranslater;
import org.dg.iati.importer.engine.exception.SkipActivityException;
import org.dg.iati.importer.util.ImporterConstants;

/**
 * @author alex
 *
 */
public class SkipActivityActionExecutor extends ActionExecutor {

	public SkipActivityActionExecutor(String name, String command,
			TypeVisibility visibility, ParameterTranslater paramTranslator,
			HashMap<String, Object> globalParams,
			HashMap<String, Object> localParams) {
		super(name, command, visibility, paramTranslator, globalParams, localParams);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.dg.iati.importer.engine.executer.ActionExecutor#innerExecute()
	 */
	@Override
	protected Object innerExecute() {
		String message = "Activity " + this.globalParams.get(ImporterConstants.ACTIVITY_NAME) + " skipped";
		if ( this.command != null && this.command.length() > 0 ) {
			message += ", reason: " + paramTranslator.translate(this.command);
		}
		throw new SkipActivityException( message );
	}

}
