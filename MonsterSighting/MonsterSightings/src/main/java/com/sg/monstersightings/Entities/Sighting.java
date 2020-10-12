/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.monstersightings.Entities;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author nelsonj
 */
@Entity
public class Sighting implements Serializable {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private int sightingid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="timeofsighting", nullable=false)
    @Past
    private LocalDate timeOfSighting;
    
    @OneToOne
    @JoinColumn(name = "locationid")
    private Location location;
    
    @ManyToOne
    @JoinColumn(name = "monsterid")
    private Monster monster;

    public int getSightingid() {
        return sightingid;
    }

    public void setSightingid(int sightingid) {
        this.sightingid = sightingid;
    }

    public LocalDate getTimeOfSighting() {
        return timeOfSighting;
    }

    public void setTimeOfSighting(LocalDate timeOfSighting) {
        this.timeOfSighting = timeOfSighting;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }



    

    
}

