package com.techassessment.controller.model;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

// A model for controller requests to create a new attack
// The DB table actually allows for null site and attack date, which doesn't seem desirable so this class requires both
// values in order to pass validation.
public class CreateAttackData {
    @NotNull
    private Date attackDate;

    @NotEmpty
    private Set<Integer> botIds;

    @NotNull
    @Length(max = 100)
    private String site;

    public Date getAttackDate() {
        return attackDate;
    }

    public Set<Integer> getBotIds() {
        return botIds;
    }

    public String getSite() {
        return site;
    }
}
