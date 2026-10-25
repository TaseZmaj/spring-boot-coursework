package mk.ukim.finki.wp.auditoryexercise4and5project.repository.implementations;

import mk.ukim.finki.wp.auditoryexercise4and5project.bootstrap.DataHolder;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.Category;
import mk.ukim.finki.wp.auditoryexercise4and5project.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository - mu kazuvas da kreira instanca od klasata (odosno Bean) koa ke se startuva programata
@Repository
public class InMemoryCategoryRepository implements CategoryRepository {
    @Override
    public Optional<Category> findById(Long id) {
        return DataHolder.categories.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    //find all
    public List<Category> findAll(){
        return DataHolder.categories;
    }

    //create OR update
    public Category save(Category c){
        if(c==null || c.getName() == null || c.getName().isEmpty()){
            return null;
        }
        DataHolder.categories.removeIf(r->r.getName().equals(c.getName()));
        DataHolder.categories.add(c);
        return c;
    }

    //Optional vika "Ako ne ja najdam soodvetnata instanca od kategorija po ime,
    // togash ke vratam Optional objekt koj vnatre ne sodrzi neshto" - za da ne vrati null
    //bidejki taka krashnuva java aplikacija
    //find
    public Optional<Category> findByName(String name){
        return DataHolder.categories.stream().filter(r->r.getName().equals(name)).findFirst();
    }

    //filter by string
    public List<Category> search(String text){
        return DataHolder.categories.stream().filter(r->r.getName().contains(text) || r.getDescription().contains(text)).toList();
    }

    //delete
    public void delete(String name){
        if(name==null){
            return;
        }
        DataHolder.categories.removeIf(r->r.getName().equals(name));
    }

}
