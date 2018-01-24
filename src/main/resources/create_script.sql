DROP TABLE IF EXISTS deadline;
DROP TABLE IF EXISTS rf_gender;
DROP TABLE IF EXISTS rf_role;
DROP TABLE IF EXISTS teacher_student;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS teacher;
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
  id       INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  role_id  INT(11)             NOT NULL,
  login    VARCHAR(32)         NOT NULL,
  password VARCHAR(128)        NOT NULL
);

CREATE TABLE student
(
  id           INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_id      INT(11)             NOT NULL,
  first_name   VARCHAR(32)         NOT NULL,
  last_name    VARCHAR(32)         NOT NULL,
  email        VARCHAR(32)         NOT NULL,
  phone_number VARCHAR(32)         NOT NULL,
  address      VARCHAR(32)         NOT NULL,
  gender       INT(11) DEFAULT '0' NOT NULL,
  CONSTRAINT student_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE teacher
(
  id           INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_id      INT(11)             NOT NULL,
  first_name   VARCHAR(32)         NOT NULL,
  last_name    VARCHAR(32)         NOT NULL,
  email        VARCHAR(32)         NOT NULL,
  phone_number VARCHAR(32)         NOT NULL,
  address      VARCHAR(32)         NOT NULL,
  CONSTRAINT teacher_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE teacher_student
(
  teacher_id INT(11) NOT NULL,
  student_id INT(11) NOT NULL,
  CONSTRAINT teacher_student_ibfk_1 FOREIGN KEY (teacher_id) REFERENCES teacher (user_id),
  CONSTRAINT teacher_student_ibfk_2 FOREIGN KEY (student_id) REFERENCES student (user_id)
);

CREATE TABLE deadline
(
  id         INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  student_id INT(11),
  due_date   TEXT,
  homework   TEXT,
  internal   TEXT,
  midterm    TEXT,
  spec_obj   TEXT,
  subject    TEXT,
  CONSTRAINT deadline_ibfk_1 FOREIGN KEY (student_id) REFERENCES student (user_id)
);

CREATE TABLE rf_gender
(
  gender_id   INT(11) NOT NULL,
  gender_name VARCHAR(32)
);
CREATE TABLE rf_role
(
  role_id   INT(11) NOT NULL,
  role_name VARCHAR(32)
);


CREATE INDEX student_id
  ON deadline (student_id);

CREATE INDEX user_id
  ON student (user_id);

CREATE INDEX user_id
  ON teacher (user_id);

CREATE INDEX student_id
  ON teacher_student (student_id);

CREATE INDEX teacher_id
  ON teacher_student (teacher_id);

INSERT INTO rf_role (role_id, role_name) VALUES ('0', 'Teacher');
INSERT INTO rf_role (role_id, role_name) VALUES ('1', 'Student');

INSERT INTO rf_gender (gender_id, gender_name) VALUES ('0', 'Male');
INSERT INTO rf_gender (gender_id, gender_name) VALUES ('1', 'Female');

INSERT INTO lyceum.user (role_id, login, password) VALUES (1, 'valodik', 'root');
INSERT INTO lyceum.user (role_id, login, password) VALUES (0, 'vaxo', 'vaxikyan');
INSERT INTO lyceum.user (role_id, login, password) VALUES (1, 'ashotik', 'ashotikyan');
INSERT INTO lyceum.user (role_id, login, password) VALUES (1, 'debilik', 'debilikyan');

INSERT INTO lyceum.student (user_id, first_name, last_name, email, phone_number, address, gender)
VALUES (1, 'valodik', 'valodikyan', 'valodik@gmail.com', '+37495914536', 'valodikneri poxoc', 0);
INSERT INTO lyceum.student (user_id, first_name, last_name, email, phone_number, address, gender)
VALUES (3, 'ashotik', 'ashotikyan', 'ashotik@gmail.com', '+37495914536', 'ashotikneri poxoc', 0);
INSERT INTO lyceum.student (user_id, first_name, last_name, email, phone_number, address, gender)
VALUES (4, 'debilik', 'debilikyan', 'debilik@gmail.com', '+37495914536', 'debilikneri poxoc', 0);

INSERT INTO lyceum.teacher (user_id, first_name, last_name, email, phone_number, address)
VALUES (2, 'vaxo', 'vaxikyan', 'vaxul@gmail.com', '91505029', 'vaxsjfsafn');