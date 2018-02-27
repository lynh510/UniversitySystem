create database UniversitySystem
go
use UniversitySystem
go

create table Person(
person_id int primary key,
person_picture varchar(500),
person_name varchar(50),
person_role tinyint, --0 for student, 1 for staff , 2 for anonymouse user, etc
birthdate date,
gender tinyint, --0 for male, 1 for female
status tinyint, --0 for active, 1 for disable
phone_number varchar(14),
enroll_date datetime,
address varchar(500),
email varchar(50),
description varchar(500)
);

create table Department(
dept_id int primary key identity,
dept_name varchar(100),
);

create table StaffRole(
role_id int primary key identity,
role_name varchar(50), 
dept_id int foreign key references Department(dept_id) 
);

create table Student(
student_id int foreign key references Person(person_id),
username varchar(50),
student_password varchar(20),
);

create table Staff(
staff_id int foreign key references Person(person_id),
role_id int foreign key references StaffRole(role_id),
username varchar(50),
staff_password varchar(20)
);

create table Tag(
tag_id int primary key identity,
tag_des varchar(500) --services, courses
);

create table Idea(
idea_id int primary key identity,
idea_des varchar(1000),
person_id int foreign key references Person(person_id),
post_date datetime,
close_date datetime,
idea_views int,
status tinyint -- 0 for pending, 1 for opening, 2 for closed
);

create table Idea_tags(
idea_tag_id int primary key identity,
idea_id int foreign key references Idea(idea_id),
tag_id int foreign key references Tag(tag_id),
);

create table Idea_attachfiles(
idea_id int foreign key references Idea(idea_id),
attachfile_id int primary key identity,
link varchar(500),

)

create table Emoji(
emo_id int primary key identity,
emo_des varchar(50), --Like and Dislike
);

create table Idea_emojis(
idea_emoji_id int primary key identity,
emo_id int foreign key references Emoji(emo_id),
idea_id int foreign key references Idea(idea_id),
person_id int foreign key references Person(person_id)
);

create table Comments(
comment_id int primary key identity,
idea_id int foreign key references Idea(idea_id),
person_id int foreign key references Person(person_id),
comment_text varchar(500),
); 

create table TermConditions(
term_id int primary key identity,
term_des varchar(1000)
)

----- extra to do if have enough time 
create table CommentLike(
id int primary key identity,
emo_id int foreign key references Emoji(emo_id),
)

create table NotificationS(
id int primary key identity,
status int, -- seen, not seen
idea_id int foreign key references Idea(idea_id),
created_time datetime,
)