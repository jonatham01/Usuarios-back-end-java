-- en mysql 
CREATE SCHEMA `tienda` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci ;

CREATE TABLE `tienda`.`categorias` (
  `id_categoria` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id_categoria`));

  CREATE TABLE `tienda`. `productos` (
   `id_producto` INT NOT NULL AUTO_INCREMENT,
   `nombre` VARCHAR(45) NULL,
   `id_categoria` INT NOT NULL,
   `precio_venta` DECIMAL(16,2) NULL,
   `cantidad_stock` INT NOT NULL,
   `estado` BOOLEAN NULL,
   `codigo_barras` VARCHAR(150) NULL,
  PRIMARY KEY ( `id_producto`),
  CONSTRAINT  `fk_PRODUCTOS_CATEGORIAS`
    FOREIGN KEY (`id_categoria`)
    REFERENCES CATEGORIAS (`id_categoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `tienda`.`clientes`(
    `id_cliente` VARCHAR(20) NOT NULL,
    `nombre` VARCHAR (45) NOT NULL,
    `apellidos` VARCHAR (45) NOT NULL,
    `telefono` NUMERIC (45) NULL,
    `direccion` VARCHAR(80) NULL,
    `correo_electronico` VARCHAR(70) NULL,
    PRIMARY KEY (`id_cliente`));

CREATE TABLE  `tienda`.`ordenes` (
  `id_orden` INT NOT NULL AUTO_INCREMENT,
  `fecha` TIMESTAMP NULL,
  `medio_pago` CHAR(1) NULL,
  `direccion_envio` VARCHAR(300) NULL,
  `comentario` VARCHAR(300) NULL,
  `id_cliente` VARCHAR(20) NOT NULL,
  `estado` CHAR(1) NULL,
  `valor_total`  DECIMAL(20,2) NULL,
  PRIMARY KEY (`id_orden`),
  CONSTRAINT `fk_ORDENES_CLIENTES`
    FOREIGN KEY (`id_cliente`)
    REFERENCES CLIENTES (`id_cliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE  `tienda`.`ordenes_productos`(
  `id_orden` INT NOT NULL,
  `id_producto` INT NOT NULL,
  `cantidad` INT NULL,
  `total` DECIMAL(16,2) NULL,
  PRIMARY KEY (`id_orden`, `id_producto`),
  CONSTRAINT `fk_ORDENES_PRODUCTOS_PRODUCTOS`
    FOREIGN KEY (`id_producto`)
    REFERENCES productos (`id_producto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ORDENES_PRODUCTOS_ORDENES`
    FOREIGN KEY (`id_orden`)
    REFERENCES ordenes (`id_orden`)
	ON DELETE NO ACTION
    ON UPDATE NO ACTION);


