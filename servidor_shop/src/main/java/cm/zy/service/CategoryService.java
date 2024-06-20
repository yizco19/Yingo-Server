package cm.zy.service;

import cm.zy.pojo.Category;

import java.util.List;

public interface CategoryService {

    void add(Category category);

    List<Category> list();

    void update(Category category);

    void delete(Integer id);

    boolean checkProductsAssociated(Integer id);
}
