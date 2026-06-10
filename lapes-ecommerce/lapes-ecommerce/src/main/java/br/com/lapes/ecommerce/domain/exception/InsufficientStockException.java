package br.com.lapes.ecommerce.domain.exception;

public class InsufficientStockException extends BusinessException {

    public InsufficientStockException(String productName, int requested, int available) {
        super("INSUFFICIENT_STOCK",
            "Produto '%s' nao tem estoque suficiente. Solicitado: %d, disponivel: %d"
                .formatted(productName, requested, available));
    }
}
