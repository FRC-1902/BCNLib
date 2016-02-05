package com.explodingbacon.bcnlib.networking;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTable implements TableInterface {
    private JedisPool pool;

    public JedisTable(String host, int port) {
        pool = new JedisPool(host, port);
    }

    @Override
    public String getString(String key, String fallback) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public Double getNumber(String key, Double fallback) {
        try (Jedis jedis = pool.getResource()) {
            return Double.parseDouble(jedis.get(key));
        } catch (Exception ignored) {
            return fallback;
        }
    }

    @Override
    public Boolean getBoolean(String key, Boolean fallback) {
        try (Jedis jedis = pool.getResource()) {
            return Boolean.parseBoolean(jedis.get(key));
        } catch (Exception ignored) {
            return fallback;
        }
    }

    @Override
    public void putString(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key, value);
        }
    }

    @Override
    public void putNumber(String key, Double value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key, value.toString());
        }
    }

    @Override
    public void putBoolean(String key, Boolean value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key, value.toString());
        }
    }
}
