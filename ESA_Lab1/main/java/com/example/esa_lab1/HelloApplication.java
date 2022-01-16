package com.example.esa_lab1;

import com.example.esa_lab1.controller.DancerClassController;
import com.example.esa_lab1.controller.DancerController;
import com.example.esa_lab1.controller.DancerStudioController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class    HelloApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(DancerClassController.class);
        classes.add(DancerController.class);
        classes.add(DancerStudioController.class);
        return classes;
    }

}