package cm.zy.controller;

import cm.zy.pojo.PaymentRequest;
import cm.zy.pojo.Result;
import cm.zy.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * Clase controlador para gestionar las operaciones de pagos.
 * Autor: Zhi Yang
 * Fecha de Creación: 22/04/2024
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    /**
     * Procesa un pago.
     *
     * @param request la solicitud de pago
     * @return un Result indicando éxito o error
     */
    @PostMapping
    public Result processPayment(@RequestBody PaymentRequest request) {
        Result result = paymentService.processPayment(request);
        if (!result.getCode().equals(0)) {
            return result;
        }
        return Result.success();
    }


}