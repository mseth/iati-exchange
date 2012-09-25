package org.dg.iati.api.persistance;

import java.util.List;

public interface IPersister<T,K> {
	public void save (T entity);
	public List<T> list();
	public T load(K key); 
}
