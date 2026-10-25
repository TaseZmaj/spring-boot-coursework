package mk.ukim.finki.wp2025.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long categoryId) {
        super(String.format("Category with id %d does not exist.", categoryId));
    }
}
