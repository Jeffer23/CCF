create database ccf;

use ccf;


CREATE TABLE `family` (
  `no` int(11) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone_no` varchar(10) NOT NULL,
  PRIMARY KEY (`no`),
  UNIQUE KEY `address_UNIQUE` (`address`),
  UNIQUE KEY `phone_no_UNIQUE` (`phone_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` int(11) NOT NULL,
  `name` varchar(25) NOT NULL,
  `dob` date NOT NULL,
  `eligibility` varchar(3) NOT NULL,
  `subscription_amount` float NOT NULL,
  `lived_till` date DEFAULT NULL,
  `marriage_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `family_no_idx` (`no`),
  CONSTRAINT `family_no` FOREIGN KEY (`no`) REFERENCES `family` (`no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;


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
  `total` float NOT NULL,
  `paid_for_date` date NOT NULL COMMENT 'This is the month for which the amount has been paid',
  `subscriptionAmount` float NOT NULL,
  PRIMARY KEY (`santha_id`),
  KEY `member_id_idx` (`member_id`),
  CONSTRAINT `member_id_fk` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;


CREATE TABLE `service_offering` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `time` varchar(20) NOT NULL,
  `service_offering` int(11) NOT NULL,
  `thanks_offering` int(11) DEFAULT NULL,
  `special_thanks_offering` int(11) DEFAULT NULL,
  `missionary` int(11) DEFAULT NULL,
  `auction` int(11) DEFAULT NULL,
  `confirmation` int(11) DEFAULT NULL,
  `sunday_school` int(11) DEFAULT NULL,
  `marriage` int(11) DEFAULT NULL,
  `others` int(11) DEFAULT NULL,
  `others_reason` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;


CREATE TABLE `ledger` (
  `ledger_id` int(11) NOT NULL AUTO_INCREMENT,
  `ledger_name` varchar(45) NOT NULL,
  PRIMARY KEY (`ledger_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;

CREATE TABLE `cheque` (
  `cheque_number` varchar(45) NOT NULL,
  `cheque_date` date NOT NULL,
  `cheque_amount` float NOT NULL,
  `cheque_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`cheque_id`),
  UNIQUE KEY `cheque_number_UNIQUE` (`cheque_number`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;


CREATE TABLE `accounts_balance` (
  `account_name` varchar(45) NOT NULL,
  `balance` float NOT NULL,
  PRIMARY KEY (`account_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `graveyard_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_graveyard_fk_idx` (`ledger_id`),
  CONSTRAINT `santha_id_graveyard_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

CREATE TABLE `mens_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_mens_fk_idx` (`ledger_id`),
  CONSTRAINT `santha_id_mens_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;



CREATE TABLE `missionary_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `service_id_UNIQUE` (`service_id`),
  UNIQUE KEY `santha_id_UNIQUE` (`santha_id`),
  KEY `service_id_fk_idx` (`service_id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_missionary_fk_idx` (`ledger_id`),
  CONSTRAINT `santha_id_missionary_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `service_id_missionary_fk` FOREIGN KEY (`service_id`) REFERENCES `service_offering` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;

CREATE TABLE `pc_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `service_id_fk_idx` (`service_id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_pc_fk_idx` (`ledger_id`),
  CONSTRAINT `santha_id_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `service_id_fk` FOREIGN KEY (`service_id`) REFERENCES `service_offering` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=latin1;


CREATE TABLE `primary_school_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_primary_school_fk_idx` (`ledger_id`),
  CONSTRAINT `santha_id_primary_school_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;


CREATE TABLE `sto_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` float NOT NULL,
  `description` varchar(100) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `service_id_fk_idx` (`service_id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_sto_fk_idx` (`ledger_id`),
  CONSTRAINT `santha_id_sto_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `service_id_sto_fk` FOREIGN KEY (`service_id`) REFERENCES `service_offering` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=latin1;


CREATE TABLE `sunday_school_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `service_id_fk_idx` (`service_id`),
  KEY `ledger_sunday_school_fk_idx` (`ledger_id`),
  CONSTRAINT `service_id_sunday_school_fk` FOREIGN KEY (`service_id`) REFERENCES `service_offering` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;


CREATE TABLE `womens_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_womens_fk_idx` (`ledger_id`),
  CONSTRAINT `santha_id_womens_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;


CREATE TABLE `youth_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_youth_fk_idx` (`ledger_id`),
  CONSTRAINT `santha_id_youth_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

CREATE TABLE `bank_graveyard_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_bank_graveyard_fk_idx` (`ledger_id`),
  CONSTRAINT `ledger_bank_graveyard_fk` FOREIGN KEY (`ledger_id`) REFERENCES `ledger` (`ledger_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `santha_id_bank_graveyard_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

CREATE TABLE `bank_mens_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_bank_mens_fk_idx` (`ledger_id`),
  CONSTRAINT `bank_mens_ledger_fk` FOREIGN KEY (`ledger_id`) REFERENCES `ledger` (`ledger_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `santha_id_bank_mens_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

CREATE TABLE `bank_missionary_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `service_id_UNIQUE` (`service_id`),
  UNIQUE KEY `santha_id_UNIQUE` (`santha_id`),
  KEY `service_id_fk_idx` (`service_id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_bank_missionary_fk_idx` (`ledger_id`),
  CONSTRAINT `ledger_bank_missionary_fk` FOREIGN KEY (`ledger_id`) REFERENCES `ledger` (`ledger_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `santha_id_bank_missionary_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `service_id_bank_missionary_fk` FOREIGN KEY (`service_id`) REFERENCES `service_offering` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

CREATE TABLE `bank_pc_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `service_id_fk_idx` (`service_id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_bank_pc_fk_idx` (`ledger_id`),
  CONSTRAINT `ledger_bank_pc_fk` FOREIGN KEY (`ledger_id`) REFERENCES `ledger` (`ledger_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `santha_id_bank_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `service_id_bank_fk` FOREIGN KEY (`service_id`) REFERENCES `service_offering` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=latin1;

CREATE TABLE `bank_primary_school_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_bank_primary_school_fk_idx` (`ledger_id`),
  CONSTRAINT `santha_id_bank_primary_school_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;


CREATE TABLE `bank_sto_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` float NOT NULL,
  `description` varchar(100) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `service_id_fk_idx` (`service_id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_bank_sto_fk_idx` (`ledger_id`),
  CONSTRAINT `santha_id_bank_sto_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `service_id_bank_sto_fk` FOREIGN KEY (`service_id`) REFERENCES `service_offering` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;


CREATE TABLE `bank_sunday_school_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `service_id_fk_idx` (`service_id`),
  KEY `ledger_bank_sunday_school_fk_idx` (`ledger_id`),
  CONSTRAINT `service_id_bank_sunday_school_fk` FOREIGN KEY (`service_id`) REFERENCES `service_offering` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;


CREATE TABLE `bank_womens_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_bank_womens_fk_idx` (`ledger_id`),
  CONSTRAINT `santha_id_bank_womens_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;


CREATE TABLE `bank_youth_account` (
  `amount` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `ledger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  KEY `ledger_bank_youth_fk_idx` (`ledger_id`),
  CONSTRAINT `santha_id_bank_youth_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;


CREATE TABLE `cheque_bank_graveyard` (
  `bank_graveyard_account_id` int(11) NOT NULL,
  `cheque_id` int(11) NOT NULL,
  PRIMARY KEY (`bank_graveyard_account_id`,`cheque_id`),
  KEY `cheque_bank_graveyard_cheque_fk_idx` (`cheque_id`),
  CONSTRAINT `cheque_bank_graveyard_cheque_fk` FOREIGN KEY (`cheque_id`) REFERENCES `cheque` (`cheque_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cheque_bank_graveyard_graveyard_fk` FOREIGN KEY (`bank_graveyard_account_id`) REFERENCES `bank_graveyard_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `cheque_bank_mens` (
  `bank_mens_account_id` int(11) NOT NULL,
  `cheque_id` int(11) NOT NULL,
  PRIMARY KEY (`bank_mens_account_id`,`cheque_id`),
  KEY `cheque_bank_mens_cheque_fk_idx` (`cheque_id`),
  CONSTRAINT `cheque_bank_mens_cheque_fk` FOREIGN KEY (`cheque_id`) REFERENCES `cheque` (`cheque_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cheque_bank_mens_mens_fk` FOREIGN KEY (`bank_mens_account_id`) REFERENCES `bank_mens_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `cheque_bank_missionary` (
  `bank_missionary_account_id` int(11) NOT NULL,
  `cheque_id` int(11) NOT NULL,
  PRIMARY KEY (`bank_missionary_account_id`,`cheque_id`),
  KEY `cheque_bank_missionary_cheque_fk_idx` (`cheque_id`),
  CONSTRAINT `cheque_bank_missionary_cheque_fk` FOREIGN KEY (`cheque_id`) REFERENCES `cheque` (`cheque_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cheque_bank_missionary_missionary_fk` FOREIGN KEY (`bank_missionary_account_id`) REFERENCES `bank_missionary_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `cheque_bank_pc` (
  `bank_pc_account_id` int(11) NOT NULL,
  `cheque_id` int(11) NOT NULL,
  PRIMARY KEY (`bank_pc_account_id`,`cheque_id`),
  KEY `cheque_bank_pc_cheque_fk_idx` (`cheque_id`),
  CONSTRAINT `cheque_bank_pc_cheque_fk` FOREIGN KEY (`cheque_id`) REFERENCES `cheque` (`cheque_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cheque_bank_pc_pc_fk` FOREIGN KEY (`bank_pc_account_id`) REFERENCES `bank_pc_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `cheque_bank_primary_school` (
  `bank_primary_school_account_id` int(11) NOT NULL,
  `cheque_id` int(11) NOT NULL,
  PRIMARY KEY (`bank_primary_school_account_id`,`cheque_id`),
  KEY `cheque_bank_primary_school_cheque_fk_idx` (`cheque_id`),
  CONSTRAINT `cheque_bank_primary_school_cheque_fk` FOREIGN KEY (`cheque_id`) REFERENCES `cheque` (`cheque_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cheque_bank_ps_ps_fk` FOREIGN KEY (`bank_primary_school_account_id`) REFERENCES `bank_primary_school_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `cheque_bank_sto` (
  `bank_sto_account_id` int(11) NOT NULL,
  `cheque_id` int(11) NOT NULL,
  PRIMARY KEY (`bank_sto_account_id`,`cheque_id`),
  KEY `bank_sto_cheque_fk_idx` (`cheque_id`),
  CONSTRAINT `bank_sto_cheque_cheque_fk` FOREIGN KEY (`cheque_id`) REFERENCES `cheque` (`cheque_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bank_sto_cheque_sto_fk` FOREIGN KEY (`bank_sto_account_id`) REFERENCES `bank_sto_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `cheque_bank_sunday_school` (
  `bank_sunday_school_account_id` int(11) NOT NULL,
  `cheque_id` int(11) NOT NULL,
  PRIMARY KEY (`bank_sunday_school_account_id`,`cheque_id`),
  KEY `cheque_bank_sunday_school_cheque_fk_idx` (`cheque_id`),
  CONSTRAINT `cheque_bank_sunday_school_cheque_fk` FOREIGN KEY (`cheque_id`) REFERENCES `cheque` (`cheque_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cheque_bank_ss_ss_fk` FOREIGN KEY (`bank_sunday_school_account_id`) REFERENCES `bank_sunday_school_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `cheque_bank_womens` (
  `bank_womens_account_id` int(11) NOT NULL,
  `cheque_id` int(11) NOT NULL,
  PRIMARY KEY (`bank_womens_account_id`,`cheque_id`),
  KEY `cheque_bank_womens_cheque_fk_idx` (`cheque_id`),
  CONSTRAINT `cheque_bank_womens_cheque_fk` FOREIGN KEY (`cheque_id`) REFERENCES `cheque` (`cheque_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cheque_bank_womens_womens_fk` FOREIGN KEY (`bank_womens_account_id`) REFERENCES `bank_womens_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `cheque_bank_youth` (
  `bank_youth_account_id` int(11) NOT NULL,
  `cheque_id` int(11) NOT NULL,
  PRIMARY KEY (`bank_youth_account_id`,`cheque_id`),
  KEY `cheque_bank_youth_cheque_fk_idx` (`cheque_id`),
  CONSTRAINT `cheque_bank_youth_cheque_fk` FOREIGN KEY (`cheque_id`) REFERENCES `cheque` (`cheque_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cheque_bank_youth_youth_fk` FOREIGN KEY (`bank_youth_account_id`) REFERENCES `bank_youth_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `ledger` VALUES (1,'Santha - Subscription Amount'),(2,'Santha - Harvest Festival'),(3,'Santha - Education Help'),(4,'Santha - Poor Help'),(5,'Santha - Bag Offer'),(6,'Santha - Missionary Offering'),(7,'Santha - Men\'s Fellowship'),(8,'Santha - Women\'s Fellowship'),(9,'Santha - Primary School'),(10,'Santha - Youth'),(11,'Santha - Graveyard'),(12,'Service - Sunday School Offering'),(13,'Service - Other Offering'),(14,'Service - Special Thanks Offering'),(15,'Service - Thanks Offering'),(16,'Service - Confirmation Offering'),(17,'Service - Marriage Offering'),(18,'Service - Auction Offering'),(19,'Service - Service Offering'),(20,'Service - Missionary Offering'),(21,'Santha - Thanks Offering'),(22,'Santha - Special Thanks Offering'),(23,'Santha - Church Renovation');