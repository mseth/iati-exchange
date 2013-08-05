/**
 * 
 */
package org.dg.iati.importer.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.dg.iati.importer.engine.executer.ActionExecutor;

/**
 * @author alex
 *
 */
public class TestProcessor {
	ActionExecutor term1, term2; 
	List<ActionExecutor> onTrueExecutors = new ArrayList<ActionExecutor>(), onFalseExecutors = new ArrayList<ActionExecutor>();
	Comparator<Object> comparator;
	private Boolean result = null;
	/**
	 * @param term1 the term1 to set
	 */
	public void setTerm1(ActionExecutor term1) {
		this.term1 = term1;
	}
	/**
	 * @param term2 the term2 to set
	 */
	public void setTerm2(ActionExecutor term2) {
		this.term2 = term2;
	}


	/**
	 * @return the onTrueExecutors
	 */
	public List<ActionExecutor> getOnTrueExecutors() {
		return onTrueExecutors;
	}
	/**
	 * @param onTrueExecutors the onTrueExecutors to set
	 */
	public void setOnTrueExecutors(List<ActionExecutor> onTrueExecutors) {
		this.onTrueExecutors = onTrueExecutors;
	}
	/**
	 * @return the onFalseExecutors
	 */
	public List<ActionExecutor> getOnFalseExecutors() {
		return onFalseExecutors;
	}
	/**
	 * @param onFalseExecutors the onFalseExecutors to set
	 */
	public void setOnFalseExecutors(List<ActionExecutor> onFalseExecutors) {
		this.onFalseExecutors = onFalseExecutors;
	}
	/**
	 * @param comparator the comparator to set
	 */
	public void setComparator(Comparator<Object> comparator) {
		this.comparator = comparator;
	}
	public Boolean getResult() {
		return this.result;
	}
	public void run() {
		Object o1	= term1.execute();
		Object o2	= term2.execute();
		
		int resultInt	= this.comparator.compare(o1, o2);
		if ( resultInt == 0 ) {
			this.result	= true;
			for (ActionExecutor onTrueAction : this.onTrueExecutors) {
				onTrueAction.execute();
			}
		}
		else {
			this.result	= false;
			for (ActionExecutor onFalseAction : this.onFalseExecutors) {
				onFalseAction.execute();
			}
		}
		
		
	}

	
}
