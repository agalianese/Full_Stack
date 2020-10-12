/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.monstersightings.Repositories;

import com.sg.monstersightings.Entities.Monster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nelsonj
 */
public interface MonsterRepository extends JpaRepository<Monster, Integer> {
//    List findByLocation(List<Location> locations);
//    List findByOrganization(List<Organization> organizations);
}