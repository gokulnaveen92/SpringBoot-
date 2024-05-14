package com.examly.springapp.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.repository.PlayerRepo;
import com.examly.springapp.repository.TeamRepo;
import com.examly.springapp.entity.Player;
import com.examly.springapp.entity.Team;
import com.examly.springapp.exception.PlayerAlreadyAssignedException;
import com.examly.springapp.service.OrganizerService;

@Service
public class OrganizerServiceImpl implements OrganizerService{

    @Autowired
    private PlayerRepo pRepo;

    @Autowired
    private TeamRepo trepo;

    @Override
    public List<Player> getUnsoldPlayers() {
       return pRepo.findBySoldFalse();
    }

    @Override
    public List<Player> getSoldPlayers() {
        return pRepo.findBySoldTrue();
    }

    @Override
    public boolean assignPlayerToTeam(long playerId, long teamId) {
      if(trepo.existsById(teamId) && pRepo.existsById(playerId)){
        Player p = pRepo.findById(playerId).get();
        Team t = trepo.findById(teamId).get();
        if(p.getTeam() == null){
          t.add(p);
          p.setTeam(t);
          pRepo.save(p);
          trepo.save(t);
          return true;
        }else{
          throw new PlayerAlreadyAssignedException("Player already  assigned to a team ");
        }
      }else{
        return false;
      }
    }

    @Override
    public void releasePlayerFromTeam(long playerId) {
      if(pRepo.existsById(playerId)){
        Player p = pRepo.findById(playerId).get();
        p.setTeam(null);
        p.setSold(false);
      }
    }

    @Override
    public List<Player> getPLayerList(long teamId) {
      if(trepo.existsById(teamId)){
        Team t = trepo.findById(teamId).get();
        return t.getPlayers();
      }else{
        return null;
      }
    }

}
