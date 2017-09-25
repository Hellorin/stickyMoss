DROP TABLE IF EXISTS TAGS;

CREATE TABLE TAGS(
    version BIGINT NOT NULL,
    id BIGINT IDENTITY(1,1),
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS COMPANIES;

CREATE TABLE COMPANIES(
    version BIGINT NOT NULL,
    id BIGINT IDENTITY(1,1),
    name VARCHAR(100) NOT NULL,
    companySize VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS PLACEMENT_COMPANIES;

CREATE TABLE PLACEMENT_COMPANIES(
    version BIGINT NOT NULL,
    id BIGINT IDENTITY(1,1),
    name VARCHAR(100) NOT NULL,
    companySize VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS SERVICES_COMPANIES;

CREATE TABLE SERVICES_COMPANIES(
    version BIGINT NOT NULL,
    id BIGINT IDENTITY(1,1),
    name VARCHAR(100) NOT NULL,
    companySize VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS CVS;

CREATE TABLE CVS(
    version BIGINT NOT NULL,
    id BIGINT IDENTITY(1,1),
    name VARCHAR(100) NOT NULL,
    format VARCHAR(25) NOT NULL,
    date_uploaded DATE,
    content BLOB NOT NULL
);

DROP TABLE IF EXISTS TABLE_CVS_TAGS;

CREATE TABLE TABLE_CVS_TAGS(
    id BIGINT IDENTITY(1,1),
    idCV BIGINT NOT NULL,
    idTag BIGINT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(idCV) REFERENCES CVS(id),
    FOREIGN KEY(idTag) REFERENCES TAGS(id)
);

DROP SEQUENCE IF EXISTS APPLICANT_SEQ;
CREATE SEQUENCE APPLICANT_SEQ START WITH 1;

DROP TABLE IF EXISTS APPLICANTS;

CREATE TABLE APPLICANTS(
    version BIGINT,
    id BIGINT,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    enc_password VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);

DROP SEQUENCE IF EXISTS JOBAPPLICATION_SEQ;
CREATE SEQUENCE JOBAPPLICATION_SEQ START WITH 1;

DROP TABLE IF EXISTS JOB_APPLICATIONS;

CREATE TABLE JOB_APPLICATIONS(
    version BIGINT,
    id BIGINT,
    date_submitted DATE NOT NULL,
    status VARCHAR(50),
    applicant_id BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY(applicant_id) REFERENCES APPLICANTS(id)
);

COMMIT;