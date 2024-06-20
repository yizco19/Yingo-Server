package cm.zy.mapper;

import cm.zy.pojo.Cart;
import cm.zy.pojo.CartItem;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface CartMapper {





    @Insert("insert into cart (id, user_id, created_at) values (#{userId}, #{userId}, #{createdAt})")
    public void add(@Param("userId") Integer userId, @Param("createdAt") Date createdAt);


    List<Cart> list(Integer userId) ;

    @Insert("insert into cart_item (cart_id,product_id,quantity ) values (#{cartId},#{productId},#{quantity})")
    public void addItem(CartItem item) ;


    @Select("select * from cart where user_id = #{userId}")
    Cart detail(Integer userId) ;

    @Update("update cart_item set quantity = #{quantity} where cart_id = #{cartId} and product_id = #{productId}")
    void updateItemQuantity(Integer cartId, Integer productId, Integer quantity) ;

    @Delete("delete from cart_item where cart_id = #{cartId} and product_id = #{productId}")
    void delete(Integer productId,Integer cartId) ;

    @Update("update cart set phone = #{phone}, address = #{address} ,pay_type = #{payType} where id = #{id}")
    void update(Cart cart);

    @Delete("DELETE FROM cart_item WHERE cart_id = #{cartId}")
    void clearCartItems(Integer cartId);

    @Update("UPDATE cart SET phone = null, address = null WHERE id = #{cartId}")
    void clearCart(Integer cartId);


    @Select("select * from cart_item where cart_id = #{userId} and product_id = #{productId}")
    CartItem getCartItemByProductId(Integer userId, Integer productId);




    @Update("update cart_item set quantity = #{quantity} where id = #{id}")
    void updateCartItem(CartItem existingItem);

    @Select("select * from cart_item where cart_id = #{cartId}")
    List<CartItem> detailItems(Integer cartId);

    void addItems(@Param( "list") List<CartItem> items);
}
