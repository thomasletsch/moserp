package org.moserp.common.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HateoasResources<T> extends HateoasLinkContainer {

    @JsonProperty("_embedded")
    private Map<String, List<T>> content;

    @JsonIgnore
    public List<T> getList() {
        if(content == null) {
            return Collections.emptyList();
        }
        return content.values().iterator().next();
    }

    public Map<String, List<T>> getContent() {
        return content;
    }

    public void setContent(Map<String, List<T>> content) {
        this.content = content;
    }
}
