package com.mamalimomen.base.services.impl;

import com.mamalimomen.base.domains.BaseEntity;
import com.mamalimomen.base.repositories.BaseRepository;
import com.mamalimomen.base.services.BaseService;

import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<PK extends String, E extends BaseEntity, REP extends BaseRepository<PK, E>> implements BaseService<PK, E> {
    protected final REP repository;

    public BaseServiceImpl(REP repository) {
        this.repository = repository;
    }

    @Override
    public void closeEntityManger() {
        repository.closeEntityManger();
    }

    @Override
    public boolean saveOne(E e) {
        return repository.saveOne(e);
    }

    @Override
    public boolean saveMany(List<E> l) {
        return repository.saveMany(l);
    }

    @Override
    public boolean updateOne(E e) {
        return repository.updateOne(e);
    }

    @Override
    public boolean deleteOne(E e) {
        return repository.deleteOne(e);
    }

    @Override
    public Optional<E> findOneById(Class<E> c, PK id) {
        return repository.findOneById(c, id);
    }

    @Override
    public Optional<E> findOneByNamedQuery(String namedQuery, Class<E> c, Object... parameters) {
        return repository.findOneByNamedQuery(namedQuery, c, parameters);
    }

    @Override
    public List<E> findManyByNamedQuery(String namedQuery, Class<E> c, Object... parameters) {
        return repository.findManyByNamedQuery(namedQuery, c, parameters);
    }

    @Override
    public List<E> findAllByNamedQuery(String namedQuery, Class<E> c) {
        return repository.findAllByNamedQuery(namedQuery, c);
    }

    @Override
    public List<E> findManyByNativeQuery(String nativeQuery, Class<E> c) {
        return repository.findManyByNativeQuery(nativeQuery, c);
    }
}
