package com.mmall.service.impl;

import com.mmall.common.Constant;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.UuidUtil;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

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
}
