-- 新增字段location_type
ALTER table df_wms_box_arrival_order_item add column `location_type` varchar(10) DEFAULT NULL COMMENT '库位类型' after `total_count`;

ALTER table df_wms_box_arrival_order add column `total_bulk_count` int(4) DEFAULT NULL COMMENT '总计混库数量' after `total_count`;
ALTER table df_wms_box_arrival_order add column `total_whole_count` int(4) DEFAULT NULL COMMENT '总计整库数量' after `total_count`;
ALTER table df_wms_box_arrival_order add column `location_lock` bit(1) DEFAULT NULL COMMENT '是否已锁库' after `status`;

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60212000, 'Location Lock', NULL, '/admin/box-arrival-orders-plan', b'1', b'0', b'1', 602, '/602/60212000/', 1, 'A', 500, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`)
VALUES (60204002, 'DICFREE_BAO_PLAN', '箱货-入库订单-排库计划', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60212000, 60204002);


UPDATE `uaa_privilege` SET `id` = 60204003 WHERE `id` = 60202001;
UPDATE `uaa_resource_privilege` SET `privilege_id` = 60204003 WHERE `privilege_id` = 60202001;

UPDATE `uaa_privilege` SET `id` = 60205005 WHERE `id` = 60203001;
UPDATE `uaa_resource_privilege` SET `privilege_id` = 60205005 WHERE `privilege_id` = 60203001;

UPDATE `uaa_privilege` SET `id` = 60207003 WHERE `id` = 60208001;
UPDATE `uaa_resource_privilege` SET `privilege_id` = 60207003 WHERE `privilege_id` = 60208001;

INSERT INTO `uaa_role` (`code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('SUPPLIER', '客户', NULL, NULL, NULL, NULL, 0);
