package org.moserp.common.converter;

import org.moserp.common.domain.RestUri;

public class StringToRestUriConverter extends SafeConverter<String, RestUri> {
    @Override
    protected RestUri doConvert(String source) throws Exception {
        return new RestUri(source);
    }
}
