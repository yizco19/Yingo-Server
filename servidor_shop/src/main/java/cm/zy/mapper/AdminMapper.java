package cm.zy.mapper;

import cm.zy.pojo.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface AdminMapper {

    @Select("select * from admin where username = #{username}")
    Admin findByUsername(String username);

    @Insert("insert into admin(username,nickname,email,password)" +
            " values(#{username},#{username},#{email},#{md5Password})")

    void add(String username, String email, String md5Password);

    @Select("select * from admin")
    List<Admin> list();

    @Update("update admin set username = #{username},nickname = #{nickname},email = #{email} where id = #{id}")
    void update(Admin admin);


    @Update("update admin set user_pic = #{avatarUrl} where id = #{id}")
    void updateAvatar(String avatarUrl, Integer id);

    @Update("update admin set password = #{md5String} where id = #{id}")
    void updatePwd(String md5String, Integer id);

    @Select("select * from admin where email = #{email}")
    Admin findByEmail(String email);

    @Select("select * from admin where id = #{userId}")
    Admin findById(Integer userId);

    @Select("select * from admin where email = #{email} or username = #{username}")
    Admin findByEmailOrUsername(String emailOrUsername);


    @Select("select * from admin where username = #{username} and id = #{id}")
    Admin findByUsernameAndId(String username ,Integer id);

    @Select("SELECT COUNT(*) FROM user")
    long countUsers();

    @Select("SELECT COUNT(*) FROM product")
    long countProducts();

    @Select("SELECT COUNT(*) FROM `order`")
    long countOrders();

    @Select("SELECT IFNULL(SUM(total), 0) FROM `order`")
    double totalRevenue();

    @Select("""
        SELECT IFNULL(SUM(total), 0) 
        FROM `order` 
        WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) 
        GROUP BY YEAR(created_at), MONTH(created_at)
    """)
    List<Double> monthlyRevenue();
}
