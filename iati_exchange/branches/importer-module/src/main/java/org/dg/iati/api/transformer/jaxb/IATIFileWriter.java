package org.dg.iati.api.transformer.jaxb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.dg.iati.api.entity.Constants;

public class IATIFileWriter{
			
	public static final String MAPPING_FOLDER	= "mapping";
	public static final Logger logger			= Logger.getLogger(IATIFileWriter.class);


	private String filename;
	private FileOutputStream fop = null;
	private File file;
	private String content = "";
	private String extension;
	private String foldername;


	public IATIFileWriter(String filename) {
		super();
		this.filename = filename;
	}
	
	public IATIFileWriter(String filename, String content) {
		super();
		this.filename 	= filename;
		this.content 	= content;
		this.extension 	= Constants.IATI_FILE_TRANSFORM_EXTENSION;
		this.foldername = XmlFileUtils.generateCustomFolderPath(filename);
	}
	
	public IATIFileWriter(String filename, String content, String extension) {
		super();
		this.filename 	= filename;
		this.content 	= content;
		this.extension 	= extension;
		this.foldername = XmlFileUtils.generateCustomFolderPath(filename);
	}
	
	public IATIFileWriter(String filename, String content, String extension, String foldername) {
		super();
		this.filename 	= filename;
		this.content 	= content;
		this.extension 	= extension;
		this.foldername = foldername;
	}
	
	public void persist (){
		try {
			
			File folder				= new File(Constants.APP_PATH + this.foldername);
			if ( !folder.isDirectory() && !folder.mkdir() ) {
				logger.error("Cannot create mapping folder !!");
				return;
			}
			file = new File(folder,filename+extension);
			fop = new FileOutputStream(file);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// get the content in bytes
			byte[] contentInBytes = content.getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
			logger.info("File "+filename+extension+ " written OK!");
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 catch (Exception e) {
				e.printStackTrace();
		 }
	}
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public FileOutputStream getFop() {
		return fop;
	}
	
	public void setFop(FileOutputStream fop) {
		this.fop = fop;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
