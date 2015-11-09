package org.moserp.common.converter;

import org.moserp.common.domain.Quantity;

public class QuantityToStringConverter extends SafeConverter<Quantity, String> {

    @Override
    protected String doConvert(Quantity source) throws Exception {
        return source.stringValue();
    }

}
