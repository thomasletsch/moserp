package org.moserp.environment.rest;

import org.moserp.environment.domain.ValueList;
import org.moserp.environment.domain.ValueListItem;
import org.moserp.environment.repository.ValueListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@ExposesResourceFor(ValueList.class)
public class ValueListController {

    @Autowired
    private ValueListRepository valueListRepository;

    @Autowired
    private EntityLinks entityLinks;

    @RequestMapping(method = RequestMethod.GET, value = "/valueLists/{key}/values")
    public Resources<Resource<ValueListItem>> getValuesForKey(@PathVariable String key) {
        ValueList valueList = valueListRepository.findByKey(key);
        if(valueList == null) {
            valueList = new ValueList(key);
            valueList = valueListRepository.save(valueList);
        }
        List<ValueListItem> values = valueList.getValues();

        Stream<Resource<ValueListItem>> resourceStream = values.stream().map(item -> new Resource<>(item));
        List<Resource<ValueListItem>> resources = resourceStream.collect(Collectors.toList());
        return new Resources<>(resources);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/valueLists/{key}/values")
    public ResponseEntity<Void> addValueForKey(@PathVariable String key, ValueListItem item) throws URISyntaxException {
        ValueList valueList = valueListRepository.findByKey(key);
        if(valueList == null) {
            valueList = new ValueList(key);
            valueList = valueListRepository.save(valueList);
        }
        valueList.addValue(item);
        URI location = new URI(entityLinks.linkToSingleResource(valueList).getHref());
        return ResponseEntity.created(location).build();
    }


}
