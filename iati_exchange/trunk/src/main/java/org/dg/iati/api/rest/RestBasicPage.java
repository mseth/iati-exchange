/**
 * 
 */
package org.dg.iati.api.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.MarkupType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.io.IOUtils;

import com.thoughtworks.xstream.XStream;

/**
 * @author Alex
 *
 */


public abstract class RestBasicPage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	private String xmlFilePath		= null;
	
	XStream xstream; 

	public RestBasicPage() {
		setStatelessHint(true);
		this.xstream	= new XStream();
		this.xstream.setMode(XStream.ID_REFERENCES);
		this.xstream.autodetectAnnotations(true);
	}

	@Override
	protected final void onRender() {
		try { 
			HttpServletResponse wr	= (HttpServletResponse)getResponse().getContainerResponse();
			wr.setContentType("text/xml");
			OutputStream responseOS	= ((HttpServletResponse) getResponse().getContainerResponse()).getOutputStream() ;
			if (this.xmlFilePath == null) {
				PrintWriter pw = new PrintWriter(responseOS); 
				pw.write(getXML().toString()); 
				pw.close(); 
			}
			else {
				File myFile			= new File(this.xmlFilePath );
				FileInputStream fis	= new FileInputStream(myFile);
				IOUtils.copy(fis, responseOS);
			}
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
	}

	private CharSequence getXML() {
//		return "<top><id><12></id><name>round n round</name></top>"; 
		XStream xstream = this.xstream;
		return xstream.toXML(getDefaultModelObject());
	}
	
	@Override 
    protected void configureResponse(final WebResponse response) {
		System.out.println("AAAAAAAAAAAA");
        super.configureResponse(response); 
        response.setContentType(MarkupType.XML_MIME); 
        
    } 
	
//	@Override 
//    public MarkupType getMarkupType() { 
//        return new MarkupType("xml", "text/xml"); 
//    } 

//	@Override
//	public final String getMarkupType() {
//		return "xml";
//	}

	@Override
	public final boolean hasAssociatedMarkup() {
		return false;
	}
	
//	@Override
//	public  Markup getAssociatedMarkup() {
//		return  null;
//	}

//	@Override
//	public final Component add(IBehavior... behaviors) {
//		throw new UnsupportedOperationException(
//				"WebServicePage does not support IBehaviours");
//	}
	
	 @Override 
	    public final Component add(Behavior... behaviors) { 
	        throw new UnsupportedOperationException( 
	                "WebServicePage does not support IBehaviours"); 
	    }

	/**
	 * @return the xmlFilePath
	 */
	public String getXmlFilePath() {
		return xmlFilePath;
	}

	/**
	 * @param xmlFilePath the xmlFilePath to set
	 */
	public void setXmlFilePath(String xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
	} 

	 
}
