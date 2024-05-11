1)//book
package com.examly.springapp.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private int id;
    private String name;
    @ManyToMany(mappedBy = "books",cascade = CascadeType.ALL)
    private Set<Author> author = new HashSet<>();
    
    public Book() {
    }
    
    public Book(String name, Set<Author> author) {
        this.name = name;
        this.author = author;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Author> getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author.add(author);
    }
    

}


2)//author
package com.examly.springapp.model;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToMany()
    @JoinTable(
        name = "author_book",
        joinColumns = @JoinColumn(name = "author_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<>();

    public Author() {
    }

    public Author(String name, Set<Book> books) {
        this.name = name;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void add(Book arg0) {
        this.books.add(arg0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Book books) {
        this.books.add(books);
    }

    



}

3)//author repo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Author;

@Repository
public interface AuhtorRepo extends JpaRepository<Author,Integer> {

}

4)//book repo
  
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book,Integer>{

}


5)//apiservice
  package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Author;
import com.examly.springapp.model.Book;
import com.examly.springapp.repository.AuhtorRepo;
import com.examly.springapp.repository.BookRepo;

@Service
public class ApiService {
    
    @Autowired
    private BookRepo bRepo;

    @Autowired
    private AuhtorRepo aRepo;

    public Author addAuthor(Author a){
        return aRepo.save(a);
    }

    public boolean addBookToAuthor(Book b,int auhtorId){
        if(aRepo.existsById(auhtorId)){
            Author a = aRepo.findById(auhtorId).get();
            b.setAuthor(a);
            bRepo.save(b);
            return true;
        }else{
            return false;
        }
    }

    public List<Author> getAllAuthors(){
        return aRepo.findAll();
    }

    public Author getByAid(int auhtorId){
        if(aRepo.existsById(auhtorId))
        return aRepo.findById(auhtorId).get();
        return null;
    }

    public Book updateBook(int bookId,int auhtorId){
        if(bRepo.existsById(bookId) && aRepo.existsById(auhtorId)){
            Book b = bRepo.findById(bookId).get();
            Author a = aRepo.findById(auhtorId).get();
            b.setAuthor(a);
            return bRepo.save(b);
        }else{
            return null;
        }
    }

}

6)//api controller
  package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.examly.springapp.model.Author;
import com.examly.springapp.model.Book;
import com.examly.springapp.service.ApiService;

@RestController
public class ApiController {

    @Autowired
    private ApiService service;

    @PostMapping("/author")
    public ResponseEntity<Boolean> add(@RequestBody Author a){
        Author a1 = service.addAuthor(a);
        if(a1 != null){
            return new ResponseEntity<>(true,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/author/{authorId}/book")
    public ResponseEntity<Boolean> addB(@PathVariable int authorId,@RequestBody Book b){
        boolean b1 = service.addBookToAuthor(b, authorId);
        if(b1){
            return new ResponseEntity<>(b1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(b1,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/author")
    public ResponseEntity<List<Author>> getList(){
        List<Author> list = service.getAllAuthors();
        if(list != null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("author/{id}")
    public ResponseEntity<Author> get(@PathVariable int id){
        Author a = service.getByAid(id);
        if(a != null){
            return new ResponseEntity<>(a,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/author/{authorId}/book/{bookId}")
    public ResponseEntity<Book> getB(@PathVariable int authorId,@PathVariable int bookId){
        Book b = service.updateBook(bookId, authorId);
        if(b != null){
            return new ResponseEntity<>(b,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
  
