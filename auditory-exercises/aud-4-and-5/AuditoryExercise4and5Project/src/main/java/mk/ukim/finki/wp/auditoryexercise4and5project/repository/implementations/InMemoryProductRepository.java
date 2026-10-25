package mk.ukim.finki.wp.auditoryexercise4and5project.repository.implementations;

import mk.ukim.finki.wp.auditoryexercise4and5project.bootstrap.DataHolder;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.Product;
import mk.ukim.finki.wp.auditoryexercise4and5project.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    //Se koristi za READ vo servisot
    @Override
    public Optional<Product> findById(long id) {
        return DataHolder.products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    //Se koristi I za CREATE i za UPDATE vo servisot
    @Override
    public Product save(Product product) {
        //Go trga ako postoi
        DataHolder.products.removeIf(m -> m.getId().equals(product.getId()));
        DataHolder.products.add(product);
        return product;
    }

    //Se koristi za READ vo servisot
    @Override
    public List<Product> findAll() {
        return DataHolder.products;
    }


    @Override
    public List<Product> search(String text) {
        String searchQuery = text.toLowerCase();
        return DataHolder.products.stream()
                .filter(
                        p -> p.getName().toLowerCase().equals(searchQuery) ||
                                p.getLevel().toString().toLowerCase().contains(text.toLowerCase()) ||
                                p.getCategory().getName().toLowerCase().contains(text.toLowerCase()) ||
                                p.getManufacturer().getName().toLowerCase().contains(text.toLowerCase()))
                .toList();


    }

    //Se koristi za DELETE vo servisot
    @Override
    public void delete(Long id) {
        DataHolder.products.removeIf(m -> m.getId().equals(id));
    }
}
