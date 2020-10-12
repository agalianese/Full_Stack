/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.monstersightings.Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author nelsonj
 */
@Entity
public class Monster implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Id
    private int id;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "powerid")
    private Power power;
    
    
    //bridge table for super_has_organization
    @ManyToMany
    @JoinTable(name = "monster_has_organization",
            joinColumns = {@JoinColumn(name = "monsterid")},
            inverseJoinColumns = {@JoinColumn(name = "orgid")})
    private List<Organization> organizations;
    
    //bridge table for super_has_location
    @ManyToMany
    @JoinTable(name = "monster_has_location",
            joinColumns = {@JoinColumn(name = "monsterid")},
            inverseJoinColumns = {@JoinColumn(name = "locationid")})
    private List<Location> locations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

}
    
