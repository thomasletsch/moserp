package org.moserp.common.converter;

import org.moserp.common.domain.RestUri;

public class RestUriToStringConverter extends SafeConverter<RestUri, String> {
    @Override
    protected String doConvert(RestUri source) {
        return source.toString();
    }
}
