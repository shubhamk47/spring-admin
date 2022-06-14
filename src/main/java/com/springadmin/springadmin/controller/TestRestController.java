package com.springadmin.springadmin.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springadmin.springadmin.annotations.AdminEntity;
import com.springadmin.springadmin.entity.EntityTest;
import com.springadmin.springadmin.repository.CommonRepository;

@RestController
@CrossOrigin
public class TestRestController {

    @Autowired
    CommonRepository commonRepository;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public Map<Class<?>, Map<String, String>> index() {
        Reflections reflections = new Reflections("com.springadmin.springadmin");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(AdminEntity.class);
        Map<Class<?>, Map<String, String>> classDetails = new HashMap<>();
        for (Class<?> classRef : classes) {
            Map<String, String> fieldMap = new HashMap<>();
            for (Field field : classRef.getDeclaredFields()) {
                if (field.getAnnotation(Id.class) == null) {
                    fieldMap.put(field.getName(), field.getType().getName());
                }
            }
            classDetails.put(classRef, fieldMap);
        }
        return classDetails;
    }

    @PostMapping("/save")
    public Map<String, String> saveObject(@RequestBody Map<String, Object> requestMap) throws IOException, ClassNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        Class<?> classToWrite = Class.forName(requestMap.get("class").toString()); 
        requestMap.remove("class");
        Object entity = objectMapper.readValue(objectMapper.writeValueAsString(requestMap), classToWrite);
        commonRepository.insert(entity);
        log.info("EntityTest - {}", entity.toString());
        return Map.of("data", entity.toString());
    }

}
