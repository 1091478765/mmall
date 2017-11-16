package com.mmall.controller.manage;

import com.mmall.common.Constant;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by 刘璐 on 2017/11/16.
 */

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;

    /**
     *  添加种类
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping("addCategory.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,String categoryName,
                                      @RequestParam(value = "parentId",defaultValue = "0") Integer parentId){

        User user = (User)session.getAttribute(Constant.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户为登录，请登录后提交");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            return categoryService.addCategory(categoryName,parentId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    /**
     * 修改种类名称
     * @param session
     * @param categoryName
     * @param categoryId
     * @return
     */
    @RequestMapping("setCategoryName.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,String categoryName,Integer categoryId){
        User user = (User)session.getAttribute(Constant.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户为登录，请登录后提交");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            return categoryService.setCategoryName(categoryName,categoryId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }


    @RequestMapping(value = "getChildredParallelCategory")
    @ResponseBody
    public ServerResponse getChildredParallelCategory(HttpSession session,
                                                      @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User)session.getAttribute(Constant.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户为登录，请登录后提交");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            return categoryService.getChildredParallelCategory(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "getDeepChild")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,
                                                             @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User)session.getAttribute(Constant.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户为登录，请登录后提交");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            return categoryService.getCategoryAndDeepChildrenCategory(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }
}
