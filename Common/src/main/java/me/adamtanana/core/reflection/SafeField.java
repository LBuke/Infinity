package me.adamtanana.core.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SafeField {
    private Field field;

    public SafeField(Field instance) {
        this.field = instance;
    }

    public String getName() {
        return field.getName();
    }

    public void set(Object instance, Object value) {
        try {
            field.set(instance, value);
        } catch (Exception e) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Plugin tried to access unknown field", e);
        }
    }

    public <T extends Annotation> T getAnnotation(Class<T> type) {
        return field.getAnnotation(type);
    }

    public Class<?> getType() {
        return field.getType();
    }

    public <T> T get(Object instance, Class<T> type) {
        return (T) get(instance);
    }

    public Object get(Object instance) {
        try {
            return field.get(instance);
        } catch (Exception e) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Plugin tried to access unknown field", e);
            return null;
        }
    }

    public Field getHandle() {
        return this.field;
    }
}
