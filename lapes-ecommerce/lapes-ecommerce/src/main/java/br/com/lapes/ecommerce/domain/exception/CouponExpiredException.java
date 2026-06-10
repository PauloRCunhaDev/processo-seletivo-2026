package br.com.lapes.ecommerce.domain.exception;

public class CouponExpiredException extends BusinessException {

    public CouponExpiredException(String code) {
        super("COUPON_EXPIRED", "Cupom '%s' esta expirado".formatted(code));
    }
}
