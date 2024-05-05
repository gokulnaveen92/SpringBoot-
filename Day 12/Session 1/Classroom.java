1)//classroom
package com.examly.springapp.model;

public class ClassRoom {
    private int roomId,roomCapacity;
    private String roomType;
    private boolean hasProjector;

    public ClassRoom(int roomId, int roomCapacity, String roomType, boolean hasProjector) {
        this.roomId = roomId;
        this.roomCapacity = roomCapacity;
        this.roomType = roomType;
        this.hasProjector = hasProjector;
    }

        public int getRoomId() {
            return roomId;
        }
        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }
        public int getRoomCapacity() {
            return roomCapacity;
        }
        public void setRoomCapacity(int roomCapacity) {
            this.roomCapacity = roomCapacity;
        }
        public String getRoomType() {
            return roomType;
        }
        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }
        public boolean isHasProjector() {
            return hasProjector;
        }
        public void setHasProjector(boolean hasProjector) {
            this.hasProjector = hasProjector;
        }            
}

2)//service
  package com.examly.springapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.ClassRoom;

@Service
public class ClassRoomService {
    List<ClassRoom> list = new ArrayList<>();

    public ClassRoom addCls(ClassRoom m){
        list.add(m);
        return m;       
    }

    public List<ClassRoom> getAll(){
        return list;
    }

    public ClassRoom getId(int id){
        for(ClassRoom d1 : list){
            if(d1.getRoomId()==id){
                return d1;
            }
        }
        return null;
    }
}

3)//controller
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

import com.examly.springapp.model.ClassRoom;
import com.examly.springapp.service.ClassRoomService;

@RestController
public class ClassRoomController {

    @Autowired
    private ClassRoomService service;

    @PostMapping("/classrooms")
        public ResponseEntity<ClassRoom> add(@RequestBody ClassRoom p){
        ClassRoom d = service.addCls(p);
        if(d!=null){
            return new ResponseEntity<>(d,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/classrooms")
    public ResponseEntity<List<ClassRoom>> getAllClassRoom(){
        List<ClassRoom> list = service.getAll();
        if(!list.isEmpty()){
        return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }  
    }

    @GetMapping("/classrooms/{roomId}")
    public ResponseEntity<ClassRoom> getByClassRoom(@PathVariable int roomId){
        ClassRoom d1 = service.getId(roomId);
        if(d1!=null){
            return new ResponseEntity<>(d1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}

