package com.pay.organism.business;

import com.pay.organism.model.Account;
import com.pay.organism.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountBusiness {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account){
        account.setActivated(false);
        return accountRepository.save(account);
    }

    public void activateAccount(int idAccount){
        accountRepository.getById(idAccount);
    }
    
}
