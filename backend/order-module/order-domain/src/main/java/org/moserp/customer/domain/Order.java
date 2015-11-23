package org.moserp.customer.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.domain.EntityWithAuditInfo;
import org.moserp.common.domain.Price;
import org.moserp.common.domain.RestUri;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Order extends EntityWithAuditInfo {

    private RestUri customer;
    private RestUri salesPerson;

    @ValueListKey("OrderStatus")
    private String status = "CREATED";

    private LocalDate orderDate = LocalDate.now();

    private Price totalPrice = Price.ZERO;

    @ValueListKey("Currency")
    private String currency;
    private List<OrderItem> items = new ArrayList<>();


    public void addItem(OrderItem item) {
        items.add(item);
        calculateTotalPrice();
    }

    public void calculateTotalPrice() {
        totalPrice = items.stream().map(OrderItem::getPrice).reduce(Price.ZERO, Price::add);
    }

}
