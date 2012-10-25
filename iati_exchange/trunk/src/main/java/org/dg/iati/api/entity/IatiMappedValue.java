/**
 * 
 */
package org.dg.iati.api.entity;

import java.util.HashMap;

/**
 * @author dan
 *
 */
public class IatiMappedValue {


	// iati-path - [local-value, iati-value]
	
	HashMap items;
	
	public boolean hasMapping(){
		return this.getItems().size()>0;
	}
	
	public IatiMappedValue(HashMap items) {
		super();
		this.items = items;
	}

	public IatiMappedValue() {
		// TODO Auto-generated constructor stub
		this.items = new HashMap<String, HashMap<String,String>>();
	}

	public boolean hasElement(String path){
		boolean result = false;
		if(this.getItems().containsKey(path)) result = true;
		return result;
	}

	public HashMap<String,String> getElement(String path){
		return (HashMap<String,String>)this.getItems().get(path);
	}
	
	public String getValue(String path, String local){
		return getElement(path).get(local);
	}
	
	public void add(String path, String local, String iati){
		HashMap<String,String> el = getElement(path);
		if(el == null){
			HashMap<String,String> h = new HashMap<String, String>();
			h.put(local, iati);
			this.getItems().put(path, h);
		}
		else {
			el.put(local, iati);
		}
	}

	public HashMap getItems() {
		return items;
	}

	public void setItems(HashMap items) {
		this.items = items;
	}

	public void add(String[] line) {
		// TODO Auto-generated method stub
		this.add(line[0],line[1],line[2]);
	}

}
