insert into `User` (Username, Email, Password, UserCreationDate) values ('tomcat', 'tomcat@gmail.com', '$2a$12$b4pnZaCz/XeGrMXsbmue2.mJOfhSfEMW0EQlwcMTDDhOZ2Aq5JrDu', '2012-01-01');
insert into `User` (Username, Email, Password, UserCreationDate) values ('netty', 'netty@gmail.com', '$2a$12$M5qLEGiFDFauayi.1G8/QOih0Nm4unnCQcLoPxEyCqEY5HoQxYZHS', '2016-01-01');
insert into `User` (Username, Email, Password, UserCreationDate) values ('spring', 'spring@gmail.com', '$2a$12$M5qLEGiFDFauayi.1G8/QOih0Nm4unnCQcLoPxEyCqEY5HoQxYZHS', '2010-01-01');

insert into Role (RoleName, Description) values ('User', 'normal user role');
insert into Role (RoleName, Description) values ('Admin', 'normal admin role');

insert into UserRole (UserRoleId, RoleId, uid) values ('a',1,1);
insert into UserRole (UserRoleId, RoleId, uid) values ('b',1,3);
insert into UserRole (UserRoleId, RoleId, uid) values ('c',1,2);
insert into UserRole (UserRoleId, RoleId, uid) values ('d',2,1);