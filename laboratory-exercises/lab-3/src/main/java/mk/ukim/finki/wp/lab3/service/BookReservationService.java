package mk.ukim.finki.wp.lab3.service;

import mk.ukim.finki.wp.lab3.model.BookReservation;

import java.util.List;

public interface BookReservationService {
    BookReservation placeReservation(String bookTitle, String readerName, String readerAddress, Long numberOfCopies);
    List<BookReservation> listAll();
}
