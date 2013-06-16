package org.dg.iati.api.rest.util;

import javax.servlet.ServletContext;

import org.dg.iati.api.rest.thread.TransformationRunnerThread;

public class StateUtil {
	/**
	 * 	  
	 * @param thread
	 * @return false if the thread with the exact same metadata is already in the servlet context, 
	 * and thus already running. Otherwise it inserts the new 'thread' in the servlet context and returns true
	 */
	public synchronized static boolean addThread(TransformationRunnerThread thread ) {
		ServletContext sContext 	= thread.getsContext();
		String uniqueIdentifier		= thread.getMetadata().uniqueIdentifier();
		if (sContext.getAttribute(uniqueIdentifier) == null) {
			sContext.setAttribute(uniqueIdentifier, thread);
			return true;
		}
		else
			return false;
	} 
	
	public synchronized static void removeThread(TransformationRunnerThread thread ) {
		ServletContext sContext 	= thread.getsContext();
		String uniqueIdentifier		= thread.getMetadata().uniqueIdentifier();
		if (sContext.getAttribute(uniqueIdentifier) != null) {
			sContext.removeAttribute(uniqueIdentifier);
		}
	}
	public synchronized static boolean checkThreadRunning(ServletContext sContext, String uniqueIdentifier) {
		if (sContext.getAttribute(uniqueIdentifier) != null) {
			return true;
		}
		else
			return false;
	}
}
