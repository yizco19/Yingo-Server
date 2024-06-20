package cm.zy.mapper;

import cm.zy.pojo.Offer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OfferMapper {
    @Insert("INSERT INTO offer(title, description, discount, start_date, end_date) VALUES(#{title}, #{description}, #{discount}, #{startDate}, #{endDate})")
    void add(Offer offer);

    @Update("UPDATE offer SET title=#{title}, description=#{description}, discount=#{discount}, start_date=#{startDate}, end_date=#{endDate} WHERE id=#{id}")
    void update(Offer offer);

    @Select("select * from offer where id = #{id}")
    List<Offer> detail(Integer id);

    @Delete("delete from offer where id = #{id}")
    void delete(Integer id);

    @Select("SELECT * FROM offer WHERE id = #{id}")
    Offer getOfferById(Integer id);

    @Select("select * from offer")
    List<Offer> list();

}