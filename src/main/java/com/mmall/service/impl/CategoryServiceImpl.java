package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by 刘璐 on 2017/11/16.
 */
@Service
public class CategoryServiceImpl implements ICategoryService{

    //private static logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryDao;

    @Override
    public ServerResponse addCategory(String categoryName,Integer parentId){

        if (StringUtils.isBlank(categoryName)  || parentId == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Category category = new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(true);
        int num = categoryDao.insert(category);
        if (num == 0){
            return ServerResponse.createByErrorMessage("保存种类失败");
        }
        return ServerResponse.createBySuccessData(category);
    }

    @Override
    public ServerResponse setCategoryName(String categoryName, Integer categoryId) {
        if (StringUtils.isBlank(categoryName) || categoryId ==null){
            return ServerResponse.createByErrorMessage("更新参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setId(categoryId);
        category.setUpdateTime(new Date());
        int num = categoryDao.updateByPrimaryKeySelective(category);
        if (num == 0){
            return ServerResponse.createByErrorMessage("更新种类名称失败");
        }
        return ServerResponse.createBySuccessData(category);
    }

    @Override
    public ServerResponse<List<Category>> getChildredParallelCategory(Integer categoryId) {
        List<Category> categories = categoryDao.selectChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categories)){
            ServerResponse.createByErrorMessage("未找到该种类的子分类");
        }
        return ServerResponse.createBySuccessData(categories);
    }

    @Override
    public ServerResponse getCategoryAndDeepChildrenCategory(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categoryId,categorySet);
        List<Integer> list= Lists.newArrayList();
        if (categoryId != null){
            for(Category c : categorySet){
                 list.add(c.getId());
            }
        }
        return ServerResponse.createBySuccessData(list);
    }

    private Set<Category> findChildCategory(Integer categoryId,Set<Category> categorySet){
        Category category = categoryDao.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);
        }
        List<Category> categories = categoryDao.selectChildrenByParentId(categoryId);
        for (Category c : categories) {
            findChildCategory(c.getId(),categorySet);
        }
        return categorySet;
    }
}
