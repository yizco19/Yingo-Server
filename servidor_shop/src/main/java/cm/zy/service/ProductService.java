package cm.zy.service;

import cm.zy.pojo.PageBean;
import cm.zy.pojo.Product;

import java.util.List;

public interface ProductService {
    void add(Product product);

    // recuperar una lista paginada de artículos basada en los parámetros proporcionados.
    PageBean<Product> list(Integer pageNum, Integer pageSize, Integer categoryId, String name);

    List<Product> list();

    Product detail(Integer id);

    void delete(Integer id);

    void update(Product product);

    void updateProductVisibility(int id, int visible);
}
