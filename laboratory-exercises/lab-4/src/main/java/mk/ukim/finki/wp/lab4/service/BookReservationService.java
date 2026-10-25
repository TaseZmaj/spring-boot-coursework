package mk.ukim.finki.wp.lab4.service;

import mk.ukim.finki.wp.lab4.model.BookReservation;

import java.util.List;

public interface BookReservationService {
    BookReservation placeReservation(String bookTitle, String readerName, String readerAddress, Long numberOfCopies);
    List<BookReservation> listAll();
}
