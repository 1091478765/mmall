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

    /**
     * 注册用户检查
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkVlidate(String str, String type);


    /**
     * 用户忘记密码获取问题
     * @param username
     * @return
     */
    ServerResponse<String> forGetQuestion(String username);

    /**
     * 验证用户名
     *
     * @param s
     * @param username
     * @return
     */
    ServerResponse<String> forgetCheckQuestion(String username, String answer, String question);
}
