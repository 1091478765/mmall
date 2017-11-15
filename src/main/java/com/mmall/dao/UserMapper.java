package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 检查用户名
     * @param userName
     * @return
     */
    int checkUserName(String userName);

    /**
     * 根据用户名密码查询用户
     * @param username
     * @param password
     * @return
     */
    User selectLogin(@Param("username") String username,@Param("password") String password);

    /**
     *检查邮箱是否存在
     * @param email
     * @return
     */
    int checkEmail(String email);

    /**
     * 根据用户名查询用户问题
     * @param username
     * @return
     */
    String selectQuestionByUserName(@Param("username") String username);

    /**
     * 根据用户名，问题，答案查询
     * @param username
     * @param answer
     * @param question
     * @return
     */
    int forgetCheckQuestion(@Param("username") String username, @Param("answer")String answer, @Param("question")String question);

    /**
     * 根据用户名更新密码
     * @param username
     * @return
     */
    int updatePasswordByUsername(@Param("username") String username,@Param("newPassword") String newPassword);

    /**
     * 检查密码
     * @param username
     * @param oldPassword
     * @return
     */
    int checkPassword(@Param("username") String username, @Param("oldPassword") String oldPassword);

    /**
     * 通过用户名检查邮箱是否重复
     * @param email
     * @param id
     * @return
     */
    int checkEmailByUserId(@Param("email") String email, @Param("id") Integer id);

}