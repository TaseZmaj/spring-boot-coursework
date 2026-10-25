package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.BookReservation;

import java.util.List;

public interface BookReservationRepository {
    BookReservation save(BookReservation reservation);
    List<BookReservation> findAll();
}
