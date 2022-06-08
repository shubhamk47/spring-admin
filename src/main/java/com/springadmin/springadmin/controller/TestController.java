package com.springadmin.springadmin.controller;

import java.lang.reflect.Field;
import java.util.Set;

import org.apache.catalina.connector.Response;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springadmin.springadmin.annotations.AdminEntity;

@RestController
public class TestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public ModelAndView index() {
        Reflections reflections = new Reflections("com.springadmin.springadmin");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(AdminEntity.class);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("data", classes);
        return modelAndView;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveObject(@RequestBody Object saveObject) {
        log.info(saveObject.getClass().getName());
        return null;
    }

}
