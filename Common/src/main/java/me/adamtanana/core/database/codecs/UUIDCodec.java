package me.adamtanana.core.database.codecs;

import me.adamtanana.core.database.DatabaseEngine;
import me.adamtanana.core.reflection.SafeField;

import java.util.UUID;

public class UUIDCodec extends DBCodec<UUID> {

    public UUIDCodec(DatabaseEngine engine) {
        super(engine);
    }

    @Override
    public boolean canEncode(Class<?> type) {
        return UUID.class.isAssignableFrom(type);
    }

    @Override
    public boolean canDecode(Class<?> type) {
        return canEncode(type);
    }

    @Override
    public Object encode(Class<?> type, SafeField field, UUID value) {
        return value.toString();
    }

    @Override
    public Object decode(Class<?> type, SafeField field, Object object) {
        return UUID.fromString((String) object);
    }
}
