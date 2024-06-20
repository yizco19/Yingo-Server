package cm.zy.service.impl;

import cm.zy.mapper.CartMapper;
import cm.zy.mapper.UserMapper;
import cm.zy.pojo.*;
import cm.zy.service.CartService;
import cm.zy.service.ProductService;
import cm.zy.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserMapper userMapper;
    @Override
    public void add(Cart cart) {
       /* Map <String, Object> map = ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");
        cart.setUserId(userId);
        cart.setCreateTime(new Date());
        cartMapper.add(cart);*/

    }

    @Override
    public void deleteItem(Integer productId) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer cartId=(Integer) map.get("id");
        cartMapper.delete(productId,cartId);
    }

    @Override
    public List<Cart> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id =(Integer) map.get("id");
        String username = (String) map.get("username");
        User user= userMapper.findByUsernameAndId(id,username);
        if(user!=null){
            List<Cart>  list= cartMapper.list(user.getId());
            return list;
        }
        return Collections.emptyList();

    }

    @Override
    public Result updateItemQuantity(Integer itemId, Integer quantity) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");
        User user = userMapper.findById(userId);

        if(user!=null){
            //comprueba si el producto existe y esta disponible
            Result El_producto_no_existe = checkProduct(itemId, quantity);
            if (El_producto_no_existe != null) return El_producto_no_existe;
            cartMapper.updateItemQuantity(user.getId(),itemId,quantity);
        }else {

            return Result.error("El usuario no existe");
        }
        return Result.success();

    }

    private Result checkProduct(Integer itemId, Integer quantity) {
        Product product = productService.detail(itemId);
        if (product == null) {
            return Result.error("El producto no existe");
        }
        if (product.getStock() < quantity) {
            return Result.error("No hay suficiente stock disponible para " + product.getName());
        }
        return null;
    }

    @Override
    public void clearCart() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer cartId=(Integer) map.get("id");
        cartMapper.clearCartItems(cartId);
        cartMapper.clearCart(cartId);


    }

    @Override
    public Result addItem(CartItem item) {
        // Obtener el ID de usuario de la sesión actual
        Integer userId = getUserIdFromSession();

        // Establecer el ID del carrito para el elemento
        item.setCartId(userId);

        // Establecer la cantidad predeterminada a 1 si no se especifica
        if (item.getQuantity() == null || item.getQuantity() <= 0) {
            item.setQuantity(1);
        }

        // Verificar si la cantidad excede el stock disponible
        Result El_producto_no_existe = checkProduct(item.getProductId(), item.getQuantity());
        if (El_producto_no_existe != null) return El_producto_no_existe;

        // Verificar si el producto ya está en el carrito
        CartItem existingItem = cartMapper.getCartItemByProductId(userId, item.getProductId());
        if (existingItem != null) {
            // Si el producto ya está en el carrito, actualizar la cantidad
            int newQuantity = item.getQuantity() + existingItem.getQuantity();
            existingItem.setQuantity(newQuantity);
            cartMapper.updateCartItem(existingItem);
        } else {
            // Si el producto no está en el carrito, agregarlo
            cartMapper.addItem(item);
        }

        return Result.success();
    }

    // Método para obtener el ID de usuario de la sesión actual
    private Integer getUserIdFromSession() {
        Map<String, Object> sessionMap = ThreadLocalUtil.get();
        return (Integer) sessionMap.get("id");
    }


    @Override
    public void update(Cart cart) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");
        cart.setId(userId);
        cart.setUserId(userId);
        cartMapper.update(cart);

    }

    @Override
    public List<CartItem> listItems() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer cartId=(Integer) map.get("id");
        return cartMapper.detailItems(cartId);
    }

    @Override
    public Cart detail() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");
        List<CartItem> items = cartMapper.detailItems(userId);
        Cart cart = cartMapper.detail(userId);
        cart.setCartItems(items);
        return cart;
    }

    @Override
    public void addItems(List<CartItem> items) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");
        cartMapper.clearCart(userId);
        cartMapper.clearCartItems(userId);
        cartMapper.addItems(items);
    }
}
