package org.moserp.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks a String field as representation of a value list value. The field should only contain values from the value list with the given
 * key.
 *
 * @see ValueList
 * @see ValueListItem
 */
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ValueListKey {

    /**
     * The key of the value list to use
     */
    String value();
}
