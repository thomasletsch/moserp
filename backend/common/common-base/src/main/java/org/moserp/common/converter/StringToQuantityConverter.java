package org.moserp.common.converter;

import org.moserp.common.domain.Quantity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class StringToQuantityConverter implements Converter<String, Quantity> {

    @Override
    public Quantity convert(String source) {
        return StringUtils.hasText(source) ? new Quantity(source) : null;
    }

}
