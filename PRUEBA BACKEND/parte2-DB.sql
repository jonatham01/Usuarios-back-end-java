CREATE TABLE  USUARIOS (
  "id" SERIAL NOT NULL,
  "nombre" VARCHAR(45) NOT NULL,
  "apellidos" VARCHAR (45) NOT NULL,
  "email" VARCHAR(60) NOT NULL,
  "contraseña" VARCHAR(45) NOT NULL,
  
  PRIMARY KEY ("id"));
