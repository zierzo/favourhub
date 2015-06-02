DROP TABLE Collaborator;
DROP TABLE Address;
DROP TABLE Country;

DROP TABLE ContactDetail;
DROP TABLE ContactType;

DROP TABLE FavourType;
DROP TABLE OfferedFavour;

--
-- Table structure for table Country
--
CREATE TABLE Country (
  ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  Country varchar(45) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT Country_UNIQUE UNIQUE  (Country)
);

INSERT INTO Country (Country) VALUES ('ExistingCountry');
INSERT INTO Country (Country) VALUES ('CountryToUpdate');
INSERT INTO Country (Country) VALUES ('CountryToDelete');



--
-- Table structure for table Address
--;
CREATE TABLE Address (
  ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  City varchar(45) NOT NULL,
  CountryID int NOT NULL,
  Address varchar(120) DEFAULT NULL,
  ZipCode varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_Address_Country FOREIGN KEY (CountryID) REFERENCES Country (ID)
);



--
-- Table structure for table Collaborator
--
CREATE TABLE Collaborator (
  ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  Email varchar(45) NOT NULL,
  Password varchar(45) NOT NULL,
  FirstName varchar(20) NOT NULL,
  LastName varchar(20) NOT NULL,
  NickName varchar(45) DEFAULT NULL,
  AddressID int DEFAULT NULL,
  CreationDate timestamp NOT NULL,
  LastModifiedAt timestamp DEFAULT NULL,
  Active smallint NOT NULL DEFAULT 0,
  PRIMARY KEY (ID),
  CONSTRAINT Email_UNIQUE UNIQUE  (Email),
  CONSTRAINT FK_Collaborator_Address FOREIGN KEY (AddressID) REFERENCES Address (ID)
);

--
-- Table structure for table ContactType
--
CREATE TABLE ContactType (
  ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  Type varchar(45) NOT NULL,
  PRIMARY KEY (ID)
);

INSERT INTO ContactType (Type) VALUES ('ExistingType');
INSERT INTO ContactType (Type) VALUES ('TypeToUpdate');
INSERT INTO ContactType (Type) VALUES ('TypeToDelete');

--
-- Table structure for table ContactDetail
--
CREATE TABLE ContactDetail (
  ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  Contact varchar(45) NOT NULL,
  ContactTypeID int NOT NULL,
  Preferred smallint DEFAULT 0,
  Active smallint DEFAULT 1,
  CollaboratorID int DEFAULT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_Contact_Type FOREIGN KEY (ContactTypeID) REFERENCES ContactType (ID),
  CONSTRAINT FK_Contact_Collaborator FOREIGN KEY (CollaboratorID) REFERENCES Collaborator (ID)
);

--
-- Table structure for table FavourType
--
CREATE TABLE FavourType (
  ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  Type varchar(45) NOT NULL,
  PRIMARY KEY (ID)
);

--
-- Table structure for table OfferedFavour
--;
CREATE TABLE OfferedFavour (
  ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  CollaboratorID int NOT NULL,
  Favour varchar(120) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_OfferedFavour_Collaborator FOREIGN KEY (CollaboratorID) REFERENCES Collaborator (ID)
);


