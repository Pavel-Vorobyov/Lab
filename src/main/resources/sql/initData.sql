INSERT INTO country (name) VALUES ('Germany');
INSERT INTO country (name) VALUES ('Belarus');
INSERT INTO country (name) VALUES ('Italy');

INSERT INTO hotel (name, phone, country_id, stars) VALUES ('Beaver', '+375293334565', 2, 3);
INSERT INTO hotel (name, phone, country_id, stars) VALUES ('Three axes', '+447871234567', 1, 4);
INSERT INTO hotel (name, phone, country_id, stars) VALUES ('Bear Den', '+399261234567', 3, 5);

INSERT INTO tour_type (name) VALUES ('WEEKEND');
INSERT INTO tour_type (name) VALUES ('FAMILY');
INSERT INTO tour_type (name) VALUES ('BEER');

INSERT INTO tour (photo, date, duration, type_id, description, coast, country_id, hotel_id) VALUES ('None','2017-11-11', 1, 1, 'None', 70, 1, 1);
INSERT INTO tour (photo, date, duration, type_id, description, coast, country_id, hotel_id) VALUES ('None','2018-06-14', 4, 2, 'None', 90, 2, 2);
INSERT INTO tour (photo, date, duration, type_id, description, coast, country_id, hotel_id) VALUES ('None','2018-05-23', 5, 3, 'None', 35.23, 3, 3);

INSERT INTO "user" (login, password, name, surname) VALUES ('pavel', 'pavel', 'Pavel', 'Varabyou');
INSERT INTO "user" (login, password, name, surname) VALUES ('admin', 'admin', 'admin', 'admin');

INSERT INTO review (tour_id, user_id, content) VALUES (1, 1, 'Cool tour');
INSERT INTO review (tour_id, user_id, content) VALUES (2, 1, 'Cool tour');
INSERT INTO review (tour_id, user_id, content) VALUES (3, 2, 'Cool tour');

INSERT INTO user_has_tour (user_id, tour_id) VALUES (1, 1);
INSERT INTO user_has_tour (user_id, tour_id) VALUES (1, 2);
INSERT INTO user_has_tour (user_id, tour_id) VALUES (2, 3);

