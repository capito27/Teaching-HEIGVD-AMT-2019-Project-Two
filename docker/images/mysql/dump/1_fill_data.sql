use `amt`;

INSERT INTO `team` (`team_name`, `team_country`) VALUES 
('Club Brugge', 'Belgium'),
('Ludogorets', 'Bulgaria'),
('Midtjylland', 'Denmark'),
('Liverpool', 'England'),
('KuPS', 'Finland'),
('Paris Saint-Germain', 'France'),
('Bayern Munich', 'Germany'),
('Juventus', 'Italy'),
('Legia Warsaw', 'Poland'),
('Basel', 'Switzerland');

INSERT INTO `user` (username, first_name, last_name, password, email,isAdmin) VALUES 
("user", "user", "name", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", "user@mail.co",0),
("filipe", "filipe", "fortunato", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", "filipe@mail.co",0),
("mickael", "mickael", "bonjour", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", "mickael@mail.co",0),
("pete", "pierre", "kohle", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", "pete@mail.co",0),
("john", "jonathan", "zaehringer", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", "john@mail.co",0),
("admin", "admin", "istrator", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", "admin@mail.co",1),
("user7", "user", "name", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", "admin@mail.co",0),
("user8", "user", "name", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", "admin@mail.co",0),
("user9", "user", "name", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", "admin@mail.co",0),
("user10", "user", "name", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", "admin@mail.co",0);

INSERT INTO `stadium` (stadium_name, stadium_location, stadium_viewer_places) VALUES
("Stadio Giuseppe Meazza", "Milan Italy", 80018),
("Estadio Monumental", "Lima, Peru", 80093),
("Shah Alam Stadium", "Shah Alam, Malaysia", 80372),
("Luzhniki Stadium", "Moscow, Russia", 81000),
("Santiago Bernabéu Stadium", "Madrid, Spain", 81044),
("Stade de France", "Paris, France", 81338),
("Signal Iduna Park", "Dortmund, Germany", 81359),
("The ANZ Stadium", "Australia", 83500),
("Borg El Arab Stadium", "Alexandria, Egypt", 86000),
("Bukit Jalil National Stadium", "Kuala Lumpur, Malaysia", 87411);

INSERT INTO `match` (score1, score2, FK_team1, FK_team2, FK_stadium, FK_user) VALUES(2,8,2,4,2,1),(3,2,3,7,3,1),(4,9,4,10,4,1),(5,3,5,3,5,1),(6,10,6,6,6,2),(7,4,7,9,7,2),(8,11,8,2,8,2),(9,5,9,5,9,2),(10,12,10,8,10,2),(11,6,1,1,1,3),(12,13,2,4,2,3),(13,7,3,7,3,3),(1,1,4,10,4,3),(2,8,5,3,5,3),(3,2,6,6,6,4),(4,9,7,9,7,4),(5,3,8,2,8,4),(6,10,9,5,9,4),(7,4,10,8,10,4),(8,11,1,1,1,5),(9,5,2,4,2,5),(10,12,3,7,3,5),(11,6,4,10,4,5),(12,13,5,3,5,5),(13,7,6,6,6,6),(1,1,7,9,7,6),(2,8,8,2,8,6),(3,2,9,5,9,6),(4,9,10,8,10,6),(5,3,1,1,1,7),(6,10,2,4,2,7),(7,4,3,7,3,7),(8,11,4,10,4,7),(9,5,5,3,5,7),(10,12,6,6,6,8),(11,6,7,9,7,8),(12,13,8,2,8,8),(13,7,9,5,9,8),(1,1,10,8,10,8),(2,8,1,1,1,9),(3,2,2,4,2,9),(4,9,3,7,3,9),(5,3,4,10,4,9),(6,10,5,3,5,9),(7,4,6,6,6,10),(8,11,7,9,7,10),(9,5,8,2,8,10),(10,12,9,5,9,10),(11,6,10,8,10,10),(12,13,1,1,1,1),(13,7,2,4,2,1),(1,1,3,7,3,1),(2,8,4,10,4,1),(3,2,5,3,5,1),(4,9,6,6,6,2),(5,3,7,9,7,2),(6,10,8,2,8,2),(7,4,9,5,9,2),(8,11,10,8,10,2),(9,5,1,1,1,3),(10,12,2,4,2,3),(11,6,3,7,3,3),(12,13,4,10,4,3),(13,7,5,3,5,3),(1,1,6,6,6,4),(2,8,7,9,7,4),(3,2,8,2,8,4),(4,9,9,5,9,4),(5,3,10,8,10,4),(6,10,1,1,1,5),(7,4,2,4,2,5),(8,11,3,7,3,5),(9,5,4,10,4,5),(10,12,5,3,5,5),(11,6,6,6,6,6),(12,13,7,9,7,6),(13,7,8,2,8,6),(1,1,9,5,9,6),(2,8,10,8,10,6),(3,2,1,1,1,7),(4,9,2,4,2,7),(5,3,3,7,3,7),(6,10,4,10,4,7),(7,4,5,3,5,7),(8,11,6,6,6,8),(9,5,7,9,7,8),(10,12,8,2,8,8),(11,6,9,5,9,8),(12,13,10,8,10,8),(13,7,1,1,1,9),(1,1,2,4,2,9),(2,8,3,7,3,9),(3,2,4,10,4,9),(4,9,5,3,5,9),(5,3,6,6,6,10),(6,10,7,9,7,10),(7,4,8,2,8,10),(8,11,9,5,9,10),(9,5,10,8,10,10),(10,12,1,1,1,1);
