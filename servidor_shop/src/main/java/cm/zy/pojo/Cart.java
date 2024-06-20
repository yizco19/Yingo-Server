package cm.zy.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Cart {

    private Integer id;
    private Integer userId;
    private String  phone;
    private String  address;
    private Integer payType;
    private Date createdAt;
    private List<CartItem> cartItems;



}
