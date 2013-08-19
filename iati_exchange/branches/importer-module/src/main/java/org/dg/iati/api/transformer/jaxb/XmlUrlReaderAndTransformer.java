/**
 * 
 */
package org.dg.iati.api.transformer.jaxb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * @author alex
 *
 */
public class XmlUrlReaderAndTransformer<T> extends
		XmlFileReaderAndTransformer<T> {
	
	private String urlString;


	public XmlUrlReaderAndTransformer(Class<T> clazz, String urlString,
			String filename, String folderName, String xslFile) {
		super(clazz, filename, folderName, xslFile);
		this.urlString	= urlString;
	}
	
	@Override
	public T load() {
		try {
			this.downloadUrl();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return super.load();
	}
	
	private void downloadUrl () throws IOException {
		FileOutputStream fos	= null;
		ReadableByteChannel rbc	= null;
		try {
			URL url = new URL( this.urlString );
			rbc = Channels.newChannel( url.openStream() );
			File downloadedFile 	= new File( this.foldername + "/" + this.filename );
			if ( !downloadedFile.exists() ) 
				downloadedFile.createNewFile();
			fos = new FileOutputStream( downloadedFile );
			fos.getChannel().transferFrom(rbc, 0, Integer.MAX_VALUE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if ( rbc != null )
				rbc.close();
			if ( fos != null )
				fos.close();
		}
	}

}
