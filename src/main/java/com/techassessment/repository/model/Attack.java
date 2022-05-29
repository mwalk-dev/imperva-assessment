package com.techassessment.repository.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "optic_data", name = "attacks")
public class Attack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "attack_date", nullable = true)
    private Date attackDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(schema = "optic_data", name = "attack_bots",
        joinColumns = @JoinColumn(name = "attack_id", referencedColumnName = "id", nullable = false, updatable = false),
        inverseJoinColumns = @JoinColumn(name = "bot_id", referencedColumnName = "id", nullable = false, updatable = false)
    )
    private Set<Bot> bots;

    @Column(length = 100, nullable = true)
    private String site;

    // Hibernate constructor
    public Attack() {}

    public Attack(String site, Date attackDate, List<Bot> bots) {
        this.site = site;
        this.attackDate = attackDate;
        this.bots = new HashSet<>(bots);
    }

    public int getId() {
        return id;
    }

    public Date getAttackDate() {
        return attackDate;
    }

    public Set<Bot> getBots() {
        return bots;
    }

    public String getSite() {
        return site;
    }
}
