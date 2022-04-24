package com.pay.organism.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.pay.organism.business.AccountBusiness;
import com.pay.organism.business.OrganismBusiness;
import com.pay.organism.dto.AccountOrganismDto;
import com.pay.organism.exceptions.EntityNotFoundException;
import com.pay.organism.exceptions.NoEntityAddedException;
import com.pay.organism.model.Account;
import com.pay.organism.model.Organism;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class OrganismController {

    @Autowired
    private OrganismBusiness organismBusiness;

    @Autowired
    private AccountBusiness accountBusiness;

    @GetMapping(value = "/Organism/Organism/get/{id}")
    public ResponseEntity<Organism> getOrganism(@PathVariable int id) {
        Optional<Organism> organism = organismBusiness.getOrganism(id);
        if (organism.isPresent()) {
            return new ResponseEntity<>(organism.get(), HttpStatus.OK);
        }

        else {
            throw new EntityNotFoundException("organism not found");

        }

    }

    @GetMapping(value = "/Organism/Organism/all")
    public List<Organism> allOrganism() {
        List<Organism> organisms = organismBusiness.findAllOrganisms();
        if (organisms.isEmpty())
            throw new EntityNotFoundException("organism not found");
        else
            return organisms;
    }

    @PostMapping(value = "/Organism/Organism/create")
    public ResponseEntity<Account> createOrganism(@RequestBody AccountOrganismDto accountOrganismDto) {
        try {
            Account account = organismBusiness.createOrganismOfThatAccount(accountOrganismDto);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new NoEntityAddedException("Organism not saved");
        }

    }

    @PutMapping(value = "/Organism/Organism/update/{id}")
    public ResponseEntity<Organism> upDateOrganism(@PathVariable("id") int id, @RequestBody Organism organism) {


            Optional<Organism> organism2 = organismBusiness.getOrganism(id);
            
            if (organism2.isPresent()) {
                try {
                  
                    Organism organism4 = organismBusiness.upDateOrganism(id, organism);
                    return new ResponseEntity<>(organism4, HttpStatus.CREATED);

                } catch (Exception e) {
                    throw new NoEntityAddedException("entity not saved");
                }

            } else {
                throw new EntityNotFoundException("document introuvable");

            }
       

    }

    @PutMapping(value = "/Organism/Organism/activate/{idAccount}")
    public ResponseEntity<Organism> activateOrganism(@PathVariable("idAccount") int idAccount) {

        Optional<Account> account = accountBusiness.getAccount(idAccount);
        if (account.isPresent()) {
            Organism organism = account.get().getOrganism();
            if (!organism.isActivated()) {
                boolean active = organismBusiness.initializeOrganism(organism.getId());
                if (active) {
                    organismBusiness.activateOrganism(organism.getId());
                    accountBusiness.activateAccount(idAccount);
                    accountBusiness.sendActivationSuccesEmail(idAccount);
                    return new ResponseEntity<>(organism, HttpStatus.CREATED);
                } else
                    throw new NoEntityAddedException("entity not saved");
            } else
                throw new NoEntityAddedException("entity not saved");
        } else
            throw new EntityNotFoundException("document introuvable");

    }
}
