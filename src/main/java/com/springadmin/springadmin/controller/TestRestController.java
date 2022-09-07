package com.springadmin.springadmin.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Id;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springadmin.springadmin.annotations.AdminEntity;
import com.springadmin.springadmin.repository.CommonRepository;

@RestController
@CrossOrigin
public class TestRestController {

    @Autowired
    CommonRepository commonRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ApplicationContext applicationContext;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public Map<Class<?>, Map<String, String>> index() {
        Reflections reflections = new Reflections(
                AutoConfigurationPackages.get(applicationContext.getAutowireCapableBeanFactory()));
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
    public Map<String, Object> saveObject(@RequestBody Map<String, Object> requestMap)
            throws IOException, ClassNotFoundException {
        Class<?> classToWrite = Class.forName(requestMap.get("class").toString());
        requestMap.remove("class");
        Object entity = objectMapper.readValue(objectMapper.writeValueAsString(requestMap), classToWrite);
        commonRepository.insert(entity);
        //log.info("EntityTest - {}", entity.toString());
        return Map.of(entity.getClass().getName(), entity);
    }

    // TODO: Add update api
    @PatchMapping("/update")
    public Map<String, Object> updateObject(@RequestBody Map<String, Object> requestMap)
            throws ClassNotFoundException, Exception {
        Class<?> classToUpdate = Class.forName(requestMap.get("class").toString());
        requestMap.remove("class");
        log.info(classToUpdate.getName());
        Object entity = objectMapper.readValue(objectMapper.writeValueAsString(requestMap), classToUpdate);
        commonRepository.update(entity);
        return Map.of(entity.getClass().getName(), entity);
    }

    // TODO: Add delete api
    @DeleteMapping("/delete")
    public Map<String, String> deleteObject(@RequestBody Map<String, Object> requestMap)
            throws ClassNotFoundException, Exception {
        Class<?> classToUpdate = Class.forName(requestMap.get("class").toString());
        requestMap.remove("class");
        log.info(classToUpdate.getName());
        Object entity = objectMapper.readValue(objectMapper.writeValueAsString(requestMap), classToUpdate);
        commonRepository.delete(entity);
        return Map.of("status", "deleted");
    }

    // TODO: Fetch single entity object
    @GetMapping("/{className}/{id}")
    public Map<String, Object> getObjectDetails(@PathVariable String className, @PathVariable String id)
            throws ClassNotFoundException, Exception {

        Class<?> requiredClass = null;
        Reflections reflections = new Reflections(
                AutoConfigurationPackages.get(applicationContext.getAutowireCapableBeanFactory()));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(AdminEntity.class);
        for (Class<?> annotatedClass : classes) {
            if (annotatedClass.getSimpleName().equals(className)) {
                requiredClass = annotatedClass;
                break;
            }
        }

        Field idField = Arrays.stream(requiredClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class)).findFirst().get();
        Object requiredObject = this.commonRepository.findById(requiredClass,
                getConvertedId(id, idField));

        return Map.of(requiredObject.getClass().getName(),
                requiredObject);

    }

    public Object getConvertedId(String id, Field idField) {
        Class<?> clazz = (Class<?>) idField.getType();
        if (clazz.isAssignableFrom(String.class)) {
            return id;
        } else if (clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(int.class)) {
            return Integer.valueOf(id);
        } else if (clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(double.class)) {
            return Double.valueOf(id);
        } else if (clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(long.class)) {
            return Long.valueOf(id);
        } else if(clazz.isAssignableFrom(UUID.class)){
            return UUID.fromString(id);
        }  else {
            throw new IllegalArgumentException("Bad type");
        }
    }

    @GetMapping("/{className}")
    public Map<Object, Object> getAllObjects(@PathVariable String className)
            throws Exception {
        Class<?> requiredClass = null;
        Reflections reflections = new Reflections("com.springadmin.springadmin");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(AdminEntity.class);
        for (Class<?> annotatedClass : classes) {
            if (annotatedClass.getSimpleName().equals(className)) {
                requiredClass = annotatedClass;
                break;
            }
        }

        Field idField = Arrays.stream(requiredClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class)).findFirst().get();

        List<? extends Object> queryResult = this.commonRepository
                .getAllObjects(requiredClass);
        Map<Object, Object> retMap = new HashMap<>();
        idField.setAccessible(true);

        for (Object item : queryResult) {
            retMap.put(idField.get(item), item);
        }

        return retMap;
    }

    // TODO: Create web interface using react

}
