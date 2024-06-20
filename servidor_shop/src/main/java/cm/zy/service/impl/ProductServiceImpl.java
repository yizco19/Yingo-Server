package cm.zy.service.impl;

import cm.zy.mapper.ProductMapper;
import cm.zy.pojo.PageBean;
import cm.zy.pojo.Product;
import cm.zy.service.ProductService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Override
    public void add(Product product) {
        productMapper.add(product);

    }

    @Override
    public PageBean<Product> list(Integer pageNum, Integer pageSize, Integer categoryId, String name) {
        // crea una instancia de PageBean
        PageBean<Product> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<Product> as = productMapper.list(categoryId,name);
        Page<Product> p =(Page<Product>)as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

    @Override
    public List<Product> list() {

        return productMapper.list();
    }

    @Override
    public Product detail(Integer id) {
        Product product = productMapper.detail(id);
        return product;
    }

    @Override
    public void delete(Integer id) {

        productMapper.delete(id);
    }

    @Override
    public void update(Product product) {

        productMapper.update(product);

    }

    @Override
    public void updateProductVisibility(int id, int visible) {

        productMapper.updateProductVisibility(id, visible);

    }
}
