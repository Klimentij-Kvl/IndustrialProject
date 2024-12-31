package org.example.UIProcessor.GUI;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

public class Controller {
    private Set<Class<?>> getConcreteClassesInPackage(String packageName) throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(clazz -> clazz.getPackageName()
                        .equalsIgnoreCase(packageName))
                .map(ClassPath.ClassInfo::load)
                .filter(load -> !Modifier.isAbstract(load.getModifiers()))
                .collect(Collectors.toSet());
    }


}
