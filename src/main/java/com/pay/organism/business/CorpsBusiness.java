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
import org.springframework.http.HttpStatus;
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
        
        Corps corps1 = corpsRepository.findById(id).get();
        if (corps.getCode() > 0)
            corps1.setCode(corps.getCode());
        if (corps.getName() != null)
            corps1.setName(corps.getName());
        return corpsRepository.save(corps1);

    }

    public Corps createCorps(Corps corps) {
        return corpsRepository.save(corps);
    }

    public boolean deleteCorps(int idCorps) {

        boolean succes = false;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = HttpEntity.EMPTY;
        ResponseEntity<Boolean> responseEntity = restTemplate
                .exchange(ressourceUrl + idCorps, HttpMethod.GET, httpEntity, Boolean.class);
        if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value())
            if (responseEntity.hasBody())
                if (responseEntity.getBody().booleanValue()) {

                    corpsRepository.deleteById(idCorps);
                    succes = true;

                }

        return succes;

    }
}
