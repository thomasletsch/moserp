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
