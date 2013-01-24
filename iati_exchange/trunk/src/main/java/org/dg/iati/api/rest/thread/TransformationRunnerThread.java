/**
 * 
 */
package org.dg.iati.api.rest.thread;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;

import org.dg.iati.api.entity.IatiMappingFile;
import org.dg.iati.api.jaxb.iatiApiMapping.IatiApiMapping;
import org.dg.iati.api.rest.entity.RestMetadata;
import org.dg.iati.api.rest.util.StateUtil;
import org.dg.iati.api.transformer.jaxb.XmlFileReader;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.IatiUtils;

/**
 * @author Alex
 *
 */
public class TransformationRunnerThread extends Thread {
	
	public static final int READY_TO_RUN	= 0;
	public static final int RUNNING		= 1;
	public static final int FINISHED		= 2;
	public static final int NOT_FOUND		= 3;
	
	
	private final ServletContext sContext;
	
	private final RestMetadata metadata;
	
	private AtomicInteger status;
	
	
	
	public TransformationRunnerThread(RestMetadata metadata, ServletContext sContext) {
		this.metadata 	= metadata;
		this.sContext	= sContext;
		status			= new AtomicInteger(READY_TO_RUN);
	}
	
	@Override
	public void run() {
		try {
			String fileName 	= metadata.getTransformationId()+".mapping.xml";
			XmlFileReader<IatiApiMapping> reader	= 
					new XmlFileReader<IatiApiMapping>(IatiApiMapping.class, fileName, IatiUtils.getPropertyValue(ConfigConstants.MAPPING_FOLDER_NAME) );
			IatiApiMapping jaxbMapping				= reader.load();
			IatiMappingFile imf						= new IatiMappingFile(jaxbMapping);
			imf.setUniqueIdentifier(metadata.uniqueIdentifier());
			
			imf.run(this.metadata.getQueryParams());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			StateUtil.removeThread(this);
		}
	}
	
	public int getStatus() {
		return status.get();
	}

	/**
	 * @return the sContext
	 */
	public ServletContext getsContext() {
		return sContext;
	}

	/**
	 * @return the metadata
	 */
	public RestMetadata getMetadata() {
		return metadata;
	}
	
	
}
