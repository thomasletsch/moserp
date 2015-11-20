package org.moserp.common.rest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RestUriTest  {

    @Test
    public void testNormalUri() {
        RestUri uri = new RestUri("http://laptop-dell-linux:8080/products/1");
        assertEquals("id", "1", uri.getId());
    }

}
