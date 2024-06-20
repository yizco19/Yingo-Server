package cm.zy.mapper;

import cm.zy.pojo.Order;
import cm.zy.pojo.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    void addOrder(Order order);

    void addOrderItems(@Param("orderItems") List<OrderItem> orderItems);




    void updateOrder(Order order);

    void updateOrderItems(Order order);


    List<Order> list(Integer userId ,Integer status);


    Order detail(Integer id);

    @Select("select * from order_item where order_id = #{id} ")
    List<OrderItem> detailItems(Integer id);

    @Select("delete from order_item where order_id = #{id} ")
    void deleteItems(Integer id);


    void delete(Integer id);


    void updateStatus(Integer id, Integer status);

    List<Order> getAllOrders();
}
