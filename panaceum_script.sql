CREATE TYPE sex AS ENUM (
    'male',
	'female'
);

--Functions

CREATE OR REPLACE FUNCTION login(_login character varying, _passwd character varying) RETURNS character
	LANGUAGE plpgsql
	AS $$
BEGIN
	IF _passwd=(SELECT password FROM tuser WHERE login=_login) THEN
		UPDATE tuser
		SET token=md5(_login || random()::character varying(255))
		WHERE login=_login;
		RETURN (SELECT token FROM tuser WHERE login=_login);
	ELSE
		RETURN NULL;
	END IF;
END;
$$;

CREATE OR REPLACE FUNCTION register(_login character varying, _passwd character varying) RETURNS void
	LANGUAGE plpgsql
	AS $$
BEGIN
	INSERT INTO tuser (
    	login,
        password)
    VALUES (
        _login,
        _passwd);
END;
$$;

--Sequence

CREATE SEQUENCE inc_tUser
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

--Tables

CREATE TABLE tUser (
	id integer DEFAULT nextval('inc_tUser'::regclass) NOT NULL,
	login character varying(32) NOT NULL UNIQUE,
	password character varying(32) NOT NULL,
	token character(32),
	PRIMARY KEY (id)
);

CREATE TABLE address (
	id integer NOT NULL,
	city character varying(30) NOT NULL,
	street character varying(50) NOT NULL,
	buildingNumber character varying(6) NOT NULL,
	flatNumber character varying(6),
	zipCode character(6) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE person (
	pesel character(11),
	firstName character varying(30) NOT NULL,
	lastName character varying(50) NOT NULL,
	phone character varying(30) NOT NULL,
	email character varying(255) NOT NULL,
	tUserId integer,
	addressId integer NOT NULL,
	PRIMARY KEY (pesel),
	FOREIGN KEY (tUserId) REFERENCES tUser (id),
	FOREIGN KEY (addressId) REFERENCES address (id) ON DELETE CASCADE
);

CREATE TABLE hospital (
	id integer NOT NULL,
	name character varying(255) NOT NULL,
	addressId integer NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (addressId) REFERENCES address (id) ON DELETE CASCADE
);

CREATE TABLE doctor (
	id integer NOT NULL,
	speciality character varying(255) NOT NULL,
	licenceNumber character(10) NOT NULL,
	pesel character(11) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (pesel) REFERENCES person (pesel) ON DELETE CASCADE
);

CREATE TABLE patient (
	id integer NOT NULL,
	sex sex NOT NULL,
	age integer NOT NULL,
	bloodType character(2) NOT NULL,
	pesel character(11) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (pesel) REFERENCES person (pesel) ON DELETE CASCADE
);

CREATE TABLE excerpt (
	id integer NOT NULL,
	excerptDate date NOT NULL,
	recognition text NOT NULL,
	recomendations text NOT NULL,
	epicrisis text NOT NULL,
	PRIMARY KEY (id)
);

CREATE tABLE interview (
	id integer NOT NULL,
	interviewDate date NOT NULL,
	idc10 character(3) NOT NULL,
	firstIllnes boolean NOT NULL,
	symptoms text NOT NULL,
	recognition text NOT NULL,
	treatment text NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE firstExamination (
	id integer NOT NULL,
	pressure character varying(10),
	pulse character varying(10),
	temperature float,
	mass float,
	height float,
	content text,
	PRIMARY KEY (id)
);

CREATE TABLE infectionCard (
	id integer NOT NULL,
	factor1 boolean NOT NULL,
	factor2 boolean NOT NULL,
	factor3 boolean NOT NULL,
	factor4 boolean NOT NULL,
	factor5 boolean NOT NULL,
	factor5Note character varying(255),
	factor6 boolean NOT NULL,
	factor6Note character varying(255),
	factor7 boolean NOT NULL,
	factor7Note character varying(255),
	factor8 boolean NOT NULL,
	factor9 boolean NOT NULL,
	factor10 boolean NOT NULL,
	factor11 boolean NOT NULL,
	factor12 boolean NOT NULL,
	factor13 boolean NOT NULL,
	factor14 boolean NOT NULL,
	factor15 boolean NOT NULL,
	factor16 boolean NOT NULL,
	factor17 boolean NOT NULL,
	factor18 boolean NOT NULL,
	factor19 boolean NOT NULL,
	factor20 boolean NOT NULL,
	factor21 boolean NOT NULL,
	factor22 boolean NOT NULL,
	factor23 boolean NOT NULL,
	factor24 boolean NOT NULL,
	factor25 boolean NOT NULL,
	factor26 boolean NOT NULL,
	factor27 boolean NOT NULL,
	factor28 boolean NOT NULL,
	factor29 boolean NOT NULL,
	factor30 boolean NOT NULL,
	notepad text,
	PRIMARY KEY (id)
);

CREATE TABLE history (
	id integer NOT NULL,
	nurseCard text,
	finalCard text,
	patientId integer NOT NULL,
	doctorId integer NOT NULL,
	hospitalId integer NOT NULL,
	interviewId integer NOT NULL,
	firstExaminationId integer NOT NULL,
	infectionCardId integer NOT NULL,
	excerptId integer,
	PRIMARY KEY (id),
	FOREIGN KEY (patientId) REFERENCES patient (id),
	FOREIGN KEY (doctorId) REFERENCES doctor (id),
	FOREIGN KEY (hospitalId) REFERENCES hospital (id),
	FOREIGN KEY (interviewId) REFERENCES interview (id) ON DELETE CASCADE,
	FOREIGN KEY (firstExaminationId) REFERENCES firstExamination (id) ON DELETE CASCADE,
	FOREIGN KEY (infectionCardId) REFERENCES infectionCard (id) ON DELETE CASCADE,
	FOREIGN KEY (excerptId) REFERENCES excerpt (id) ON DELETE CASCADE
);

CREATE TABLE therapyPlan (
	id integer NOT NULL,
	examination text,
	orders text,
	historyId integer NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (historyId) REFERENCES history (id) ON DELETE CASCADE
);

CREATE TABLE medicine (
	id integer NOT NULL,
	name character varying(50) NOT NULL,
	activeSubstance text NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE prescription (
	id integer NOT NULL,
	dosage text,
	medicineId integer NOT NULL,
	therapyPlanId integer,
	excerptId integer,
	doctorId integer NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (medicineId) REFERENCES medicine (id),
	FOREIGN KEY (therapyPlanId) REFERENCES therapyPlan (id),
	FOREIGN KEY (excerptId) REFERENCES excerpt (id),
	FOREIGN KEY (doctorId) REFERENCES doctor (id)
);