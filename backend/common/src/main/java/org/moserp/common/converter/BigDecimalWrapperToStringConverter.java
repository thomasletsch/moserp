package org.moserp.common.converter;

import org.moserp.common.domain.BigDecimalWrapper;

public class BigDecimalWrapperToStringConverter extends SafeConverter<BigDecimalWrapper, String> {

    @Override
    protected String doConvert(BigDecimalWrapper source) throws Exception {
        return source.stringValue();
    }

}
