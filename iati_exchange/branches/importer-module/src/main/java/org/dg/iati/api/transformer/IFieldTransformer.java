/**
 * 
 */
package org.dg.iati.api.transformer;

/**
 * @author Alex
 *
 */
public interface IFieldTransformer<S,D> {
	public D transform(S source);
	public void populate (S source, D destination);
}
