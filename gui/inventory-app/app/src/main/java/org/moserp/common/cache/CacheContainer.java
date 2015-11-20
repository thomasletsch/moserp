package org.moserp.common.cache;

import org.androidannotations.annotations.EBean;
import org.moserp.common.rest.HateoasLinkContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EBean(scope = EBean.Scope.Singleton)
public class CacheContainer {

    private final Map<Class<? extends HateoasLinkContainer>, ResourceCache<? extends HateoasLinkContainer>> caches = new HashMap<>();

    public <RESOURCE extends HateoasLinkContainer> void cache(Class<RESOURCE> resourceClass, List<RESOURCE> resources) {
        caches.put(resourceClass, new ResourceCache<RESOURCE>(resources));
    }

    @SuppressWarnings("unchecked")
    public <RESOURCE extends HateoasLinkContainer> ResourceCache<RESOURCE> get(Class<RESOURCE> resourceClass) {
        return (ResourceCache<RESOURCE>) caches.get(resourceClass);
    }
}
