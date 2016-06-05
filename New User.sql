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
  PRIMARY KEY (`id`),
  KEY `family_no_idx` (`no`),
  CONSTRAINT `family_no` FOREIGN KEY (`no`) REFERENCES `family` (`no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;


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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;


CREATE TABLE `pc_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` float NOT NULL,
  `balance` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `service_id_fk_idx` (`service_id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  CONSTRAINT `santha_id_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `service_id_fk` FOREIGN KEY (`service_id`) REFERENCES `service_offering` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;


CREATE TABLE `graveyard_account` (
  `amount` float NOT NULL,
  `balance` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  CONSTRAINT `santha_id_graveyard_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;


CREATE TABLE `mens_account` (
  `amount` float NOT NULL,
  `balance` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  CONSTRAINT `santha_id_mens_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;


CREATE TABLE `missionary_account` (
  `amount` float NOT NULL,
  `balance` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `service_id_UNIQUE` (`service_id`),
  UNIQUE KEY `santha_id_UNIQUE` (`santha_id`),
  KEY `service_id_fk_idx` (`service_id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  CONSTRAINT `santha_id_missionary_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `service_id_missionary_fk` FOREIGN KEY (`service_id`) REFERENCES `service_offering` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;


CREATE TABLE `primary_school_account` (
  `amount` float NOT NULL,
  `balance` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  CONSTRAINT `santha_id_primary_school_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;


CREATE TABLE `sto_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` float NOT NULL,
  `balance` float NOT NULL,
  `description` varchar(100) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `service_id_fk_idx` (`service_id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  CONSTRAINT `santha_id_sto_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `service_id_sto_fk` FOREIGN KEY (`service_id`) REFERENCES `service_offering` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;


CREATE TABLE `sunday_school_account` (
  `amount` float NOT NULL,
  `balance` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `service_id_fk_idx` (`service_id`),
  CONSTRAINT `service_id_sunday_school_fk` FOREIGN KEY (`service_id`) REFERENCES `service_offering` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;


CREATE TABLE `womens_account` (
  `amount` float NOT NULL,
  `balance` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  CONSTRAINT `santha_id_womens_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;


CREATE TABLE `youth_account` (
  `amount` float NOT NULL,
  `balance` float NOT NULL,
  `description` varchar(45) NOT NULL,
  `cr_dr` varchar(2) NOT NULL,
  `santha_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `santha_id_fk_idx` (`santha_id`),
  CONSTRAINT `santha_id_youth_fk` FOREIGN KEY (`santha_id`) REFERENCES `santha` (`santha_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
