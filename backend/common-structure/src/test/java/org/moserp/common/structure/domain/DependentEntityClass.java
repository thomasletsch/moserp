package org.moserp.common.structure.domain;

import lombok.Data;
import org.moserp.common.domain.DependentEntity;

@Data
public class DependentEntityClass extends DependentEntity {

    private String subProperty;

}
