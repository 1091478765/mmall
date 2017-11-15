package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

import javax.servlet.http.HttpSession;

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
     * @param
     * @param username
     * @return
     */
    ServerResponse<String> forgetCheckQuestion(String username, String answer, String question);

    /**
     * 忘记密码重置密码
     * @param username
     * @param newPassword
     * @param token
     * @return
     */
    ServerResponse<String> forgetResetPassword(String username, String newPassword, String token);

    /**
     * 重置密码
     * @param session
     * @param newPassword
     * @param oldPassword
     * @return
     */
    ServerResponse<String> resetPassword(HttpSession session, String newPassword, String oldPassword);

    /**
     * 更新用户信息
     * @param session
     * @param user
     * @return
     */
    ServerResponse<User> updateInformation(HttpSession session, User user);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    ServerResponse<User> getInformation(Integer id);
}
