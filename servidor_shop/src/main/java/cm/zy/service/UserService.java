package cm.zy.service;


import cm.zy.pojo.Result;
import cm.zy.pojo.User;

import java.util.List;

public interface UserService {

    // buscar usuario por nombre de usuario
    User findByUsername(String username) ;

    // registrar usuario
    void register(String username ,String email,String password);

    // actualizar usuario
    void update(User user);

    /**
     *     actualizar imagen de perfil
     * @param avatarUrl
     */
    void updateAvatar(String avatarUrl);


    /**
     * @param newPassword
     */
    void updatePwd(String newPassword);

    User findByEmail(String email);

    List<User> list();

    User findById(Integer userId);

    User findByEmailOrUsername(String emailOrUsername);

    Result<Double> redeemCode(String activateCode) ;
}
