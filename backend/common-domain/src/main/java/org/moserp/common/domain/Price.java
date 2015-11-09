package org.moserp.common.domain;

import java.math.BigDecimal;

public class Price extends BigDecimalWrapper<Price> {

    public static final Price ZERO = new Price(0);

    public Price(String val) {
        super(new BigDecimal(val));
    }

    public Price(int val) {
        super(new BigDecimal(val));
    }

    public Price(BigDecimal bigDecimal) {
        super(bigDecimal);
    }

    @Override
    protected Price fromBigDecimal(BigDecimal bigDecimal) {
        return new Price(bigDecimal);
    }

}
