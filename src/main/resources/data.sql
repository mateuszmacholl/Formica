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

INSERT INTO post(id, creation_date, content, title, solved, votes, author_id)
 VALUES (1000,'2118-09-01 12:00:00', 'content1000', 'title1000', false, -10, 1000);
INSERT INTO post(id, creation_date, content, title, solved, votes, author_id)
VALUES (1001,'2118-09-01 12:00:00', 'content1001', 'title1001', true, 10, 1000);
INSERT INTO post(id, creation_date, content, title, solved, votes, author_id)
VALUES (1002,'2118-09-01 12:00:00','content1002', 'title1002', true, 0, 1000);
INSERT INTO post(id, creation_date, content, title, solved, votes, author_id)
VALUES (1003,'2118-09-01 12:00:00', 'content1003', 'title1003', false, 0, 1001);

INSERT INTO post_tag(post_id, tag_id) VALUES (1000,1000);
INSERT INTO post_tag(post_id, tag_id) VALUES (1000,1001);
INSERT INTO post_tag(post_id, tag_id) VALUES (1003,1000);
INSERT INTO post_tag(post_id, tag_id) VALUES (1003,1003);