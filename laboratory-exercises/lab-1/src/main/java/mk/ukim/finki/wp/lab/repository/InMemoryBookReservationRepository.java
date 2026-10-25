package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.BookReservation;
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
    public List<BookReservation> findAll(){
        return DataHolder.reservations;
    }
}
