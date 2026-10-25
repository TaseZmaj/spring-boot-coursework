package mk.ukim.finki.wp.kol2025g3.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidExpenseIdException extends RuntimeException {

    public InvalidExpenseIdException(Long id) {
        super("Expense with an id of: " + id + " does not exist");
    }
}
