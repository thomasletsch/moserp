package org.moserp.product;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.androidannotations.api.rest.RestClientRootUrl;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import static org.moserp.product.ProductResources.*;

@Rest(converters = {MappingJackson2HttpMessageConverter.class})
public interface ProductRestService extends RestClientErrorHandling, RestClientRootUrl, RestClientSupport {
    @Get("/{id}")
    Product getById(String id);

    @Get("/" )
    ProductResources get();

    @Get("/" + RELATIVE_SEARCH + "/" + FIND_BY_NAME_OR_EAN + "?" + FIND_BY_NAME_OR_EAN_PARAM + "={query}")
    ProductResources findByNameOrEan(String query);

    @Get( "/{id}/quantityOnHand")
    String getQuantityOnHand(String id);

}
