package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookReservation {
    String bookTitle;
    String readerName;
    String readerAddress;
    Long numberOfCopies;
}
