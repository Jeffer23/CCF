CREATE TABLE `cheque_bank_sto` (
  `bank_sto_account_id` int(11) NOT NULL,
  `cheque_id` int(11) NOT NULL,
  PRIMARY KEY (`bank_sto_account_id`,`cheque_id`),
  KEY `bank_sto_cheque_fk_idx` (`cheque_id`),
  CONSTRAINT `bank_sto_cheque_cheque_fk` FOREIGN KEY (`cheque_id`) REFERENCES `cheque` (`cheque_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bank_sto_cheque_sto_fk` FOREIGN KEY (`bank_sto_account_id`) REFERENCES `bank_sto_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `cheque_bank_sto` (
  `bank_sto_account_id` int(11) NOT NULL,
  `cheque_id` int(11) NOT NULL,
  PRIMARY KEY (`bank_sto_account_id`,`cheque_id`),
  KEY `bank_sto_cheque_fk_idx` (`cheque_id`),
  CONSTRAINT `bank_sto_cheque_cheque_fk` FOREIGN KEY (`cheque_id`) REFERENCES `cheque` (`cheque_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bank_sto_cheque_sto_fk` FOREIGN KEY (`bank_sto_account_id`) REFERENCES `bank_sto_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE `cheque` (
  `cheque_number` varchar(45) NOT NULL,
  `cheque_date` date NOT NULL,
  `cheque_amount` float NOT NULL,
  `cheque_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`cheque_id`),
  UNIQUE KEY `cheque_number_UNIQUE` (`cheque_number`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;


CREATE TABLE `cheque_bank_pc` (
  `bank_pc_account_id` int(11) NOT NULL,
  `cheque_id` int(11) NOT NULL,
  PRIMARY KEY (`bank_pc_account_id`,`cheque_id`),
  KEY `cheque_bank_pc_cheque_fk_idx` (`cheque_id`),
  CONSTRAINT `cheque_bank_pc_pc_fk` FOREIGN KEY (`bank_pc_account_id`) REFERENCES `bank_pc_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cheque_bank_pc_cheque_fk` FOREIGN KEY (`cheque_id`) REFERENCES `cheque` (`cheque_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
