package org.example.auditoryexercise2.repository.implementations;

import org.example.auditoryexercise2.bootstrap.DataHolder;
import org.example.auditoryexercise2.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository - mu kazuvas da kreira instanca od klasata koa ke se startuva programata
@Repository
public class InMemoryCategoryRepository {

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
