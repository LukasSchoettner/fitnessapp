INSERT INTO "user" (email, phone_number, address_id)
VALUES ('user1@example.com', '123-456-7890', 1);

INSERT INTO "customer" (email, phone_number, address_id, gender, first_name, last_name)
VALUES ('customer1@example.com', '987-654-3210', 2, 'MALE', 'John', 'Doe');

-- Assuming 'customer_id' is the foreign key in the 'Workout' table
INSERT INTO "workout" (customer_id, workout_date, duration)
VALUES (1, '2024-01-11', 60);

-- Inserting courses
INSERT INTO "course" (course_name, description)
VALUES ('Yoga', 'Relaxation and flexibility');

INSERT INTO "course" (course_name, description)
VALUES ('CrossFit', 'High-intensity functional training');

-- Associating customers with courses (many-to-many)
INSERT INTO customer_attend_course (customer_id, course_id)
VALUES (1, 1); -- Customer 1 attending Yoga

INSERT INTO customer_attend_course (customer_id, course_id)
VALUES (1, 2); -- Customer 1 attending CrossFit
