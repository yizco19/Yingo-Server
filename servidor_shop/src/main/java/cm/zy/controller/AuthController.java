package cm.zy.controller;

import cm.zy.constant.UserConstant;
import cm.zy.pojo.Admin;
import cm.zy.pojo.Result;
import cm.zy.pojo.User;
import cm.zy.service.AdminService;
import cm.zy.service.UserService;
import cm.zy.utils.JwtUtil;
import cm.zy.utils.PasswordUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * Controlador que maneja las operaciones de autenticación, como registro e inicio de sesión.
 * Este controlador proporciona funcionalidades para que los usuarios se registren y se autentiquen.
 * Los usuarios pueden registrarse como clientes o administradores.
 * Se utiliza JWT (JSON Web Token) para generar tokens de autenticación válidos.
 * Los tokens generados se almacenan en Redis durante una hora para su posterior verificación.
 * Autor: Zhi Yang
 * Fecha de Creación: 25/03/2024
 *
 */
@RestController
@RequestMapping
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * Registra un nuevo usuario o administrador.
     *
     * @param username el nombre de usuario
     * @param email el correo electrónico
     * @param password la contraseña
     * @param role el rol (cliente o administrador)
     * @return un Result indicando éxito o error
     */
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{4,16}$" ) String username , String email, @Pattern(regexp = "^\\S{4,16}$" )  String password,
                           @org.jetbrains.annotations.NotNull @RequestHeader(value = "rol") String role){
        if(role.equals(UserConstant.CLIENT)){
            // buscar usuario
            User user = userService.findByUsername(username);
            if (user != null){
                return Result.error("El nombre de usuario ya existe");
            } else {
                userService.register(username, email, password);
                return Result.success();
            }
        }
        if (role.equals(UserConstant.ADMIN)){
            // buscar usuario
            Admin admin = adminService.findByUsername(username);
            if (admin != null){
                return Result.error("El nombre de usuario ya existe");
            } else {
                adminService.register(username, email, password);
                return Result.success();
            }
        }
        return Result.error("No tiene acceso");


    }
    /**
     * Autentica a un usuario o administrador y genera un token JWT.
     *
     * @param usernameOrEmail el nombre de usuario o correo electrónico
     * @param password la contraseña
     * @param role el rol (cliente o administrador)
     * @return un Result indicando éxito o error con el token generado
     */
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String usernameOrEmail,
                        @Pattern(regexp = "^\\S{5,16}$") String password,
                        @RequestHeader(value = "rol") String role) {
        if (UserConstant.CLIENT.equals(role)) {
            User user = userService.findByEmailOrUsername(usernameOrEmail);
            if (user == null) {
                return Result.error("El nombre de usuario o el correo electrónico no existe");
            }
            if (PasswordUtil.matchPassword(user.getPassword(), password)) {
                return Result.error("Contraseña incorrecta");
            }
            return generateToken(user.getId(), user.getUsername());
        } else if (UserConstant.ADMIN.equals(role)) {
            Admin admin = adminService.findByEmailOrUsername(usernameOrEmail);
            if (admin == null) {
                return Result.error("El nombre de cliente o el correo electrónico no existe");
            }
            if (!checkPassword(admin.getPassword(), password)) {
                return Result.error("Contraseña incorrecta");
            }
            return generateToken(admin.getId(), admin.getUsername());
        } else {
            return Result.error("Rol no válido");
        }



    }
    /**
     * Verifica la contraseña ingresada con la almacenada.
     *
     * @param storedPassword la contraseña almacenada
     * @param inputPassword la contraseña ingresada
     * @return true si las contraseñas coinciden, false de lo contrario
     */
    private boolean checkPassword(String storedPassword, String inputPassword) {
        String hashedInputPassword = PasswordUtil.encodePassword(inputPassword);
        return storedPassword.equals(hashedInputPassword);
    }

    /**
     * Genera un token JWT para el usuario autenticado.
     *
     * @param id el ID del usuario
     * @param username el nombre de usuario
     * @return un Result con el token generado
     */
    private Result generateToken(Integer id, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);
        String token = JwtUtil.genToken(claims);

        stringRedisTemplate.opsForValue().set(token, token, 1, TimeUnit.HOURS);

        return Result.success(token);
    }

}
