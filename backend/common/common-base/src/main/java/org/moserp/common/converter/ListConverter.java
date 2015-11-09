package org.moserp.common.converter;

import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.stream.Collectors;

public class ListConverter<S, T> {

    private Converter<S, T> converter;

    public ListConverter(Converter<S, T> converter) {
        this.converter = converter;
    }

    public List<T> convert(List<S> source) {
        return source.stream().map(converter::convert).collect(Collectors.toList());
    }
}
