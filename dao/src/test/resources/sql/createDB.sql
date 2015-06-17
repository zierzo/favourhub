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
  PRIMARY KEY (ID)
);

INSERT INTO Country (Country) VALUES ('ExistingCountry');
INSERT INTO Country (Country) VALUES ('CountryToUpdate');
INSERT INTO Country (Country) VALUES ('OtherCountry');
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

INSERT INTO Address (City, CountryID, Address, ZipCode) VALUES ('ExistingCity',1,'Existing Address', 'Z1');
INSERT INTO Address (City, CountryID, Address, ZipCode) VALUES ('CityToUpdate',2,'Address to update', 'Z2');
INSERT INTO Address (City, CountryID, Address, ZipCode) VALUES ('CityToDelete',3,'Address to delete', 'Z3');

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
  CreationDate timestamp NOT NULL,
  LastModifiedAt timestamp DEFAULT NULL,
  Active smallint NOT NULL DEFAULT 0,
  PRIMARY KEY (ID),
  CONSTRAINT Email_UNIQUE UNIQUE  (Email),
  CONSTRAINT FK_Collaborator_Address FOREIGN KEY (ID) REFERENCES Address (ID)
);

INSERT INTO Collaborator (Email, Password, FirstName, LastName, NickName, CreationDate, LastModifiedAt, Active)
            VALUES('ExistingEmail', 'ExistingPassword', 'ExistingFirstName', 'ExistingLastName', 'ExistingNickName',
            TIMESTAMP('2015-06-08 09:00:15'), NULL, 1);

INSERT INTO Collaborator (Email, Password, FirstName, LastName, NickName, CreationDate, LastModifiedAt, Active)
            VALUES('EmailToUpdate', 'PasswordToUpdate', 'FirstNameToUpdate', 'LastNameToUpdate', 'NickNameToUpdate',
            TIMESTAMP('2015-06-09 09:00:20'), NULL, 1);

INSERT INTO Collaborator (Email, Password, FirstName, LastName, NickName, CreationDate, LastModifiedAt, Active)
            VALUES('EmailToDelete', 'PasswordToDelete', 'FirstNameToDelete', 'LastNameToDelete', 'NickNameToDelete',
            TIMESTAMP('2015-06-10 09:00:25'), NULL, 1);



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
INSERT INTO ContactType (Type) VALUES ('OtherType');
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

INSERT INTO ContactDetail (Contact, contactTypeID, Preferred, Active, CollaboratorID)
                          VALUES ('ExistingContact',1,1,1,1);
INSERT INTO ContactDetail (Contact, contactTypeID, Preferred, Active, CollaboratorID)
                          VALUES ('ContactToUpdate',2,0,1,1);
INSERT INTO ContactDetail (Contact, contactTypeID, Preferred, Active, CollaboratorID)
                          VALUES ('ContactToDelete',3,0,1,1);

INSERT INTO ContactDetail (Contact, contactTypeID, Preferred, Active, CollaboratorID)
                          VALUES ('ExistingContact',1,1,1,2);
INSERT INTO ContactDetail (Contact, contactTypeID, Preferred, Active, CollaboratorID)
                          VALUES ('ExistingContact',1,1,1,3);

--
-- Table structure for table FavourType
--
CREATE TABLE FavourType (
  ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  Type varchar(45) NOT NULL,
  PRIMARY KEY (ID)
);

INSERT INTO FavourType (Type) VALUES ('ExistingType');
INSERT INTO FavourType (Type) VALUES ('TypeToUpdate');
INSERT INTO FavourType (Type) VALUES ('TypeToDelete');

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

INSERT INTO OfferedFavour (CollaboratorID, Favour) VALUES (1, 'ExistingFavour');
INSERT INTO OfferedFavour (CollaboratorID, Favour) VALUES (1, 'FavourToUpdate');
INSERT INTO OfferedFavour (CollaboratorID, Favour) VALUES (1, 'FavourToDelete');

INSERT INTO OfferedFavour (CollaboratorID, Favour) VALUES (2, 'ExistingFavour');
INSERT INTO OfferedFavour (CollaboratorID, Favour) VALUES (3, 'ExistingFavour');



