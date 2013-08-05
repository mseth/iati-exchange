package org.dg.iati.importer.rules.loaders;

import org.dg.iati.api.jaxb.iatiImportRules.IatiImportRules;

public interface RulesLoader {

	IatiImportRules loadJaxbRules();

}
