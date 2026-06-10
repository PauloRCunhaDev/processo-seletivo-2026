package br.com.lapes.ecommerce.domain.entity;

public enum OrderStatus {
    PENDING,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    public boolean canCancel() {
        return this == PENDING || this == PAID;
    }

    public boolean canTransitionTo(OrderStatus next) {
        return switch (this) {
            case PENDING   -> next == PAID || next == CANCELLED;
            case PAID      -> next == SHIPPED || next == CANCELLED;
            case SHIPPED   -> next == DELIVERED;
            default        -> false;
        };
    }
}
