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

package org.moserp.common.domain;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

@EqualsAndHashCode(callSuper = false)
public abstract class BigDecimalWrapper<TYPE extends BigDecimalWrapper> extends Number {
    private static final long serialVersionUID = 1L;
    protected BigDecimal value;

    public BigDecimalWrapper(BigDecimal bigDecimal) {
        value = new BigDecimal(bigDecimal.unscaledValue(), bigDecimal.scale(), new MathContext(bigDecimal.precision()));
    }

    public BigDecimalWrapper(BigInteger bigInteger) {
        value = new BigDecimal(bigInteger);
    }

    protected abstract TYPE fromBigDecimal(BigDecimal bigDecimal);

    public boolean lowerThan(TYPE wrapper) {
        return value.compareTo(wrapper.value) < 0;
    }

    public TYPE subtract(TYPE wrapper) {
        if(wrapper == null) {
            return (TYPE) this;
        }
        return fromBigDecimal(value.subtract(wrapper.value));
    }

    public TYPE add(TYPE wrapper) {
        if(wrapper == null) {
            return (TYPE) this;
        }
        return fromBigDecimal(value.add(wrapper.value));
    }

    public TYPE multiply(TYPE wrapper) {
        if(wrapper == null) {
            return (TYPE) this;
        }
        return fromBigDecimal(value.multiply(wrapper.value));
    }

    public TYPE multiply(Integer multiplicand) {
        if(multiplicand == null) {
            return (TYPE) this;
        }
        return fromBigDecimal(value.multiply(new BigDecimal(multiplicand)));
    }

   public TYPE divide(BigDecimal divisor) {
        if(divisor == null) {
            return (TYPE) this;
        }
        return fromBigDecimal(value.divide(divisor));
    }

    public TYPE negate() {
        return fromBigDecimal(value.negate());
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

    public String stringValue() {
        return value.toPlainString();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + stringValue();
    }
}
