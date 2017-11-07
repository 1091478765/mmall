package com.mmall.controller.protal;

import com.mmall.common.Constant;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 用户登录相关控制器
 * Created by 刘璐 on 2017/11/2.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do")
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = userService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Constant.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 退出
     * @param session
     * @return
     */
    @RequestMapping(value = "logout.do")
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Constant.CURRENT_USER);
        return ServerResponse.createBySuccessMessage("登出成功");
    }

    /**
     * 用户退出
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do")
    @ResponseBody
    public ServerResponse<User> register(User user){
        return userService.register(user);
    }

    /**
     * 用户输入验证
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "checkVlidate.do")
    @ResponseBody
    public ServerResponse<String> checkVlidate(String str,String type){
        return userService.checkVlidate(str ,type);
    }

    /**
     * 获取登录用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "getUserInfo.do")
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User)session.getAttribute(Constant.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return ServerResponse.createBySuccessData(user);
    }

    /**
     * 根据用户名查询偶用户问题
     * @param username
     * @return
     */
    @RequestMapping(value = "forgetGetQuestion.do")
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username) {
        return userService.forGetQuestion(username);
    }

    @RequestMapping(value = "forgetCheckQuestion.do")
    @ResponseBody
    public ServerResponse<String> forgetCheckQuestion(String username, String answer,String question) {
        return userService.forgetCheckQuestion(username,answer,question);
    }

}
