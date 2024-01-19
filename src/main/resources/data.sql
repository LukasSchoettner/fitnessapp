INSERT INTO workout (name, date, level) VALUES ('Cardio Workout', '2024-05-15', 'BEGINNER');
INSERT INTO workout (name, date, level) VALUES ('Strength Training', '2024-05-15', 'INTERMEDIATE');
INSERT INTO workout (name, date, level) VALUES ('Yoga Session', '2024-05-15', 'INTERMEDIATE');
INSERT INTO workout (name, date, level) VALUES ('HIIT Session', '2024-05-15', 'PROFESSIONAL');
INSERT INTO workout (name, date, level) VALUES ('Upper Body Workout', '2024-05-15', 'ADVANCED');


INSERT INTO exercise (name, primary_muscle, secondary_muscle, instruction, equipment, workout_id) VALUES ('Push-ups', 'Quadriceps', 'Triceps Brachii', 'Lower your body by bending your elbows', '', 1);
INSERT INTO exercise (name, primary_muscle, secondary_muscle, instruction, equipment, workout_id) VALUES ('Hip Thrusts', 'Hamstrings', 'Biceps Brachii', 'Hinge at your hips, keeping your back flat', 'Yoga Props', 1);
INSERT INTO exercise (name, primary_muscle, secondary_muscle, instruction, equipment, workout_id) VALUES ('Squats', 'Gluteus Maximus', 'Hip Flexors', 'Lower your hips back and down, bending your knees', 'Bodyweight Training Tools', 2);
INSERT INTO exercise (name, primary_muscle, secondary_muscle, instruction, equipment, workout_id) VALUES ('Plank', 'Calves', 'Adductors', 'Hold weights at shoulder height', 'Gymnastics Equipment', 3);
INSERT INTO exercise (name, primary_muscle, secondary_muscle, instruction, equipment, workout_id) VALUES ('Deadlifts', 'Pectoralis Major', 'Gastrocnemius', 'Maintain a straight line from head to heels in a plank position', 'Free Weights', 4);
INSERT INTO exercise (name, primary_muscle, secondary_muscle, instruction, equipment, workout_id) VALUES ('Lunges', 'Latissimus Dorsi', 'Rhomboids', 'Reach forward toward the toes of the straight leg', 'Pilates Equipment', 5);


INSERT INTO course (name, date, trainer, participants) VALUES ('Cardio Fitness Class', '2023-05-15', 'John Doe', ARRAY ['Alice', 'Bob', 'Charlie']);
INSERT INTO course (name, date, trainer, participants) VALUES ('Strength Training Workshop', '2023-05-15', 'Jane Smith', ARRAY ['David', 'Eva', 'Frank']);
INSERT INTO course (name, date, trainer, participants) VALUES ('Yoga Retreat', '2024-05-15', 'Sam Brown', ARRAY ['Grace', 'Harry', 'Ivy']);
INSERT INTO course (name, date, trainer, participants) VALUES ('HIIT Bootcamp', '2024-05-15', 'Alex Green', ARRAY ['Jack', 'Kate', 'Leo']);
INSERT INTO course (name, date, trainer, participants) VALUES ('Pilates for Beginners', '2024-01-04', 'Emily White', ARRAY ['Mike', 'Nina', 'Oscar']);
INSERT INTO course (name, date, trainer, participants) VALUES ('Outdoor Fitness Challenge', '2023-02-15', 'Ryan Black', ARRAY ['Sophie', 'Tom', 'Ursula']);
INSERT INTO course (name, date, trainer, participants) VALUES ('Core Strengthening Workshop', '2023-03-15', 'Olivia Davis', ARRAY ['Victor', 'Wendy', 'Xander']);
INSERT INTO course (name, date, trainer, participants) VALUES ('Zumba Dance Party', '2024-05-20', 'Chris Taylor', ARRAY ['Yara', 'Zack', 'Amy']);

INSERT INTO USER (login, password, email, active) values ('franz', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'elke@gmail.com', 1);
INSERT INTO USER (login, password, email, active) values ('doe', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'doe@example.com', 1);
INSERT INTO USER (login, password, email, active) values ('smith', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'smith.alice@gmail.com', 1);
INSERT INTO USER (login, password, email, active) values ('jones', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'bob@gmail.com', 1);
INSERT INTO USER (login, password, email, active) values ('doej', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'charlie.do@example.com', 1);
INSERT INTO USER (login, password, email, active) values ('wilson', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'diana@gmail.com', 1);
INSERT INTO USER (login, password, email, active) values ('miller', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'millerk@gmail.com', 1);
INSERT INTO USER (login, password, email, active) values ('baker', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'baker.chris@example.com', 1);

INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Regensburg','1', 'Markusplatz', '93047');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Regensburg','2', 'Arnulfsplatz', '93049');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Regensburg','3', 'Musterstraße', '93057');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Munich', '4', 'Hauptstraße', '80331');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Berlin', '5', 'Alexanderplatz', '10178');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Hamburg', '6', 'Königstraße', '20095');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Cologne', '7', 'Domplatz', '50667');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Frankfurt', '8', 'Römerberg', '60311');

INSERT INTO TRAINER(id, last_name, first_name, phone, birth_date, address_id) VALUES (1, 'Franz', 'Elke', '0234 567890', '1986-08-15', '1');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date, address_id) VALUES (2,'Doe', 'John', '0151 111111', '1985-05-15', '2');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date, address_id) VALUES (3,'Smith', 'Alice', '0176 815234', '1990-08-21', '3');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date, address_id) VALUES (4,'Jones', 'Bob', '0123 456789', '1985-05-15', '4');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date, address_id) VALUES (5,'Doe', 'Charlie', '0234 567890', '1992-11-03', '5');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date, address_id) VALUES (6,'Wilson', 'Diana', '0345 678901', '1988-07-09', '6');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date, address_id) VALUES (7,'Miller', 'Kevin', '0567 890123', '1982-02-14', '7');
INSERT INTO trainer(id, last_name, first_name, phone, birth_date, address_id) VALUES (8,'Baker', 'Chris', '0234 567890', '1986-08-15', '8');

INSERT INTO NOTE(DATE, TRAINER_ID, MESSAGE, TITLE) VALUES('2024-01-01','3','Gutes Neues Jahr','Happy new Year');
INSERT INTO NOTE(DATE, TRAINER_ID, MESSAGE, TITLE) VALUES('2024-01-18','1','Hallo alle zusammen','Hallo');

INSERT INTO ROLE (description) VALUES ( 'ADMIN');
INSERT INTO ROLE (description) VALUES ( 'TRAINER');

INSERT INTO USERROLE(iduser, idrole) VALUES (1,1);
INSERT INTO USERROLE(iduser, idrole) VALUES (1,2);
