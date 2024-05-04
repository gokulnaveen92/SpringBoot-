1)//book controller
package com.examly.springapp.controller;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Book;
import com.examly.springapp.service.BookService;

@RestController
public class BookController {

        @Autowired
        private BookService service;

        @PostMapping("/books")
        public ResponseEntity<Book> addBook(@RequestBody Book b){
            Book b1 = service.addBook(b);
            if(b1!=null){
                return new ResponseEntity<>(b1,HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/books")
        public ResponseEntity <List<Book>> getAllBooks(){
            List<Book> list = service.getAll();
            if(!list.isEmpty()){
                return new ResponseEntity<>(list,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/books/{id}")
        public ResponseEntity<Book> getBookbyId(@PathVariable int id){
            Book b2 = service.getbyId(id);
            if(b2!=null){
                return new ResponseEntity<>(b2,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @GetMapping("/books/authors/{author}")
        public ResponseEntity<List<Book>> getBookbyAuthor(@PathVariable String author){
            List<Book> b3 = service.getbyAuthor(author);
            if(!b3.isEmpty()){
                return new ResponseEntity<>(b3,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @GetMapping("/books/year/{year}")
        public ResponseEntity<List<Book>> getBookbyYear(@PathVariable int year){
            List<Book> b4 = service.getbyYear(year);
            if(!b4.isEmpty()){
                return new ResponseEntity<>(b4,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }


}

2)//book
package com.examly.springapp.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private int year;

    public Book(){}
    
    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }


}

3)//service
package com.examly.springapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Book;

@Service
public class BookService {
    List<Book> list = new ArrayList<>();

    public Book addBook(Book b){
        list.add(b);
        return b;
    }

    public Book getbyId(int id){
        for(Book b:list){
            if(b.getId()==id){
                return b;
            }
        }
        return null;
    }

    public List<Book> getbyAuthor(String author){
        List<Book> b2 = new ArrayList<>();
        for(Book b : list){
            if(b.getAuthor().equals(author)){
                b2.add(b);
            }
        }
        return b2;
    }

    public List<Book> getbyYear(int year){
        List<Book> b4 = new ArrayList<>();
        for(Book b:list){
            if(b.getYear()==year){
                b4.add(b);
            }
        }
        return b4;
    }

    public List<Book> getAll(){
        return list;
    }

    
}
