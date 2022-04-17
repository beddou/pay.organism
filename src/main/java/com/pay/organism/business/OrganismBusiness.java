package com.pay.organism.business;

import java.util.List;

import com.pay.organism.model.Organism;
import com.pay.organism.repository.OrganismRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class OrganismBusiness {

    @Autowired
    private OrganismRepository organismRepository;

  public List<Organism> findAllOrganisms(){
      return organismRepository.findAll();

  }

  public Organism createOrganism(Organism organism){
      organism.setActivated(false);
      return organismRepository.save(organism);
  }

  public void activateOrganism(int idOrganism){
      Organism organism = organismRepository.getById(idOrganism);
      organism.setActivated(true);
      organismRepository.save(organism);
  }

  public void initializeOrganism(int idOrganism){

  }
    
}
