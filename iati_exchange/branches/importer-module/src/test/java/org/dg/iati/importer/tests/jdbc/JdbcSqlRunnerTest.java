package org.dg.iati.importer.tests.jdbc;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.dg.iati.importer.jdbc.JdbcSqlRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

public class JdbcSqlRunnerTest {

	private static Logger logger	= Logger.getLogger(JdbcSqlRunnerTest.class);
	private JdbcSqlRunner sqlRunner;

	@Before
	public void setUp() throws Exception {
		sqlRunner = JdbcSqlRunner.getInstance( "testConfig" );
		sqlRunner.setUsername( "postgres" );
		sqlRunner.setPassword( "abc" );
		sqlRunner.setUrl( "localhost:5432/amp_nepal" );
		
		sqlRunner.startTransaction ();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetInstance() {
		assertNotNull(this.sqlRunner);
	}

	@Test
	public final void testFinish() {
		this.sqlRunner.finish();
		Object ret	=	this.sqlRunner.runSql("select now();");
		assertNull("Verify it's null since connection is closed", ret);
	}

	@Test
	public final void testRunSql() {
		Object result = this.sqlRunner.runSql("select now();");
		assertNotNull(result);
		logger.info ( "Result is of class" + result.getClass() );
	}

}
