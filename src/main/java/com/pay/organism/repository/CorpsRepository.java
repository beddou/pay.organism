package com.pay.organism.repository;

import java.util.List;

import com.pay.organism.model.Corps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CorpsRepository extends JpaRepository<Corps, Integer> {
    @Query(value = "SELECT * FROM corps WHERE organism_id = ?1 ", nativeQuery = true)
    List<Corps> findByOrganism(int idOrganism);
    
}
