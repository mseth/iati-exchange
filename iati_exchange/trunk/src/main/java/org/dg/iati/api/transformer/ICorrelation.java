package org.dg.iati.api.transformer;

public interface ICorrelation<T,S> {

	public boolean correlated(T obj1, S obj2);
}
