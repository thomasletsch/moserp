package org.moserp.inventory;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.androidannotations.api.rest.RestClientRootUrl;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(converters = {MappingJackson2HttpMessageConverter.class})
public interface InventoryItemRestService extends RestClientErrorHandling, RestClientRootUrl, RestClientSupport {

    @Get("/")
    InventoryItemResources get();

    @Get("/search/findByProductIdOrFacilityId?productId={productId}")
    InventoryItemResources findInventoryItemsByProduct(String productId);
}
