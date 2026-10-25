package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.BookReservation;

import java.util.List;

public interface BookReservationService {
    BookReservation placeReservation(String bookTitle, String readerName, String readerAddress, long numberOfCopies);
    List<BookReservation> listAll();
}
