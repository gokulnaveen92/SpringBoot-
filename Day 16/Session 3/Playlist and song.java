1)//playlist
package com.examly.springapp.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "playlists",cascade = CascadeType.ALL)
    private Set<Song> songs = new HashSet<>();

    public Playlist() {
    }

    public Playlist(String name, Set<Song> songs) {
        this.name = name;
        this.songs = songs;
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

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public void add(Song arg0) {
        songs.add(arg0);
    }

}

2)//song
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
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String artist;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "song_playlist",
        joinColumns = @JoinColumn(name = "song_id"),
        inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private Set<Playlist> playlists = new HashSet<>();

    public Song() {
    }

    public Song(String name, String artist, Set<Playlist> playlists) {
        this.name = name;
        this.artist = artist;
        this.playlists = playlists;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void add(Playlist arg0) {
        playlists.add(arg0);
    }

    
}

3)//playlistrepo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Playlist;

@Repository
public interface PlaylistRepo extends JpaRepository<Playlist,Integer>{

}

4)//song repo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Song;

@Repository
public interface SongRepo extends JpaRepository<Song,Integer>{

}

5)//api service
  package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Playlist;
import com.examly.springapp.model.Song;
import com.examly.springapp.repository.PlaylistRepo;
import com.examly.springapp.repository.SongRepo;

@Service
public class ApiService {

    @Autowired
    private SongRepo sRepo;

    @Autowired
    private PlaylistRepo pRepo;

    public boolean addPlaylist(Playlist p){
        pRepo.save(p);
        return true;
    }

    public boolean addSong(Song s){
        sRepo.save(s);
        return true;
    }

    public boolean addSongToPlaylist(int playlistId,int songId){
        if(pRepo.existsById(playlistId) && sRepo.existsById(songId)){
            Playlist p = pRepo.findById(playlistId).get();
            Song s = sRepo.findById(songId).get();
            p.add(s);
            pRepo.save(p);
            return true;
        }else{
            return false;
        }
    }

    public List<Playlist> getAllPlaylists(){
        return pRepo.findAll();
    }

    public List<Song> getAllSongs(){
        return sRepo.findAll();
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.examly.springapp.model.Playlist;
import com.examly.springapp.model.Song;
import com.examly.springapp.service.ApiService;

@RestController
public class ApiController {

    @Autowired
    private ApiService service;

    @PostMapping("/addplaylist")
    public ResponseEntity<Boolean> addP(@RequestBody Playlist p){
        boolean p1 = service.addPlaylist(p);
        if(p1){
            return new ResponseEntity<>(p1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addsong")
    public ResponseEntity<Boolean> addS(@RequestBody Song s){
        boolean s1 = service.addSong(s);
        if(s1){
            return new ResponseEntity<>(s1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(s1,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/playlists/{playlistId}/addSong/{songId}")
    public ResponseEntity<Boolean> addBoth(@PathVariable int playlistId,@PathVariable int songId){
        boolean b = service.addSongToPlaylist(playlistId, songId);
        if(b){
            return new ResponseEntity<>(b,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(b,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/playlists")
    public ResponseEntity<List<Playlist>> getListP(){
        List<Playlist> list = service.getAllPlaylists();
        if(list !=null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getListS(){
        List<Song> list = service.getAllSongs();
        if(list !=null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
