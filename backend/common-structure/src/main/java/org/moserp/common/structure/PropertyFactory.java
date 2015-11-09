package org.moserp.common.structure;

import org.moserp.common.structure.domain.EntityProperty;

public interface PropertyFactory {
    boolean appliesTo(PropertyFactoryContext context);

    EntityProperty create(PropertyFactoryContext context);
}
