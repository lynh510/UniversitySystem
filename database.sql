create database UniversitySystem
go
use UniversitySystem
go

create table Person(
person_id int identity(1,1) primary key,
person_picture varchar(500),
person_name varchar(50),
person_role tinyint, --0 for student, 1 for staff , 2 for anonymouse user, etc
birthdate date,
gender tinyint, --0 for male, 1 for female
_status tinyint, --0 for active, 1 for disable
phone_number varchar(14),
enroll_date datetime,
_address varchar(500),
email varchar(50),
_description varchar(500)
);

create table Department(
dept_id int primary key identity,
dept_name varchar(100),
);

create table Student(
student_id int foreign key references Person(person_id) UNIQUE,
username varchar(50),
password_hash binary(64) not null,
salt UNIQUEIDENTIFIER 
);

create table QAManager(
manager_id int foreign key references Person(person_id) UNIQUE,
username varchar(50),
password_hash binary(64) not null,
salt UNIQUEIDENTIFIER 
);

create table Administrator(
admin_id int foreign key references Person(person_id) UNIQUE,
username varchar(50),
password_hash binary(64) not null,
salt UNIQUEIDENTIFIER 
);

create table Staff(
staff_id int foreign key references Person(person_id) UNIQUE,
dep_id int foreign key references Department(dept_id),
username varchar(50),
password_hash binary(64) not null,
salt UNIQUEIDENTIFIER 
);

create table Tag(
tag_id int primary key identity,
tag_des varchar(50), --services, courses
tag_created_time datetime,
tag_close_time datetime,
tag_status int
);

create table Idea(
idea_id int primary key identity,
idea_title varchar(500),
idea_content varchar(2000),
person_id int foreign key references Person(person_id),
post_date datetime,
close_date datetime,
idea_views int,
mode tinyint, --1 for publish, 0 for anonymous
_status tinyint -- 0 for pending, 1 for opening, 2 for closed
);

create table Idea_tags(
idea_tag_id int primary key identity,
idea_id int foreign key references Idea(idea_id),
tag_id int foreign key references Tag(tag_id),
);

create table Idea_attachfiles(
attachfile_id int primary key identity,
idea_id int foreign key references Idea(idea_id),
old_name varchar(500),
new_name varchar(500),
file_type varchar(20),
link varchar(500)
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
comment_time datetime
); 

create table UserExternalLogin(
userid int foreign key references Person(person_id),
email varchar(50),
);


SELECT * FROM Student
insert into Emoji values ('Thumb Up'),('Thumb Down')
insert into Tag values ('Course'),('Service')
-- stored procdure for adding new student
--run from here
CREATE PROCEDURE add_student
    @stuID int,
	@stuUserName VARCHAR(50),
    @stuPassword VARCHAR(50),
    @responseMessage NVARCHAR(250) OUTPUT
AS
BEGIN
    SET NOCOUNT ON

    DECLARE @salt UNIQUEIDENTIFIER=NEWID()
    BEGIN TRY

        INSERT INTO Student(student_id, username,password_hash, salt)
        VALUES(@stuID,@stuUserName, HASHBYTES('SHA2_512', @stuPassword+CAST(@salt AS NVARCHAR(36))), @salt)

       SET @responseMessage='Success'

    END TRY
    BEGIN CATCH
        SET @responseMessage=ERROR_MESSAGE() 
    END CATCH

END
---stop here
-- Stored procedure for loggin in 
---run from here
CREATE PROCEDURE student_login
    @stuUsername NVARCHAR(254),
    @stuPassword NVARCHAR(50)
AS
BEGIN

    SET NOCOUNT ON

    DECLARE @userID INT

    IF EXISTS (SELECT TOP 1 student_id FROM Student WHERE username=@stuUsername)
    BEGIN
        SET @userID=(SELECT student_id FROM Student WHERE username=@stuUsername AND password_hash=HASHBYTES('SHA2_512', @stuPassword+CAST(salt AS NVARCHAR(36))))

       IF(@userID IS NULL)
           select * from Person where person_id = 0
       ELSE 
           select * from Person where person_id = @userID
    END
    ELSE
       select * from Person where person_id = 0

END
--stop here

-- start here 
CREATE PROCEDURE add_QAManager
    @managerID int,
	@managerUserName VARCHAR(50),
    @managerPassword VARCHAR(50),
    @responseMessage NVARCHAR(250) OUTPUT
AS
BEGIN
    SET NOCOUNT ON

    DECLARE @salt UNIQUEIDENTIFIER=NEWID()
    BEGIN TRY

        INSERT INTO QAManager(manager_id, username,password_hash, salt)
        VALUES(@managerID,@managerUserName, HASHBYTES('SHA2_512', @managerPassword+CAST(@salt AS NVARCHAR(36))), @salt)

       SET @responseMessage='Success'

    END TRY
    BEGIN CATCH
        SET @responseMessage=ERROR_MESSAGE() 
    END CATCH

END
--stop here
CREATE PROCEDURE QAManager_login
    @managerUsername NVARCHAR(254),
    @managerPassword NVARCHAR(50)
AS
BEGIN

    SET NOCOUNT ON

    DECLARE @userID INT

    IF EXISTS (SELECT TOP 1 manager_id FROM QAManager WHERE username=@managerUsername)
    BEGIN
        SET @userID=(SELECT manager_id FROM QAManager WHERE username=@managerUsername AND password_hash=HASHBYTES('SHA2_512', @managerPassword+CAST(salt AS NVARCHAR(36))))

       IF(@userID IS NULL)
           select * from Person where person_id = 0
       ELSE 
           select * from Person where person_id = @userID
    END
    ELSE
       select * from Person where person_id = 0

END
--------
DECLARE @responseMessage NVARCHAR(250);
Exec add_student 1,'username','password', @responseMessage output;
select @responseMessage as N'@responseMessage'

----- extra to do if have enough time 
create table CommentLike(
id int primary key identity,
emo_id int foreign key references Emoji(emo_id),
)

create table Activity(
activity_id int primary key identity,
activity_name varchar(100) --comment on an idea, idea is accepted
)

create table _Notification(
id int primary key identity,
is_read boolean, -- seen, not seen
sender_id int foreign key references Person(person_id), --who commented
recipient_id int foreign key references Person(person_id), -- is the one who receives
activity_type int foreign key references Activity(activity_id),
notification_description varchar(100), --John just commented into your post .. ago
url varchar(500),
time_sent datetime
)

select * from idea

SELECT * FROM Idea  Where person_id = 1 ORDER BY post_date DESC