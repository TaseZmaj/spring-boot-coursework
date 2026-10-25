package mk.ukim.finki.wp.kol2025g2.service.impl;


import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.kol2025g2.model.SkiResort;
import mk.ukim.finki.wp.kol2025g2.model.SkiSlope;
import mk.ukim.finki.wp.kol2025g2.model.SlopeDifficulty;
import mk.ukim.finki.wp.kol2025g2.model.exceptions.InvalidSkiSlopeIdException;
import mk.ukim.finki.wp.kol2025g2.repository.SkiSlopeRepository;
import mk.ukim.finki.wp.kol2025g2.service.SkiResortService;
import mk.ukim.finki.wp.kol2025g2.service.SkiSlopeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;

import static mk.ukim.finki.wp.kol2025g2.service.specification.FieldFilterSpecification.*;

@Service
@AllArgsConstructor
public class SkiSlopeServiceImpl implements SkiSlopeService {

    private final SkiSlopeRepository skiSlopeRepository;
    private final SkiResortService skiResortService;

    @Override
    public List<SkiSlope> listAll() {
        return skiSlopeRepository.findAll();
    }

    @Override
    public SkiSlope findById(Long id) {
        return skiSlopeRepository.findById(id).orElseThrow(()->new InvalidSkiSlopeIdException(id));
    }

    @Override
    public SkiSlope create(String name, Integer length, SlopeDifficulty difficulty, Long skiResort) {
//        if(name == null || name.isEmpty() || length == null || difficulty == null || skiResort == null){
//            throw new IllegalArgumentException();
//        }

        SkiResort resort = skiResortService.findById(skiResort);
        SkiSlope slope = new SkiSlope(name, length, difficulty, resort);

        return skiSlopeRepository.save(slope);
    }

    @Override
    public SkiSlope update(Long id, String name, Integer length, SlopeDifficulty difficulty, Long skiResort) {
//        if(name == null || name.isEmpty() || length == null || difficulty == null || skiResort == null){
//            throw new IllegalArgumentException();
//        }

        SkiResort resort = skiResortService.findById(skiResort);
        SkiSlope slope = this.findById(id);

        slope.setName(name);
        slope.setLength(length);
        slope.setDifficulty(difficulty);
        slope.setSkiResort(resort);

        return skiSlopeRepository.save(slope);
    }

    @Override
    public SkiSlope delete(Long id) {
        SkiSlope slope = this.findById(id);
        skiSlopeRepository.delete(slope);

        return slope;
    }

    @Override
    public SkiSlope close(Long id) {
        SkiSlope slope = this.findById(id);
        slope.setClosed(true);

        return skiSlopeRepository.save(slope);
    }

    @Override
    public Page<SkiSlope> findPage(String name, Integer length, SlopeDifficulty difficulty, Long skiResort, int pageNum, int pageSize) {
        Specification<SkiSlope> specification = Specification.allOf(
                filterContainsText(SkiSlope.class, "name", name),
                greaterThan(SkiSlope.class, "length", length),
                filterEqualsV(SkiSlope.class, "difficulty", difficulty),
                filterEquals(SkiSlope.class, "skiResort.id", skiResort)
        );

        return this.skiSlopeRepository.findAll(
                specification,
                PageRequest.of(pageNum, pageSize));
    }
}
