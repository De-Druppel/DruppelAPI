CREATE TABLE `measurement` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `type` varchar(25) NOT NULL,
  `value` float NOT NULL,
  `date_created` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

CREATE TABLE `plant` (
  `id` int(11) NOT NULL,
  `esp_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

CREATE TABLE `v_measurement` (
`esp_id` int(11)
,`type` varchar(25)
,`value` double(19,2)
,`date_created` date
);

-- --------------------------------------------------------

DROP TABLE IF EXISTS `v_measurement`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `v_measurement`  AS  select `t2`.`esp_id` AS `esp_id`,`t1`.`type` AS `type`,round(avg(`t1`.`value`),2) AS `value`,`t1`.`date_created` AS `date_created` from (`measurement` `t1` join `plant` `t2` on(`t1`.`client_id` = `t2`.`id`)) group by `t2`.`esp_id`,`t1`.`date_created` ;

ALTER TABLE `measurement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `plant_relation` (`client_id`);

ALTER TABLE `plant`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `plant_esp_id_uindex` (`esp_id`);

ALTER TABLE `measurement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

ALTER TABLE `plant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE `measurement`
  ADD CONSTRAINT `plant_relation` FOREIGN KEY (`client_id`) REFERENCES `plant` (`id`);
COMMIT;
