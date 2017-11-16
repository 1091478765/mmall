package com.mmall.service.impl;

import com.mmall.common.Constant;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCatch;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;

/**
 * Created by 刘璐 on 2017/11/2.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;



    @Override
    public ServerResponse<User> login(String username, String password) {
        int num = userMapper.checkUserName(username);
        if (num == 0 ){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = userMapper.selectLogin(username,password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    @Override
    public ServerResponse<User> register(User user) {
        int num = userMapper.checkUserName(user.getUsername());
        if (num != 0 ){
            return ServerResponse.createByErrorMessage("用户名已存在");
        }
        num = userMapper.checkEmail(user.getEmail());
        if(num > 0){
            return ServerResponse.createByErrorMessage("邮箱已经存在");
        }
        user.setRole(Constant.Role.ROLE_CUSTOMER);
        String salt = UuidUtil.getUuid();
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        if (userMapper.insert(user) == 0){
            return ServerResponse.createByErrorMessage("用户注册失败");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccessData(user);
    }

    @Override
    public ServerResponse<String> checkVlidate(String str, String type) {
        int num = 0;
        if (Constant.USER_NAME.equals(type)){
            num = userMapper.checkUserName(str);
            if (num > 0 ){
                return ServerResponse.createByErrorMessage("用户名已存在");
            }
        }else if(Constant.EMAIL.equals(type)){
            num = userMapper.checkEmail(str);
            if(num > 0){
                return ServerResponse.createByErrorMessage("邮箱已经存在");
            }
        }else{
            return ServerResponse.createByErrorMessage("输入参数有误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> forGetQuestion(String username) {
        //校验用户名
        int num = userMapper.checkUserName(username);
        if (num <= 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String question = userMapper.selectQuestionByUserName(username);
        if (question == null && question == null){
            return ServerResponse.createByErrorMessage("找回密码问题为空");
        }
        return ServerResponse.createBySuccessData(question);
    }

    @Override
    public ServerResponse<String> forgetCheckQuestion(String username, String answer, String question) {
        int num = userMapper.forgetCheckQuestion(username,answer,question);
        if (num > 0){
            String token = UuidUtil.getUuid();
            TokenCatch.setKey(TokenCatch.TOKEN_PREFIX+username,token);
            return ServerResponse.createBySuccessData(token);
        }
        return ServerResponse.createByErrorMessage("问题答案有误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String newPassword, String forgetToken) {
        if (StringUtils.isEmpty(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误，缺少token");
        }
        int num = userMapper.checkUserName(username);
        if (num <= 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String token = TokenCatch.getKey(TokenCatch.TOKEN_PREFIX+username);
        if (StringUtils.isEmpty(token)){
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }
        if (StringUtils.equals(forgetToken,token)){
            newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
            int result = userMapper.updatePasswordByUsername(username,newPassword);
            if (result > 0){
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
            return ServerResponse.createByErrorMessage("重置密码失败");
        }
        return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码token");
    }

    @Override
    public ServerResponse<String> resetPassword(HttpSession session, String newPassword, String oldPassword) {
        User user = (User)session.getAttribute(Constant.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String username =  user.getUsername();
        int num  = userMapper.checkPassword(username,DigestUtils.md5DigestAsHex(oldPassword.getBytes()));
        if(num == 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        int result = userMapper.updateByPrimaryKeySelective(user);
        if (result > 0){
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("重置密码失败");
    }

    @Override
    public ServerResponse<User> updateInformation(HttpSession session, User user) {
       int num =  userMapper.checkEmailByUserId(user.getEmail(),user.getId());
       if (num>0){
           return ServerResponse.createByErrorMessage("邮箱已存在，请更换邮箱后重试");
       }
       int result = userMapper.updateByPrimaryKeySelective(user);
       if (result ==0){
           return ServerResponse.createByErrorMessage("更新用户信息失败");
       }
       User user1 = userMapper.selectByPrimaryKey(user.getId());
       return ServerResponse.createBySuccessData(user1);
    }

    @Override
    public ServerResponse<User> getInformation(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        user.setPassword("");
        return ServerResponse.createBySuccessData(user);
    }

    @Override
    public ServerResponse checkAdminRole(User user) {
        if (user != null && user.getRole().intValue() ==Constant.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}
