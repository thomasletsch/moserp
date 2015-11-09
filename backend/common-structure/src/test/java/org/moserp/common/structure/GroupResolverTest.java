package org.moserp.common.structure;

import org.junit.Test;
import org.moserp.common.structure.domain.SimpleClass;
import org.moserp.common.structure.domain.subpackage.GroupResolverTestEntityDomainWithSub;

import static org.junit.Assert.assertEquals;

public class GroupResolverTest {

    GroupResolver resolver = new GroupResolver();

    @Test
    public void testSimple() throws Exception {
        assertEquals("org.moserp.common.structure", resolver.resolveGroup(GroupResolverTestEntitySimple.class));
    }

    @Test
    public void testDomain() throws Exception {
        assertEquals("org.moserp.common.structure", resolver.resolveGroup(SimpleClass.class));
    }

    @Test
    public void testDomainWithSubPackage() throws Exception {
        assertEquals("org.moserp.common.structure", resolver.resolveGroup(GroupResolverTestEntityDomainWithSub.class));
    }
}