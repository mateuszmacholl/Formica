INSERT INTO user (id, creation_date, email, enabled, password, username)
 VALUES (1000, '2018-09-01 12:00:00', 'd_fresh_default@gmail.com', false , '$2a$10$ETBZ1t1O.qHYYduX3lz8reiChlZ29ghDOhF7PS/EFHoilTy76s1Ai', 'd_fresh_user');
INSERT INTO user (id, creation_date, email, enabled, password, username)
 VALUES (1001, '2018-09-01 12:00:00', 'd_enabled_default@gmail.com', true , '$2a$10$ETBZ1t1O.qHYYduX3lz8reiChlZ29ghDOhF7PS/EFHoilTy76s1Ai', 'd_enabled_user');

INSERT INTO user_roles(user_id, roles) VALUES (1000, 'user');
INSERT INTO user_roles(user_id, roles) VALUES (1001, 'user');

INSERT INTO password_reset_token(id, expiration_date, token)
VALUES (1000, '2118-09-01 12:00:00', 'token12345');
INSERT INTO password_reset_token(id, expiration_date, token)
VALUES (1001, '2118-09-01 12:00:00', 'token12345678901234567890');

INSERT INTO verification_token(id, expiration_date, token)
VALUES (1000, '2118-09-01 12:00:00', 'token12345');