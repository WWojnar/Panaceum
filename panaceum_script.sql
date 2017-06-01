CREATE TYPE privileges AS ENUM (
	'admin',
	'doctor'
);

CREATE TYPE sex AS ENUM (
    'male',
	'female'
);

--Functions

CREATE OR REPLACE FUNCTION addAddress(_city character varying, _street character varying, _buildingNumber character varying, _flatNumber character varying, _zipCode character) RETURNS integer
	LANGUAGE plpgsql
    AS $$
	DECLARE ret integer;
BEGIN
	ret := (SELECT checkAddress (_city, _street, _buildingNumber, _flatNumber, _zipCode));
	IF ret = 0 THEN
		INSERT INTO address (
			city,
			street,
			buildingNumber,
			flatNumber,
			zipCode)
		VALUES (
			_city,
			_street,
			_buildingNumber,
			_flatNumber,
			_zipCode) RETURNING id INTO ret;
		RETURN ret;
	ELSE
		RETURN ret;
	END IF;
END;
$$;

CREATE OR REPLACE FUNCTION addDoctor(_speciality character varying, _licenceNumber character varying, _pesel character, _firstName character varying, _lastName character varying, _phone character varying, _email character varying, _city character varying, _street character varying, _buildingNumber character varying, _flatNumber character varying, _zipCode character, _login character varying) RETURNS TABLE (doctorId integer, password character varying(32)) 
	LANGUAGE plpgsql
    AS $$
	DECLARE ret integer;
	DECLARE _userId integer;
	DECLARE zero integer;
	DECLARE vc character varying(32);
BEGIN
	zero := 0;
	vc := 'NaN';
	IF 0 = (SELECT COUNT(*) FROM doctor WHERE licenceNumber = _licenceNumber) THEN
		INSERT INTO doctor (
			speciality,
			licenceNumber,
			pesel)
		VALUES (
			_speciality,
			_licenceNumber,
			(SELECT addPerson(_pesel, _firstName, _lastName, _phone, _email, _city, _street, _buildingNumber, _flatNumber, _zipCode))) RETURNING id INTO ret;
		
		INSERT INTO tUser (
			login,
			password,
			privileges)
		VALUES (
			_login,
			substring(md5(_login || random()::character varying(32)) from 1 for 8),
			'doctor') RETURNING id INTO _userId;
		
		UPDATE person
		SET tUserId = _userId
		WHERE pesel = _pesel;
		
		RETURN QUERY SELECT doctorView.doctorId, doctorView.password FROM doctorView WHERE pesel = _pesel;
	ELSE RETURN QUERY SELECT zero, vc;
	END IF;
END;
$$;

CREATE OR REPLACE FUNCTION addPatient(_sex sex, _age integer, _bloodType character, _pesel character, _firstName character varying, _lastName character varying, _phone character varying, _email character varying, _city character varying, _street character varying, _buildingNumber character varying, _flatNumber character varying, _zipCode character) RETURNS integer 
	LANGUAGE plpgsql
    AS $$
	DECLARE ret integer;
BEGIN
	IF 0 = (SELECT COUNT(*) FROM patient WHERE pesel = _pesel) THEN
		INSERT INTO patient (
			sex,
			age,
			bloodType,
			pesel)
		VALUES (
			_sex,
			_age,
			_bloodType,
			(SELECT addPerson(_pesel, _firstName, _lastName, _phone, _email, _city, _street, _buildingNumber, _flatNumber, _zipCode))) RETURNING id INTO ret;
		RETURN ret;
	ELSE RETURN 0;
	END IF;
END;
$$;

CREATE OR REPLACE FUNCTION addPerson(_pesel character, _firstName character varying, _lastName character varying, _phone character varying, _email character varying, _city character varying, _street character varying, _buildingNumber character varying, _flatNumber character varying, _zipCode character) RETURNS character
	LANGUAGE plpgsql
    AS $$
	DECLARE ret character(11);
BEGIN
	IF 0 = (SELECT COUNT(*) FROM person WHERE pesel = _pesel) THEN
		INSERT INTO person (
			pesel,
			firstName,
			lastName,
			phone,
			email,
			addressId)
		VALUES (
			_pesel,
			_firstName,
			_lastName,
			_phone,
			_email,
			(SELECT addAddress(_city, _street, _buildingNumber, _flatNumber, _zipCode))) RETURNING pesel INTO ret;
		RETURN ret;
	END IF;
	RETURN _pesel;
END;
$$;

