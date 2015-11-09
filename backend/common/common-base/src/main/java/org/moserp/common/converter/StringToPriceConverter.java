package org.moserp.common.converter;

import org.moserp.common.domain.Price;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class StringToPriceConverter implements Converter<String, Price> {

    @Override
    public Price convert(String source) {
        return StringUtils.hasText(source) ? new Price(source) : null;
    }

}
