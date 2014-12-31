/*
SQLyog Ultimate v8.55 
MySQL - 5.1.57-community : Database - cluster2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cluster2` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `cluster2`;

/*Table structure for table `weathermonitor` */

DROP TABLE IF EXISTS `weathermonitor`;

CREATE TABLE `weathermonitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `temperature` varchar(10) DEFAULT NULL,
  `precipitation` varchar(10) DEFAULT NULL,
  `humidity` varchar(10) DEFAULT NULL,
  `windspeed` varchar(10) DEFAULT NULL,
  `direction` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=latin1;

/*Data for the table `weathermonitor` */

insert  into `weathermonitor`(`id`,`temperature`,`precipitation`,`humidity`,`windspeed`,`direction`) values (1,'-6.4*C','9.9*C','5*C','8KM/H','N'),(2,'-4.3*C','9.9*C','5*C','8KM/H','E'),(3,'-1.6*C','9.9*C','5*C','8KM/H','S'),(4,'0.7*C','9.9*C','5*C','7KM/H','N'),(5,'0.2*C','9.9*C','5*C','8KM/H','N'),(6,'-0.2*C','9.9*C','5*C','8KM/H','W'),(7,'-6.1*C','9.9*C','5*C','8KM/H','N'),(8,'1.0*C','9.9*C','5*C','8KM/H','S'),(9,'2.0*C','9.9*C','5*C','8KM/H','E');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
