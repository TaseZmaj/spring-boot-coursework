package mk.ukim.finki.wp2025.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ManufacturerNotFoundException extends RuntimeException {

    public ManufacturerNotFoundException(Long manufacturerId) {
        super(String.format("Manufacturer with id %d does not exist.", manufacturerId));
    }
}

