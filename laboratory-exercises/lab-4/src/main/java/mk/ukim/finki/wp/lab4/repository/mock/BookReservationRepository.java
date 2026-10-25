package mk.ukim.finki.wp.lab4.repository.mock;

import mk.ukim.finki.wp.lab4.model.BookReservation;

import java.util.List;

public interface BookReservationRepository {
    BookReservation save(BookReservation reservation);
    List<BookReservation> findAll();
}
