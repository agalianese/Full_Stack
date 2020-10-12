/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.monstersightings.Controller;

import com.sg.monstersightings.Entities.*;
import com.sg.monstersightings.Repositories.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author nelsonj
 */
@Controller
public class MainController {
    
    //sets of validators
    Set<ConstraintViolation<Power>> powerConstraints = new HashSet<>();
    Set<ConstraintViolation<Monster>> heroConstraints = new HashSet<>();
    Set<ConstraintViolation<Location>> locationConstraints = new HashSet<>();
    Set<ConstraintViolation<Organization>> organizationConstraints = new HashSet<>();
    Set<ConstraintViolation<Sighting>> sightingConstraints = new HashSet<>();
    
    //autowire the repositories where the data will be saved
    @Autowired
    LocationRepository locations;
    @Autowired
    OrganizationRepository organizations;
    @Autowired
    MonsterRepository monsters;
    @Autowired
    PowerRepository powers;
    @Autowired
    SightingRepository sightings;
    
    
    /**home page for when you open the application
     * displays the most recent 10 sightings when it opens
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        //pull in all sightings
        List<Sighting> allSightings = sightings.findAll();
        
        //create empty lists
        List<LocalDate> list= new ArrayList<>(); 
        List<Sighting> finalSightings = new ArrayList<>();
        
        //grab the date of every sighting and make it a list
        for (Sighting sight : allSightings) {
            list.add(sight.getTimeOfSighting());
        }
        
        //sort by the date, with most recent first
        Collections.sort(list);
        Collections.reverse(list);
        
        //get only 10 sightings
        if (list.size() < 10) {
            //check if the date matches the sighting, if they match, add it to the finalSIghtings list
            for (LocalDate date : list) {
                for (Sighting sight : allSightings) {
                    if (sight.getTimeOfSighting() == date) {
                        finalSightings.add(finalSightings.size(), sight);
                    } //end of if
                } //end of for sight: allSightings
            }// end of date : list
            
        } else {
            //create a sublist of 10 dates
            List<LocalDate> subList = list.subList(0, 10);
            //check if the date matches the sighting, if they match, add it to the finalSIghtings list
            for (LocalDate date : subList) {
                for (Sighting sight : allSightings) {
                    if (sight.getTimeOfSighting() == date) {
                        finalSightings.add(finalSightings.size(), sight);
                    } //end of if
                } //end of for sight: allSightings
            }// end of date : subList
        }//end of else statement
       
        model.addAttribute("finalSightings", finalSightings);
        return "index";
    }
    
    
    // ============= MONSTER METHODS ====================

    /** Loads webpage used to view all monsters
     * @param model
     */
    @GetMapping("/monsters")
    public String getMonsters (Model model) {
        model.addAttribute("monsters", monsters.findAll());
        model.addAttribute("powers", powers.findAll());
        model.addAttribute("organizations", organizations.findAll());
        model.addAttribute("locations", locations.findAll());
        return "/monsters";
    }
    
    /** Method to add a monster to the database
     * @param monster
     * @param request
     */
    @PostMapping("addMonster")
    public String addMonster(Monster monster, HttpServletRequest request) {
        //pull in power and organizations of that monster
        Integer powerId = Integer.parseInt(request.getParameter("powerId"));
        String[] organizationString = request.getParameterValues("organizationId");

        //create a list to store the organizations
        List<Organization> organizationList = new ArrayList<>();
        
        //try catch for if the monster doesn't belong to any organizations
        try {
            //pull in each organizations and add it to the list
            for (String orgId : organizationString) {
                Organization newOrg = organizations.getOne(Integer.parseInt(orgId));
                organizationList.add(organizationList.size(), newOrg);
            }
        }catch (NullPointerException e) {}
        
        //set the monster's organizations and power
        monster.setOrganizations(organizationList);
        monster.setPower(powers.getOne(powerId));
        
        //save the monster to the database if there are no issues
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        heroConstraints = validate.validate(monster);
        if (heroConstraints.isEmpty()) {
            monsters.save(monster);
        }
        return "redirect:/monsters";
    }
    
