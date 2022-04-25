package com.pay.organism.business;

import java.util.List;
import java.util.Optional;

import com.pay.organism.model.Corps;
import com.pay.organism.repository.CorpsRepository;
import com.pay.organism.repository.OrganismRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CorpsBusiness {

    @Autowired
    private CorpsRepository corpsRepository;

    @Value("${SalariedUrl}")
    private String ressourceUrl;

    public List<Corps> findCorpsByOrganism(int idOrganism) {
        return corpsRepository.findByOrganism(idOrganism);

    }

    public Optional<Corps> getCorps(int id) {
        return corpsRepository.findById(id);
    }

    public Corps updateCorps(int id, Corps corps) {
        Optional<Corps> corps1 = corpsRepository.findById(id);
        Corps corps2 = corps1.get();
        if (corps.getCode() > 0)
            corps2.setCode(corps.getCode());
        if (corps.getName() != null)
            corps2.setName(corps.getName());
        return corpsRepository.save(corps2);

    }

    public Corps createCorps(Corps corps) {
        return corpsRepository.save(corps);
    }

    public void deleteCorps(int idCorps) {

        /*RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = HttpEntity.EMPTY;
        ResponseEntity<Boolean> responseEntity = restTemplate
                .exchange(ressourceUrl + idCorps, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseEntity.getBody()) {
            corpsRepository.deleteById(idCorps);
        }
        ;*/
        corpsRepository.deleteById(idCorps);

    }
}
