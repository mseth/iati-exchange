package org.dg.iati.importer.tests.engine.executor;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.dg.iati.api.jaxb.iatiApiResult.Item;
import org.dg.iati.api.jaxb.iatiImportRules.Action;
import org.dg.iati.api.jaxb.iatiImportRules.TypeMode;
import org.dg.iati.api.jaxb.iatiImportRules.TypeVisibility;
import org.dg.iati.importer.engine.ParameterTranslater;
import org.dg.iati.importer.engine.executer.ActionExecutor;
import org.dg.iati.importer.engine.executer.IatiPathActionExecutor;
import org.dg.iati.importer.engine.factories.ActionExecutorFactory;
import org.dg.iati.importer.tests.engine.ParameterUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IatiPathActionExecutorTest {
	
	private HashMap<String, String> httpReqParams = new HashMap<String, String>();

	private HashMap<String, Object> globalParams = new HashMap<String, Object>();
	
	private HashMap<String, Object> localParams = new HashMap<String, Object>();

	private ActionExecutorFactory factory;
	
	private Item plannedDisbursements;

	private ActionExecutor executor;

	@Before
	public void setUp() throws Exception {
		
		ParameterUtil.populateHttpReqParams(httpReqParams);
		ParameterUtil.populateGlobalParams(globalParams);
		ParameterUtil.populateLocalParams(localParams);
		
		this.plannedDisbursements	= ParameterUtil.getFirstPlannedDisbursementItem();
		
		ParameterTranslater paramTranslator	= new ParameterTranslater(httpReqParams, globalParams, localParams, this.plannedDisbursements);
		
		Action rulesAction				= new Action();
		rulesAction.setMode(TypeMode.IATI_PATH);
		rulesAction.setVisibility(TypeVisibility.LOCAL);
		rulesAction.setName("TestAction");
		rulesAction.setContent("$I{source-type}");
		
		factory = new ActionExecutorFactory(globalParams, localParams);
		executor = (IatiPathActionExecutor) factory.getActionExecutorInstance(rulesAction, paramTranslator);
	}	

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testExecute() {
		Object result	= executor.execute();
		assertNotNull(result);
		
		assertEquals("Direct Grant", result);
	}

}
