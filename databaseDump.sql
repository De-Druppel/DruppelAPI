CREATE TABLE `measurements` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `type` varchar(25) NOT NULL,
  `value` float NOT NULL,
  `date_created` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `v_measurements` (
`client_id` int(11)
,`type` varchar(25)
,`round(avg(value),2)` double(19,2)
,`date_created` date
);

DROP TABLE IF EXISTS `v_measurements`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `v_measurements`  AS  select `measurements`.`client_id` AS `client_id`,`measurements`.`type` AS `type`,round(avg(`measurements`.`value`),2) AS `round(avg(value),2)`,`measurements`.`date_created` AS `date_created` from `measurements` group by `measurements`.`date_created` ;

ALTER TABLE `measurements`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `measurements`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
COMMIT;