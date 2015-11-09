package org.moserp.common.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;

@NoRepositoryBean
public abstract class RestRepository<T> implements CrudRepository<T, String> {

    @Autowired
    protected RestTemplate restTemplate;

    @Override
    public <S extends T> S save(S entity) {
        return null;
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public T findOne(String uri) {
        return restTemplate.getForObject(uri, getResultType());
    }

    @Override
    public boolean exists(String uri) {
        return false;
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    public Iterable<T> findAll(Iterable<String> uris) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(String uri) {

    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void delete(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @SuppressWarnings("unchecked")
    protected Class<T> getResultType() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
