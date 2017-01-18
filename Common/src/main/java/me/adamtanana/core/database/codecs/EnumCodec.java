package me.adamtanana.core.database.codecs;

import me.adamtanana.core.database.DatabaseEngine;
import me.adamtanana.core.reflection.SafeField;

import java.lang.reflect.Method;

public class EnumCodec extends DBCodec<Object> {

    public EnumCodec(DatabaseEngine engine) {
        super(engine);
    }

    @Override
    public boolean canEncode(Class<?> type) {
        return type.isEnum();
    }

    @Override
    public boolean canDecode(Class<?> type) {
        return type.isEnum();
    }

    @Override
    public Object encode(Class<?> type, SafeField field, Object value) {
        return ((Enum) value).name();
    }

    @Override
    public Object decode(Class<?> type, SafeField field, Object object) {
        try {
            Method method = type.getMethod("valueOf", String.class);
            return method.invoke(null, object);
        } catch (Exception e) {
            return null;
        }
    }
}
