package me.adamtanana.core.database.codecs;

import com.mongodb.BasicDBObject;
import me.adamtanana.core.database.DatabaseEngine;
import me.adamtanana.core.database.Model;
import me.adamtanana.core.database.Storable;
import me.adamtanana.core.reflection.SafeField;
import me.adamtanana.core.util.CommonUtil;

import java.util.Map;
import java.util.Map.Entry;

public class ModelCodec extends DBCodec<Object> {
    public ModelCodec(DatabaseEngine engine) {
        super(engine);
    }

    @Override
    public boolean canEncode(Class<?> type) {
        Class<?> clazz = type;
        while (clazz != null) {
            if (clazz.isAnnotationPresent(Model.class)) {
                return true;
            }
            clazz = clazz.getSuperclass();
        }
        return false;
    }

    @Override
    public boolean canDecode(Class<?> type) {
        return canEncode(type);
    }

    @Override
    public Object encode(Class<?> type, SafeField field, Object value) {
        BasicDBObject dbObject = new BasicDBObject();
        for (Entry<String, SafeField> entry : engine.getFields(type).entrySet()) {
            SafeField f = entry.getValue();
            if (f.getAnnotation(Storable.class) == null) {
                continue;
            }
            Object val = f.get(value);
            dbObject.append(parse(entry.getKey()), engine.encode(f.getType(), f, val));
        }

        return dbObject;
    }

    @Override
    public Object decode(Class<?> type, SafeField field, Object object) {
        BasicDBObject dbObject = (BasicDBObject) object;
        Map<String, SafeField> fields = engine.getFields(type);
        Object instance = CommonUtil.instance(type);
        for (Entry<String, Object> entry : dbObject.entrySet()) {
            String name = entry.getKey();
            Object raw = entry.getValue();
            SafeField f = fields.get(name);
            if (f != null) {
                f.set(instance, engine.decode(f.getType(), f, raw));
            }
        }

        return instance;
    }
}
