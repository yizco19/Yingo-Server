package cm.zy.mapper;

import cm.zy.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("insert into category(category_name,category_alias)  values(#{categoryName},#{categoryAlias})")
    void add(Category category);

    @Select("select * from category")
    List<Category> list();

    @Update("update category set category_name = #{categoryName},category_alias = #{categoryAlias} where id = #{id}")
    void update(Category category);

    @Delete("delete from category where id = #{id}")
    void delete(Integer id);

    @Select("select count(*) from product where category_id = #{id}")
    int checkProductsAssociated(Integer id);
}
