package com.pay.organism.business;

import java.util.List;
import java.util.Optional;

import com.pay.organism.model.Organism;
import com.pay.organism.repository.OrganismRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrganismBusiness {

    @Autowired
    private OrganismRepository organismRepository;

    @Value("${PayItemUrl}")
    private String resourceUrl;

    public Optional<Organism> getOrganism(int id) {
        return organismRepository.findById(id);
    }

    public List<Organism> findAllOrganisms() {
        return organismRepository.findAll();

    }

    public Organism createOrganism(Organism organism) {
        organism.setActivated(false);
        return organismRepository.save(organism);
    }

    public Organism upDateOrganism(Organism organism) {

        return organismRepository.save(organism);

    }

    public Organism activateOrganism(int idOrganism) {


        
        Organism organism = organismRepository.getById(idOrganism);
        organism.setActivated(true);
        return organismRepository.save(organism);
    }

    public boolean initializeOrganism(int idOrganism) {

        boolean succes = false;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = HttpEntity.EMPTY;
        ResponseEntity responseEntity = restTemplate
                .exchange(resourceUrl + idOrganism, HttpMethod.POST, httpEntity, Void.class);

        if (responseEntity.getStatusCodeValue() == HttpStatus.CREATED.value())

            succes = true;

        return succes;

    }

}
