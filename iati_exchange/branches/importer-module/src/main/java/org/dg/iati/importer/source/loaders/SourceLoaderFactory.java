package org.dg.iati.importer.source.loaders;

import org.dg.iati.api.jaxb.iatiApiResult.IatiActivities;
import org.dg.iati.api.jaxb.iatiImportRules.IatiImportRules;
import org.dg.iati.api.transformer.jaxb.XmlFileReader;
import org.dg.iati.api.transformer.jaxb.XmlFileReaderAndTransformer;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.ConfigInstance;

public class SourceLoaderFactory {
	
	private static final String CUSTOM_FILE = "custom-file";
	private static final String FILE = "file";
	private static final String URL = "url";
	
	private IatiImportRules importRules;

	public SourceLoaderFactory(IatiImportRules importRules) {
		super();
		this.importRules = importRules;
	}
	
	public SourceLoader getSourceLoaderInstace() {
		if ( this.importRules != null && this.importRules.getDatasource() != null 
				&& this.importRules.getDatasource().getType() != null
				&& this.importRules.getDatasource().getType().length() > 0 ) {
			
			if ( FILE.equals( this.importRules.getDatasource().getType() ) || 
					CUSTOM_FILE.equals( this.importRules.getDatasource().getType() ) ) {
				String srcBaseFolderName	= ConfigInstance.getInstance().get( ConfigConstants.IMPORT_MAPPING_FOLDER_NAME );
				String ruleName			= this.importRules.getMappingName();
				if ( ruleName == null ) throw new CannotCreateSourceLoaderException( "Cannot find rule name" );
				
				String sourceFileName	= this.importRules.getDatasource().getUrl();
				
				String folderName		= srcBaseFolderName + "/" + ruleName; 
				
				String xslFile			= ConfigInstance.getInstance().get( ConfigConstants.IMPORT_XSL_FILE );
				
				XmlFileReader<IatiActivities> xmlFileReader = null;
				if ( CUSTOM_FILE.equals( this.importRules.getDatasource().getType() ) ) {
					xmlFileReader = new XmlFileReader<IatiActivities>( IatiActivities.class, sourceFileName, folderName );
				}
				else if ( FILE.equals( this.importRules.getDatasource().getType() ) ){
					xmlFileReader = new XmlFileReaderAndTransformer<IatiActivities>( IatiActivities.class, sourceFileName, folderName, xslFile );
				}
				SourceLoader srcLoader	= new LocalSourceLoader( xmlFileReader );
				
				
				return srcLoader;
			}
			
		}
		throw new CannotCreateSourceLoaderException( "Cannot find datasource type." );
	}
}
