package cm.zy.service.impl;

import cm.zy.mapper.CartMapper;
import cm.zy.mapper.UserMapper;
import cm.zy.pojo.Result;
import cm.zy.pojo.User;
import cm.zy.service.UserService;
import cm.zy.utils.PasswordUtil;
import cm.zy.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CartMapper cartMapper;
    @Override
    public User findByUsername(String username) {
        User u =userMapper.findByUsername(username);
        return u;
    }

    @Override
    public void register(String username, String email,String password) {
        // codificar password
        String md5Password = PasswordUtil.encodePassword(password);
        // registrar usuario
        userMapper.add(username,email,md5Password);
        User user = userMapper.findByEmail(email);
        if(user!=null){
            //crea una carrito unico
            cartMapper.add(user.getId(),new Date());
        }




    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    // actualizar imagen de perfil
    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);

    }

    @Override
    public void updatePwd(String newPassword) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(PasswordUtil.encodePassword(newPassword),id);
    }

    @Override
    public User findByEmail(String email) {
        User userEmail = userMapper.findByEmail(email);
        return userEmail;
    }
    @Override
    public User findById(Integer userId) {
        User user = userMapper.findById(userId);
        return user;
    }

    @Override
    public User findByEmailOrUsername(String emailOrUsername) {
        User user = userMapper.findByEmailOrUsername(emailOrUsername);
        return user;
    }

    @Override
    public Result<Double> redeemCode(String redeemCode) {
        //comprubar si el código coincide
        if(redeemCode.equals("123456")){
            Map<String, Object> map = ThreadLocalUtil.get();
            Integer id = (Integer) map.get("id");
            userMapper.redeemCode(100.00,id);
            return Result.successMessage("Ha canjado el código de 100.00€",100.00);
        }
        return Result.error("El código no coincide");
    }

    @Override
    public List<User> list() {
        List<User> list = userMapper.list();
        return list;
    }

}
