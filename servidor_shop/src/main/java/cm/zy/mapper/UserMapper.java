package cm.zy.mapper;

import cm.zy.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface UserMapper {

    // buscar usuario por nombre de usuario
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);

    @Insert("insert into user(username,nickname,email,password)" +
            " values(#{username},#{username},#{email},#{password})")
    void add(String username,String email, String password);

    @Select("select * from user")
    List<User> list();


    void update(User user);

    @Update("update user set user_pic = #{avatarUrl} where id = #{id}")
    void updateAvatar(String avatarUrl ,Integer id);

    @Update("update user set password = #{md5String} where id = #{id}")
    void updatePwd(String md5String, Integer id);

    @Select("select * from user where email = #{email}")
    User findByEmail(String email);

    @Select("select * from user where id = #{userId}")
    User findById(Integer userId);

    @Select("select * from user where email = #{email} or username = #{username}")
    User findByEmailOrUsername(String emailOrUsername);

    @Select("select * from user where username = #{username} and id = #{id}")
    User findByUsernameAndId(Integer id, String username);

    @Update("UPDATE user SET wallet = wallet + #{wallet} WHERE id = #{id}")
    void redeemCode(@Param("wallet") Double wallet, @Param("id") Integer id);

}
