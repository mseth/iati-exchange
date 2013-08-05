/**
 * 
 */
package org.dg.iati.api.transformer.jaxb;

import java.io.File;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.dg.iati.api.jaxb.iatiApiResult.IatiActivities;

/**
 * @author alex
 *
 */
public class XmlFileReaderAndTransformer<T> extends XmlFileReader<T> {
	
	private final Logger logger = Logger.getLogger( XmlFileReaderAndTransformer.class );
	
	protected String xslFile;
	
	public XmlFileReaderAndTransformer( Class<T> clazz, String filename, String foldername, String xslFile ) {
		super( clazz, filename, foldername );
		this.xslFile	= xslFile;
	}
	
	protected Transformer createTransformer() throws TransformerConfigurationException {
		TransformerFactory factory 	= TransformerFactory.newInstance();
		File xslFileObj				= new File( this.xslFile );
		logger.info("File " + xslFileObj.getAbsolutePath() + " exists: " + xslFileObj.exists() );
		StreamSource xslStream 		= new StreamSource( xslFileObj );
		Transformer transformer = factory.newTransformer(xslStream);
		return transformer;
	}
	
	@Override
	public T load() {
		try {
			Transformer transformer		= this.createTransformer();
			File sourceFile				= new File( this.foldername + "/" + this.filename );
			File customFile				= new File( this.foldername + "/" + "custom_" + this.filename );
			StreamSource inSrc			= new StreamSource( sourceFile );
			StreamResult customOut		= new StreamResult( customFile );
			transformer.transform( inSrc, customOut );
			
			
			Unmarshaller unmarshaller 	= this.createJAXBUnmarshaller();
			T retObj					= (T) unmarshaller.unmarshal( customFile );
			return retObj;
		} catch ( JAXBException e ) {
			e.printStackTrace();
		} catch ( TransformerConfigurationException e ) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
