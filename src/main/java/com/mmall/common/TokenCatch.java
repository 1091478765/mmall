package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 工具类，缓存token
 * Created by 刘璐 on 2017/11/7.
 */
public class TokenCatch {

    private static Logger logger = LoggerFactory.getLogger(TokenCatch.class);

    public static String TOKEN_PREFIX = "token_";

    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
                //默认的数据加载实现
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    /**
     * 设置缓存
     * @param key
     * @param value
     */
    public static void setKey(String key,String value){
        localCache.put(key,value);
    }

    /**
     * 根据key获取值
     * @param key
     * @return
     */
    public static String getKey(String key){
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)){
                return null;
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
