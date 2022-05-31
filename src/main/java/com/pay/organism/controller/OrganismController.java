package com.pay.organism.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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

    @GetMapping(value = "/Organism/Organism/Get/{id}")
    public ResponseEntity<Organism> getOrganism(@PathVariable("id") int id) {
        Optional<Organism> organism = organismBusiness.getOrganism(id);
        if (organism.isPresent()) {
            return new ResponseEntity<>(organism.get(), HttpStatus.OK);
        }

        else {
            throw new EntityNotFoundException("organism not found");

        }

    }

    @GetMapping(value = "/Organism/Organism/All")
    public List<Organism> allOrganism() {
        List<Organism> organisms = organismBusiness.findAllOrganisms();
        if (organisms.isEmpty())
            throw new EntityNotFoundException("organism not found");
        else
            return organisms;
    }

    @PostMapping(value = "/Organism/Organism/Create")
    public ResponseEntity<Account> createOrganism(@RequestBody AccountOrganismDto accountOrganismDto) {
        try {
            Account account = organismBusiness.createOrganismOfThatAccount(accountOrganismDto);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new NoEntityAddedException("Organism not saved");
        }

    }

    @PutMapping(value = "/Organism/Organism/Update/{id}")
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

    @PutMapping(value = "/Organism/Organism/Activate/{idAccount}")
    public ResponseEntity<Organism> activateOrganism(@PathVariable("idAccount") int idAccount) {

        Optional<Account> account = accountBusiness.getAccount(idAccount);
        if (account.isPresent()) {
            Organism organism = account.get().getOrganism();
            if (!organism.isActivated()) {
                boolean succes = organismBusiness.initializeOrganism(organism.getId());
                if (succes) {
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

    @GetMapping(value = "/Organism/Organism/GetPayDate/{id}")
    public ResponseEntity<LocalDate> getPayDate(@PathVariable int id) {

        LocalDate payDate = organismBusiness.getPayDate(id);
        if (payDate != null) {
            return new ResponseEntity<>(payDate, HttpStatus.OK);
        }

        else {
            throw new EntityNotFoundException("Date of Pay not found");

        }

    }

    @PutMapping(value = "/Organism/Organism/IncrementPayDate/{id}")
    public ResponseEntity<LocalDate> incrementPayDate(@PathVariable("id") int id) {
        LocalDate payDate = organismBusiness.incrementPayDate(id);
        if (payDate != null) {
            return new ResponseEntity<>(payDate, HttpStatus.OK);
        }

        else {
            throw new EntityNotFoundException("Date of Pay not incremented");

        }

    }

    @GetMapping(value = "/Organism/Organism/GetPrimeDate/{id}")
    public ResponseEntity<LocalDate> getPrimeDate(@PathVariable int id) {

        LocalDate primeDate = organismBusiness.getPrimeDate(id);
        if (primeDate != null) {
            return new ResponseEntity<>(primeDate, HttpStatus.OK);
        }

        else {
            throw new EntityNotFoundException("Date of Pay not found");

        }

    }

    @PutMapping(value = "/Organism/Organism/IncrementPrimeDate/{id}")
    public ResponseEntity<LocalDate> incrementPrimeDate(@PathVariable("id") int id) {
        LocalDate primeDate = organismBusiness.incrementPrimeDate(id);
        if (primeDate != null) {
            return new ResponseEntity<>(primeDate, HttpStatus.OK);
        }

        else {
            throw new EntityNotFoundException("Date of Pay not incremented");

        }

    }
}
