package org.moserp.billing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.domain.EntityWithAuditInfo;
import org.moserp.common.domain.Price;
import org.moserp.common.domain.RestUri;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Bill extends EntityWithAuditInfo {

    @Size(max = 12)
    private String number;
    @ValueListKey("Currency")
    private String currency;

    private LocalDate saleDate = LocalDate.now();
    private List<BillItem> items = new ArrayList<>();
    private Price totalPrice = Price.ZERO;
    private RestUri customer;

    public String calculateNumber(int billCounter) {
        NumberFormat format = new DecimalFormat("00");
        return saleDate.getYear() + "/" + format.format(saleDate.getMonth().getValue()) + format.format(billCounter);
    }

    public void addItem(BillItem billItem) {
        items.add(billItem);
        calculateTotalPrice();
    }

    public boolean removeItem(BillItem billItem) {
        boolean remove = items.remove(billItem);
        calculateTotalPrice();
        return remove;
    }

    public void calculateTotalPrice() {
        totalPrice = items.stream().map(BillItem::getPrice).reduce(Price.ZERO, Price::add);
    }

    @JsonIgnore
    public Price getNetPrice() {
        return items.stream().map(BillItem::getNetPrice).reduce(Price.ZERO, Price::add);
    }


}
