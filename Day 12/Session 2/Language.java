1)//Language
package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Language {
    @Id
    private int languageId;
    private String languageName;
    private String languageComplexity;

    public Language() {
    }
   
    public Language(int languageId, String languageName, String languageComplexity) {
        this.languageId = languageId;
        this.languageName = languageName;
        this.languageComplexity = languageComplexity;
    }

    /*public Language(String languageComplexity, String languageName, int languageId) {
        this.languageComplexity = languageComplexity;
        this.languageName = languageName;
        this.languageId = languageId;
    }*/

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageComplexity() {
        return languageComplexity;
    }

    public void setLanguageComplexity(String languageComplexity) {
        this.languageComplexity = languageComplexity;
    }    
}

2)//repository
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language,Integer> {

}

3)//service
package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Language;
import com.examly.springapp.repository.LanguageRepository;
import java.util.*;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository repo;

    public Language addLang(Language l){
        return repo.save(l);
    }

    public List<Language> getAll(){
        return repo.findAll();
    }

    public Language getById(int id){
        if(repo.existsById(id)){
            return repo.findById(id).get();
        }else{
            return null;
        }
    }
}


4)//controller
package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Language;
import com.examly.springapp.service.LanguageService;

@RestController
public class LanguageController {

    @Autowired
    private LanguageService service;

    @PostMapping("/api/language")
    public ResponseEntity<Language> add(@RequestBody Language l){
        Language l2 = service.addLang(l);
        if(l2!=null){
            return new ResponseEntity<>(l2,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/language")
    public ResponseEntity<List<Language>> getAll(){
        List<Language> list = service.getAll();
        if(!list.isEmpty()){
           return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/language/{languageId}")
    public ResponseEntity<Language> getId(@PathVariable int languageId){
        Language l2 = service.getById(languageId);
        if(l2!=null){
            return new ResponseEntity<>(l2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
