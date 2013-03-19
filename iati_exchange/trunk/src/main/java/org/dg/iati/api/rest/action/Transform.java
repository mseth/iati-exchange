package org.dg.iati.api.rest.action;

import java.io.File;

import javax.servlet.ServletContext;

import org.dg.iati.api.entity.Constants;
import org.dg.iati.api.entity.IatiMappingFile;
import org.dg.iati.api.rest.Answer;
import org.dg.iati.api.rest.constants.RestConstants;
import org.dg.iati.api.rest.entity.RestMetadata;
import org.dg.iati.api.rest.thread.TransformationRunnerThread;
import org.dg.iati.api.rest.util.StateUtil;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.IatiUtils;

public class Transform {
	private RestMetadata metadata	= new RestMetadata();
	
	private ServletContext sContext;
	private String serverName;
	private int serverPort;
	
	private String directResponseFilePath;


	public Transform(ServletContext sContext, String serverName,
			int serverPort) {
		super();
		this.sContext = sContext;
		this.serverName = serverName.replace("${port}", serverPort+"");
		if ( !this.serverName.startsWith("http") ) {
			this.serverName	= "http://" + this.serverName;
		}
		this.serverPort = serverPort;
		
		this.directResponseFilePath	= null;
	}



	public void addParams(String key, String value) {
		if ( RestConstants.REST_ACTION.equals(key) ) {
			metadata.setTransformationAction(value);
			return;
		}
		
		if ( RestConstants.REST_TRASNF_ID.equals(key) ) {
			metadata.setTransformationId(value);
			return;
		}
		
		if ( RestConstants.REST_TRANSF_ADDITIONAL_INFORMATION.equals(key)  ) {
			metadata.setAdditionalInformation(value);
			return;
		}
		
		metadata.getQueryParams().put(key, value);
	}
	
	
		
	public Answer execute() {
		Answer a	= new Answer();
		a.setResult(RestConstants.REST_RESULT_NONE);
		if ( RestConstants.REST_ACTION_RUN.equals(metadata.transformationAction) ) {
				executeRun(a, false);
		}
		else if ( RestConstants.REST_ACTION_DIRECT_RUN.equals(metadata.transformationAction) ) {
			executeRun(a, true);
		}
		else if ( RestConstants.REST_ACTION_RETRIEVE.equals(metadata.transformationAction) ) {
			executeRetrieve(a);
		}
		
		return a;
	}
	
	public void executeRun(Answer a, boolean directResponse) {
		TransformationRunnerThread thread 	= new TransformationRunnerThread(metadata,sContext);
		
		String mappingName	= this.metadata.getTransformationId();
		String filename		= IatiMappingFile.generateResultFileName(mappingName, this.metadata.uniqueIdentifier() );
		a.setResult(this.createResultString(filename));
		
		a.setId(metadata.uniqueIdentifier());
		
		boolean readyToRun		= StateUtil.addThread(thread);
		if (readyToRun) {
			a.setStatus(Answer.STATUSES.get(TransformationRunnerThread.READY_TO_RUN));
			if ( directResponse) {
				thread.run();
				this.directResponseFilePath	= IatiMappingFile.generateFinalIatiFilePath(mappingName,filename);
			}
			else {
				thread.start();
			}
		}
		else
			a.setStatus(Answer.STATUSES.get(TransformationRunnerThread.RUNNING));
		
		
	}
	
	public void executeRetrieve(Answer a) {
		a.setId(metadata.additionalInformation);
		
		boolean stillRunning	= StateUtil.checkThreadRunning(sContext, this.metadata.getAdditionalInformation() );
		if ( stillRunning ) {
			a.setStatus(Answer.STATUSES.get(TransformationRunnerThread.RUNNING));
		}
		else {
			String mappingName	= this.metadata.getTransformationId();
			String filename		= IatiMappingFile.generateResultFileName(mappingName, this.metadata.getAdditionalInformation() );
			File f	= new File(IatiMappingFile.generateFinalIatiFilePath(mappingName, filename));
			if ( f.exists() ) {
				a.setStatus(Answer.STATUSES.get(TransformationRunnerThread.FINISHED));
				a.setResult(this.createResultString(filename));
			}
			else {
				a.setStatus(Answer.STATUSES.get(TransformationRunnerThread.NOT_FOUND));
			}
		}
		
	}
	
	private String createResultString(String filename) {
		String portStr	= (this.serverPort > 0 ? ":" + this.serverPort : "");
		return this.serverName+"/file/"+filename+Constants.IATI_FILE_EXTENSION;
	}



	/**
	 * @return the directResponseFilePath
	 */
	public String getDirectResponseFilePath() {
		return directResponseFilePath;
	}
	
	
}
