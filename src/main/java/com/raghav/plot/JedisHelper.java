package com.raghav.plot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raghav
 */


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisHelper {

    private static JedisPool jedisPool;
    private static final String HOST = "localhost";
    private static final int PORT = 6379;
    private static final int TIMEOUT = 10000;

    static {
        try {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            jedisPool = new JedisPool(poolConfig,HOST,PORT);
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
    
    
    public static void main (String[] args){
        System.out.println("hi");
        System.out.println(cacheContainsKey("raghav"));
    }

    public static void destory() {
        jedisPool.destroy();
    }

    public static Jedis getInstance() {
        try{
        return jedisPool.getResource();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void close() {
        jedisPool.close();
    }

    public static boolean cacheContainsKey(String key) {
        boolean hasKey;
        try (Jedis jedis = getInstance()) {
            hasKey = jedis.exists(key);
        }
        return hasKey;
    }

    public static <T> T getObject(Class<T> clazz, String key) {
        T object = null;
        try {
            String objectData = get(key);
          //  object = JsonHelper.getObjectFromJson(clazz, objectData);

        } catch (Exception ex) {
            Logger.getLogger(JedisHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return object;
    }
    
     public static <T> T getObject(String key) {
        T object = null;
        try {
            String objectData = get(key);
          //  object = JsonHelper.getObjectFromJson(clazz, objectData);

        } catch (Exception ex) {
            Logger.getLogger(JedisHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return object;
    }

    public static String get(String key) {
        String data;

        try (Jedis jedis = getInstance()) {
            data = jedis.get(key);
        }
        return data;
    }

    public static String set(String key, String value) {
        String result;
        try (Jedis jedis = getInstance()) {
            result = jedis.set(key, value);
        }
        return result;
    }

    public static <T> String setObject(String key, T object) {
        String result = null;

        try {
       //     result = set(key, JsonHelper.convertObjectToJson(object));
        } catch (Exception ex) {
            Logger.getLogger(JedisHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public static <T> List<T> getList(Class<T> clazz, String key) {
        List<T> result = null;
        try {
            Jedis jedis = getInstance();

            List<String> list = jedis.lrange(key, 0, Long.MAX_VALUE);
           

            if (list != null && !list.isEmpty()) {

                result = list.stream().map(s -> {
                    T object = null;
                    try {
                     //   object = JsonHelper.getObjectFromJson(clazz, s);
                    } catch (Exception ex) {
                        Logger.getLogger(JedisHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return object;
                }).collect(Collectors.toList());
            }

        } catch (Exception ex) {
            Logger.getLogger(JedisHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
