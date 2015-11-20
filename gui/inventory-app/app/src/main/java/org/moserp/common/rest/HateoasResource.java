package org.moserp.common.rest;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class HateoasResource<T> extends HateoasLinkContainer {

    private T content;

    @JsonUnwrapped
    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
