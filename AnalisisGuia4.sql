CREATE DATABASE Escuela;
USE Escuela;

CREATE TABLE Alumno(
	Cod_alumno INT PRIMARY KEY,
    Nombre     VARCHAR(80),
    Apellido   VARCHAR(80),
    Edad 	   INT,
    Direccion  VARCHAR(100)
);

CREATE TABLE Materia(
	Cod_materia INT PRIMARY KEY,
    Nombre		VARCHAR(25),
    Descripcion VARCHAR(100)
);

CREATE TABLE Alumno_Materia(
	Cod_alumno  INT NOT NULL,
    Cod_materia INT NOT NULL,
    CONSTRAINT fk_alumno  FOREIGN KEY(Cod_alumno) 	REFERENCES Alumno(Cod_alumno) ON DELETE CASCADE,
    CONSTRAINT fk_materia FOREIGN KEY(Cod_materia)	REFERENCES Materia(Cod_materia) ON DELETE CASCADE
);

INSERT INTO Alumno VALUES(2, 'Christopher', 'Sosa', 23, 'Calle 123');

INSERT INTO Materia VALUES(3, 'POO', 'Programacion Orientada a Objetos con Java');
INSERT INTO Materia VALUES(4, 'MTD', 'Matematica Discreta');

INSERT INTO Alumno_Materia VALUES(2, 3);


SELECT Materia.Cod_materia, Nombre, Descripcion, COUNT(alumno_materia.Cod_alumno) AS 'Alumnos Registrados'  FROM Materia LEFT OUTER JOIN Alumno_Materia ON Materia.Cod_materia = Alumno_Materia.Cod_materia;
SELECT m.Cod_materia, m.Nombre, m.Descripcion, (SELECT COUNT(*) FROM Alumno_Materia am WHERE am.Cod_materia = m.Cod_materia) AS 'Alumnos Registrados' FROM Materia m