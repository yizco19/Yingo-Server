package cm.zy.mapper;

import cm.zy.pojo.Favorite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FavoriteMapper {
    @Insert("insert into favorite(user_id,product_id)  values(#{userId},#{productId})")
    void add(Favorite favorite);
    @Delete("delete from favorite where product_id = #{productId} and user_id = #{userId}")
    void delete(Integer productId,Integer userId);
    @Select("select * from favorite")
    List<Favorite> list();

    @Delete("delete from favorite where user_id = #{userId}")
    void deleteAll(Integer userId);
}
