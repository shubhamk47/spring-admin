package com.springadmin.springadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springadmin.springadmin.models.SearchRequest;
import com.springadmin.springadmin.repository.CommonRepository;
import com.springadmin.springadmin.service.CommonService;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@RestController
@CrossOrigin
public class MainRestController {

    @Autowired
    CommonRepository commonRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    CommonService commonService;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public Map<Class<?>, Map<String, String>> index() {
        Reflections reflections = new Reflections(
                AutoConfigurationPackages.get(applicationContext.getAutowireCapableBeanFactory()));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Entity.class);
        Map<Class<?>, Map<String, String>> classDetails = new HashMap<>();
        for (Class<?> classRef : classes) {
            Map<String, String> fieldMap = new HashMap<>();
            for (Field field : classRef.getDeclaredFields()) {
                // Ignoring fields annotated with @Id for now
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
        log.info("request - {}", requestMap.toString());
        log.info("date type - {}", requestMap.get("dob").getClass().getName());
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
            throws Exception {
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
            throws Exception {
        Class<?> classToUpdate = Class.forName(requestMap.get("class").toString());
        requestMap.remove("class");
        log.info(classToUpdate.getName());
        Object entity = objectMapper.readValue(objectMapper.writeValueAsString(requestMap), classToUpdate);
        commonRepository.delete(entity);
        return Map.of("status", "deleted");
    }

    // TODO: Fetch single entity object
    @GetMapping("/{className}/{id}")
    public Object getObjectDetails(@PathVariable String className, @PathVariable String id)
            throws Exception {

        Class<?> requiredClass = Class.forName(className);

        Field idField = Arrays.stream(requiredClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class)).findFirst().get();
        Object requiredObject = this.commonRepository.findById(requiredClass,
                this.commonService.getConvertedId(id, idField));

        return requiredObject;
    }

    @GetMapping("/{className}")
    public List<? extends Object> getAllObjects(@PathVariable String className)
            throws Exception {
        Class<?> requiredClass = Class.forName(className);

        Field idField = Arrays.stream(requiredClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class)).findFirst().get();

        List<? extends Object> queryResult = this.commonRepository
                .getAllObjects(requiredClass);

        return queryResult;
    }

    // TODO: Add search
    @GetMapping("/search")
    public List<? extends Object> search(@RequestBody List<SearchRequest> searchRequestList) {
        this.commonService.search(searchRequestList);
        return null;
    }


}
