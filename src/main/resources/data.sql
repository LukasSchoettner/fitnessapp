INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Regensburg','1', 'Markusplatz', '93047');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Regensburg','2', 'Arnulfsplatz', '93049');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Regensburg','3', 'Musterstraße', '93057');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Munich', '4', 'Hauptstraße', '80331');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Berlin', '5', 'Alexanderplatz', '10178');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Hamburg', '6', 'Königstraße', '20095');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Cologne', '7', 'Domplatz', '50667');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Frankfurt', '8', 'Römerberg', '60311');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Cologne', '7', 'Waldweg', '50667');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Frankfurt', '8', 'Marktplatz', '60311');


INSERT INTO baseuser (login, password, email, active, address_id) values ('franz', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'elke@gmail.com', 1, '1');
INSERT INTO baseuser (login, password, email, active, address_id) values ('doe', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'doe@example.com', 1, '2');
INSERT INTO baseuser (login, password, email, active, address_id) values ('smith', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'smith.alice@gmail.com', 1, '3');
INSERT INTO baseuser (login, password, email, active, address_id) values ('jones', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'bob@gmail.com', 1, '4');
INSERT INTO baseuser (login, password, email, active, address_id) values ('doej', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'charlie.do@example.com', 1, '5');
INSERT INTO baseuser (login, password, email, active, address_id) values ('wilson', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'diana@gmail.com', 1, '6');
INSERT INTO baseuser (login, password, email, active, address_id) values ('miller', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'millerk@gmail.com', 1, '7');
INSERT INTO baseuser (login, password, email, active, address_id) values ('baker', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'baker.chris@example.com', 1, '8');
INSERT INTO baseuser (login, password, email, active, address_id) values ('ray', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'ray.john@gmail.com', 1, '9');
INSERT INTO baseuser (login, password, email, active, address_id) values ('sailor', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'sailor.jessica@example.com', 1, '10');

INSERT INTO trainer(id, last_name, first_name, phone, birth_date) VALUES (1, 'Franz', 'Elke', '0234 567890', '1986-08-15');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date) VALUES (2,'Doe', 'John', '0151 111111', '1985-05-15');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date) VALUES (3,'Smith', 'Alice', '0176 815234', '1990-08-21');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date) VALUES (4,'Jones', 'Bob', '0123 456789', '1985-05-15');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date) VALUES (5,'Doe', 'Charlie', '0234 567890', '1992-11-03');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date) VALUES (6,'Wilson', 'Diana', '0345 678901', '1988-07-09');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date) VALUES (7,'Miller', 'Kevin', '0567 890123', '1982-02-14');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date) VALUES (8,'Baker', 'Chris', '0234 567890', '1986-08-15');


INSERT INTO customer (id, gender, last_name, first_name) VALUES (9, 'MALE', 'Ray', 'John');
INSERT INTO customer (id, gender, last_name, first_name) VALUES (10, 'FEMALE', 'Sailor', 'Jessica');


INSERT INTO course (trainer_id, name, date) VALUES (1, 'Cardio Fitness Class', '2024-05-17');
INSERT INTO course (trainer_id, name, date) VALUES (1, 'Strength Training Workshop', '2023-03-21');
INSERT INTO course (trainer_id, name, date) VALUES (1, 'Yoga Retreat', '2024-04-28');
INSERT INTO course (trainer_id, name, date) VALUES (1, 'HIIT Bootcamp', '2024-05-01');
INSERT INTO course (trainer_id, name, date) VALUES (1, 'Outdoor Fitness Challenge', '2024-02-04');
INSERT INTO course (trainer_id, name, date) VALUES (1, 'Core Strengthening Workshop', '2024-08-11');
INSERT INTO course (trainer_id, name, date) VALUES (2, 'Pilates for Beginners', '2024-01-04');
INSERT INTO course (trainer_id, name, date) VALUES (2, 'Outdoor Fitness Challenge', '2023-02-15');
INSERT INTO course (trainer_id, name, date) VALUES (3, 'Core Strengthening Workshop', '2023-03-15');
INSERT INTO course (trainer_id, name, date) VALUES (4, 'Zumba Dance Party', '2024-05-20');


INSERT INTO NOTE(DATE, COURSE_ID, TRAINER_ID, MESSAGE, TITLE) VALUES('2024-01-01','1','3','Gutes Neues Jahr','Happy new Year');
INSERT INTO NOTE(DATE, COURSE_ID, TRAINER_ID, MESSAGE, TITLE) VALUES('2024-01-18','3','1','Hallo alle zusammen','Hallo');


INSERT INTO ROLE (description) VALUES ( 'ADMIN');
INSERT INTO ROLE (description) VALUES ( 'TRAINER');
INSERT INTO ROLE (description) VALUES ( 'CUSTOMER');


INSERT INTO USERROLE(iduser, idrole) VALUES (1,1);
INSERT INTO USERROLE(iduser, idrole) VALUES (1,2);
INSERT INTO USERROLE(iduser, idrole) VALUES (2,3);
INSERT INTO USERROLE(iduser, idrole) VALUES (9,3);
INSERT INTO USERROLE(iduser, idrole) VALUES (10,3);


INSERT INTO course_participant (course_id, customer_id) VALUES (1, 9);
INSERT INTO course_participant (course_id, customer_id) VALUES (2, 10);