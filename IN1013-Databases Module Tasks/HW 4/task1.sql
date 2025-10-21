CREATE TABLE petPet (
  petname varchar(20),
  owner varchar(45),
  species varchar(45),
  gender ENUM('M', 'F'),
  birth date,
  death date,
  PRIMARY KEY (petname)
);

CREATE TABLE petEvent (
  petname varchar(20),
  eventdate date,
  eventtype varchar(255),
  remark varchar(45),
  FOREIGN KEY (petname) REFERENCES petPet(petname),
  PRIMARY KEY (petname, eventdate)
);
