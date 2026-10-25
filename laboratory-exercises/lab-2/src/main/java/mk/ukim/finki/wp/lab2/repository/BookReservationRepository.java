package mk.ukim.finki.wp.lab2.repository;

import mk.ukim.finki.wp.lab2.model.BookReservation;

import java.util.List;

public interface BookReservationRepository {
    BookReservation save(BookReservation reservation);
    List<BookReservation> findAll();
}
