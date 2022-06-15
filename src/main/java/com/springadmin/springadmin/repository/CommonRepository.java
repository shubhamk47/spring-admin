package com.springadmin.springadmin.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
    public <E> E update(E entity) {
        em.merge(entity);
        return entity;
    }

    @Transactional
    public void delete(Object entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Transactional
    public Object findById(Class<?> classToFind, Object id) {
        return em.find(classToFind, id);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Object> getAllObjects(Class<?> classToFind) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object> cq = (CriteriaQuery<Object>) cb.createQuery(classToFind);
        Root<Object> rootEntry = (Root<Object>) cq.from(classToFind);
        CriteriaQuery<Object> all = cq.select(rootEntry);
        TypedQuery<Object> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

}
