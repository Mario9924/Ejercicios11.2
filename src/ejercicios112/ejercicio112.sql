create database ejercicio112;
use ejercicio112

CREATE TABLE `ejercicio112`.`canciones`
 (`ID` INT(10) NOT NULL AUTO_INCREMENT , 
`Nombre` VARCHAR(50) NOT NULL , 
`Url` VARCHAR(500) NOT NULL , 
PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

INSERT INTO `canciones` ( `Nombre`, `Url`) VALUES ( 'Runway', 'https://youtu.be/XXIX2WnfbpE?si=B24zuXGXZJf7Ffge')
INSERT INTO `canciones` ( `Nombre`, `Url`) VALUES ( 'I ﹤3 YOU', 'https://youtu.be/cmFEMLWs-tI?si=TNbrbnRsk8Rgoa5C')
INSERT INTO `canciones` ( `Nombre`, `Url`) VALUES ( 'Heads Will Roll', 'https://youtu.be/vw3k_jWa-gI?si=btIeA_lThXkR0FMn')
INSERT INTO `canciones` ( `Nombre`, `Url`) VALUES ( 'Dracula', 'https://youtu.be/0UPDBODtxzw?si=alQPu1ge7P3Vs4P9')
INSERT INTO `canciones` ( `Nombre`, `Url`) VALUES ( 'JOYRIDE', 'https://youtu.be/bDpi8EdPMhU?si=5E-QJLAK0balDhj7')
INSERT INTO `canciones` ( `Nombre`, `Url`) VALUES ( '365', 'https://youtu.be/Ol9CCM240Ag?si=TrIMp1446Q7vjmZ5')
INSERT INTO `canciones` ( `Nombre`, `Url`) VALUES ( 'Never Dull - Deep down', 'https://youtu.be/O9sxGCTvlLU?si=QU5a891rt11FfXKc')


