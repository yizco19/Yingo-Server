package cm.zy.service.impl;

import cm.zy.constant.OrderConstant;
import cm.zy.constant.UserConstant;
import cm.zy.mapper.OrderMapper;
import cm.zy.pojo.Admin;
import cm.zy.pojo.Order;
import cm.zy.pojo.OrderItem;
import cm.zy.service.AdminService;
import cm.zy.service.OrderService;
import cm.zy.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AdminService adminService;

    @Override
    public void add(Order order) {
        order.setStatus(OrderConstant.PENDING);
        orderMapper.addOrder(order); // Llama al método addOrder definido en el OrderMapper
        if(order.getOrderItems() != null && !order.getOrderItems().isEmpty()){
            List<OrderItem> orderItems = order.getOrderItems();
            //bucle para pone orderId en cada OrderItem

            for (OrderItem orderItem : orderItems) {
                orderItem.setOrderId(order.getId());
            }
            orderMapper.addOrderItems(orderItems);
    }

    }

    @Override
    public void update(Order order) {
        orderMapper.updateOrder(order);
        if(order.getOrderItems() != null && !order.getOrderItems().isEmpty()){
            orderMapper.updateOrderItems(order);
        }
    }

    @Override
    public List<Order> list(Integer status) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");

        List<Order> list = orderMapper.list(userId ,status);
        return list;
    }

    @Override
    public Order detail(Integer id) {
        Order order = orderMapper.detail(id);
        if(order != null){
            List<OrderItem> orderItems = orderMapper.detailItems(id);
            order.setOrderItems(orderItems);
            return order;
        }
        return null;

    }

    @Override
    public void delete(Integer id) {

        orderMapper.deleteItems(id);
        orderMapper.delete(id);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {

        orderMapper.updateStatus(id, status);
    }

    @Override
    public List<Order> getAllOrders() {
        //comprueba si es admin
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        String username = (String) map.get("username");
        Admin admin = adminService.findByUsernameAndId(username, id); // adminService.findByEmailOrUsername()
        if(admin!=null && UserConstant.ROLE_ADMIN.equals(admin.getRol())){
            return orderMapper.getAllOrders();
        }
        return Collections.emptyList();
    }

    @Override
    public List<OrderItem> detailItems(Integer id) {

        return orderMapper.detailItems(id);
    }

}
