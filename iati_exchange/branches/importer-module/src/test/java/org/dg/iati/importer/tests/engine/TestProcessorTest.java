package org.dg.iati.importer.tests.engine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.dg.iati.api.jaxb.iatiApiResult.Item;
import org.dg.iati.api.jaxb.iatiImportRules.TypeCompareAs;
import org.dg.iati.api.jaxb.iatiImportRules.TypeComparisonValues;
import org.dg.iati.api.jaxb.iatiImportRules.TypeVisibility;
import org.dg.iati.importer.engine.ParameterTranslater;
import org.dg.iati.importer.engine.TestComparator;
import org.dg.iati.importer.engine.TestProcessor;
import org.dg.iati.importer.engine.executer.ActionExecutor;
import org.dg.iati.importer.engine.executer.ConstantActionExecutor;
import org.dg.iati.importer.engine.executer.IatiPathActionExecutor;
import org.junit.Before;
import org.junit.Test;

public class TestProcessorTest {
	
	TestProcessor testProcessor;
	
	private Item plannedDisbursements;

	private HashMap<String, String> httpReqParams = new HashMap<String, String>();

	private HashMap<String, Object> globalParams = new HashMap<String, Object>();
	
	private HashMap<String, Object> localParams = new HashMap<String, Object>();
	
	@Before
	public void setUp() throws Exception {
		this.testProcessor	= new TestProcessor();
		ParameterUtil.populateHttpReqParams(httpReqParams);
		ParameterUtil.populateGlobalParams(globalParams);
		ParameterUtil.populateLocalParams(localParams);
		this.plannedDisbursements	= ParameterUtil.getFirstPlannedDisbursementItem();
		
		ActionExecutor term1, term2, ontrue, onfalse;
		ParameterTranslater pTranslater = 
				new ParameterTranslater(this.httpReqParams, this.globalParams, this.localParams, this.plannedDisbursements);
		term1	= new ConstantActionExecutor(null, "Swiss Agency for Development and Cooperation", 
				TypeVisibility.LOCAL, pTranslater, globalParams, localParams);
		term2	= new IatiPathActionExecutor(null, "$I{this.provider-org}", TypeVisibility.LOCAL, 
								pTranslater, globalParams, localParams);
		
		ontrue	= new ConstantActionExecutor("test-ontrue", "ontrue-value", 
				TypeVisibility.LOCAL, pTranslater, globalParams, localParams);
		
		onfalse	= new ConstantActionExecutor("test-onfalse", "onfalse-value", 
				TypeVisibility.GLOBAL, pTranslater, globalParams, localParams);
		
		List<ActionExecutor> onTrueExecutors = new ArrayList<ActionExecutor>(), onFalseExecutors = new ArrayList<ActionExecutor>();
		onTrueExecutors.add(ontrue);
		onFalseExecutors.add(onfalse);
		
		testProcessor.setTerm1(term1);
		testProcessor.setTerm2(term2);
		testProcessor.setOnTrueExecutors(onTrueExecutors);
		testProcessor.setOnFalseExecutors(onFalseExecutors);
		
	
	}

	@Test
	public final void testRunEQ() {
		Comparator<Object> comparator	= new TestComparator(TypeCompareAs.STRING, TypeComparisonValues.EQ);
		testProcessor.setComparator(comparator);
		testProcessor.run();
		assertTrue("test should be true",  testProcessor.getResult() );
		
		assertTrue("Local variables should contain test-ontrue", localParams.containsKey("test-ontrue"));
		assertFalse("Global variables should NOT contain test-ontrue", globalParams.containsKey("test-ontrue"));
		assertFalse("Local variables should NOT contain test-onfalse", localParams.containsKey("test-onfalse"));
		assertFalse("Global variables should NOT contain test-onfalse", globalParams.containsKey("test-onfalse"));
	}
	
	@Test
	public final void testRunNEQ() {
		Comparator<Object> comparator	= new TestComparator(TypeCompareAs.STRING, TypeComparisonValues.NEQ);
		testProcessor.setComparator(comparator);
		testProcessor.run();
		assertFalse("test should be false",  testProcessor.getResult() );
		
		assertFalse("Local variables should NOT contain test-ontrue", localParams.containsKey("test-ontrue"));
		assertFalse("Global variables should NOT contain test-ontrue", globalParams.containsKey("test-ontrue"));
		assertFalse("Local variables should NOT contain test-onfalse", localParams.containsKey("test-onfalse"));
		assertTrue("Global variables should contain test-onfalse", globalParams.containsKey("test-onfalse"));
	}


}
