package br.com.lapes.ecommerce.domain.exception;

import br.com.lapes.ecommerce.domain.entity.OrderStatus;

public class OrderCancellationNotAllowedException extends BusinessException {

    public OrderCancellationNotAllowedException(OrderStatus currentStatus) {
        super("ORDER_CANCELLATION_NOT_ALLOWED",
            "Pedido com status '%s' nao pode ser cancelado".formatted(currentStatus));
    }
}
