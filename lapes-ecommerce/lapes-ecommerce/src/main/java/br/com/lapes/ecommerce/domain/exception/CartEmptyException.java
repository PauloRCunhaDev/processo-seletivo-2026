package br.com.lapes.ecommerce.domain.exception;

public class CartEmptyException extends BusinessException {

    public CartEmptyException() {
        super("CART_EMPTY", "O carrinho esta vazio");
    }
}
