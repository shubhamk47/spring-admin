package com.springadmin.springadmin.repository;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CommonSpecification implements Specification<Object> {

    @Override
    public Predicate toPredicate(Root<Object> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        
        return null;
    }
}
