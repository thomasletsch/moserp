package org.moserp.common.json_schema;

import org.moserp.common.json_schema.domain.EntityProperty;

public interface PropertyFactory {
    boolean appliesTo(PropertyFactoryContext context);

    EntityProperty create(PropertyFactoryContext context);
}
