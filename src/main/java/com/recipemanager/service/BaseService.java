package com.recipemanager.service;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public abstract class BaseService<T, ID> implements CrudService<T, ID> {
    // for using methods form JPA repo
    protected abstract JpaRepository<T, ID> getJpaRepository();

    @Override
    public T save(T entity) {
        return getJpaRepository().save(entity);
    }
    @Override
    public Optional<T> findById(ID id) {
        return getJpaRepository().findById(id);
    }
    @Override
    public List<T> findAll() {
        return getJpaRepository().findAll();
    }
    @Override
    public void deleteById(ID id) {
        getJpaRepository().deleteById(id);
    }
}
