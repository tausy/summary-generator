CREATE TABLE person
(
PersonID int primary key auto_increment,
firstName varchar(255),
surName varchar(255),
gender varchar(255),
contact varchar(255),
address varchar(255),
userName varchar(255),
postalCode varchar(255),
email varchar(255),
password varchar(255),
confirmPassword varchar(255)
);

CREATE TABLE `accidentinfo` (
`ReferenceNumber` varchar(255) DEFAULT NULL,
`Easting` varchar(255) DEFAULT NULL,
`Northing` varchar(255) DEFAULT NULL,
`NumberofVehicles` varchar(255) DEFAULT NULL,
`AccidentDate` varchar(255) DEFAULT NULL,
`timeaccident` varchar(255) DEFAULT NULL,
`firstRoadClass` varchar(255) DEFAULT NULL,
`RoadSurface` varchar(255) DEFAULT NULL,
`LightingConditions` varchar(255) DEFAULT NULL,
`WeatherConditions` varchar(255) DEFAULT NULL,
`CasualtyClass` varchar(255) DEFAULT NULL,
`CasualtySeverity` varchar(255) DEFAULT NULL,
`SexofCasualty` varchar(255) DEFAULT NULL,
`AgeofCasualty` varchar(255) DEFAULT NULL,
`TypeofVehicle` varchar(255) DEFAULT NULL
);

