package org.moserp.common.cache;

import org.moserp.common.rest.HateoasLinkContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceCache<RESOURCE extends HateoasLinkContainer> {

    Map<String, RESOURCE> cache = new HashMap<>();

    public ResourceCache(List<RESOURCE> resources) {
        cacheInit(resources);
    }

    private void cacheInit(List<RESOURCE> resources) {
        for (RESOURCE resource : resources) {
            cache.put(resource.getSelf().getHref(), resource);
        }
    }

    public RESOURCE get(String uri) {
        return cache.get(uri);
    }

    public List<RESOURCE> getAll() {
        Collection<RESOURCE> values = cache.values();
        List<RESOURCE> list = new ArrayList<>(values);
        Collections.sort(list, new Comparator<RESOURCE>() {
            @Override
            public int compare(RESOURCE lhs, RESOURCE rhs) {
                return lhs.getSelf().getHref().compareTo(rhs.getSelf().getHref());
            }
        });
        return list;
    }
}
