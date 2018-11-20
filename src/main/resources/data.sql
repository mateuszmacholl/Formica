INSERT INTO user (id, creation_date, email, enabled, password, username)
 VALUES (1000,'2018-09-01 12:00:00', 'd_fresh_default@gmail.com', false , '$2a$10$ETBZ1t1O.qHYYduX3lz8reiChlZ29ghDOhF7PS/EFHoilTy76s1Ai', 'd_fresh_user');
INSERT INTO user (id, creation_date, email, enabled, password, username)
 VALUES (1001,'2018-09-01 12:00:00','d_enabled_default@gmail.com', true , '$2a$10$ETBZ1t1O.qHYYduX3lz8reiChlZ29ghDOhF7PS/EFHoilTy76s1Ai', 'd_enabled_user');

INSERT INTO user_roles(user_id, roles) VALUES (1000, 'user');
INSERT INTO user_roles(user_id, roles) VALUES (1001, 'user');

INSERT INTO password_reset_token(id, expiration_date, token)
VALUES (1000, '2118-09-01 12:00:00', 'token12345');
INSERT INTO password_reset_token(id, expiration_date, token)
VALUES (1001, '2118-09-01 12:00:00', 'token12345678901234567890');

INSERT INTO verification_token(id, expiration_date, token)
VALUES (1000, '2118-09-01 12:00:00', 'token12345');

INSERT INTO tag(id, name) VALUES (1000, 'kotlin');
INSERT INTO tag(id, name) VALUES (1001, 'books');
INSERT INTO tag(id, name) VALUES (1002, 'cars');
INSERT INTO tag(id, name) VALUES (1003, 'java');

insert into coordinates (id, latitude, longitude) values (1000, 10, 11);
insert into coordinates (id, latitude, longitude) values (1001, 11, 10);
insert into coordinates (id, latitude, longitude) values (1002, 11.01, 10.01);
insert into coordinates (id, latitude, longitude) values (1003, 54.6, 18.2514);
insert into coordinates (id, latitude, longitude) values (1004, 11.01, 11.01);
insert into coordinates (id, latitude, longitude) values (1005, 13.01, 10.01);
insert into coordinates (id, latitude, longitude) values (1006, 1, 1);
insert into coordinates (id, latitude, longitude) values (1007, 1.005, 1);

insert into channel(id, name, coordinates_id) values (1000, 'Gdansk', 1000);
insert into channel(id, name, coordinates_id) values (1001, 'Pomnik Neptuna', 1001);
insert into channel(id, name, coordinates_id) values (1002, 'Dworzec Lublin', 1002);
insert into channel(id, name, coordinates_id) values (1003, 'Dworzec Gdynia', 1006);
insert into channel(id, name, coordinates_id) values (1004, 'Dworzec Sopot', 1007);

INSERT INTO post(id, creation_date, content, title, votes, author_id, coordinates_id)
 VALUES (1000,'2018-11-20 12:00:00', 'content1000', 'title1000', 1000, 1000, 1003);
INSERT INTO post(id, creation_date, content, title, votes , author_id, coordinates_id)
VALUES (1001,'2018-09-01 12:00:00', 'content1001', 'title1001', 10, 1000, 1004);
INSERT INTO post(id, creation_date, content, title, votes, author_id, channel_id)
VALUES (1002,'2018-09-01 12:00:00','content1002', 'title1002', 0, 1000, 1002);
INSERT INTO post(id, creation_date, content, title, votes, author_id, channel_id)
VALUES (1003,'2018-09-01 12:00:00', 'content1003', 'title1003', 0, 1001, 1000);

INSERT INTO post_tag(post_id, tag_id) VALUES (1000,1000);
INSERT INTO post_tag(post_id, tag_id) VALUES (1000,1001);
INSERT INTO post_tag(post_id, tag_id) VALUES (1003,1000);
INSERT INTO post_tag(post_id, tag_id) VALUES (1003,1003);

INSERT INTO answer(id, creation_date, content, votes, author_id, post_id)
VALUES (1000,'2118-09-01 12:00:00', 'content1000', 100, 1000, 1000);
INSERT INTO answer(id, creation_date, content, votes, author_id, post_id)
VALUES (1001,'2118-09-01 12:00:00', 'content1002', 100, 1000, 1001);
INSERT INTO answer(id, creation_date, content, votes, author_id, post_id)
VALUES (1002,'2118-09-01 12:00:00', 'content1003', 100, 1001, 1002);


INSERT INTO comment(id, creation_date, content, author_id, answer_id)
VALUES (1000,'2118-09-01 12:00:00', 'content1000', 1000, 1000);
INSERT INTO comment(id, creation_date, content, author_id, answer_id)
VALUES (1001,'2118-09-01 12:00:00', 'content1002', 1000, 1001);
INSERT INTO comment(id, creation_date, content, author_id, answer_id)
VALUES (1002,'2118-09-01 12:00:00', 'content1003', 1001, 1002);

insert into post_has_been_commented_notification(id, creation_date, seen, notified_user_id, notifier_user_id, type, post_id)
values (1001, '2108-09-01 12:00:00', false, 1000, 1001, 'post_has_been_commented', 1000);
insert into post_has_been_commented_notification(id, creation_date, seen, notified_user_id, notifier_user_id, type, post_id)
values (1002, '2108-09-01 12:00:00', false, 1000, 1001, 'post_has_been_commented', 1001);
insert into post_has_been_commented_notification(id, creation_date, seen, notified_user_id, notifier_user_id, type, post_id)
values (1003, '2108-09-01 12:00:00', false, 1001, 1000, 'post_has_been_commented', 1001);