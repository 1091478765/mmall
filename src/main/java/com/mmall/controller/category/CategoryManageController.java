package com.mmall.controller.category;

import com.mmall.common.Constant;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author nevermore
 * @version V1.0
 * @Package : com.mmall.controller.category
 * @Description: TODO
 * @date 2017/11/16 15:10
 */

@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {


    public ServerResponse addCategory(HttpSession session, @RequestParam(value = "parentId",defaultValue = "0") int parentId){
        User user = (User)session.getAttribute(Constant.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户为登录");
        }
        return null;
    }
}
