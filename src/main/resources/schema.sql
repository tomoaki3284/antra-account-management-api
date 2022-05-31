create table `User`(
  uid int not null AUTO_INCREMENT,
  Username varchar(20) not null,
  Email varchar(30) not null,
  Password varchar(300) not null,
  UserCreationDate DATE NOT NULL,
  PRIMARY KEY (uid)
);

create table Role(
  RoleId int not null AUTO_INCREMENT,
  RoleName varchar(20) not null,
  Description varchar(100),
  PRIMARY KEY (RoleId)
);

create table UserRole(
  UserRoleId varchar(20) not null,
  RoleId int not null,
  uid int not null,
  foreign key (uid) references `User`(uid)
    on delete cascade on update cascade,
  foreign key (RoleId) references `Role`(RoleId)
    on delete cascade on update cascade
);
