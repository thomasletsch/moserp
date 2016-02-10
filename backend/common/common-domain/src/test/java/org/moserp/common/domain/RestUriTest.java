/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

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
