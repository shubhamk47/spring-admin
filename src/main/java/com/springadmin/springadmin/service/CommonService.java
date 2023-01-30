package com.springadmin.springadmin.service;

import com.springadmin.springadmin.models.SearchCriteria;
import com.springadmin.springadmin.models.SearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

@Service
public class CommonService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    public Object getConvertedId(String id, Field idField) {
        Class<?> clazz = idField.getType();
        if (clazz.isAssignableFrom(String.class)) {
            return id;
        } else if (clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(int.class)) {
            return Integer.valueOf(id);
        } else if (clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(double.class)) {
            return Double.valueOf(id);
        } else if (clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(long.class)) {
            return Long.valueOf(id);
        } else if (clazz.isAssignableFrom(UUID.class)) {
            return UUID.fromString(id);
        } else {
            throw new IllegalArgumentException("Bad type");
        }
    }

    public void search(List<SearchRequest> searchRequestList) {

        StringBuilder query = new StringBuilder("");

        for (SearchRequest searchRequest : searchRequestList) {
            query.append("(");
            for (SearchCriteria searchCriteria : searchRequest.getSearchCriteriaList()) {
                query.append(searchCriteria.getFilterClass() + "."
                        + searchCriteria.getFilterKey() + "="
                        + searchCriteria.getValue() + " "
                        + searchCriteria.getDataOption() + " ");
            }
            query.append(")" + searchRequest.getSearchCriteraOptions());
        }

        log.info("Generated query - {}", query.toString());

    }
}
