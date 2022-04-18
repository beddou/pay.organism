package com.pay.organism.business;

import java.util.List;

import com.pay.organism.model.Corps;
import com.pay.organism.repository.CorpsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorpsBusiness {
    
    @Autowired
    private CorpsRepository corpsRepository;

    public List<Corps> findCorpsByOrganism(int organism){
        return corpsRepository.findByOrganism(organism);

    }

    public Corps updateCorps(Corps corps){
        Corps cor = corpsRepository.getById(corps.getId());
        cor.setCode(corps.getCode());
        cor.setName(corps.getName());
        return corpsRepository.save(cor);

    }

    public Corps createCorps(Corps corps){
        return corpsRepository.save(corps);
    }

    public void deleteCorps(int idCorps){
        corpsRepository.deleteById(idCorps);
    }
}
