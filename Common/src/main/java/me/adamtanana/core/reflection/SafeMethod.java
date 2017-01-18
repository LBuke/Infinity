package me.adamtanana.core.reflection;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SafeMethod {
    private Method method;

    public SafeMethod(Method method) {
        this.method = method;
    }

    public String getName() {
        return method.getName();
    }

    @SuppressWarnings("unchecked")
    public <T> T invoke(Object instance, Class<T> type, Object... args) {
        return (T) invoke(instance, args);
    }

    public Object invoke(Object instance, Object... args) {
        try {
            return method.invoke(instance, args);
        } catch (Exception e) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Plugin tried to access unknown method", e);
            return null;
        }
    }

    public Method getHandle() {
        return this.method;
    }
}
