package mk.ukim.finki.wp.lab3.service.implementations;

import mk.ukim.finki.wp.lab3.model.BookReservation;
import mk.ukim.finki.wp.lab3.repository.mock.BookReservationRepository;
import mk.ukim.finki.wp.lab3.service.BookReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookReservationServiceImpl implements BookReservationService {
    private final BookReservationRepository bookReservationRepository;
    public BookReservationServiceImpl(BookReservationRepository bookReservationRepository) {
        this.bookReservationRepository = bookReservationRepository;
    }

    @Override
    public BookReservation placeReservation(String bookTitle, String readerName, String readerAddress, Long numberOfCopies) {
        if(bookTitle == null || bookTitle.isEmpty()){
            throw new IllegalArgumentException("Book Title cannot be null or empty");
        }
        if(readerName == null || readerName.isEmpty()){
            throw new IllegalArgumentException("Reader Name cannot be null or empty");
        }
        if(readerAddress == null || readerAddress.isEmpty()){
            throw new IllegalArgumentException("Reader Address cannot be null or empty");
        }
        if(numberOfCopies <= 0){
            throw new IllegalArgumentException("Number of Copies cannot be null or zero");
        }
        BookReservation newBookReservation = new BookReservation(bookTitle, readerName, readerAddress, numberOfCopies);
        bookReservationRepository.save(newBookReservation);
        return newBookReservation;
    }

    @Override
    public List<BookReservation> listAll() {
        return bookReservationRepository.findAll();
    }
}
