package org.moserp.common.json_schema.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EntityProperty {

    private String title;
    private String key;
    private String format;
    private Boolean readOnly;
    private String type;
    private transient boolean required;

    /**
     * For select etc. types
     */
    private List<NameValueMap> titleMap;

    @JsonProperty(value = "enum")
    private List<String> enumeration;

    /**
     * For array type
     */
    private BusinessEntity items;
}
