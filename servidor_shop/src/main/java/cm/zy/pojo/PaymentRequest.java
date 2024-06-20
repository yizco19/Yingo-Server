package cm.zy.pojo;

import lombok.Data;

@Data
public class PaymentRequest {
    private Integer type;
    private Double amount;
    private String phone;
    private String address;
    private String name;
}
