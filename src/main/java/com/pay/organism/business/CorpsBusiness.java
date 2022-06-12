package com.pay.organism.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pay.organism.model.Corps;
import com.pay.organism.repository.CorpsRepository;

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
        Corps corps2 = new Corps();
        if (corps1.isPresent()) {
            corps2 = corps1.get();
            if (corps.getCode() > 0)
                corps2.setCode(corps.getCode());
            if (corps.getDesign() != null && !corps.getDesign().equals("") && !corps.getDesign().trim().equals(""))
                corps2.setDesign(corps.getDesign());
        }
        return corpsRepository.save(corps2);

    }

    public Corps createCorps(Corps corps) {
        return corpsRepository.save(corps);
    }

    public boolean deleteCorps(int idCorps) {

        boolean succes = false;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> httpEntity = HttpEntity.EMPTY;
        ResponseEntity<Boolean> responseEntity = restTemplate
                .exchange(ressourceUrl + idCorps, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value() && responseEntity.hasBody()) {
            boolean reponse = responseEntity.getBody();
            if (reponse) {

                corpsRepository.deleteById(idCorps);
                succes = true;

            }
        }

        return succes;

    }
}
