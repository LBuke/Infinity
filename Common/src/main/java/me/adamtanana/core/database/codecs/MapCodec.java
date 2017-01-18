package me.adamtanana.core.database.codecs;

import com.mongodb.BasicDBObject;
import me.adamtanana.core.database.DatabaseEngine;
import me.adamtanana.core.database.Storable;
import me.adamtanana.core.reflection.SafeField;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapCodec extends DBCodec<Map<String, ?>> {

    protected MapCodec(DatabaseEngine engine) {
        super(engine);
    }

    @Override
    public boolean canEncode(Class<?> type) {
        return Map.class.isAssignableFrom(type);
    }

    @Override
    public boolean canDecode(Class<?> type) {
        return canEncode(type);
    }

    @Override
    public Object encode(Class<?> type, SafeField field, Map<String, ?> value) {
        BasicDBObject object = new BasicDBObject();
        Class<?> genericType = field.getAnnotation(Storable.class).genericType();
        for (Entry<String, ?> entry : value.entrySet()) {
            object.append(entry.getKey(), engine.encode(genericType, field, entry.getValue()));
        }

        return object;
    }

    @Override
    public Object decode(Class<?> type, SafeField field, Object object) {
        Map<String, Object> map = new HashMap<>();
        BasicDBObject dbObject = (BasicDBObject) object;
        Class<?> genericType = field.getAnnotation(Storable.class).genericType();
        for (Entry<String, Object> entry : dbObject.entrySet()) {
            map.put(entry.getKey(), engine.decode(genericType, field, entry.getValue()));
        }

        return map;
    }
}
