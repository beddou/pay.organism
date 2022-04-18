package com.pay.organism.business;

import java.time.Duration;
import java.util.List;

import com.pay.organism.model.Organism;
import com.pay.organism.repository.OrganismRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class OrganismBusiness {

    @Autowired
    private OrganismRepository organismRepository;

    @Autowired
	WebClient webClient;

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

  public Mono<Boolean> initializeOrganism(int idOrganism){
   return webClient.get()
    .uri("/initialize/" + idOrganism)
    .retrieve()
    .bodyToMono(Boolean.class).timeout(Duration.ofMillis(10_000));

  }
    
}
