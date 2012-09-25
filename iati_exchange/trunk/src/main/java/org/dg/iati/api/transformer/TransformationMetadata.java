/**
 * 
 */
package org.dg.iati.api.transformer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Alex
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface TransformationMetadata {
	String jaxbMapping() default TransformationConstants.NONE;
	String transformerClassName() default "org.dg.iati.api.transformer.BasicTransformer";
	String backwardsTransformerClassName() default TransformationConstants.NONE;
	
	boolean isContainer() default false;
	
	/**
	 * Only used for lists
	 * @return the class name of an item in the destination (jaxb) list
	 */
	String itemClassName() default TransformationConstants.NONE;
	String correlationClassName() default TransformationConstants.NONE;
	
}
