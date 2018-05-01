package com.example.yunus.gallery;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by yunus on 4/19/18.
 */

public class MapComparator implements Comparator<HashMap<String, String>> {
    private final String key;
    private final String order;

    public MapComparator(String key, String order) {
        this.key = key;
        this.order = order;
    }

    public int compare(HashMap<String, String> first,
                       HashMap<String, String> second) {
        String firstValue = first.get(key);
        String secondValue = second.get(key);
        if (this.order.toLowerCase().contentEquals("asc")) {
            return firstValue.compareTo(secondValue);
        } else {
            return secondValue.compareTo(firstValue);
        }

    }
}