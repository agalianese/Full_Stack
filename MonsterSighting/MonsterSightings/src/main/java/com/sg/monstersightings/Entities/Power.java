package com.sg.monstersightings.Entities;

/**
 *
 * @author nelsonj
 */


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity(name = "powertable")
public class Power implements Serializable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Id
    private int id;
    
    @Column(name = "powerstring", nullable=false)
    private String powerString;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPowerString() {
        return powerString;
    }

    public void setPowerString(String powerString) {
        this.powerString = powerString;
    }
    
    
}
