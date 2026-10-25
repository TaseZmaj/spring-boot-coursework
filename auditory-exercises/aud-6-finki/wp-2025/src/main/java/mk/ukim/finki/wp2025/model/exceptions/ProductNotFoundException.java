package mk.ukim.finki.wp2025.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long productId) {
        super(String.format("Product with id %d does not exist.", productId));
    }
}

