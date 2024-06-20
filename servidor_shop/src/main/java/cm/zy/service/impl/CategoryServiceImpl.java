package cm.zy.service.impl;

import cm.zy.constant.UserConstant;
import cm.zy.mapper.AdminMapper;
import cm.zy.mapper.CategoryMapper;
import cm.zy.mapper.UserMapper;
import cm.zy.pojo.Admin;
import cm.zy.pojo.Category;
import cm.zy.pojo.User;
import cm.zy.service.CategoryService;
import cm.zy.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public void add(Category category) {
        // agregar información de categoría
        categoryMapper.add(category);

    }

    @Override
    public List<Category> list() {
        List<Category> list = categoryMapper.list();
        return list;

    }

    @Override
    public void update(Category category) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId =(Integer) map.get("id");
        Admin admin =adminMapper.findById(userId);
        if(admin.getRol().equals(UserConstant.ROLE_ADMIN) || admin.getRol().equals(UserConstant.ROLE_PRODUCTOR)){
            categoryMapper.update(category);
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId =(Integer) map.get("id");
        Admin admin =adminMapper.findById(userId);
        if(admin.getRol().equals(UserConstant.ROLE_ADMIN) || admin.getRol().equals(UserConstant.ROLE_PRODUCTOR)){
            categoryMapper.delete(id);
        }
    }

    @Override
    public boolean checkProductsAssociated(Integer id) {

        int count = categoryMapper.checkProductsAssociated(id);
        if(count >= 0){
            return true;
        }
        return false;
    }
}
