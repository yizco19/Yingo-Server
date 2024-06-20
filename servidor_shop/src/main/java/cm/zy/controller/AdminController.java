package cm.zy.controller;

import cm.zy.pojo.Admin;
import cm.zy.pojo.Result;
import cm.zy.pojo.StatisticsDTO;
import cm.zy.pojo.User;
import cm.zy.service.AdminService;
import cm.zy.service.UserService;
import cm.zy.utils.PasswordUtil;
import cm.zy.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Clase controlador para gestionar las operaciones de administradores.
 * Autor: Zhi Yang
 * Fecha de Creación: 22/04/2024
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    /**
     * Lista todos los administradores.
     *
     * @return una lista de administradores
     */
    @GetMapping("/list")
    public List<Admin> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        String username = (String) map.get("username");
        User user = userService.findById(id);
        if (user == null) {
            return Collections.emptyList();
        }else{
            List<Admin> admins = adminService.list();
            return admins;

        }


    }
    /**
     * Actualiza un administrador.
     *
     * @param admin el administrador a actualizar
     * @return un Result indicando éxito
     */
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin){

        adminService.update(admin);
        return Result.success();
    }

    /**
     * Actualiza el avatar de un administrador.
     *
     * @param avatarUrl la URL del nuevo avatar
     * @return un Result indicando éxito
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam String avatarUrl){

        adminService.updateAvatar(avatarUrl);
        return Result.success();
    }
    /**
     * Obtiene la información del usuario actual.
     *
     * @return un Result con la información del usuario
     */
    @RequestMapping("/userInfo")
    public Result userInfo(){

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        Admin admin = adminService.findById(id);
        return Result.success(admin);
    }

    /**
     * Actualiza la contraseña de un administrador.
     *
     * @param params los parámetros que contienen las contraseñas
     * @param token el token de autorización
     * @return un Result indicando éxito o error
     */
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params ,@RequestHeader(name = "Authorization" ) String token){
        //1.
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");
        String rePassword = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePassword)) {
            return Result.error("contraseña vacía");
        }
        // comprobar contraseña old si es correcta
        // usa userService para buscar la contraseña del usuario y comparar la contraseña
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        Admin loginAdmin = adminService.findByUsername(username);
        if (!loginAdmin.getPassword().equals(PasswordUtil.encodePassword(oldPwd))) {
            return Result.error("contraseña antigente");
        }
        if (!newPwd.equals(rePassword)) {
            return Result.error("contraseñas no coinciden");
        }
        // eliminar contraseña antigente de redis
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        // 2. actualizar contraseña
        userService.updatePwd(newPwd);
        return Result.success();


    }
    /**
     * Obtiene las estadísticas del sistema.
     *
     * @return un Result con las estadísticas
     */
    @GetMapping("/statistics")
    public Result<StatisticsDTO> getStatistics() {
        StatisticsDTO statisticsDTO = adminService.getStatistics();
        return Result.success(statisticsDTO);
    }

}
