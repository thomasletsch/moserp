package org.moserp.common.json_schema.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model class to contain one element of the structure of the application. Can be used to derive a menu / CRUD structure in the front end
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BusinessEntity {

    private String title;
    private String type;
    private String description;

    private Map<String, EntityProperty> properties = new HashMap<>();
    private List<String> required = new ArrayList<>();

}
