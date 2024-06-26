-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema lifting-equipment-db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema lifting-equipment-db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `lifting-equipment-db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `lifting-equipment-db` ;

-- -----------------------------------------------------
-- Table `lifting-equipment-db`.`customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lifting-equipment-db`.`customers` (
  `id` BINARY(16) NOT NULL,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `customer name` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `phone number` VARCHAR(255) NULL DEFAULT NULL,
  `postcode` INT NULL DEFAULT NULL,
  `taxpayer identification number` DECIMAL(38,0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_lto1t77b8prboukv83bi99vk7` (`taxpayer identification number` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `lifting-equipment-db`.`lifting_equipment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lifting-equipment-db`.`lifting_equipment` (
  `id` BINARY(16) NOT NULL,
  `date_added` DATETIME(6) NULL DEFAULT NULL,
  `date_manufactured` INT NULL DEFAULT NULL,
  `manufacturer` VARCHAR(255) NULL DEFAULT NULL,
  `model` VARCHAR(255) NULL DEFAULT NULL,
  `serial_number` VARCHAR(255) NOT NULL,
  `customer_id` BINARY(16) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_m707d2cwa9cmwax9e6v3h3eqp` (`serial_number` ASC) VISIBLE,
  INDEX `FKestfpi5fdyv656s2psg1l1ju7` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `FKestfpi5fdyv656s2psg1l1ju7`
    FOREIGN KEY (`customer_id`)
    REFERENCES `lifting-equipment-db`.`customers` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `lifting-equipment-db`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lifting-equipment-db`.`users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `updated_at` DATETIME(6) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `role` ENUM('USER') NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE USER 'lifting-equipment-admin' IDENTIFIED BY '12345';

GRANT ALL ON `lifting-equipment-db`.* TO 'lifting-equipment-admin';
GRANT SELECT ON TABLE `lifting-equipment-db`.* TO 'lifting-equipment-admin';
GRANT SELECT, INSERT, TRIGGER ON TABLE `lifting-equipment-db`.* TO 'lifting-equipment-admin';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE `lifting-equipment-db`.* TO 'lifting-equipment-admin';
GRANT EXECUTE ON ROUTINE `lifting-equipment-db`.* TO 'lifting-equipment-admin';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
