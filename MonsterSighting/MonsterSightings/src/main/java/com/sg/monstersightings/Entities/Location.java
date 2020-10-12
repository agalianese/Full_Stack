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
import javax.persistence.ManyToMany;

/**
 *
 * @author nelsonj
 */
    
@Entity(name = "location")
public class Location implements Serializable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Id
    private int id;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private String description;
    
    @Column
    private String address;
    
    @Column
    private String city;
    
    @ManyToMany(mappedBy = "locations")
    private List<Monster> locationMonsters;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Monster> getLocationMonsters() {
        return locationMonsters;
    }

    public void setLocationMonsters(List<Monster> locationMonsters) {
        this.locationMonsters = locationMonsters;
    }


    
    
    


    
}
