CREATE TABLE `web_donations` (   
	`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
	`obj_id` int(11) DEFAULT NULL,
	`char_name` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
	`amount` int(11) NOT NULL,   `time` int(11) NOT NULL,
	`pay_system` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
	`status` tinyint(1) NOT NULL DEFAULT '0',
	`email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
	`real_amount` int(11) DEFAULT '0',
	PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=1376 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
