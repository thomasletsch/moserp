package org.moserp.common.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Quantity extends BigDecimalWrapper<Quantity> {

    private static final long serialVersionUID = 1L;

    public static final Quantity ZERO = new Quantity(0);

    public Quantity(String val) {
        super(new BigDecimal(val));
    }

    public Quantity(int val) {
        super(new BigDecimal(val));
    }

    public Quantity(BigDecimal bigDecimal) {
        super(bigDecimal);
    }

    public Quantity(BigInteger bigInteger) {
        super(bigInteger);
    }

    @Override
    protected Quantity fromBigDecimal(BigDecimal bigDecimal) {
        return new Quantity(bigDecimal);
    }


}
