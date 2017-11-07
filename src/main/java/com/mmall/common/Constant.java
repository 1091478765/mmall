package com.mmall.common;

/**
 * Created by 刘璐 on 2017/11/2.
 */
public class Constant {

    public static final String CURRENT_USER = "currentUser";

    public static final String USER_NAME ="userName";

    public static final String EMAIL = "email";

    public interface Role{
        int ROLE_CUSTOMER = 0 ; //普通用户
        int ROLE_ADMIN = 1 ;//管理员
    }
}
