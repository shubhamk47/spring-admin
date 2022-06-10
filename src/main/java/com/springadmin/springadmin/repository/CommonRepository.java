package com.springadmin.springadmin.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class CommonRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public <E> E insert(E entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    public <E> E update(E entity){
        em.merge(entity);
        return entity;
    }

    @Transactional
    public void delete(Object entity) {
        em.remove(entity);
    }

}
