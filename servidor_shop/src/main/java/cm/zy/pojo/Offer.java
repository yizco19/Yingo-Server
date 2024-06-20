package cm.zy.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Offer {

    private Integer id;
    private String title;
    private String description;
    private Double discount;
    private Date startDate;
    private Date endDate;

}
