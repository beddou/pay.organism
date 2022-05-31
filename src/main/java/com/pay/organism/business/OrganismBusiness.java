package com.pay.organism.business;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
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
        organism.setCreationDate(LocalDate.now());
        organism.setPayDate(LocalDate.now().withDayOfMonth(1));
        organism.setPrimeDate(LocalDate.now().withDayOfMonth(1));
        organism.setBudget(budget);
        organism = organismRepository.save(organism);

        account.setOrganism(organism);
        account.setRole(role);
        account.setActivated(false);
        account = accountRepository.save(account);
        accountBusiness.sendActivationEmail(account.getId());
        return account;
    }

    @Transactional
    public Organism upDateOrganism(int id, Organism organism) {

        Organism organism1 = organismRepository.findById(id).get();

        if (organism.getBudget() != null)
            organism1.setBudget(organism.getBudget());
        if (organism.getDesign() != null && !organism.getDesign().equals("") && !organism.getDesign().trim().equals(""))
            organism1.setDesign(organism.getDesign());
        if (organism.getPayDate() != null)
            organism1.setPayDate(organism.getPayDate().withDayOfMonth(1));
        if (organism.getPrimeDate() != null)
            organism1.setPrimeDate(organism.getPrimeDate().withDayOfMonth(1));

        return organismRepository.save(organism1);

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
        ResponseEntity<Boolean> responseEntity = restTemplate
                .exchange(ressourceUrl + idOrganism, HttpMethod.POST, httpEntity, Boolean.class);

        if (responseEntity.getStatusCodeValue() == HttpStatus.CREATED.value())
            if (responseEntity.hasBody()) {
                succes = responseEntity.getBody().booleanValue();
            }

        return succes;

    }

    public LocalDate getPayDate(int idOrganism) {

        Optional<Organism> organism = organismRepository.findById(idOrganism);
        if (organism.isPresent())
            return organism.get().getPayDate();
        else
            return null;

    }

    public LocalDate incrementPayDate(int idOrganism) {
        Optional<Organism> organism = organismRepository.findById(idOrganism);
        if (organism.isPresent()) {

            Organism organism1 = organism.get();
            LocalDate date = organism1.getPayDate().plusMonths(1);
            organism1.setPayDate(date);
            organismRepository.save(organism1);
            return date;
        } else
            return null;
    }

    public LocalDate getPrimeDate(int idOrganism) {

        Optional<Organism> organism = organismRepository.findById(idOrganism);
        if (organism.isPresent())
            return organism.get().getPrimeDate();
        else
            return null;

    }

    public LocalDate incrementPrimeDate(int idOrganism) {
        Optional<Organism> organism = organismRepository.findById(idOrganism);
        if (organism.isPresent()) {
            Organism organism1 = organism.get();
            LocalDate date = organism1.getPrimeDate().plusMonths(3);
            organism1.setPrimeDate(date);
            organismRepository.save(organism1);
            return date;
        } else
            return null;

    }

}
