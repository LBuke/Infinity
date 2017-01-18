package me.adamtanana.core.database.codecs;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import me.adamtanana.core.database.DatabaseEngine;
import me.adamtanana.core.database.Storable;
import me.adamtanana.core.reflection.SafeField;

import java.util.List;
import java.util.stream.Collectors;

public class ListCodec extends DBCodec<List<?>> {

    public ListCodec(DatabaseEngine engine) {
        super(engine);
    }

    @Override
    public boolean canEncode(Class<?> type) {
        return List.class.isAssignableFrom(type);
    }

    @Override
    public boolean canDecode(Class<?> type) {
        return canEncode(type);
    }

    @Override
    public Object encode(Class<?> type, SafeField field, List<?> value) {
        BasicDBList list = value.stream().map(entry ->
                engine.encode(field.getAnnotation(Storable.class).genericType(), field, entry)).
                collect(Collectors.toCollection(BasicDBList::new));

        return list;
    }

    @Override
    public Object decode(Class<?> type, SafeField field, Object object) {
        List<Object> list = Lists.newArrayList();
        BasicDBList dbList = (BasicDBList) object;
        list.addAll(dbList.stream().map(entry -> engine.decode(field.getAnnotation(Storable.class).genericType(), field, entry)).collect(Collectors.toList()));

        return list;
    }
}
