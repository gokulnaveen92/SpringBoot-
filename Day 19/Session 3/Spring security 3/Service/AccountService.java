package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Account;
import com.examly.springapp.repository.AccountRepo;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepo aRepo;

    public Account addNew(Account u){
        return aRepo.save(u);
    }

    public List<Account> getAll(){
        return aRepo.findAll();
    }

    public boolean deleteUser(int accountId){
        if(aRepo.existsById(accountId)){
            aRepo.deleteById(accountId);
            return true;
        }else{
            return false;
        }
    }
}
