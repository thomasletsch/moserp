package org.moserp.common.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RestUriTest {

    @Test
    public void testWithoutPathSimple() throws Exception {
        RestUri restUri = new RestUri("http://localhsot:8888/");
        assertEquals("http://localhsot:8888/", restUri.withoutPath().getUri());
    }

    @Test
    public void testWithoutPathWithPath() throws Exception {
        RestUri restUri = new RestUri("http://localhsot:8888/some_path");
        assertEquals("http://localhsot:8888/", restUri.withoutPath().getUri());
    }

    @Test
    public void testWithoutPathWithoutPort() throws Exception {
        RestUri restUri = new RestUri("http://localhsot");
        assertEquals("http://localhsot/", restUri.withoutPath().getUri());
    }

    @Test
    public void testWithoutPathNumericIp() throws Exception {
        RestUri restUri = new RestUri("http://1.1.1.1:8888/some_path");
        assertEquals("http://1.1.1.1:8888/", restUri.withoutPath().getUri());
    }
}
