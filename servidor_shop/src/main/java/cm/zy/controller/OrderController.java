package cm.zy.controller;

import cm.zy.pojo.Order;

import cm.zy.pojo.Result;
import cm.zy.pojo.OrderItem;
import cm.zy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase controlador para gestionar las operaciones de pedidos.
 * Autor: Zhi Yang
 * Fecha de Creación: 22/04/2024
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    /**
     * Agrega un nuevo pedido.
     *
     * @param order el pedido a agregar
     * @return un Result indicando éxito o error
     */
    @RequestMapping
    public Result add(@RequestBody Order order) {
        orderService.add(order);
        return Result.success();
    }
    /**
     * Actualiza un pedido existente.
     *
     * @param order el pedido a actualizar
     * @return un Result indicando éxito o error
     */
    @PutMapping
    public Result update(@RequestBody Order order) {
        orderService.update(order);
        return Result.success();
    }
    /**
     * Actualiza el estado de un pedido.
     *
     * @param orderId el ID del pedido
     * @param status el nuevo estado del pedido
     * @return un Result indicando éxito o error
     */
    @PutMapping("/status")
    public Result updateStatus(@RequestParam Integer orderId, @RequestParam Integer status) {
        orderService.updateStatus(orderId, status);
        return Result.success();
    }
    /**
     * Obtiene una lista de todos los pedidos (para administradores).
     *
     * @return un Result que contiene la lista de todos los pedidos si tiene éxito, o un mensaje de error si falla
     */
    @GetMapping("/admin/list")
    public Result<List<Order>> getAllOrders() {
        List<Order> allOrders = orderService.getAllOrders();
        return Result.success(allOrders);
    }
    /**
     * Obtiene una lista de pedidos filtrada por estado.
     *
     * @param status el estado del pedido (opcional, por defecto es 0)
     * @return un Result que contiene la lista de pedidos filtrada si tiene éxito, o un mensaje de error si falla
     */
    @GetMapping("/list")
    public Result<List<Order>> list(@RequestParam(required = false) Integer status) {
        if (status == null) {
            status = 0;
        }
        List<Order> list = orderService.list(status);
        return Result.success(list);
    }
    /**
     * Obtiene los detalles de los artículos de un pedido.
     *
     * @param id el ID del pedido
     * @return un Result que contiene la lista de artículos del pedido si tiene éxito, o un mensaje de error si falla
     */
    @GetMapping("items")
    public Result<List<OrderItem>> detailItems(@RequestParam Integer id) {

        List<OrderItem> list = orderService.detailItems(id);
        return Result.success(list);
    }
    /**
     * Obtiene los detalles de un pedido.
     *
     * @param id el ID del pedido
     * @return un Result que contiene los detalles del pedido si tiene éxito, o un mensaje de error si falla
     */
    @GetMapping("/detail")
    public Result<Order> detail(@RequestParam Integer id ) {

        Order article = orderService.detail(id );
        return Result.success(article);
    }
    /**
     * Obtiene los detalles de un pedido.
     *
     * @param id el ID del pedido
     * @return un Result que contiene los detalles del pedido si tiene éxito, o un mensaje de error si falla
     */
    @DeleteMapping
    public Result delete(@RequestParam Integer id) {
        orderService.delete(id);
        return Result.success();
    }


}
