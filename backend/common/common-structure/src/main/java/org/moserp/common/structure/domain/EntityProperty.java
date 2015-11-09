package org.moserp.common.structure.domain;

import lombok.Data;

import java.util.List;

@Data
public class EntityProperty {

    private String name;
    private String description;
    private EntityPropertyType type;
    private String format;
    private String uri;
    private int order = 0;
    private List<String> items;
    private BusinessEntity dependentEntity;
    private boolean required;
    private boolean readOnly;
}