CREATE OR REPLACE FUNCTION addPrescription(_login character varying, _token character, _dosage text, _expiryDate date, _name character varying, _doctorId integer, _patientId integer) RETURNS integer AS
$BODY$
DECLARE ret integer;
BEGIN
	IF _token = (SELECT token FROM tUser WHERE login = _login) THEN
		INSERT INTO prescription (
			dosage,
			prescriptionDate,
			expiryDate,
			medicineId,
			therapyPlanId,
			doctorId)
		VALUES (
			_dosage,
			now(),
			_expiryDate,
			(SELECT id FROM medicine WHERE name = _name),
			(SELECT therapyPlan.id FROM therapyPlan JOIN history ON therapyPlan.historyId = history.id WHERE patientId = _patientId),
			_doctorId) RETURNING id INTO ret;
		RETURN ret;
	ELSE RETURN 0;
	END IF;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION checkAddress(_city character varying, _street character varying, _buildingNumber character varying, _flatNumber character varying, _zipCode character) RETURNS integer
	LANGUAGE plpgsql
    AS $$
	DECLARE ret integer;
BEGIN
	ret := (SELECT id
		FROM address
		WHERE city = _city AND
			street = _street AND
			buildingNumber = _buildingNumber AND
			flatNumber = _flatNumber AND
			zipCode = _zipCode);
	IF ret IS NOT NULL THEN
		RETURN ret;
	ELSE
		RETURN 0;
	END IF;
END;
$$;

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

CREATE OR REPLACE FUNCTION register(_login character varying, _passwd character varying, _privileges privileges) RETURNS void
	LANGUAGE plpgsql
	AS $$
BEGIN
	INSERT INTO tuser (
    	login,
        password,
		privileges)
    VALUES (
        _login,
        _passwd,
		_privileges);
END;
$$;

CREATE FUNCTION validate(_login character varying, _token character) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF _token=(SELECT token FROM tUser WHERE login=_login) 
	   AND _token IS NOT NULL THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END;
$$;

--Sequence

CREATE SEQUENCE inc_address
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE inc_doctor
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE inc_excerpt
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE inc_firstExamination
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE inc_history
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE inc_hospital
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE inc_infectionCard
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE inc_interview
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE inc_medicine
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE inc_patient
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE inc_prescription
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;
	
CREATE SEQUENCE inc_therapyPlan
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE inc_tUser
    START WITH 1
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
	privileges privileges,
	PRIMARY KEY (id)
);

CREATE TABLE address (
	id integer DEFAULT nextval('inc_address'::regclass) NOT NULL,
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
	id integer DEFAULT nextval('inc_hospital'::regclass) NOT NULL,
	name character varying(255) NOT NULL,
	regon character(9),
	phone character varying(30),
	addressId integer NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (addressId) REFERENCES address (id) ON DELETE CASCADE
);

CREATE TABLE doctor (
	id integer DEFAULT nextval('inc_doctor'::regclass) NOT NULL,
	speciality character varying(255) NOT NULL,
	licenceNumber character(10) NOT NULL,
	pesel character(11) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (pesel) REFERENCES person (pesel) ON DELETE CASCADE
);

CREATE TABLE patient (
	id integer DEFAULT nextval('inc_patient'::regclass) NOT NULL,
	sex sex NOT NULL,
	age integer NOT NULL,
	bloodType character(2) NOT NULL,
	pesel character(11) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (pesel) REFERENCES person (pesel) ON DELETE CASCADE
);

CREATE TABLE excerpt (
	id integer DEFAULT nextval('inc_excerpt'::regclass) NOT NULL,
	excerptDate date NOT NULL,
	recognition text NOT NULL,
	recomendations text NOT NULL,
	epicrisis text NOT NULL,
	PRIMARY KEY (id)
);

