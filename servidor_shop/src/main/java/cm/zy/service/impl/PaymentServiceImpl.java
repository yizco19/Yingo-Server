package cm.zy.service.impl;

import cm.zy.constant.PaymentConstant;
import cm.zy.mapper.ProductMapper;
import cm.zy.pojo.*;
import cm.zy.service.*;
import cm.zy.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OfferService offerService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result processPayment(PaymentRequest request) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        List<CartItem> cartItems = cartService.listItems();
        //comprueba si el producto tiene un stock para la compra
        for (CartItem item : cartItems) {
            Product product = productMapper.detail(item.getProductId());
            if (product.getStock() < item.getQuantity()) {
                return Result.error("No hay suficiente stock");
            }
        }
        Double total = calculateTotal(cartItems);

        Double amount = request.getAmount();
        if (!Objects.equals(amount, total)) {
            amount = total;
        }



        switch (request.getType()) {
            case PaymentConstant.TYPE_WALLET:
                User user = userService.findById(userId);
                if (user.getWallet() < total) {
                    return Result.error("Fondos insuficientes");
                }
                handleWalletPayment(userId, total, cartItems, request);
                break;
            case PaymentConstant.TYPE_CARD:
                handleCardPayment(userId, total, cartItems, request);
                break;
            default:
                return Result.error("El tipo de pago no existe");
        }

        return Result.success();
    }

    private Double calculateTotal(List<CartItem> cartItems) {
        Double total = 0.0;
        for (CartItem item : cartItems) {
            Double price = getProductPrice(item.getProductId());
            total += price * item.getQuantity();
        }
        return total;
    }

    private Double getProductPrice(Integer productId) {
        Product product = productMapper.detail(productId);
        Double price = product.getPrice();
        Offer offer = offerService.getOfferById(product.getOfferId());
        if (Objects.nonNull(offer)) {
            price -= price * offer.getDiscount() / 100;
        }
        return price;
    }

    private void handleWalletPayment(Integer userId, Double total, List<CartItem> cartItems, PaymentRequest request) {
        User user = userService.findById(userId);
        if (Objects.isNull(user)) {
            throw new RuntimeException("El usuario no existe");
        }
        payWithWallet(user, total, cartItems, request);
    }

    private void handleCardPayment(Integer userId, Double total, List<CartItem> cartItems, PaymentRequest request) {
        // Implement card payment logic here
    }

    private void payWithWallet(User user, Double total, List<CartItem> cartItems, PaymentRequest request) {
        try {
        Cart cart = cartService.detail();
        Order order = moveCartToOrder(cart, total, cartItems, request);
        cartService.clearCart();
        user.setWallet(user.getWallet() - total);
        userService.update(user);

        productMapper.updateStock(cartItems);
        } catch (Exception e) {
            // Log the exception
            throw e; // This will trigger the rollback
        }
    }

    private Order moveCartToOrder(Cart cart, Double total, List<CartItem> cartItems, PaymentRequest request) {
        try {
        Order order = new Order();
        order.setUserId(cart.getUserId());
        order.setTotal(total);
        order.setCreatedAt(new Date());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setPayType(request.getType());

        List<OrderItem> orderItems = new java.util.ArrayList<>();
        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            Double price = getProductPrice(item.getProductId());
            orderItem.setPrice(price);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        orderService.add(order);
        return order;
        } catch (Exception e) {
            // Log the exception
            throw e; // This will trigger the rollback
        }
    }

}
