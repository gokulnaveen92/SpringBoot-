package com.examly.springapp.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.entity.Player;
import com.examly.springapp.repository.PlayerRepo;
import com.examly.springapp.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService{
    @Autowired
    private PlayerRepo repo;

    @Override
    public Player addPlayer(Player p) {
        return repo.save(p);
    }

    @Override
    public Player updatePlayer(long playerId,Player p) {
        if(repo.existsById(playerId)){
            Player p1 = repo.findById(playerId).get();
            p1.setName(p.getName());
            p1.setAge(p.getAge());
            p1.setCategory(p.getCategory());
            p1.setBiddingPrice(p.getBiddingPrice());
            p1.setSold(p.isSold());
            return p1;    
        }else{
            return null;
        }
        }

    @Override
    public List<Player> getAll() {
        return repo.findAll();
    }

    @Override
    public Player getByPlayerId(long playerId) {
        if(repo.existsById(playerId)){
            return repo.findById(playerId).get();
        }else{
            return null;
        }
    }

    @Override
    public boolean deletByPlayerId(long playerId) {
        if(repo.existsById(playerId)){
            repo.deleteById(playerId);
            return true;
        }else{
            return false;
        }
    }

}
