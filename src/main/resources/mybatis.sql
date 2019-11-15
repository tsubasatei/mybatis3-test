/*
SQLyog Ultimate v12.08 (32 bit)
MySQL - 8.0.13 : Database - mybatis
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mybatis` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

USE `mybatis`;

/*Table structure for table `tbl_department` */

DROP TABLE IF EXISTS `tbl_department`;

CREATE TABLE `tbl_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tbl_department` */

insert  into `tbl_department`(`id`,`department_name`) values (1,'开发部'),(2,'测试部');

/*Table structure for table `tbl_employee` */

DROP TABLE IF EXISTS `tbl_employee`;

CREATE TABLE `tbl_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `d_id` int(11) DEFAULT NULL,
  `emp_status` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `d_id` (`d_id`),
  CONSTRAINT `tbl_employee_ibfk_1` FOREIGN KEY (`d_id`) REFERENCES `tbl_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tbl_employee` */

insert  into `tbl_employee`(`id`,`last_name`,`gender`,`email`,`d_id`,`emp_status`) values (1,'Admin','0','tom@163.com',1,NULL),(2,'Jerry','1','jerry@163.com',2,NULL),(5,'Jack','0','jack@163.com',1,NULL),(6,'AA','0','aa@163.com',1,NULL),(7,'BB','1','bb@163.com',2,NULL),(8,'CC','0','cc@163.com',1,NULL),(9,'AA2','0','aa@163.com',1,NULL),(10,'BB2','1','bb@163.com',2,NULL),(11,'CC2','0','cc@163.com',1,NULL),(13,'e52e8','0','aa@163.com',NULL,NULL),(14,'4daf2','0','aa@163.com',NULL,NULL),(15,'a609d','0','aa@163.com',NULL,NULL),(16,'071aa','0','aa@163.com',NULL,NULL),(17,'c2ce2','0','aa@163.com',NULL,NULL),(18,'sanae','1','sanae@163.com',NULL,'LOGIN'),(19,'Sanae','1','sanae@163.com',NULL,'0'),(20,'Sanae','1','sanae@163.com',NULL,'100'),(21,'Sanae','1','sanae@163.com',NULL,'100');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
