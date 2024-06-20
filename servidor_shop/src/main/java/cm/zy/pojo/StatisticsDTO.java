package cm.zy.pojo;

import lombok.Data;

import java.util.List;

@Data
public class StatisticsDTO {
    private long userCount;
    private long productCount;
    private long orderCount;
    private double totalRevenue;
    private List<Double> monthlyRevenue;

    public StatisticsDTO(long userCount, long productCount, long orderCount, double totalRevenue, List<Double> monthlyRevenue) {
        this.userCount = userCount;
        this.productCount = productCount;
        this.orderCount = orderCount;
        this.totalRevenue = totalRevenue;
        this.monthlyRevenue = monthlyRevenue;
    }
}
