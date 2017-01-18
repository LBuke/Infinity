package me.adamtanana.core.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SafeClass {
    private Class<?> instance;
    private List<SafeField> fieldList = new ArrayList<>();
    private List<SafeMethod> methodList = new ArrayList<>();

    public SafeClass(Class<?> instance) {
        this.instance = instance;
        findFields(instance);
        findMethods(instance);
    }

    private void findFields(Class<?> instance) {
        if (instance == null) {
            return;
        }

        for (Field field : instance.getDeclaredFields()) {
            field.setAccessible(true);
            fieldList.add(new SafeField(field));
        }

        findFields(instance.getSuperclass());
    }

    private void findMethods(Class<?> instance) {
        if (instance == null) {
            return;
        }

        for (Method method : instance.getDeclaredMethods()) {
            methodList.add(new SafeMethod(method));
        }

        findMethods(instance.getSuperclass());
    }

    public Object newInstance() {
        try {
            return instance.newInstance();
        } catch (Exception e) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Plugin tried to access unknown class", e);
            return null;
        }
    }

    public SafeField getField(String name) {
        //TODO: Cache?
        return getField(instance, name);
    }

    private SafeField getField(Class<?> clazz, String name) {
        try {
//            String fieldName = MCVersion.getServer().getFieldName(clazz, name);
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return new SafeField(field);
        } catch (Exception e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getField(superClass, name);
            } else {
                return null;
            }
        }
    }

    public SafeField getRawField(String name) {
        //TODO: Cache?
        return getRawField(instance, name);
    }

    private SafeField getRawField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return new SafeField(field);
        } catch (Exception e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getRawField(superClass, name);
            } else {
                return null;
            }
        }
    }

    public SafeMethod getMethod(String name, Class<?>... params) {
        //TODO: Cache?
        return getMethod(instance, name, params);
    }

    public SafeMethod getMethod(Class<?> clazz, String name, Class<?>... params) {
        try {
//            String methodName = MCVersion.getServer().getMethodName(clazz, name, params);
            Method method = clazz.getDeclaredMethod(name, params);
            method.setAccessible(true);
            return new SafeMethod(method);
        } catch (Exception e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getMethod(superClass, name, params);
            } else {
                return null;
            }
        }
    }

    public SafeMethod getRawMethod(String name, Class<?>... params) {
        return getRawMethod(instance, name, params);
    }

    private SafeMethod getRawMethod(Class<?> clazz, String name, Class<?>... params) {
        try {
            Method method = clazz.getDeclaredMethod(name, params);
            method.setAccessible(true);
            return new SafeMethod(method);
        } catch (Exception e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getRawMethod(superClass, name, params);
            } else {
                return null;
            }
        }
    }

    public Class<?> getHandle() {
        return this.instance;
    }

    public Collection<SafeField> getFields() {
        return fieldList;
    }

    public Collection<SafeMethod> getMethods() {
        return methodList;
    }
}