CREATE tABLE interview (
	id integer DEFAULT nextval('inc_interview'::regclass) NOT NULL,
	interviewDate date NOT NULL,
	idc10 character(3) NOT NULL,
	firstIllnes boolean NOT NULL,
	symptoms text NOT NULL,
	recognition text NOT NULL,
	treatment text NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE firstExamination (
	id integer DEFAULT nextval('inc_firstExamination'::regclass) NOT NULL,
	pressure character varying(10),
	pulse character varying(10),
	temperature float,
	mass float,
	height float,
	content text,
	PRIMARY KEY (id)
);

CREATE TABLE infectionCard (
	id integer DEFAULT nextval('inc_infectionCard'::regclass) NOT NULL,
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
	id integer DEFAULT nextval('inc_history'::regclass) NOT NULL,
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
	id integer DEFAULT nextval('inc_therapyPlan'::regclass) NOT NULL,
	examination text,
	orders text,
	historyId integer NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (historyId) REFERENCES history (id) ON DELETE CASCADE
);

CREATE TABLE medicine (
	id integer DEFAULT nextval('inc_medicine'::regclass) NOT NULL,
	name character varying(50) NOT NULL,
	activeSubstance text NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE prescription (
	id integer DEFAULT nextval('inc_prescription'::regclass) NOT NULL,
	dosage text,
	prescriptionDate date NOT NULL,
	expiryDate date,
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

--Views

CREATE VIEW doctorView AS
	SELECT doctor.id AS doctorId,
		speciality,
		licenceNumber,
		person.pesel,
		firstName,
		lastName,
		phone,
		email,
		address.id AS addressId,
		city,
		street,
		buildingNumber,
		flatNumber,
		zipCode,
		tUser.id AS userId,
		login,
		password,
		token,
		privileges
	FROM doctor
		JOIN person ON doctor.pesel = person.pesel
		JOIN address ON person.addressId = address.id
		JOIN tUser ON person.tUserId = tUser.id;

CREATE OR REPLACE VIEW historyview AS 
	SELECT history.id AS historyid,
		history.nursecard,
		history.finalcard,
		history.patientid,
		patient.pesel,
		history.hospitalid,
		interview.id AS interviewid,
		interview.interviewdate,
		interview.idc10,
		interview.firstillnes,
		interview.symptoms,
		interview.recognition AS interviewrecognition,
		interview.treatment,
		firstexamination.id AS firstexaminationid,
		firstexamination.pressure,
		firstexamination.pulse,
		firstexamination.temperature,
		firstexamination.mass,
		firstexamination.height,
		firstexamination.content,
		excerpt.id AS excerptid,
		excerpt.excerptdate,
		excerpt.recognition AS excerptrecognition,
		excerpt.recomendations,
		excerpt.epicrisis,
		infectioncard.id AS infectioncardid,
		infectioncard.factor1,
		infectioncard.factor2,
		infectioncard.factor3,
		infectioncard.factor4,
		infectioncard.factor5,
		infectioncard.factor5note,
		infectioncard.factor6,
		infectioncard.factor6note,
		infectioncard.factor7,
		infectioncard.factor7note,
		infectioncard.factor8,
		infectioncard.factor9,
		infectioncard.factor10,
		infectioncard.factor11,
		infectioncard.factor12,
		infectioncard.factor13,
		infectioncard.factor14,
		infectioncard.factor15,
		infectioncard.factor16,
		infectioncard.factor17,
		infectioncard.factor18,
		infectioncard.factor19,
		infectioncard.factor20,
		infectioncard.factor21,
		infectioncard.factor22,
		infectioncard.factor23,
		infectioncard.factor24,
		infectioncard.factor25,
		infectioncard.factor26,
		infectioncard.factor27,
		infectioncard.factor28,
		infectioncard.factor29,
		infectioncard.factor30,
		infectioncard.notepad
	FROM history
		JOIN patient ON history.patientid = patient.id
		JOIN interview ON history.interviewid = interview.id
		JOIN firstexamination ON history.firstexaminationid = firstexamination.id
		JOIN excerpt ON history.excerptid = excerpt.id
		JOIN infectioncard ON history.infectioncardid = infectioncard.id;

CREATE OR REPLACE VIEW hospitalView AS
	SELECT hospital.id AS hospitalId,
		name,
		regon,
		phone,
		address.id AS addressId,
		city,
		street,
		buildingNumber,
		flatNumber,
		zipCode
	FROM hospital
		JOIN address ON hospital.addressId = address.id;
		
CREATE VIEW patientView AS
	SELECT patient.id AS patientId,
		sex,
		age,
		bloodType,
		person.pesel,
		firstName,
		lastName,
		phone,
		email,
		address.id AS addressId,
		city,
		street,
		buildingNumber,
		flatNumber,
		zipCode
	FROM patient
		JOIN person ON patient.pesel = person.pesel
		JOIN address ON person.addressId = address.id;

CREATE OR REPLACE VIEW prescriptionExcerptView AS
	SELECT prescription.id AS prescriptionId,
		dosage,
		prescriptionDate,
		expiryDate,
		medicineId,
		name AS medicineName,
		activeSubstance,
		prescription.therapyPlanId,
		prescription.excerptId,
		prescription.doctorId,
		patient.id AS patientId,
		patient.pesel AS patientPesel,
		firstName AS patientFirstName,
		lastName AS patientLastName
	FROM prescription
		JOIN medicine ON prescription.medicineId = medicine.id
		JOIN excerpt ON prescription.excerptId = excerpt.id
		JOIN history ON excerpt.Id = history.excerptId
		JOIN patient ON history.patientId = patient.id
		JOIN person ON patient.pesel = person.pesel;
		
CREATE OR REPLACE VIEW prescriptionView AS
	SELECT prescription.id AS prescriptionId,
		dosage,
		prescriptionDate,
		expiryDate,
		medicineId,
		name AS medicineName,
		activeSubstance,
		prescription.therapyPlanId,
		prescription.excerptId,
		prescription.doctorId,
		patient.id AS patientId,
		patient.pesel AS patientPesel,
		firstName AS patientFirstName,
		lastName AS patientLastName
	FROM prescription
		JOIN medicine ON prescription.medicineId = medicine.id
		JOIN therapyPlan ON prescription.therapyPlanId = therapyPlan.id
		JOIN history ON therapyPlan.historyId = history.id
		JOIN patient ON history.patientId = patient.id
		JOIN person ON patient.pesel = person.pesel;
		
CREATE OR REPLACE VIEW userDoctorView AS
	SELECT tUser.id AS userId,
		login,
		doctor.id AS doctorId
	FROM tuser
		JOIN person ON tUser.id = person.tUserId
		JOIN doctor ON person.pesel = doctor.pesel;