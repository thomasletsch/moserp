package org.moserp.common.domain;

import android.databinding.BindingConversion;
import android.util.Log;

import java.math.BigDecimal;
import java.math.MathContext;

public class Quantity extends Number {

    private static final long serialVersionUID = 1L;

    public static final Quantity ZERO = new Quantity(BigDecimal.ZERO);

    private BigDecimal value;

    @BindingConversion
    public static String convertQuantityToString(Quantity quantity) {
        Log.d("Quantity", "convert quantity to string " + quantity);
        if (quantity == null) {
            return null;
        }
        return quantity.toString();
    }

    @BindingConversion
    public static Quantity convertStringToQuantity(String string) {
        Log.d("Quantity", "convert string to quantity " + string);
        if (string == null) {
            return Quantity.ZERO;
        }
        return new Quantity(string);
    }

    public Quantity(String val) {
        if (val == null || val.equals("")) {
            value = BigDecimal.ZERO;
        } else {
            value = new BigDecimal(val);
        }
    }

    public Quantity(int val) {
        value = new BigDecimal(val);
    }

    public Quantity(BigDecimal bigDecimal) {
        value = new BigDecimal(bigDecimal.unscaledValue(), bigDecimal.scale(), new MathContext(bigDecimal.precision()));
    }

    public boolean lowerThan(Quantity quantity) {
        return value.compareTo(quantity.value) < 0;
    }

    public Quantity subtract(Quantity quantity) {
        return new Quantity(value.subtract(quantity.value));
    }

    public Quantity add(Quantity quantity) {
        return new Quantity(value.add(quantity.value));
    }

    public Quantity negate() {
        return new Quantity(value.negate());
    }

    public int intValue() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    @Override
    public float floatValue() {
        return value.floatValue();
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity = (Quantity) o;
        return value.equals(quantity.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
