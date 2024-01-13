INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Regensburg','1', 'Markusplatz', '93047');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Regensburg','2', 'Arnulfsplatz', '93049');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Regensburg','3', 'Musterstraße', '93057');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Munich', '4', 'Hauptstraße', '80331');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Berlin', '5', 'Alexanderplatz', '10178');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Hamburg', '6', 'Königstraße', '20095');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Cologne', '7', 'Domplatz', '50667');
INSERT INTO ADDRESS(CITY, HOUSE_NUMBER, STREET, ZIP) VALUES ('Frankfurt', '8', 'Römerberg', '60311');

INSERT INTO trainer(email, last_name, first_name, phone, birth_date, password, address_id) VALUES ('hallo@gmail.com', 'Franz', 'Elke', '0171 223344', '1992-12-02','{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', '1');
INSERT INTO trainer(email, last_name, first_name, phone, birth_date, password, address_id) VALUES ('john.doe@example.com', 'Doe', 'John', '0151 111111', '1985-05-15', '{bcrypt}$2a$12$AbCdEfGhIjKlMnOpQrStUvWxYzAbCdEfGhIjKlMnOpQrStUvWxYz', '2');
INSERT INTO trainer(email, last_name, first_name, phone, birth_date, password, address_id) VALUES ('alice.smith@example.com', 'Smith', 'Alice', '0176 815234', '1990-08-21', '{bcrypt}$2a$12$CjB2Hx2tMh/0syQq.GbFtOaDcFLn8IBPRFoT4Q5UCJYrkXU7Bd3yi', '3');
INSERT INTO trainer(email, last_name, first_name, phone, birth_date, password, address_id) VALUES ('bob.jones@example.com', 'Jones', 'Bob', '0123 456789', '1985-05-15', '{bcrypt}$2a$12$AbCdEfGhIjKlMnOpQrStUvWxYzAbCdEfGhIjKlMnOpQrStUvWxYz', '4');
INSERT INTO trainer(email, last_name, first_name, phone, birth_date, password, address_id) VALUES ('charlie.doe@example.com', 'Doe', 'Charlie', '0234 567890', '1992-11-03', '{bcrypt}$2a$12$D4E5F6G7H8I9J0K1L2M3N4O5P6Q7R8S9T0U1V2W3X4Y5Z6A7B8C9', '5');
INSERT INTO trainer(email, last_name, first_name, phone, birth_date, password, address_id) VALUES ('diana.wilson@example.com', 'Wilson', 'Diana', '0345 678901', '1988-07-09', '{bcrypt}$2a$12$E5F6G7H8I9J0K1L2M3N4O5P6Q7R8S9T0U1V2W3X4Y5Z6A7B8C9D0', '6');
INSERT INTO TRAINER(email, last_name, first_name, phone, birth_date, password, address_id) VALUES ('kevin.miller@example.com', 'Miller', 'Kevin', '0567 890123', '1982-02-14', '{bcrypt}$2a$12$J7K8L9M0N1O2P3Q4R5S6T7U8V9W0X1Y2Z3A4B5C6D7E8F9G0H1I2', '7');
INSERT INTO TRAINER(email, last_name, first_name, phone, birth_date, password, address_id) VALUES ('chris.baker@example.com', 'Baker', 'Chris', '0234 567890', '1986-08-15', '{bcrypt}$2a$12$L5M6N7O8P9Q0R1S2T3U4V5W6X7Y8Z9A0B1C2D3E4F5G6H7I8J9K0', '8')
