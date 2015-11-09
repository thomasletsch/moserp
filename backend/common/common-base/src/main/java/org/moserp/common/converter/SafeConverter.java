package org.moserp.common.converter;

import org.springframework.core.convert.converter.Converter;

public abstract class SafeConverter<S, T> implements Converter<S, T> {
    @Override
    public T convert(S source) {
        if (source == null) {
            return null;
        }
        try {
            return doConvert(source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract T doConvert(S source) throws Exception;
}
