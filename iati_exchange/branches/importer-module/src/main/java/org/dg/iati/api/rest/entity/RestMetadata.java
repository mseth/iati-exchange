package org.dg.iati.api.rest.entity;

import java.util.HashMap;

public class RestMetadata {
	public String transformationId;
	public String transformationAction;
	public String additionalInformation;
	
	public HashMap<String, String> queryParams;
	
	public RestMetadata() {
		this.queryParams	= new HashMap<String, String>();
	}
	
	public String getTransformationId() {
		return transformationId;
	}
	public void setTransformationId(String transformationName) {
		this.transformationId = transformationName;
	}
	public String getTransformationAction() {
		return transformationAction;
	}
	public void setTransformationAction(String transformationAction) {
		this.transformationAction = transformationAction;
	}
	public HashMap<String, String> getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(HashMap<String, String> queryParams) {
		this.queryParams = queryParams;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	
	
	@Override
	public int hashCode() {
		return queryParams.hashCode() + transformationId.hashCode(); 
				//+ transformationAction.hashCode() 
				//+ (additionalInformation!=null?additionalInformation.hashCode():0);
	}
	
	public String uniqueIdentifier() {
		return "tr" + this.hashCode();
	}
	
}
