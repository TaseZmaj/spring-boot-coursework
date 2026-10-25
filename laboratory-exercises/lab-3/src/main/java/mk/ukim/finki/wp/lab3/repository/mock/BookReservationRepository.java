package mk.ukim.finki.wp.lab3.repository.mock;

import mk.ukim.finki.wp.lab3.model.BookReservation;

import java.util.List;

public interface BookReservationRepository {
    BookReservation save(BookReservation reservation);
    List<BookReservation> findAll();
}
