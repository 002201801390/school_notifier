DROP TRIGGER IF EXISTS validate_responsible_student ON responsible_student;

DROP VIEW IF EXISTS admin;
DROP VIEW IF EXISTS employee;
DROP VIEW IF EXISTS student;
DROP VIEW IF EXISTS responsible;

DROP TABLE IF EXISTS responsible_student;
DROP TABLE IF EXISTS classes_students;

DROP TABLE IF EXISTS report_cards;
DROP TABLE IF EXISTS classes;
DROP TABLE IF EXISTS disciplines;

DROP TABLE IF EXISTS tokens;
DROP TABLE IF EXISTS users;

DROP TYPE IF EXISTS module;
DROP TYPE IF EXISTS role;
DROP TYPE IF EXISTS day_of_week;

DROP extension IF EXISTS "uuid-ossp";
