package cm.zy.service;

import cm.zy.pojo.PaymentRequest;
import cm.zy.pojo.Result;

public interface PaymentService {

    Result processPayment(PaymentRequest request);
}
