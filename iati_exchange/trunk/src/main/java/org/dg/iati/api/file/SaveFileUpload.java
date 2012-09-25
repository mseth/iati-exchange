package org.dg.iati.api.file;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.dg.iati.api.file.exception.CannotSaveUploadedFileException;
import org.dg.iati.api.transformer.jaxb.XmlFileWriter;

public class SaveFileUpload {
	
	public static final Logger logger						= Logger.getLogger(SaveFileUpload.class);
	public static final String DEFAULT_FILE_UPLOAD_FOLDER	= "uploads";
	
	private FileUpload fUpload		= null;
	private String uploadFolder		= null;
	
	public SaveFileUpload (FileUpload fUpload, String uploadFolder) {
		if (uploadFolder == null || uploadFolder.length() > 0 ) {
			uploadFolder	= DEFAULT_FILE_UPLOAD_FOLDER;
		}
		
		this.uploadFolder	= uploadFolder;
		this.fUpload		= fUpload;
		
	}
	
	public String save() throws CannotSaveUploadedFileException {
		File folder				= new File(this.uploadFolder);
		if ( !folder.isDirectory() && !folder.mkdir() ) {
			throw new CannotSaveUploadedFileException("Cannot create upload folder !!");
		}
		if ( fUpload == null || fUpload.getClientFileName() == null )
			throw new CannotSaveUploadedFileException("Problem with uploaded file");
		File file				= new File (folder, fUpload.getClientFileName() );
		try {
			fUpload.writeTo(file);
			return this.uploadFolder + "/" + fUpload.getClientFileName();
		} catch (IOException e) {
			throw new CannotSaveUploadedFileException("Problems writing contents of uploaded file on disk", e);
			
		}
	}
}
