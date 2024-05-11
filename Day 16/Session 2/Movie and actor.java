1)//movie
package com.examly.springapp.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Movie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private int year;

    @ManyToMany
    @JoinTable(
        name = "movie_actor",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Actor> actors = new HashSet<>();

    public Movie(String title, int year, Set<Actor> actors) {
        this.title = title;
        this.year = year;
        this.actors = actors;
    }

    public Movie() {
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    

}

2)//actor
package com.examly.springapp.model;



import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies = new HashSet<>();

    public Actor() {
    }

    public Actor(String name, Set<Movie> movies) {
        this.name = name;
        this.movies = movies;
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

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Movie movies) {
        this.movies.add(movies);
    }


    

}

3)//movie repo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer>{

}

4)//actor repo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor,Integer>{

}

5)//movie service
package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Actor;
import com.examly.springapp.model.Movie;
import com.examly.springapp.repository.ActorRepository;
import com.examly.springapp.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private ActorRepository arepo;

    @Autowired
    private MovieRepository mrepo;
    
    public Movie addMovie(Movie m){
        return mrepo.save(m);
    }

    public Actor addActor(Actor a){
        return arepo.save(a);
    }

    public boolean addMovieToActor(int movieId,int actorId){
        if(mrepo.existsById(movieId) && arepo.existsById(actorId)){
            Movie m = mrepo.findById(movieId).get();
            Actor a = arepo.findById(actorId).get();
            a.setMovies(m);
            arepo.save(a);
            return true;
        }else{
            return false;
        }
    }

    public List<Movie> getAllMovies(){
        return mrepo.findAll();   
    }

    public Actor getByActorid(int actorId){
        return arepo.findById(actorId).get();
    }

    public Actor updateActor(int actorId,Actor a){
        if(arepo.existsById(actorId)){
            a.setId(actorId);
            return arepo.save(a);
        }else{
            return null;
        }
    }

    public Movie updateMovie(int movieId,Movie m){
        if(mrepo.existsById(movieId)){
            m.setId(movieId);
            return mrepo.save(m);
        }else{
            return null;
        }
    }
}


6)//apicontroller

package com.examly.springapp.controller;

import org.apache.catalina.connector.Response;
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
import com.examly.springapp.model.Actor;
import com.examly.springapp.model.Movie;
import com.examly.springapp.service.MovieService;

@RestController
public class ApiController {

    @Autowired
    private MovieService service;

    @PostMapping("/addmovie")
    public ResponseEntity<Movie> add(@RequestBody Movie m){
        Movie m1 = service.addMovie(m);
        if(m1 != null){
            return new ResponseEntity<>(m1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addactor")
    public ResponseEntity<Actor> add(@RequestBody Actor a){
        Actor a1 = service.addActor(a);
        if(a1 != null){
            return new ResponseEntity<>(a1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("movies/{movieId}/actors/{actorId}")
    public ResponseEntity<Boolean> add(@PathVariable int movieId,@PathVariable int actorId){
        boolean b = service.addMovieToActor(movieId, actorId);
        if(b){
            return new ResponseEntity<>(b,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getListM(){
        List<Movie> list = service.getAllMovies();
        if(list != null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/actors/{actorId}")
    public ResponseEntity<Actor> getByActor(@PathVariable int actorId){
        Actor a = service.getByActorid(actorId);
        if(a != null){
            return new ResponseEntity<>(a,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateactor/{actorId}")
    public ResponseEntity<Actor> update(@PathVariable int actorId,@RequestBody Actor a){
        Actor a1 = service.updateActor(actorId, a);
        if(a1 != null){
            return new ResponseEntity<>(a1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatemovie/{movieId}")
    public ResponseEntity<Movie> update(@PathVariable int movieId,@RequestBody Movie m){
        Movie m1 = service.updateMovie(movieId, m);
        if(m1 != null){
            return new ResponseEntity<>(m1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
