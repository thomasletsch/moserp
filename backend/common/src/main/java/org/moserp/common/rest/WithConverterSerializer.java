package org.moserp.common.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;

@RequiredArgsConstructor
public class WithConverterSerializer<TYPE> extends JsonSerializer<TYPE> {

    @NonNull
    private Converter<TYPE, String> converter;

    @Override
    public void serialize(TYPE value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value == null) {
            serializers.defaultSerializeNull(gen);
        } else {
            gen.writeString(converter.convert(value));
        }
    }
}
