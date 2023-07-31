package alticshaw.com.coszastore.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class ConvertArray {
    public static String convertListToUrlString(List<String> list) {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (String url : list) {
            joiner.add("\"" + url + "\"");
        }
        return joiner.toString();
    }

    public static List<String> parseStringToList(String jsonString) {
        List<String> resultList = new ArrayList<>();
        jsonString = jsonString.substring(1, jsonString.length() - 1);
        String[] elements = jsonString.split("\",\"");

//        for (String element : elements) {
//            resultList.add(element);
//        }

        Collections.addAll(resultList, elements);

        return resultList;
    }


}
