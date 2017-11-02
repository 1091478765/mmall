package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * Created by 刘璐 on 2017/11/2.
 */
public interface IUserService {

    /**
     * 用户登录接口
     * @return
     */
    public ServerResponse<User> login(String username,String password);

    /**
     * 用户注册
     * @param user
     * @return
     */
    public ServerResponse<User> register(User user);

}
