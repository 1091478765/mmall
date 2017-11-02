package com.mmall.util;

import java.util.UUID;

/**
 * Created by 刘璐 on 2017/11/2.
 */
public class UuidUtil {

    /**
     * 获取uuid
     * @return
     */
    public static String getUuid(){
       return UUID.randomUUID().toString().replaceAll("-","");
    }
}
