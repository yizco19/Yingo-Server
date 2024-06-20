package cm.zy.controller;

import cm.zy.constant.UserConstant;
import cm.zy.pojo.Admin;
import cm.zy.pojo.Result;
import cm.zy.pojo.User;
import cm.zy.service.AdminService;
import cm.zy.service.UserService;
import cm.zy.utils.JwtUtil;
import cm.zy.utils.PasswordUtil;
import cm.zy.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Clase controlador para gestionar las operaciones de usuarios.
 * Autor: Zhi Yang
 * Fecha de Creación: 25/03/2024
 */
@RestController
@RequestMapping("/user")
@CrossOrigin//Permite solicitudes de origen cruzado:
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    /**
     * Registra un nuevo usuario.
     *
     * @param username el nombre de usuario
     * @param email el correo electrónico
     * @param password la contraseña
     * @return un Result indicando éxito o error
     */

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{4,16}$" ) String username , String email, @Pattern(regexp = "^\\S{4,16}$" )  String password){
        // buscar usuario
        User user = userService.findByUsername(username);
        if (user != null){
            return Result.error("El nombre de usuario ya existe");
        } else {
            userService.register(username, email, password);
            return Result.success();
        }

    }
    /**
     * Inicia sesión de usuario.
     *
     * @param usernameOrEmail el nombre de usuario o correo electrónico
     * @param password la contraseña
     * @return un Result con el token de autenticación si tiene éxito, o un mensaje de error si falla
     */
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$" ) String usernameOrEmail,@Pattern(regexp = "^\\S{5,16}$" ) String password) {

            // Buscar usuario con nombre de usuario o email
            User loginUser = userService.findByEmail(usernameOrEmail);
            User loginUser2 = userService.findByUsername(usernameOrEmail);

            if (loginUser == null && loginUser2 == null) {
                return Result.error("El nombre de usuario o el correo electrónico no existe");
            }

            if (loginUser == null) {
                loginUser = loginUser2;
            }

            // Verificar la contraseña
            String hashedPassword = PasswordUtil.encodePassword(password);
            if (loginUser.getPassword().equals(hashedPassword)) {
                // Generar token JWT
                Map<String, Object> claims = new HashMap<>();
                claims.put("id", loginUser.getId());
                claims.put("username", loginUser.getUsername());
                String token = JwtUtil.genToken(claims);

                // Almacenar token en Redis
                ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
                operations.set(token, token, 1, TimeUnit.HOURS);

                return Result.success(token);
            } else {
                return Result.error("Contraseña incorrecta");
            }
    }


    @GetMapping("/list")
    public Result<List<User>> getAll() {

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        String username = (String) map.get("username");
        Admin admin = adminService.findByUsernameAndId(username, id); // adminService.findByEmailOrUsername()
        if(admin != null && UserConstant.ROLE_ADMIN.equals(admin.getRol())) {
            List<User> list = userService.list();
            return Result.success(list);
        }
            return Result.error("No tiene acceso");

    }
    /**
     * Obtiene la información del usuario actual.
     *
     * @return un Result que contiene la información del usuario si tiene éxito, o un mensaje de error si falla
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user){

        userService.update(user);
        return Result.success();
    }
    /**
     * Actualiza el avatar de un usuario.
     *
     * @param avatarUrl la URL del nuevo avatar
     * @return un Result indicando éxito o error
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam String avatarUrl){

        userService.updateAvatar(avatarUrl);
        return Result.success();
    }
    /**
     * Obtiene la información del usuario actual.
     *
     * @return un Result que contiene la información del usuario si tiene éxito, o un mensaje de error si falla
     */
    @RequestMapping("/userInfo")
    public Result userInfo(){

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        User user = userService.findById(id);
        return Result.success(user);
    }
    /**
     * Actualiza la contraseña del usuario.
     *
     * @param params los parámetros que contienen la contraseña antigua y la nueva contraseña
     * @param token el token de autenticación
     * @return un Result indicando éxito o error
     */
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params ,@RequestHeader(name = "Authorization" ) String token){
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");
        String rePassword = params.get("rePwd");
        // Verificar si las contraseñas son válidas
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePassword)) {
            return Result.error("contraseña vacía");
        }
        //comprobar el tamaño de caracteres de la contraseña
        if (!newPwd.equals(rePassword)) {
            return Result.error("contraseñas no coinciden");
        }
        //comprobar el contraseña tiene mayor de 5 caracteres
        if (newPwd.length() < 5) {
            return Result.error("contraseña demasiado corta");
        }
        // Comprobar si la contraseña antigua es correcta
        // usa userService para buscar la contraseña del usuario y comparar la contraseña
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        if (!loginUser.getPassword().equals(PasswordUtil.encodePassword(oldPwd))) {
            return Result.error("contraseña antigente");
        }

        // Eliminar la contraseña antigua de Redis
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        // Actualizar la contraseña
        userService.updatePwd(newPwd);
        // Devolver un Result con el resultado
        return Result.success();


    }
    /**
     * Activa un código de activación.
     *
     * @param activateCode el código de activación
     * @return un Result indicando éxito o error
     */
    @PutMapping("/activateCode")
    public Result<Double> activateCode(@RequestParam String activateCode){


        var result = userService.redeemCode(activateCode);
        if(result.getCode() == 0) {
            return result;
        }
        return Result.error("El código no coincide");
    }


}