    /** Load webpage for editing the monster
     * @param request
     * @param model
     */
    @GetMapping("editMonster")
    public String editMonster(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Monster monster = monsters.findById(id).orElse(null);
        model.addAttribute("monster", monster);
        model.addAttribute("powers", powers.findAll());
        model.addAttribute("organizations", organizations.findAll());
        model.addAttribute("locations", locations.findAll());
        return "editMonster";
    }
    
    /** Method to edit a monster in the database
     * @param request
     * @param monster
     */
    @PostMapping("editMonster")
    public String editMonster(HttpServletRequest request, Monster monster) {
        
        //pull in parameters of power and organizations
        Integer powerid = Integer.parseInt(request.getParameter("Id"));
        Power thisPower = powers.getOne(powerid);
        String[] organizationString = request.getParameterValues("organizationId");

        //create a list to store the organizations
        List<Organization> organizationList = new ArrayList<>();
        try {
            for (String orgId : organizationString) {
                Organization newOrg = organizations.getOne(Integer.parseInt(orgId));
                organizationList.add(organizationList.size(), newOrg);
            }
        }catch (NullPointerException e) {}
        
        //set the fields
        monster.setPower(thisPower);
        monster.setOrganizations(organizationList);
                
        //check for errors and save it to the database
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        heroConstraints = validate.validate(monster);
        if (heroConstraints.isEmpty()) {
            monsters.save(monster);
        }
        return "redirect:/monsters";
    }
   

    /** delete hero method
     * @param monster id
     */
    @GetMapping("/deleteMonster")
    public String deleteMonster(Integer id) {
        //find all sightings and the specific monster
        List<Sighting> allSightings = sightings.findAll();
        Monster currentMonster = monsters.getOne(id);
        
        //delete sightings where the monster is recorded
        //monster handles organizations and location so it will automatically removed from those
        for (Sighting single : allSightings) {
            if (single.getMonster() == currentMonster) {
                sightings.delete(single);
            }
        }
        monsters.deleteById(id);
        return("redirect:/monsters");
        
    }
    
    
    // ============= ORGANIZATION METHODS ====================

    /** Webpage to load organizations
     * @param model
     */
    @GetMapping("/organizations")
    public String getOrganizations (Model model) {
        model.addAttribute("organizations", organizations.findAll());
        model.addAttribute("monsters", monsters.findAll());
        return "/organizations";
    }
    
