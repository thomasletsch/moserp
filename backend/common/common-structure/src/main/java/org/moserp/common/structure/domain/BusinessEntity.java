package org.moserp.common.structure.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class to contain one element of the structure of the application. Can be used to derive a menu / CRUD structure in the front end
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BusinessEntity {

    private String name;
    private String uri;
    private String group;
    private String type;
    private List<EntityProperty> properties = new ArrayList<>();

}
