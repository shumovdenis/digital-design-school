package com.digdes.school;

import java.util.List;
import java.util.Map;

public class JavaSchoolStarter {
    public JavaSchoolStarter(){

    }

    public List<Map<String,Object>> execute(String request) throws Exception {
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.parse(request);
        List<Map<String, Object>> collectionList = DataService.getCollectionList();

        return collectionList;
    }
}
