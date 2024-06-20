package cm.zy.pojo;

import lombok.Data;

@Data
public class CartItem {

    private Integer id;
    private Integer cartId;
    private Integer productId;
    private Integer quantity;

}
