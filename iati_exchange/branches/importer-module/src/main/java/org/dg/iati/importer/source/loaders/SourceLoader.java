package org.dg.iati.importer.source.loaders;

import org.dg.iati.api.jaxb.iatiApiResult.IatiActivities;

public interface SourceLoader {

	IatiActivities loadJaxbSource();

}
