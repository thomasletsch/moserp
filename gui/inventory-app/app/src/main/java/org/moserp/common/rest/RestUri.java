package org.moserp.common.rest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestUri {

    private static Pattern uriPattern = Pattern.compile("^([^:/?#]+)://(.*):(\\d*)/(.*)/(.*)?");
    private final Matcher matcher;

    /*
        String uri = "http://laptop-dell-linux:8080/products/1";
        Matcher matcher = uriPattern.matcher(uri);
        assertTrue("matches", matcher.matches());
        assertEquals("http", matcher.group(1));
        assertEquals("laptop-dell-linux", matcher.group(2));
        assertEquals("8080", matcher.group(3));
        assertEquals("products", matcher.group(4));
        assertEquals("1", matcher.group(5));
     */
    public RestUri(String uri) {
        matcher = uriPattern.matcher(uri);
    }

    public String getId() {
        if(!matcher.matches()) {
            return null;
        }
        return matcher.group(5);
    }

}
