package org.moserp.common.repository;

import org.moserp.common.domain.EntityWithAuditInfo;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TestEntityWithAuditInfo extends EntityWithAuditInfo {

    private String name;

    public TestEntityWithAuditInfo() {
    }

    public TestEntityWithAuditInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
