create database ejercicio112;
use ejercicio112

CREATE TABLE `ejercicio112`.`canciones`
 (`ID` INT(10) NOT NULL AUTO_INCREMENT , 
`Nombre` VARCHAR(50) NOT NULL , 
`Url` VARCHAR(500) NOT NULL , 
PRIMARY KEY (`ID`)
) ENGINE = InnoDB;