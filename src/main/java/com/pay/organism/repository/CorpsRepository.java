package com.pay.organism.repository;

import java.util.List;

import com.pay.organism.model.Corps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorpsRepository extends JpaRepository<CorpsRepository, Integer> {
    List<Corps> findByOrganism(int organism);
    
}
