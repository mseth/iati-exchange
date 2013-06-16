package org.dg.iati.api.rest;

import java.io.Serializable;
import java.util.HashMap;

import org.dg.iati.api.rest.thread.TransformationRunnerThread;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("answer")
public class Answer implements Serializable {
	/**
	 * 
	 */
	
	public final static transient HashMap<Integer, String> STATUSES	= new HashMap<Integer, String>();
	static{
		STATUSES.put(TransformationRunnerThread.READY_TO_RUN, "ready-to-run");
		STATUSES.put(TransformationRunnerThread.RUNNING, "running");
		STATUSES.put(TransformationRunnerThread.FINISHED, "finished");
		STATUSES.put(TransformationRunnerThread.NOT_FOUND, "not-found");
		
	}
	
	private static final long serialVersionUID = 2839529576941700214L;
	private String id;
	private String status;
	private String result;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	

	
}
