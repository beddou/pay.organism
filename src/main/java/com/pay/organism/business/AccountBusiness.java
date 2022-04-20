package com.pay.organism.business;

import java.util.Optional;

import com.pay.organism.model.Account;
import com.pay.organism.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountBusiness {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> getAccount(int id) {
        return accountRepository.findById(id);
    }


    public Account createAccount(Account account){
        account.setActivated(false);
        return accountRepository.save(account);
    }

    public Account activateAccount(int idAccount){
        Account account = accountRepository.getById(idAccount);
        account.setActivated(true);
        return accountRepository.save(account);
    }

    public void sendActivationEmail(){
        
    }
    
}
