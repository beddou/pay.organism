package com.pay.organism.business;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.pay.organism.model.Organism;
import com.pay.organism.model.tools.BudgetType;
import com.pay.organism.model.tools.RoleType;
import com.pay.organism.repository.AccountRepository;
import com.pay.organism.repository.OrganismRepository;
import com.pay.organism.dto.AccountOrganismDto;
import com.pay.organism.model.Account;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class OrganismBusiness {

    @Autowired
    private OrganismRepository organismRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountBusiness accountBusiness;

    @Value("${PayItemUrl}")
    private String ressourceUrl;

    public Optional<Organism> getOrganism(int id) {
        return organismRepository.findById(id);
    }

    public List<Organism> findAllOrganisms() {
        return organismRepository.findAll();

    }

    @Transactional
    public Account createOrganismOfThatAccount(AccountOrganismDto accountOrganismDto) {

        ModelMapper modelMapper = new ModelMapper();
        Organism organism = modelMapper.map(accountOrganismDto, Organism.class);
        Account account = modelMapper.map(accountOrganismDto, Account.class);

        BudgetType budget = BudgetType.valueOf(accountOrganismDto.getOrganismBudget().toUpperCase());
        RoleType role = RoleType.valueOf(accountOrganismDto.getAccountRole().toUpperCase());

        organism.setActivated(false);
        organism.setCreationDate(new Date());
        organism.setBudget(budget);
        organism = organismRepository.save(organism);

        account.setOrganism(organism);
        account.setRole(role);
        account.setActivated(false);
        account = accountRepository.save(account);
        accountBusiness.sendActivationEmail(account.getId());
        return account;
    }

    public Organism upDateOrganism(int id, Organism organism) {

        Optional<Organism> org = organismRepository.findById(id);
        Organism organism2 = org.get();

        if (organism.getBudget() != null)
            organism2.setBudget(organism.getBudget());
        if (organism.getDesign() != null)
            organism2.setDesign(organism.getDesign());

        return organismRepository.save(organism2);

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
                .exchange(ressourceUrl + idOrganism, HttpMethod.POST, httpEntity, Void.class);

        if (responseEntity.getStatusCodeValue() == HttpStatus.CREATED.value())

            succes = true;

        return succes;

    }

}
