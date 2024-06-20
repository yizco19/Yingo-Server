package cm.zy.service;

import cm.zy.pojo.Order;
import cm.zy.pojo.OrderItem;

import java.util.List;

public interface OrderService {

    void add(Order order);

    void update(Order order);

    List<Order> list(Integer status);

    Order detail(Integer id);

    void delete(Integer id);

    void updateStatus(Integer id, Integer status);

    List<Order> getAllOrders();

    List<OrderItem> detailItems(Integer id);
}
