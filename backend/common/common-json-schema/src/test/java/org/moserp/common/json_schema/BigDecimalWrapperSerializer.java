package org.moserp.common.json_schema;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.moserp.common.domain.BigDecimalWrapper;

import java.io.IOException;

public class BigDecimalWrapperSerializer extends JsonSerializer<BigDecimalWrapper> {
    @Override
    public void serialize(BigDecimalWrapper value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeString(value.stringValue());
    }
}
