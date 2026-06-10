package br.com.lapes.ecommerce.domain.exception;

public class CouponAlreadyUsedException extends BusinessException {

    public CouponAlreadyUsedException(String code) {
        super("COUPON_ALREADY_USED", "Cupom '%s' ja foi utilizado por este usuario".formatted(code));
    }
}
