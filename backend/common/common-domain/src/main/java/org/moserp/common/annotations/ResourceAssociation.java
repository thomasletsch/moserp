package org.moserp.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks a RetUri field as a link to another resource. The resource name should be given as the value of this annotation.
 * The complete resource url can be obtained from ModuleRegistry.
 *
 */
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ResourceAssociation {

    /**
     * The name of the resource (e.g. "unitOfMeasurements")
     */
    String value();
}
