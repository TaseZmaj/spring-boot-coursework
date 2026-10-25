package mk.ukim.finki.wp.kol2025g2.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.kol2025g2.model.SkiResort;
import mk.ukim.finki.wp.kol2025g2.model.SkiSlope;
import mk.ukim.finki.wp.kol2025g2.model.SlopeDifficulty;
import mk.ukim.finki.wp.kol2025g2.model.exceptions.InvalidSkiResortIdException;
import mk.ukim.finki.wp.kol2025g2.repository.SkiResortRepository;
import mk.ukim.finki.wp.kol2025g2.service.SkiResortService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SkiResortServiceImpl implements SkiResortService {

    private final SkiResortRepository skiResortRepository;

    @Override
    public SkiResort findById(Long id) {
        return skiResortRepository.findById(id).orElseThrow(()-> new InvalidSkiResortIdException(id));
    }

    @Override
    public List<SkiResort> listAll() {
        return skiResortRepository.findAll();
    }

    @Override
    public SkiResort create(String name, String location) {
//        if(name == null || name.isEmpty() || location == null || location.isEmpty()){
//            throw new IllegalArgumentException();
//        }
        SkiResort resort = new SkiResort(name, location);
        return skiResortRepository.save(resort);
    }
}
