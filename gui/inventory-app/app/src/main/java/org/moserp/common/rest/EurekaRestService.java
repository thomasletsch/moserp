package org.moserp.common.rest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientRootUrl;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Map;

@Rest(converters = {MappingJackson2HttpMessageConverter.class})
public interface EurekaRestService extends RestClientRootUrl, RestClientSupport {

    @Get("/apps")
    Map getApps();

}
