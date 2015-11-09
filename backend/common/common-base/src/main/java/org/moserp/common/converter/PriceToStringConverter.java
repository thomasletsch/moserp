package org.moserp.common.converter;

import org.moserp.common.domain.Price;

public class PriceToStringConverter extends SafeConverter<Price, String> {

    @Override
    protected String doConvert(Price source) throws Exception {
        return source.stringValue();
    }

}
