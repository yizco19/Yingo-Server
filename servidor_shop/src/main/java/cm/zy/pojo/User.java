package cm.zy.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Data
public class User {
    @NotNull
    private int id;
    private String username;
    private String nickname;
    @NotEmpty
    @Email
    private String email;
    @JsonIgnore
    private String password;
    private String userPic;
    private char gender;
    private Date birthdate;
    private String address;
    private String phone;
    private Double wallet;

}
