CREATE DATABASE  IF NOT EXISTS `favour_hub` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `favour_hub`;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Country`
--

DROP TABLE IF EXISTS `Country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Country` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Country` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Country_UNIQUE` (`Country`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `Address`
--

DROP TABLE IF EXISTS `Address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Address` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `City` varchar(45) NOT NULL,
  `CountryID` int(11) NOT NULL,
  `Address` varchar(120) DEFAULT NULL,
   `ZipCode` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Address_Country_idx` (`CountryID`),
  CONSTRAINT `FK_Address_Country` FOREIGN KEY (`CountryID`) REFERENCES `Country` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



--
-- Table structure for table `Collaborator`
--

DROP TABLE IF EXISTS `Collaborator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Collaborator` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `NickName` varchar(45) DEFAULT NULL,
  `CreationDate` datetime NOT NULL,
  `LastModifiedAt` datetime DEFAULT NULL,
  `Active` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Email_UNIQUE` (`Email`),
  CONSTRAINT `FK_Collaborator_Address` FOREIGN KEY (`ID`) REFERENCES `Address` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `ContactType`
--

DROP TABLE IF EXISTS `ContactType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ContactType` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `ContactDetail`
--

DROP TABLE IF EXISTS `ContactDetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ContactDetail` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Contact` varchar(45) NOT NULL,
  `ContactTypeID` int(11) NOT NULL,
  `Preferred` bit(1) DEFAULT b'0',
  `Active` bit(1) DEFAULT b'1',
  `CollaboratorID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Contact_1_idx` (`CollaboratorID`),
  KEY `FK_Contact_Type_idx` (`ContactTypeID`),
  CONSTRAINT `FK_Contact_Type` FOREIGN KEY (`ContactTypeID`) REFERENCES `ContactType` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Contact_Collaborator` FOREIGN KEY (`CollaboratorID`) REFERENCES `Collaborator` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `FavourType`
--
DROP TABLE IF EXISTS `FavourType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FavourType` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `OfferedFavour`
--
DROP TABLE IF EXISTS `OfferedFavour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OfferedFavour` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CollaboratorID` int(11) NOT NULL,
  `Favour` varchar(120) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_OfferedFavour_Collaborator_idx` (`CollaboratorID`),
  CONSTRAINT `FK_OfferedFavour_Collaborator` FOREIGN KEY (`CollaboratorID`) REFERENCES `Collaborator` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET character_set_client = @saved_cs_client */;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


