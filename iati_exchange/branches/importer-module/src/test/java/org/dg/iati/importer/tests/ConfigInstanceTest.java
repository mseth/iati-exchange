/**
 * 
 */
package org.dg.iati.importer.tests;

import static org.junit.Assert.*;

import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.ConfigInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author alex
 *
 */
public class ConfigInstanceTest {

	private ConfigInstance configInstance;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.configInstance	= ConfigInstance.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.dg.iati.api.util.ConfigInstance#get()}.
	 */
	@Test
	public final void testGetMappingFolder() {
		assertNotNull( this.configInstance.get( ConfigConstants.MAPPING_FOLDER_NAME ) );
		assertTrue( this.configInstance.get( ConfigConstants.MAPPING_FOLDER_NAME ).length() > 0 );
	}

	/**
	 * Test method for {@link org.dg.iati.api.util.ConfigInstance#get()}.
	 */
	@Test
	public final void testGetImportMappingFolder() {
		assertNotNull( this.configInstance.get( ConfigConstants.IMPORT_MAPPING_FOLDER_NAME ) );
		assertTrue( this.configInstance.get( ConfigConstants.IMPORT_MAPPING_FOLDER_NAME ).length() > 0 );
	}
	
	/**
	 * Test method for {@link org.dg.iati.api.util.ConfigInstance#get()}.
	 */
	@Test
	public final void testGetImportXslFile() {
		assertNotNull( this.configInstance.get( ConfigConstants.IMPORT_XSL_FILE ) );
		assertTrue( this.configInstance.get( ConfigConstants.IMPORT_XSL_FILE ).length() > 0 );
	}

}
