package com.springadmin.springadmin.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springadmin.springadmin.annotations.AdminEntity;
import com.springadmin.springadmin.repository.CommonRepository;

@Controller
public class TestController {

    @Autowired
    CommonRepository commonRepository;

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

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> saveObject(HttpServletRequest saveObject)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, ClassNotFoundException {

        Map<String, String[]> dataMap = saveObject.getParameterMap();
        Object obj = Class.forName(dataMap.get("class")[0]).getDeclaredConstructor().newInstance();

        for (Entry<String, String[]> data : dataMap.entrySet()) {
            if (!data.getKey().equals("class") && !data.getKey().equals("id")) {
                log.info("Form data - " + data.getKey() + ", " + data.getValue()[0]);
                Method method = Class.forName(obj.getClass().getName()).getDeclaredMethod(
                        "set" + data.getKey().substring(0, 1).toUpperCase() + data.getKey().substring(1), data.getValue()[0].getClass());
                method.invoke(obj, data.getValue()[0]);
            }
        }

        commonRepository.insert(obj);
        return null;
    }

}
