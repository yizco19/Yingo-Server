package cm.zy.controller;

import cm.zy.pojo.*;
import cm.zy.service.CartService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Clase controlador para gestionar las operaciones de carrito de compras.
 * Autor: Zhi Yang
 * Fecha de Creación: 22/04/2024
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    /**
     * Agrega un nuevo carrito.
     *
     * @param cart el carrito a agregar
     * @return un Result indicando éxito
     */
    @PostMapping
    public Result add(@RequestBody Cart cart){
        cartService.add(cart);
        return Result.success();
        //return Result.success();
    }
    /**
     * Actualiza un carrito existente.
     *
     * @param cart el carrito a actualizar
     * @return un Result indicando éxito
     */
    @PutMapping
    public Result update(@RequestBody Cart cart){

        cartService.update(cart);
        return Result.success();
    }
    /**
     * Agrega un nuevo artículo al carrito.
     *
     * @param item el artículo a agregar
     * @return un Result indicando éxito o error
     */
    @PostMapping("/addItem")
    public Result addItem(@RequestBody CartItem item) {
        Result result = cartService.addItem(item);
        if (!result.getCode().equals(0)) {
            return result;
        }
        return Result.success();

    }
    /**
     * Actualiza la cantidad de un artículo en el carrito.
     *
     * @param itemId el ID del artículo
     * @param quantity la nueva cantidad
     * @return un Result indicando éxito o error
     */
    @PutMapping("/updateItemQuantity")
    public Result updateItemQuantity(@RequestParam Integer itemId, @RequestParam Integer quantity){
        Result result = cartService.updateItemQuantity(itemId, quantity);
        if (!result.getCode().equals(0)) {
            return result;
        }
        return Result.success();
    }
    /**
     * Elimina un artículo del carrito.
     *
     * @param productId el ID del producto a eliminar
     * @return un Result indicando éxito
     */
    @DeleteMapping
    public Result deleteItem(@RequestParam Integer productId){
        cartService.deleteItem(productId);
        return Result.success();
    }
    /**
     * Limpia el carrito.
     *
     * @return un Result indicando éxito
     */
    @DeleteMapping("/clear")
    public Result clearCart(){
        cartService.clearCart();
        return Result.success();
    }
    /**
     * Lista todos los carritos.
     *
     * @return un Result que contiene la lista de carritos
     */
    @GetMapping
    public Result<List<Cart>> list(){
        List<Cart> list = cartService.list();
        return Result.success(list);
    }
    /**
     * Lista todos los artículos en el carrito.
     *
     * @return un Result que contiene la lista de artículos del carrito
     */
    @GetMapping("/items")
    public Result<List<CartItem>> listItems(){
        List<CartItem> list = cartService.listItems();
        return Result.success(list);

    }
    /**
     * Agrega múltiples artículos al carrito.
     *
     * @param items la lista de artículos a agregar
     * @return un Result indicando éxito
     */
    @PostMapping("/addItems")
    public Result addItems(@RequestBody List<CartItem> items){

        cartService.addItems(items);
        return Result.success();
    }
    /**
     * Obtiene los detalles del carrito.
     *
     * @return un Result que contiene los detalles del carrito
     */
    @GetMapping("/detail")
    public Result<Cart> detail(){
        Cart cart = cartService.detail();
        return Result.success(cart);

    }



}
