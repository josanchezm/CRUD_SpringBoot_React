SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `contactosTelefonicos` DEFAULT CHARACTER SET utf8 ;
USE `contactosTelefonicos` ;

/******************** Add Table: contacto ************************/
/* Build Table Structure */
CREATE TABLE contacto
(
cnt_id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
cnt_nombre VARCHAR(100) NOT NULL,
cnt_apellido VARCHAR(100) NOT NULL,
cnt_telefono VARCHAR(30) UNIQUE NOT NULL,
cnt_email VARCHAR(100) UNIQUE NOT NULL,
cnt_fecha_nac DATE NOT NULL,
cnt_estado BOOLEAN DEFAULT TRUE,
cnt_created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
cnt_updated_at DATETIME NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/* Add Indexes */
CREATE INDEX contacto_cnt_apellido_Idx ON contacto (cnt_apellido) USING BTREE;
CREATE INDEX contacto_cnt_nombre_Idx ON contacto (cnt_nombre) USING BTREE;
CREATE INDEX contacto_cnt_nombre_cnt_apellido_Idx ON contacto (cnt_nombre, cnt_apellido) USING BTREE;

insert into contacto (cnt_nombre, cnt_apellido, cnt_telefono, cnt_email, cnt_fecha_nac)
values 
	('Jaime', 'Sanchez', '2161120','jimisanchezm@hotmail.com', '1995-10-07'),
    ('Sofia', 'Sanchez', '777777', 'sofiasanchez@gmail.es', '1998-02-07'),
    ('Sofia', 'Sanchez', '7777', 'ssanchez@gmail.es', '1998-02-07'),
    ('Jesus', 'Sanchez', '2335', 'jemasan@gmail.es', '1996-02-07'),
    ('Isabel', 'Mendieta', '56689', 'mendieta@gmail.es', '1997-02-07'),
    ('Nicolas', 'Sanchez', '007', 'nssanchez@gmail.es', '1993-02-07'),
    ('Ana', 'Vainilla', '2645789', 'anavainilla@outlook.es', '2000-05-21');

select * from contacto;

/* Soft delete */
update contacto 
set cnt_estado = false
where cnt_id = 3;

select * from contacto where cnt_estado = true;

select * from contacto;