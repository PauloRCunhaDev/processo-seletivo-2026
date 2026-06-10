package br.com.lapes.ecommerce.domain.exception;

import java.math.BigDecimal;

public class CouponMinOrderValueException extends BusinessException {

    public CouponMinOrderValueException(String code, BigDecimal minValue, BigDecimal orderTotal) {
        super("COUPON_MIN_ORDER_VALUE",
            "Cupom '%s' requer pedido minimo de R$ %.2f. Total atual: R$ %.2f"
                .formatted(code, minValue, orderTotal));
    }
}
