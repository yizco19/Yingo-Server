package cm.zy.service;

import cm.zy.pojo.Admin;
import cm.zy.pojo.Result;
import cm.zy.pojo.StatisticsDTO;
import cm.zy.pojo.User;

import java.util.List;

public interface AdminService {
    Admin findByUsername(String username) ;

    // registrar usuario
    void register(String username ,String email,String password);

    // actualizar client
    void update(Admin admin);

    void updateAvatar(String avatarUrl);


    void updatePwd(String newPassword);

    Admin findByEmail(String email);

    List<Admin> list();

    Admin findById(Integer userId);

    Admin findByEmailOrUsername(String emailOrUsername);

    Admin findByUsernameAndId(String username, Integer id);

    StatisticsDTO getStatistics();
}
