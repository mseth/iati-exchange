/**
 * 
 */
package org.dg.iati.importer.tests.engine;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dg.iati.api.jaxb.iatiImportRules.TypeCompareAs;
import org.dg.iati.api.jaxb.iatiImportRules.TypeComparisonValues;
import org.dg.iati.importer.engine.TestComparator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author alex
 *
 */
public class TestComparatorTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.dg.iati.importer.engine.TestComparator#compare(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	public final void testCompareEQStrInt() {
		TestComparator comparator 	= new TestComparator(TypeCompareAs.STRING, TypeComparisonValues.EQ);
		assertEquals(0,comparator.compare("2222", 2222) );
	}
	
	/**
	 * Test method for {@link org.dg.iati.importer.engine.TestComparator#compare(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	public final void testCompareLtNum() {
		TestComparator comparator 	= new TestComparator(TypeCompareAs.NUMBER, TypeComparisonValues.LT);
		assertEquals(0,comparator.compare(111.1, 222.222) );
		assertFalse( comparator.compare(222.222,111.1) == 0 );
	}
	/**
	 * Test method for {@link org.dg.iati.importer.engine.TestComparator#compare(java.lang.Object, java.lang.Object)}.
	 * @throws ParseException 
	 */
	@Test
	public final void testCompareGTDate() throws ParseException {
		TestComparator comparator 	= new TestComparator(TypeCompareAs.DATE, TypeComparisonValues.GT);
		DateFormat df	= new SimpleDateFormat(TestComparator.DEFAULT_DATE_FORMAT);
		Date d			= df.parse("2012-11-30 00:00:00");
		assertEquals(0,comparator.compare("2012-12-30 00:00:00", d) );
	}
	

}
