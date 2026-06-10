package br.com.lapes.ecommerce.domain.exception;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String resource, Object id) {
        super("RESOURCE_NOT_FOUND", "%s nao encontrado: %s".formatted(resource, id));
    }
}
