package org.moserp.product;

import org.moserp.common.domain.IdentifiableEntity;

public class Product extends IdentifiableEntity  {

    private String externalId;
    private String ean;
    private String name;

    public Product() {
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Override
    public String toString() {
        return name;
    }
}
