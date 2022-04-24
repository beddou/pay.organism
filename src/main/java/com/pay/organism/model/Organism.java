package com.pay.organism.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pay.organism.model.tools.BudgetType;

@Entity
public class Organism {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    
    @NotNull(message = "Name cannot be null") @NotEmpty @NotBlank
    
    private String design;
    
    @Enumerated(EnumType.STRING)
    private BudgetType budget;
    private Date creationDate;
    @Column(columnDefinition = "boolean default false")
    private boolean activated;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDesign() {
        return design;
    }
    public void setDesign(String design) {
        this.design = design;
    }
    public BudgetType getBudget() {
        return budget;
    }
    public void setBudget(BudgetType budget) {
        this.budget = budget;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public boolean isActivated() {
        return activated;
    }
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    

}