     /** Add organization method
     * @param organization
     * @param request
     */
    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request) {
        //pull in members of the organizations and create a list to store them
        String[] memberString = request.getParameterValues("monsterId");
        List<Monster> memberList = new ArrayList<>();
        
        //try catch for if the organization has no members
        try {
            for (String memberId : memberString) {
                //pulls in the monster
                Monster newMonster = monsters.getOne(Integer.parseInt(memberId));
                //gets a list of organizations that they are a part of
                List<Organization> organizationsList = newMonster.getOrganizations();
                
                //if the organizations they're apart of doesn't include the organization, add it
                if (!organizationsList.contains(organization)) {
                    organizationsList.add(organizationsList.size(), organization);
                    newMonster.setOrganizations(organizationsList);
                    monsters.save(newMonster);
                }
                
                //add the monster to the list of organization members
                memberList.add(memberList.size(), newMonster);
            }
        } catch (NullPointerException e) {}
        
        
        //save to the organizations repository if there are no issues
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        organizationConstraints = validate.validate(organization);
        if (organizationConstraints.isEmpty()) {
            organizations.save(organization);
        }
        return "redirect:organizations";
    }
    
    /** Webpage to allow the user to edit the organization
     * @param request
     * @param model
     */
    @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = organizations.getOne(id);
        model.addAttribute("organization", organization);
        model.addAttribute("monsters", monsters.findAll());
        return "editOrganization";
    } 
    
    /** Performs the edit function
     * @param organization
     * @param request
     * @return
     */
    @PostMapping("/editOrganization")
    public String editOrganization(Organization organization, HttpServletRequest request) {
        
        //pull in an organization's members
        String[] memberString = request.getParameterValues("monsterId");

        //begin by removing every monster from this organization
        for (Monster monster : monsters.findAll()) {
            List<Organization> organizations1 = monster.getOrganizations();
            organizations1.remove(organization);
            monster.setOrganizations(organizations1);
        }
       //surrond in try catch in case no members are added
       try {
           //for every monster id
            for (String memberId : memberString) {
                //pulls in the monster
                Monster newMonster = monsters.getOne(Integer.parseInt(memberId));
                //gets a list of organizations that they are a part of
                List<Organization> organizationsList = newMonster.getOrganizations();
                //if they're not already a part of this organization, add them to it
                if (!organizationsList.contains(organization)) {
                    organizationsList.add(organizationsList.size(), organization);
                    newMonster.setOrganizations(organizationsList);
                }
            }
        } catch (NullPointerException e) {}
       organizations.save(organization);
       return "redirect:/organizations";
    }
    
    

    /** delete organization method
     * @param organization id
     */
    @GetMapping("/deleteOrganization")
    public String deleteOrganization(Integer id) {
        //pull in the organization and all monsters
        Organization org = organizations.getOne(id);
        List<Monster> allMonsters = monsters.findAll();
        
        //if a monster belongs to an organization, remove it
        for (Monster single : allMonsters) {
            if (single.getOrganizations().contains(org)) {
                single.getOrganizations().remove(org);
            }
        }
        //finish by deleting the organization
        organizations.deleteById(id);
        return("redirect:/organizations");
    } 
    
    
    // ============= LOCATION METHODS ====================

    /** loads locations webpage
     * @param model
     */
    @GetMapping("/locations")
    public String getLocations (Model model) {
        model.addAttribute("locations", locations.findAll());
        return "/locations";
    }
    
    /** Allows the user to add a location
     * @param location
     */
    @PostMapping("/addLocation")
    public String addLocation(Location location) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        locationConstraints = validate.validate(location);
        if (locationConstraints.isEmpty()) {
            locations.save(location);
        }
        return "redirect:/locations";
    }
    
    /** Retrieves the location being edited
     * @param request
     * @param model
     */
    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        //pull in the locations being edited
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locations.findById(id).orElse(null);

        model.addAttribute("location", location);
        return "editLocation";
    } 
    
    /** Saves the edited location to the database
     * @param location
     */
    @PostMapping("/editLocation")
    public String editLocation(Location location) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        locationConstraints = validate.validate(location);
        if (locationConstraints.isEmpty()) {
            locations.save(location);
        }
        return "redirect:/locations";
    }    
        

    /**delete location
     * @param location id
     * @return
     */
    @GetMapping("/deleteLocation")
    public String deleteLocation(Integer id) {
        locations.deleteById(id);
        return("redirect:/locations");
    }
    
    // ============= POWER METHODS ====================

    /** loads powers webpage
     * @param model
     */
    @GetMapping("/powers")
    public String getPower (Model model) {
        model.addAttribute("powers", powers.findAll());
        return "/powers";
    }
    
    /** Adds power to the database
     * @param newPower
     */
    @PostMapping("addPower")
    public String addPower(Power newPower) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        powerConstraints = validate.validate(newPower);
        if (powerConstraints.isEmpty()) {
            powers.save(newPower);
        }
        return "redirect:/powers";
    }
    
    /** Edit power webpage
     * @param power id
     * @param model
     */
    @GetMapping("editPower")
    public String editPower(Integer id, Model model) {
        model.addAttribute("singleSpecie", powers.getOne(id));
        return "editPower";
    }
    
    /** Saves edited power to the database
     * @param editedPower
     */
    @PostMapping("editPower")
    public String editPower(Power editedPower) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        powerConstraints = validate.validate(editedPower);
        if (powerConstraints.isEmpty()) {
            powers.save(editedPower);
        }
        return "redirect:/power";
    }
    
 

    /**deletes Power and changes monster using that power to the none power
     * @param id
     * @return
     */
    @GetMapping("/deletePower")
    public String deletePower(Integer id) {
        //first pull in power and all monsters
        List<Monster> monsterList = monsters.findAll();
        Power nonePower = powers.getOne(1);
        
        //if the monster has the power being deleted, reset it to a power of none
        for (Monster monster : monsterList) {
            if (monster.getPower().getId() == id) {
                monster.setPower(nonePower);
            }//end of if statement
        }//end of for monster : monsterList     
        powers.deleteById(id);
        return("redirect:/powers");
    }
    
    // ============= SIGHTING METHODS ====================

    /** sightings webpage
     * @param model
     */
    
    @GetMapping("/sightings")
    public String getSightings (Model model) {
        model.addAttribute("sightings", sightings.findAll());
        model.addAttribute("monsters", monsters.findAll());
        model.addAttribute("locations", locations.findAll());
        return "/sightings";
    }
    
    /** Adds sighting to the sightings repository
     * @param request
     */
    @PostMapping("/addSighting")
    public String addSighting(HttpServletRequest request) {
        //pull in parameters from the site
        LocalDate date = LocalDate.parse(request.getParameter("timeOfSighting"));
        Integer locationid = Integer.parseInt(request.getParameter("locationId"));
        Integer monsterId = Integer.parseInt(request.getParameter("monsterId"));
        
        //create location and monster objects from their ids
        Location location = locations.getOne(locationid);
        Monster monster = monsters.getOne(monsterId);
        
        //get the list of locations from the monster, and add the new location
        List<Location> monsterLocs = monster.getLocations();
        if (!monsterLocs.contains(location)) {
            monsterLocs.add(monsterLocs.size(), location);
        }

        //create a new sighting object and fill it in
        Sighting sighting = new Sighting(); 
        sighting.setTimeOfSighting(date);
        sighting.setMonster(monster);
        sighting.setLocation(location);
       
        //if there are no issues, save the sighting and update the monster's locations
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        sightingConstraints = validate.validate(sighting);
        if (sightingConstraints.isEmpty()) {
            monster.setLocations(monsterLocs);
            sightings.save(sighting);
        }
        return "redirect:/sightings";
    }
    
    /** edit sighting webpage
     * @param sighting id
     * @param model
     */
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        model.addAttribute("sighting", sightings.getOne(id));
        model.addAttribute("monsters", monsters.findAll());
        model.addAttribute("locations", locations.findAll());
        return "editSighting";
    }
    
    /** Saves the edited sighting
     * @param request
     * @param sighting
     */
    @PostMapping("/editSighting")
    public String editSighting(HttpServletRequest request, Sighting sighting) {
        //pulls in ids nad the id's objects
        Integer locationid = Integer.parseInt(request.getParameter("locationId"));
        Integer monsterId = Integer.parseInt(request.getParameter("monsterId"));
        Monster monster = monsters.getOne(monsterId);
        Location location = locations.getOne(locationid);
        
        //update the monster's locations
        List<Location> monstLocs = monster.getLocations();
        monstLocs.add(monstLocs.size(), location);
        monster.setLocations(monstLocs);
       
        //validate and save the sighting information
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        sightingConstraints = validate.validate(sighting);
        if (sightingConstraints.isEmpty()) {
            sighting.setMonster(monster);
            sighting.setLocation(location);
            monsters.save(monster);
            sightings.save(sighting);
        }
        return "redirect:/sightings";
    }
    
 
    /** delete sighting
     * @param sighting id
     */
    @GetMapping("/deleteSighting")
    public String deleteSighting(Integer id) {
        
        //pull in sighting information
        Sighting sighting = sightings.getOne(id);
        List<Sighting> allSightings = sightings.findAll();
        Monster thisMonster = sighting.getMonster();
        Location thisLocation = sighting.getLocation();
        
        //begin the count for how many times a monster has been seen at this location
        int count = 0;
        
        //search through sightings to find how many times this monster has been seen at this location
        for (Sighting sight : allSightings) {
            if (sight.getLocation().equals(thisLocation)) {
                count += 1;
            }
        }
        
        //if the monster has only been seen at this location once, then remove it from the monster's locations
        if (count == 1) {
            List<Location> locs = thisMonster.getLocations();
            locs.remove(thisLocation);
            thisMonster.setLocations(locs);
            monsters.save(thisMonster);
        }
       
        sightings.deleteById(id);
        return("redirect:/sightings");
    } 
    
   

    // ================ LISTING WEBPAGE METHODS =====================
    

    /** lists members of an organization
     * @param organization id
     * @param model
     */
    @GetMapping("listOrgMembers")
    public String listOrgMembers(Integer id, Model model) {
        Organization org = organizations.findById(id).orElse(null);
        List<Monster> orgMonsters = org.getOrgMonsters();
        model.addAttribute("orgMembers", orgMonsters);
        return "listOrgMembers";
    }
    
    /**
     *lists organizations a monster is part of
     * @param monster Id
     * @param model
     */
    @GetMapping("listOrganizations")
    public String listOrganizations(Integer id, Model model) {
        Monster monster = monsters.getOne(id);
        List<Organization> getOrgsFromHero = monster.getOrganizations();
        
          model.addAttribute("organizations", getOrgsFromHero);
          return "listOrganizations";
    }
    
   
    /** lists what monsters have been sighted at a location
     * @param location id
     * @param model
     */
    @GetMapping("listMonstersByLoc")
    public String listMonstersByLoc(Integer id, Model model) {
        //pull in the location and all monsters
        Location loc = locations.getOne(id);
        List<Monster> monsterList = monsters.findAll();
        
        //create a new list to store the monsters of this location
        List<Monster> locMonsters = new ArrayList<>();
        
        //for every monster, if they have been seen at this location add them to the list
        for (Monster monster : monsterList) {
            if (monster.getLocations().contains(loc)) {
                locMonsters.add(locMonsters.size(), monster);
            } //end of if
        } //end of for loop
   
        model.addAttribute("monsters", locMonsters);
        return "listMonstersByLoc";
    }
    
    /** lists locations a monster has been
     * @param monster id
     * @param model
     */
    @GetMapping("listLocations")
    public String listLocations(Integer id, Model model) {
        Monster monster = monsters.getOne(id);
        List<Location> getLocsFromMonster = monster.getLocations();
        model.addAttribute("locations", getLocsFromMonster);
        return "listLocations";
    }
   
    

    
    /** find other sightings that are found on the same date
     * @param sighting id
     * @param model
     */
    @GetMapping("listSightings")
    public String listSightings(Integer id, Model model) {
        //pull in sighting id, date of the sighting, and all sightings
        Sighting sighting = sightings.getOne(id);
        List<Sighting> sightingsList = new ArrayList<>();
        List<Sighting> allSightings = sightings.findAll();
        
        //find where the sightings date match and add to the list
        for (Sighting sight : allSightings) {
            //dont add the original sighting
            if (sight.getTimeOfSighting().equals(sighting.getTimeOfSighting()) && !sighting.equals(sight)) {
                sightingsList.add(sightingsList.size(), sight);
            }
        }
         
        model.addAttribute("sightingsList", sightingsList);
        return "listSightings";
    }
    
    
} //end of controller
