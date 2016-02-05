package com.explodingbacon.bcnlib.networking;

public interface TableInterface {
    String getString(String key, String fallback);

    Double getNumber(String key, Double fallback);

    Boolean getBoolean(String key, Boolean fallback);

    void putString(String key, String value);

    void putNumber(String key, Double value);

    void putBoolean(String key, Boolean value);
}
