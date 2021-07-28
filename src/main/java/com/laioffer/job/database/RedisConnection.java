package com.laioffer.job.database;

import redis.clients.jedis.Jedis;

public class RedisConnection {
    private static final String INSTANCE = "";
    private static final int PORT = 0000;
    private static final String PASSWORD = "";
    private static final String SEARCH_KEY_TEMPLATE = "";
    private static final String FAVORITE_KEY_TEMPLATE = "";

    private Jedis jedis;

    public RedisConnection() {
        jedis = new Jedis(INSTANCE, PORT);
        jedis.auth(PASSWORD);
    }

    public void close() {
        jedis.close();
    }

    public String getSearchResult(double lat, double lon, String keyword) {
        if (jedis == null) {
            return null;
        }
        String key = String.format(SEARCH_KEY_TEMPLATE, lat, lon, keyword);
        return jedis.get(key);
    }

    public void setSearchResult(double lat, double lon, String keyword, String value) {
        if (jedis == null) {
            return;
        }
        String key = String.format(SEARCH_KEY_TEMPLATE, lat, lon, keyword);
        jedis.set(key, value);
        jedis.expire(key, 10);
    }

    public String getFavoriteResult(String userId) {
        if (jedis == null) {
            return null;
        }
        String key = String.format(FAVORITE_KEY_TEMPLATE, userId);
        return jedis.get(key);
    }

    public void setFavoriteResult(String userId, String value) {
        if (jedis == null) {
            return;
        }
        String key = String.format(FAVORITE_KEY_TEMPLATE, userId);
        jedis.set(key, value);
        jedis.expire(key, 10);
    }

    public void deleteFavoriteResult(String userId) {
        if (jedis == null) {
            return;
        }
        String key = String.format(FAVORITE_KEY_TEMPLATE, userId);
        jedis.del(key);
    }
}


