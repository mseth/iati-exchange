package org.dg.iati.api.transformer;

public interface IChildRepresenter {
	public String getName();
	public void setName(String name);
	public boolean isRepresented(IChildRepresenter child);
}
