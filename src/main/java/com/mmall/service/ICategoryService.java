package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * Created by 刘璐 on 2017/11/16.
 */
public interface ICategoryService {


    /***
     * 添加种类
     * @param categoryName
     * @param parentId
     * @return
     */
    ServerResponse addCategory(String categoryName, Integer parentId);

    /**
     * 修改种类名称
     * @param categoryName
     * @param categoryId
     * @return
     */
    ServerResponse setCategoryName(String categoryName, Integer categoryId);

    /**
     * 获取子节点，不进行递归
     * @param categoryId
     * @return
     */
    ServerResponse getChildredParallelCategory(Integer categoryId);

    /**
     * 获取所有子节点
     * @param categoryId
     * @return
     */
    ServerResponse getCategoryAndDeepChildrenCategory(Integer categoryId);
}
