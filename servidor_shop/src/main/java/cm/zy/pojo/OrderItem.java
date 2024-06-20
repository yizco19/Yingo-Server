package cm.zy.pojo;

import lombok.Data;

@Data
public class OrderItem {

    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Double price;
}
