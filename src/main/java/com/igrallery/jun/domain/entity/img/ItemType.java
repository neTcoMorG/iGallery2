package com.igrallery.jun.domain.entity.img;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ItemType {
    ITEM1, ITEM2, ITEM3, ITEM4, ITEM5;

    private static Map<String, ItemType> typeMapping = new ConcurrentHashMap<>();
    static {
        typeMapping.put(ITEM1.name(), ITEM1);
        typeMapping.put(ITEM2.name(), ITEM2);
        typeMapping.put(ITEM3.name(), ITEM3);
        typeMapping.put(ITEM4.name(), ITEM4);
        typeMapping.put(ITEM5.name(), ITEM5);
    }

    public static ItemType getType(String field) throws Exception {
        if (typeMapping.get(field) == null) {
            throw new Exception("해당 범위 값은 존재하지 않음");
        }
        return typeMapping.get(field);
    }
}
