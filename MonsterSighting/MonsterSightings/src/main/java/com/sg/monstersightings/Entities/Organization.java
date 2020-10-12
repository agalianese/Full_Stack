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
@Entity(name = "org")
public class Organization implements Serializable{
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Id
    private int id;
    
    @Column(nullable=false)
    private String name;
    
    @Column
    private String description;
    
    @Column(name="phonenumber")
    private String phoneNumber;
    
    @ManyToMany(mappedBy = "organizations")
    private List<Monster> orgMonsters;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Monster> getOrgMonsters() {
        return orgMonsters;
    }

    public void setOrgMonsters(List<Monster> orgMonsters) {
        this.orgMonsters = orgMonsters;
    }






       
       
}
