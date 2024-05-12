1)//student
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
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    public Student() {
    }

    public Student(String name, Set<Course> courses) {
        this.name = name;
        this.courses = courses;
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

    public Set<Course> getCourses() {
        return courses;
    }

    public void addCourses(Course courses) {
        this.courses.add(courses);
    }

    public void setCourses(Set<Course> c){
        this.courses = c;
    }
}

2)//course
  package com.examly.springapp.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();

    public Course() {
    }

    public Course(String name, Set<Student> students) {
        this.name = name;
        this.students = students;
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

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Student students) {
        this.students.add(students);
    }
    
    
}

3)//course repo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course,Integer>{

}

4)//student repo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer>{

}

5)//api serivice
  package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Course;
import com.examly.springapp.model.Student;
import com.examly.springapp.repository.CourseRepo;
import com.examly.springapp.repository.StudentRepo;

@Service
public class ApiService {

    @Autowired
    private StudentRepo srepo;

    @Autowired
    private CourseRepo crepo;

    public Student addStudent(Student s){
        return srepo.save(s);
    }

    public List<Student> getAllStudents(){
        return srepo.findAll();
    }

    public Student getById(int studentId){
        if(srepo.existsById(studentId)){
            return srepo.findById(studentId).get();
        }else{
            return null;
        }
    }

    public Student updateStudent(Student s,int studentId){
        if(srepo.existsById(studentId)){
            s.setId(studentId);
            return srepo.save(s);
        }else{
            return null;
        }
    }

    public Course updateCourse(Course c,int courseId){
        if(!crepo.existsById(courseId)){
            c.setId(courseId);
            return crepo.save(c);
        }else{
            return null;
        }
    }

    public boolean delete(int studentId){
        if(srepo.existsById(studentId)){
            srepo.deleteById(studentId);
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteCourse(int courseId){
        if(crepo.existsById(courseId)){
            crepo.deleteById(courseId);
            return true;
        }else{
            return false;
        }
    }
}

6)//api controller
  package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Course;
import com.examly.springapp.model.Student;
import com.examly.springapp.service.ApiService;

@RestController
public class ApiController {

    @Autowired
    private ApiService service;

    @PostMapping("/addstudent")
    public ResponseEntity<Student> addS(@RequestBody Student s){
        Student s1 = service.addStudent(s);
        if(s1 != null){
            return new ResponseEntity<>(s1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getallstudent")
    public ResponseEntity<List<Student>> getAll(){
        List<Student> s1 = service.getAllStudents();
        if(s1 != null){
            return new ResponseEntity<>(s1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable int studentId){
        Student s1 = service.getById(studentId);
        if(s1 != null){
            return new ResponseEntity<>(s1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/updatestudent/{studentId}")
    public ResponseEntity<Student> update(@RequestBody Student s,@PathVariable int studentId){
        Student s1 = service.updateStudent(s, studentId);
        if(s1 != null){
            return new ResponseEntity<>(s1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/updatecourse/{courseId}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course c,@PathVariable int courseId){
        Course s1 = service.updateCourse(c, courseId);
        if(s1 != null){
            return new ResponseEntity<>(s1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/deletstudent/{studentId}")
    public ResponseEntity<String> delete(@PathVariable int studentId){
        boolean s1 = service.delete(studentId);
        if(s1){
            return new ResponseEntity<>("Student deleted Successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Student not found",HttpStatus.OK);
        }
    }
    
    @DeleteMapping("/deletecourse/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable int courseId){
        boolean s1 = service.deleteCourse(courseId);
        if(s1){
            return new ResponseEntity<>("Course deleted Successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Course not found",HttpStatus.OK);
        }
    }

}
