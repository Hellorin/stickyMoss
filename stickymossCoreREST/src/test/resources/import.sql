INSERT INTO USER_ROLES(role_name) VALUES ('USER');

INSERT INTO USERS (version, id, user_type, firstname, lastname, email, enc_password, is_enable) VALUES (1, NEXT VALUE FOR APPLICANT_SEQ, 'applicant', 'test01', 'TEST', 'email@email.com', 'password', TRUE);

COMMIT;

INSERT INTO USERS_TO_ROLES(user_id, role_name) VALUES (1, 'USER');
INSERT INTO USERS_TO_ROLES(user_id, role_name) VALUES (2, 'ADMIN');

COMMIT;