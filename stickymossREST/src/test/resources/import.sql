INSERT INTO APPLICANTS (version, id, firstname, lastname, email, enc_password) VALUES (1, NEXT VALUE FOR APPLICANT_SEQ, 'test01', 'TEST', 'email@email.com', 'password');

COMMIT;