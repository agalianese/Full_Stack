/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.monstersightings.Repositories;

import com.sg.monstersightings.Entities.Power;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nelsonj
 */
@Repository
public interface PowerRepository extends JpaRepository<Power, Integer> {

}