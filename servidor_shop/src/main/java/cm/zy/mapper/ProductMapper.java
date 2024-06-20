package cm.zy.mapper;

import cm.zy.pojo.CartItem;
import cm.zy.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Insert("insert into product (category_id,name,description,product_pic,price,visible) " +
            "values (#{categoryId},#{name},#{description},#{productPic},#{price},#{visible})")
    void add(Product product);

    List<Product> list( Integer categoryId, String name);

    List<Product> list();



    @Select("select * from product where id = #{id}")
    Product detail(Integer id);

    @Delete("delete from product where id = #{id}")
    void delete(Integer id);

    @Update("update product set name = #{name} , category_id = #{categoryId},description = #{description},product_pic = #{productPic},price = #{price},stock = #{stock},visible = #{visible},offer_id = #{offerId} where id = #{id}")
    void update(Product product);

    void updateStock(@Param("list") List<CartItem> cartItems);

    @Update("update product set visible = #{visibility} where id = #{id}")
    void updateProductVisibility(int id, int visibility);
}
