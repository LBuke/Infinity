package me.adamtanana.core.database.codecs;

import me.adamtanana.core.database.DatabaseEngine;
import me.adamtanana.core.reflection.SafeField;

public abstract class DBCodec<T> {
    protected final DatabaseEngine engine;

    protected DBCodec(DatabaseEngine engine) {
        this.engine = engine;
    }

    public abstract boolean canEncode(Class<?> type);

    public abstract boolean canDecode(Class<?> type);

    public abstract Object encode(Class<?> type, SafeField field, T value);

    public abstract Object decode(Class<?> type, SafeField field, Object object);

    public Object encodeFrom(Class<?> type, SafeField field, Object object) {
        return encode(type, field, (T) object);
    }

    protected String parse(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
