package io.github.brewagebear.order.adapter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Body {
    private final Map<String, Object> data = new LinkedHashMap<>();

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Map<String, Object> toMap() {
        return Collections.unmodifiableMap(data);
    }
}
