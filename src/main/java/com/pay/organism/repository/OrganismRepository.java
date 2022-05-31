package com.pay.organism.repository;

import java.util.List;

import com.pay.organism.model.Organism;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganismRepository extends JpaRepository<Organism, Integer> {

    List<Organism> findAll();

}
