DROP TABLE IF EXISTS `sevendays_reward`;
CREATE TABLE `sevendays_reward` (
	`account` varchar(255) NOT NULL,
	`ip`	varchar(255) DEFAULT NULL,
	`hwid` varchar(255) DEFAULT NULL,
	`date` mediumtext,
	`count` int(2) NOT NULL DEFAULT '1',
	UNIQUE KEY `unique_account` ('account') USING BTREE
)	ENGINE=MyISAM DEFAULT CHARSET=latin1;
SET FOREIGN_KEY_CHECKS=1;