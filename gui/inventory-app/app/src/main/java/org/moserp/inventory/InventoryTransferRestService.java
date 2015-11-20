package org.moserp.inventory;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.androidannotations.api.rest.RestClientRootUrl;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(converters = {MappingJackson2HttpMessageConverter.class})
public interface InventoryTransferRestService extends RestClientErrorHandling, RestClientRootUrl, RestClientSupport {

    @Post("/" )
    @Accept("application/hal+json")
    ResponseEntity<InventoryTransfer> post(InventoryTransfer inventoryTransfer);

}
