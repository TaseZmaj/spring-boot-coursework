package mk.ukim.finki.wp.lab2.service;

import mk.ukim.finki.wp.lab2.model.BookReservation;

import java.util.List;

public interface BookReservationService {
    BookReservation placeReservation(String bookTitle, String readerName, String readerAddress, long numberOfCopies);
    List<BookReservation> listAll();
}
