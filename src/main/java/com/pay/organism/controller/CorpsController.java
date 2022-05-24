package com.pay.organism.controller;

import java.util.List;
import java.util.Optional;

import com.pay.organism.business.CorpsBusiness;
import com.pay.organism.business.OrganismBusiness;
import com.pay.organism.exceptions.EntityNotFoundException;
import com.pay.organism.exceptions.NoEntityAddedException;
import com.pay.organism.model.Corps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class CorpsController {

    @Autowired
    CorpsBusiness corpsBusiness;

    @Autowired
    OrganismBusiness organismBusiness;

    @GetMapping(value = "/Organism/Corps/All/{idOrganism}")
    public List<Corps> allCorpsByOrganism(@PathVariable int idOrganism) {
        List<Corps> corps = corpsBusiness.findCorpsByOrganism(idOrganism);
        if (corps.isEmpty())
            throw new EntityNotFoundException("corps not found");
        else
            return corps;
    }

    @PostMapping(value = "/Organism/Corps/Create")
    public ResponseEntity<Corps> createCorps(@RequestBody Corps corps) {
        try {
            Corps corp = corpsBusiness.createCorps(corps);
            return new ResponseEntity<>(corp, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new NoEntityAddedException("Corps not saved");
        }

    }

    @PutMapping(value = "/Organism/Corps/Update/{id}")
    public ResponseEntity<Corps> upDateCorps(@PathVariable("id") int id, @RequestBody Corps corps) {

        Optional<Corps> corps2 = corpsBusiness.getCorps(id);

        if (corps2.isPresent()) {
            try {

                Corps corps3 = corpsBusiness.updateCorps(id, corps);
                return new ResponseEntity<>(corps3, HttpStatus.CREATED);

            } catch (Exception e) {
                throw new NoEntityAddedException("entity not saved");
            }

        } else {
            throw new EntityNotFoundException("document introuvable");

        }

    }

    @DeleteMapping(value = "/Organism/Corps/Delete/{idCorps}")

    public ResponseEntity<Boolean> deleteCorps(@PathVariable int idCorps) {

        Optional<Corps> corps = corpsBusiness.getCorps(idCorps);
        if (!corps.isPresent())
            throw new EntityNotFoundException("entity introuvable");

        return new ResponseEntity<>(corpsBusiness.deleteCorps(idCorps), HttpStatus.OK);

    }

}
