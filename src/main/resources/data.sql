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

