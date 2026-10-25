package mk.ukim.finki.wp.lab4.repository.mock.implementations;

import mk.ukim.finki.wp.lab4.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab4.model.BookReservation;
import mk.ukim.finki.wp.lab4.repository.mock.BookReservationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryBookReservationRepository implements BookReservationRepository {

    @Override
    public BookReservation save(BookReservation reservation) {
        if(reservation == null){
            return null;
        }
        DataHolder.reservations.add(reservation);
        return reservation;
    }

    @Override
    public List<BookReservation> findAll(){
        return DataHolder.reservations;
    }
}
