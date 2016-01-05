package org.moserp.common.json_schema;

import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.rest.webmvc.json.JacksonMetadata;

@Data
@AllArgsConstructor
public class PropertyFactoryContext {

    @NonNull
    private BeanPropertyDefinition definition;
    @NonNull
    private JacksonMetadata jacksonMetadata;
    private PersistentProperty<?> persistentProperty;

}
