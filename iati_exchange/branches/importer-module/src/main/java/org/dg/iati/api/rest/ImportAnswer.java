package org.dg.iati.api.rest;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("answer")
public class ImportAnswer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1547261994932497675L;
	private String id;
	private String summary;
	@XStreamAlias("successful")
	private Successful successful	= new Successful();
	private Failed failed			= new Failed();
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return the successful
	 */
	public List<String> getSuccessful() {
		return successful.getItems();
	}
	/**
	 * @param successful the successful to set
	 */
	public void setSuccessful(List<String> successful) {
		this.successful.setItems( successful );
	}
	/**
	 * @return the failed
	 */
	public List<String> getFailed() {
		return failed.getItems();
	}
	/**
	 * @param failed the failed to set
	 */
	public void setFailed(List<String> failed) {
		this.failed.setItems( failed );
	}
	
	private class Successful {
		@XStreamImplicit(itemFieldName="item")
		private List<String> items;

		/**
		 * @return the items
		 */
		public List<String> getItems() {
			return items;
		}

		/**
		 * @param items the items to set
		 */
		public void setItems(List<String> items) {
			this.items = items;
		}
	}
	
	private class Failed {
		@XStreamImplicit(itemFieldName="item")
		private List<String> items;

		/**
		 * @return the items
		 */
		public List<String> getItems() {
			return items;
		}

		/**
		 * @param items the items to set
		 */
		public void setItems(List<String> items) {
			this.items = items;
		}
	}
}
