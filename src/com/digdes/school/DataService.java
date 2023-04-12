package com.digdes.school;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataService {
    private static List<Map<String, Object>> collectionList = new ArrayList<>();


    public static List<Map<String, Object>> getCollectionList() {
        return collectionList;
    }

    public List<Map<String, Object>> insert(Map<String, Object> row) {
        collectionList.add(row);
        return collectionList;
    }

    public List<Map<String, Object>> update(Map<String, Object> values) {
        for (int i = 0; i < collectionList.size(); i++) {
            Map<String, Object> row = collectionList.get(i);
            for (Map.Entry entry : values.entrySet()) {
                row.put((String) entry.getKey(), entry.getValue());
            }
        }
        return collectionList;
    }

    public List<Map<String, Object>> select() {
        return collectionList;
    }


}
