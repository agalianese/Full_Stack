DROP DATABASE IF EXISTS monsterDB;
CREATE DATABASE monsterDB;

USE monsterDB;

CREATE TABLE powerTable(
	id INT PRIMARY KEY AUTO_INCREMENT,
    powerString VARCHAR(45) NOT NULL
);

#begin by creating monster table
CREATE TABLE monster(
    id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    `description` VARCHAR(100),
    powerId INT,
    FOREIGN KEY (powerId) REFERENCES powerTable(id)
);

#create organization table
CREATE TABLE org(
    id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    `description` VARCHAR(100),
    phoneNumber VARCHAR(100)
);

#create location table
CREATE TABLE location(
    id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(100),
    `description` VARCHAR(100),
    address VARCHAR(100), 
    city VARCHAR(100)
);

#create location + cryptid bridge table
CREATE TABLE monster_has_location(
	monster_has_locationId INT AUTO_INCREMENT,
    monsterId int not null,
	locationId int not null,
	primary key(monster_has_locationId, monsterId, locationId),
	foreign key (monsterId) references monster(id),
	foreign key (locationId) references location(id)
);

#create org + cryptid bridge table
CREATE TABLE monster_has_organization(
	monsterId INT NOT NULL,
    orgId INT NOT NULL,
    primary key(orgId, monsterId),
	foreign key (monsterId) references monster(id),
	foreign key (orgId) references org(id)
);

CREATE TABLE sighting(
	sightingId INT PRIMARY KEY AUTO_INCREMENT,
	timeOfSighting DATE NOT NULL,
    locationId INT,
    monsterId INT,
    FOREIGN KEY (locationId) REFERENCES location(id),
    FOREIGN KEY (monsterId) REFERENCES monster(id)
);