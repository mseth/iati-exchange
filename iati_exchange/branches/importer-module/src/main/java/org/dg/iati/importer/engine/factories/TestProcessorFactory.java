/**
 * 
 */
package org.dg.iati.importer.engine.factories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.dg.iati.api.jaxb.iatiApiResult.Item;
import org.dg.iati.api.jaxb.iatiImportRules.Action;
import org.dg.iati.api.jaxb.iatiImportRules.Test;
import org.dg.iati.api.jaxb.iatiImportRules.TypeCompareAs;
import org.dg.iati.api.jaxb.iatiImportRules.TypeComparison;
import org.dg.iati.importer.engine.ParameterTranslater;
import org.dg.iati.importer.engine.TestComparator;
import org.dg.iati.importer.engine.TestProcessor;
import org.dg.iati.importer.engine.executer.ActionExecutor;

/**
 * @author alex
 *
 */
public class TestProcessorFactory {
	
	public static TestProcessor createTestProcessorInstance(Test test, Item iatiItem, HashMap<String, String> httpReqParams,
			HashMap<String, Object> globalParams, HashMap<String, Object> localParams) {
		TestProcessor tProcessor	= new TestProcessor();
		
		Action term1Act=null, term2Act=null;
		
		List<Action> onTrueActions	= new ArrayList<Action>(), onFalseActions = new ArrayList<Action>();
		TypeComparison typeComp = null;
		
		ActionExecutor term1=null, term2=null; 
		List<ActionExecutor> onTrueExecutors = new ArrayList<ActionExecutor>(), onFalseExecutors = new ArrayList<ActionExecutor>();
		Comparator<Object> comparator = null;
		
		for ( Serializable s: test.getContent() ) {
			if ( s instanceof JAXBElement ) {
				JAXBElement<? extends Object> element	= (JAXBElement<? extends Object>) s;
				if ( element.getName().toString().equals("term1") ) {
					term1Act	= ((JAXBElement<Action>)element).getValue();
				}
				else if ( element.getName().toString().equals("term2") ) {
					term2Act	= ((JAXBElement<Action>)element).getValue();
				}
				else if ( element.getName().toString().equals("on-false") ) {
					onFalseActions.add( ((JAXBElement<Action>)element).getValue() );
				}
				else if ( element.getName().toString().equals("on-true") ) {
					onTrueActions.add( ((JAXBElement<Action>)element).getValue() );
				}
				else if ( element.getName().toString().equals("comparison") ) {
					typeComp	= ((JAXBElement<TypeComparison>)element).getValue();
				}
			}
		}
		
		ParameterTranslater pm		= new ParameterTranslater(httpReqParams, globalParams, localParams, iatiItem);
		ActionExecutorFactory actionExecutorFactory 	= new ActionExecutorFactory(globalParams, localParams);
		if (term1Act != null)
			term1	= actionExecutorFactory.getActionExecutorInstance(term1Act, pm);
		if (term2Act != null)
			term2	= actionExecutorFactory.getActionExecutorInstance(term2Act, pm);
		for (Action onFalseAct : onFalseActions) {
			onFalseExecutors.add( actionExecutorFactory.getActionExecutorInstance(onFalseAct, pm) );
		}
		for ( Action onTrueAct : onTrueActions ) {
			onTrueExecutors.add( actionExecutorFactory.getActionExecutorInstance(onTrueAct, pm) );
		}
		
		if ( typeComp != null ) {
			comparator	= new TestComparator(typeComp.getCompareAs(), typeComp.getValue());
		}
		
		tProcessor.setTerm1(term1);
		tProcessor.setTerm2(term2);
		tProcessor.setOnTrueExecutors(onTrueExecutors);
		tProcessor.setOnFalseExecutors(onFalseExecutors);
		tProcessor.setComparator(comparator);
		
		return tProcessor;
	}
}
