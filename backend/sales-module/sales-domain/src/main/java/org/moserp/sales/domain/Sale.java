/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.moserp.sales.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moserp.billing.domain.Bill;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.domain.Price;
import org.moserp.customer.domain.CustomerActivity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Sale extends CustomerActivity {

    @ValueListKey("Currency")
    private String currency;
    private Price totalPrice = Price.ZERO;
    private LocalDate saleDate = LocalDate.now();
    private Boolean payed = Boolean.FALSE;
    protected Bill bill;
    private List<SaleItem> items = new ArrayList<>();

    public void calculateTotalPrice() {
        totalPrice = items.stream().map(SaleItem::getPrice).reduce(Price.ZERO, Price::add);
    }

}
