SELECT 'AbcDef' as Marker, TABLE_NAME FROM INFORMATION_SCHEMA.TABLES;
SHOW TABLES;

INSERT INTO DepartmentTable (DepartmentKey, DepartmentName, CreateOffsetDateTime) VALUES (111, 'DepartmentOne', '2001-01-31');
INSERT INTO DepartmentTable (DepartmentKey, DepartmentName, CreateOffsetDateTime) VALUES (222, 'DepartmentTwo','2002-02-28');
INSERT INTO DepartmentTable (DepartmentKey, DepartmentName, CreateOffsetDateTime) VALUES (333, 'DepartmentThree', CURRENT_TIMESTAMP);
INSERT INTO DepartmentTable (DepartmentKey, DepartmentName, CreateOffsetDateTime) VALUES (444, 'DepartmentFourNoSeedEmps', CURRENT_TIMESTAMP);

INSERT INTO EmployeeTable (EmployeeKey, DepartmentForeignKey, Ssn, LastName, FirstName, CreateOffsetDateTime) VALUES (1111, 111, '000-00-1111', 'Smith', 'John', CURRENT_TIMESTAMP);
INSERT INTO EmployeeTable (EmployeeKey, DepartmentForeignKey, Ssn, LastName, FirstName, CreateOffsetDateTime) VALUES (1112, 111, '000-00-1112', 'Smith', 'Mary', CURRENT_TIMESTAMP);
INSERT INTO EmployeeTable (EmployeeKey, DepartmentForeignKey, Ssn, LastName, FirstName, CreateOffsetDateTime) VALUES (1113, 111, '000-00-1113', 'Smith', 'Pepper', CURRENT_TIMESTAMP);

INSERT INTO EmployeeTable (EmployeeKey, DepartmentForeignKey, Ssn, LastName, FirstName, CreateOffsetDateTime) VALUES (2221, 222, '000-00-1111', 'Patel', 'Janet', CURRENT_TIMESTAMP);
INSERT INTO EmployeeTable (EmployeeKey, DepartmentForeignKey, Ssn, LastName, FirstName, CreateOffsetDateTime) VALUES (2222, 222, '000-00-1112', 'Patel', 'Mark', CURRENT_TIMESTAMP);
INSERT INTO EmployeeTable (EmployeeKey, DepartmentForeignKey, Ssn, LastName, FirstName, CreateOffsetDateTime) VALUES (2223, 222, '000-00-1113', 'Patel', 'Salty', CURRENT_TIMESTAMP);

INSERT INTO EmployeeTable (EmployeeKey, DepartmentForeignKey, Ssn, LastName, FirstName, CreateOffsetDateTime) VALUES (3331, 333, '000-00-3331', 'Jones', 'Cindy', CURRENT_TIMESTAMP);
INSERT INTO EmployeeTable (EmployeeKey, DepartmentForeignKey, Ssn, LastName, FirstName, CreateOffsetDateTime) VALUES (3332, 333, '000-00-3332', 'Jones', 'Mindy', CURRENT_TIMESTAMP);
INSERT INTO EmployeeTable (EmployeeKey, DepartmentForeignKey, Ssn, LastName, FirstName, CreateOffsetDateTime) VALUES (3333, 333, '000-00-3333', 'Jones', 'Lenny', CURRENT_TIMESTAMP);


SELECT * FROM DepartmentTable;
SELECT * FROM EmployeeTable;