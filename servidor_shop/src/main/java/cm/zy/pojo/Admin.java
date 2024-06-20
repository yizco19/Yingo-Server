package cm.zy.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Admin {
    @NotNull
    private Integer id;                   // ID principal
    private String username;              // Nombre de usuario
    private String nickname;              // Apodo o sobrenombre
    @NotEmpty
    @Email
    private String email;                 // Correo electrónico
    @JsonIgnore
    private String password;              // Contraseña del usuario
    private Integer rol;                  // Rol del usuario
    private String userPic;               // Dirección de la imagen de perfil del usuario
}
