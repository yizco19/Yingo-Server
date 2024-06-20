package cm.zy.service;

import cm.zy.pojo.Cart;
import cm.zy.pojo.CartItem;
import cm.zy.pojo.Result;

import java.util.List;

public interface CartService {

    void add(Cart cart);

    void deleteItem(Integer productId);

    List<Cart> list();

    Result updateItemQuantity(Integer itemId, Integer quantity);

    void clearCart();

    Result addItem(CartItem item);

    void update(Cart cart);

    List<CartItem> listItems();

    Cart detail();

    void addItems(List<CartItem> items);
}
