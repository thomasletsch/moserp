package org.moserp.product.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductAttributeEnum extends ProductAttribute {

    private List<ProductAttributeEnumItem> items = new ArrayList<>();

    public void addItem(ProductAttributeEnumItem item) {
        items.add(item);
    }

    @Override
    public void mergeWith(ProductAttribute newAttribute) {
        if(newAttribute instanceof ProductAttributeEnum) {
            items.addAll(((ProductAttributeEnum) newAttribute).items);
        }
    }

    @Override
    public String getName() {
        if(super.getName() == null) {
            return getCode();
        }
        return super.getName();
    }

    public void restrictItemsTo(List<String> enumValues) {
        items = items.stream().filter(item -> enumValues.contains(item.getValue())).collect(Collectors.toList());
    }
}
