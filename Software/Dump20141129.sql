CREATE DATABASE  IF NOT EXISTS `ccf` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ccf`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: ccf
-- ------------------------------------------------------
-- Server version	5.5.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `family`
--

DROP TABLE IF EXISTS `family`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `family` (
  `no` int(11) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone_no` varchar(10) NOT NULL,
  PRIMARY KEY (`no`),
  UNIQUE KEY `address_UNIQUE` (`address`),
  UNIQUE KEY `phone_no_UNIQUE` (`phone_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `family`
--

LOCK TABLES `family` WRITE;
/*!40000 ALTER TABLE `family` DISABLE KEYS */;
INSERT INTO `family` VALUES (100,'Sample Address	','8887343443'),(101,'Sample Test Address','2343423456'),(102,'Next Address Sample','7483924739'),(103,'Medavakkam','3458901238'),(105,'New Family New Address','3647328767'),(106,'New Address Different Family','2374930403'),(107,'Vadapalani','6453872670'),(108,'Chennai','3464873387'),(109,'Test New Address','8765674569'),(110,'USA','9876544321'),(112,'mbmbb','5454534343'),(121,'New Address','9597590145'),(123,'#16, 5th cross, 1st street, Ammayappa Nagar, Vayalur road, Trichy- 620017.','9600766275'),(142,'Test Address','9994010500'),(143,'dfsdfsdf3','44656564'),(145,'Trichy vayalur road','9600766274'),(150,'Address','8794563278'),(151,'Beema Nagar','1234567356'),(175,'New Address to new Family','9600743234'),(200,'BKJBKJBK','6626562953'),(250,'Sample New Address Test','4356765458'),(300,'Thiruvarur','7373440085'),(301,'C0imbatore','3455124686');
/*!40000 ALTER TABLE `family` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` int(11) NOT NULL,
  `name` varchar(25) NOT NULL,
  `dob` date NOT NULL,
  `eligibility` varchar(3) NOT NULL,
  `subscription_amount` float NOT NULL,
  `lived_till` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `family_no_idx` (`no`),
  CONSTRAINT `family_no` FOREIGN KEY (`no`) REFERENCES `family` (`no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,145,'Isaac','2013-10-12','Yes',100,NULL),(2,123,'Isaac','2014-10-07','Yes',100,NULL),(3,123,'Jeffer','2014-10-02','Yes',100,NULL),(4,121,'Isaac','2014-10-02','Yes',100,NULL),(5,121,'Jeffer','2014-10-03','Yes',100,NULL),(6,110,'Jo','2014-10-02','Yes',100,NULL),(7,110,'Jessi','2014-10-02','Yes',100,NULL),(8,142,'Isaac','2014-10-15','yes',100,NULL),(14,145,'Jessi','2014-10-01','No',100,NULL),(15,143,'kjhkj','2014-10-01','Yes',100,NULL),(16,150,'Isaac Jefferson','1990-04-23','Yes',100,NULL),(17,100,'JO','1990-04-16','Yes',100,NULL),(18,100,'ssffgf','2014-10-01','No',100,'2014-11-16'),(19,175,'Jyothi','2014-10-01','No',100,NULL),(20,175,'Jayachandran','2014-10-01','Yes',100,NULL),(21,200,'aaa','2014-10-01','Yes',100,NULL),(22,200,'vvv','2014-10-01','Yes',100,NULL),(23,101,'Isaac','2014-11-09','yes',0,NULL),(30,102,'JayaChandran','1947-10-30','Yes',1000,NULL),(32,108,'kalaipandian','1941-05-13','Yes',1000,NULL),(33,103,'Vijay','1981-02-15','Yes',100,NULL),(34,112,'rerwer','2014-10-29','Yes',347,NULL),(35,112,'dfsdfd','2014-10-27','No',300,NULL),(36,112,'ljlkjl','2014-10-26','No',499,'2014-12-06'),(37,151,'Soloman','1955-07-04','Yes',500,NULL),(38,151,'Jothirathinam','1960-10-17','Yes',400,NULL),(39,151,'Kiruba','1980-05-13','Yes',500,NULL),(42,300,'Ganesh','1966-08-23','Yes',100,NULL),(44,250,'Rani','1963-03-25','Yes',100,NULL);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `santha`
--

DROP TABLE IF EXISTS `santha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `santha` (
  `santha_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NOT NULL,
  `paid_date` date NOT NULL COMMENT 'This is the month the amount has actually paid. In this date a person can pay for other months also',
  `harvest_festival` float NOT NULL,
  `missionary` float NOT NULL,
  `mens_fellowship` float NOT NULL,
  `womens_fellowship` float NOT NULL,
  `education_help` float NOT NULL,
  `primary_school` float NOT NULL,
  `youth` float NOT NULL,
  `poor_help` float NOT NULL,
  `church_renovation` float NOT NULL,
  `graveyard` float NOT NULL,
  `bag_offer` float NOT NULL,
  `thanks_offer` float NOT NULL,
  `sto` float NOT NULL,
  `other1` float NOT NULL,
  `other2` float NOT NULL,
  `total` float NOT NULL,
  `paid_for_date` date NOT NULL COMMENT 'This is the month for which the amount has been paid',
  PRIMARY KEY (`santha_id`),
  KEY `member_id_idx` (`member_id`),
  CONSTRAINT `member_id_fk` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `santha`
--

LOCK TABLES `santha` WRITE;
/*!40000 ALTER TABLE `santha` DISABLE KEYS */;
INSERT INTO `santha` VALUES (1,1,'2014-10-12',100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,1500,'2014-10-12'),(2,1,'2014-09-12',100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,1500,'2014-09-12'),(3,16,'2014-09-01',100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,1500,'2014-09-01'),(9,4,'2014-10-12',20,20,20,2,20,20,20,20,20,20,20,20,20,20,20,282,'2014-10-12'),(11,17,'2014-10-12',20,20,20,20,20,20,20,202,20,20,20,20,20,20,20,482,'2014-10-12'),(13,2,'2014-09-09',0,100,0,0,0,0,10,0,0,0,100,0,0,0,0,210,'2014-09-09'),(14,20,'2014-10-08',100,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-10-08'),(15,3,'2014-10-07',100,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-09-10'),(16,20,'2014-10-01',0,100,0,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-09-02'),(17,1,'2014-10-01',0,100,0,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-11-05'),(18,5,'2014-10-01',0,100,0,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-11-05'),(19,4,'2014-10-01',0,100,0,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-11-05'),(20,4,'2014-10-01',0,100,0,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-12-11'),(21,5,'2014-10-01',0,100,0,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-12-11'),(22,5,'2014-10-01',0,0,100,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-09-03'),(23,4,'2014-10-01',0,0,100,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-09-03'),(24,16,'2014-10-01',0,0,100,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-10-01'),(25,16,'2014-10-01',0,0,100,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-11-05'),(26,21,'2014-10-01',0,0,100,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-10-01'),(27,22,'2014-10-01',0,0,100,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-10-01'),(28,2,'2014-10-01',0,0,0,0,100,0,0,0,0,0,0,0,0,0,0,100,'2014-08-07'),(29,3,'2014-10-01',0,0,0,0,100,0,0,0,0,0,0,0,0,0,0,100,'2014-08-07'),(30,2,'2014-10-06',0,0,0,0,100,0,0,0,0,0,0,0,0,0,0,100,'2014-11-13'),(31,3,'2014-10-06',0,0,0,0,100,0,0,0,0,0,0,0,0,0,0,100,'2014-11-13'),(32,2,'2014-10-19',0,0,0,0,100,0,0,0,0,0,0,0,0,0,0,100,'2014-12-17'),(33,3,'2014-10-19',0,0,0,0,0,0,0,0,0,100,0,0,0,0,0,100,'2014-12-17'),(36,3,'2014-10-12',0,100,0,0,0,0,0,0,100,0,0,0,0,0,100,300,'2014-10-12'),(37,2,'2014-11-02',0,100,0,0,100,100,0,0,0,0,0,0,0,0,0,300,'2015-01-18'),(38,3,'2014-11-02',0,0,0,0,0,0,100,0,0,100,0,0,0,0,0,200,'2015-01-18'),(39,2,'2014-11-02',100,100,0,0,100,100,0,0,0,0,0,0,0,0,0,400,'2015-02-22'),(40,5,'2014-11-02',0,100,0,0,0,0,0,0,0,0,0,0,0,0,0,100,'2015-01-11'),(41,17,'2014-11-09',20,20,20,20,20,20,20,202,20,20,20,20,20,20,20,482,'2014-11-09'),(42,18,'2014-11-09',20,20,20,20,20,20,20,202,20,20,20,20,20,20,20,482,'2014-11-09'),(43,17,'2014-11-09',20,20,20,20,20,20,20,202,20,20,20,20,20,20,20,482,'2014-12-14'),(44,18,'2014-11-09',20,20,20,20,20,20,20,202,20,20,20,20,20,20,20,482,'2014-12-14'),(45,2,'2014-11-16',100,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-10-12'),(46,37,'2014-11-16',100,0,0,0,0,400,200,100,50,50,25,25,25,25,0,1000,'2014-11-16'),(47,38,'2014-11-16',50,50,25,50,60,40,100,300,100,200,25,50,50,25,0,1125,'2014-11-16'),(48,39,'2014-11-16',25,50,50,25,100,50,25,200,25,100,25,25,100,50,50,900,'2014-11-16'),(61,38,'2014-11-16',50,50,25,50,60,40,100,300,100,200,25,50,50,25,0,1125,'2014-12-07'),(63,39,'2014-11-16',25,50,50,25,100,50,25,200,25,100,25,25,100,50,50,900,'2014-12-07'),(67,44,'2014-11-23',0,100,0,0,0,0,0,0,0,0,0,0,0,0,0,100,'2014-11-23'),(69,44,'2014-11-23',0,50,0,0,0,0,50,0,0,0,0,0,0,0,0,100,'2014-12-14');
/*!40000 ALTER TABLE `santha` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-29 12:22:11
