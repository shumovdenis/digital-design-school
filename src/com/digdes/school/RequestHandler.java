package com.digdes.school;

import java.util.*;

public class RequestHandler {


    public void parse(String request) {
        DataService dataService = new DataService();

        String[] dataArray = dataExtraction(request);
        Map<String, String> groupsDataMap = divisionIntoGroups(dataArray);
        String method = groupsDataMap.get("method");

        Map<String, Object> rowValues;
        Map<String, Object> rowConditions;
        switch (method.toLowerCase()) {
            case "insert" -> {
                rowValues = prepareValuesToRow(groupsDataMap.get("values"));
                dataService.insert(rowValues);
            }
            case "update" -> {
                rowValues = prepareValuesToRow(groupsDataMap.get("values"));
                dataService.update(rowValues);
            }
            case "select" -> {
                dataService.select();
            }
        }
    }

    private Map<String, Object> prepareValuesToRow(String rawValues) {
        HashMap<String, Object> row = new HashMap<>();
        String rawData = rawValues.replace("=", " ")
                .replace("[", "")
                .replace("]", "")
                .replace(",", "");
        String[] dataParts = rawData.split(" ");
        for (int i = 0; i < dataParts.length - 1; i = i + 2) {
            getValueType(row, dataParts, i);
        }
        return row;
    }

    private void getValueType(Map<String, Object> row, String[] dataParts, int i) {
        try {
            switch (dataParts[i].toLowerCase().trim()) {
                case "id" -> {
                    row.put("id", Long.parseLong(dataParts[i + 1].trim()));
                }
                case "age" -> {
                    row.put("age", Long.parseLong(dataParts[i + 1].trim()));
                }
                case "lastname" -> {
                    row.put("lastName", (dataParts[i + 1].trim()));
                }
                case "cost" -> {
                    row.put("cost", (Double.parseDouble(dataParts[i + 1].trim())));
                }
                case "active" -> {
                    row.put("active", (Boolean.parseBoolean(dataParts[i + 1].trim())));
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected value: " + dataParts[i + 1] + " in param: " + dataParts[i]);
        }

    }

    private String[] dataExtraction(String request) {
        request = request.replace("'", " ");
        request = request.replace(",", " ");
        request = request.replace("=", " = ");
        String[] rawReqArr = request.split("[\\s]+");
        return rawReqArr;
    }

    private Map<String, String> divisionIntoGroups(String[] paramArr) {
        StringBuilder sb = new StringBuilder();
        List<String> valuesList = new ArrayList<>();
        List<String> conditionsList = new ArrayList<>();
        for (int i = 0; i < paramArr.length; i++) {
            if (paramArr[i].toUpperCase(Locale.ROOT).equals("VALUES")) {
                i++;
                while (!paramArr[i].toUpperCase(Locale.ROOT).equals("WHERE")) {
                    sb.append(paramArr[i]).append(paramArr[i + 1]).append(paramArr[i + 2]);
                    valuesList.add(sb.toString());
                    sb.setLength(0);
                    if ((i + 3) >= paramArr.length) {
                        break;
                    }
                    i = i + 3;
                }
            }
            if (paramArr[i].toUpperCase(Locale.ROOT).equals("WHERE")) {
                i++;
                while (i < paramArr.length) {
                    sb.append(paramArr[i]).append(" ").append(paramArr[i + 1]).append(" ").append(paramArr[i + 2]);
                    i = i + 3;
                    conditionsList.add(sb.toString());
                    sb.setLength(0);
                    if (i < paramArr.length) {
                        sb.append(paramArr[i]);
                        conditionsList.add(sb.toString());
                        sb.setLength(0);
                        i++;
                    }
                }
            }


        }

        Map<String, String> requestParsedMap = new HashMap<>();
        requestParsedMap.put("method", paramArr[0]);
        requestParsedMap.put("values", valuesList.toString());
        requestParsedMap.put("conditions", conditionsList.toString());
        return requestParsedMap;

    }
}
