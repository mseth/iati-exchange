/**
 * 
 */
package org.dg.iati.api.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.io.IOUtils;
import org.apache.wicket.util.string.StringValue;
import org.dg.iati.api.util.ConfigConstants;
import org.dg.iati.api.util.IatiUtils;

/**
 * @author Alex
 *
 */
public class MyFileResourceReference extends ResourceReference {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4099970501252005423L;


	/**
	 * @param scope
	 * @param name
	 */
	public MyFileResourceReference() {
		super(MyFileResourceReference.class, "iatiFiles");
		// TODO Auto-generated constructor stub
	}



	/* (non-Javadoc)
	 * @see org.apache.wicket.request.resource.ResourceReference#getResource()
	 */
	@Override
	public IResource getResource() {
		
		try {
			
			ByteArrayResource res	= new ByteArrayResource("text/xml"){
				@Override
				protected byte[] getData(IResource.Attributes attributes) {
					StringValue fileNameSV	= attributes.getParameters().get("file");
					File myFile			= 
							new File(IatiUtils.getPropertyValue(ConfigConstants.MAPPING_FOLDER_NAME) + "/" + fileNameSV.toString() );
					FileInputStream fis;
					try {
						fis 					= new FileInputStream(myFile);
						byte[] responseBytes	= IOUtils.toByteArray(fis);
						return responseBytes;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				}
			};
			return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
