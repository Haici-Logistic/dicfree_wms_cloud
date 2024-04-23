ALTER table df_wms_product_delivery_order add column `wave_task_id` bigint(20) DEFAULT NULL COMMENT '波次任务id' after `id`;
ALTER table df_wms_product_delivery_order add column `type` varchar(2) DEFAULT NULL COMMENT '出库单类型' after `wave_task_id`;
ALTER table df_wms_product_delivery_order add column `off_shelf_status` varchar(20) DEFAULT NULL COMMENT '下架状态' after `outbound`;
ALTER table df_wms_product_delivery_order add column `off_shelf_count` int(3) DEFAULT NULL COMMENT '下架数量' after `off_shelf_status`;
ALTER table df_wms_product_delivery_order add column `basket_no` varchar(10) DEFAULT NULL COMMENT '分配的篮子号' after `total_count`;
ALTER table df_wms_product_delivery_order add column `sorting_status` varchar(20) DEFAULT NULL COMMENT '分拣状态' after `off_shelf_count`;
ALTER table df_wms_product_delivery_order add column `sorting_count` int(3) DEFAULT NULL COMMENT '分拣数量' after `sorting_status`;

ALTER table df_wms_product_delivery_order_item add column `wave_task_id` bigint(20) DEFAULT NULL COMMENT '波次任务id' after `id`;
ALTER table df_wms_product_delivery_order_item add column `off_shelf_status` varchar(20) DEFAULT NULL COMMENT '下架状态' after `product_sku_code`;
ALTER table df_wms_product_delivery_order_item add column `off_shelf_count` int(3) DEFAULT NULL COMMENT '下架数量' after `off_shelf_status`;
ALTER table df_wms_product_delivery_order_item add column `sorting_status` varchar(20) DEFAULT NULL COMMENT '分拣状态' after `off_shelf_count`;
ALTER table df_wms_product_delivery_order_item add column `sorting_count` int(3) DEFAULT NULL COMMENT '分拣数量' after `sorting_status`;

ALTER table df_wms_product_sn add column `sorted` bit(1) DEFAULT NULL COMMENT '是否已分拣' after `off_shelf_time`;
ALTER table df_wms_product_sn add column `verified` bit(1) DEFAULT NULL COMMENT '是否已检验' after `sorted`;

-- ----------------------------
-- Table structure for df_wms_product_wave_task
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_product_wave_task`;
CREATE TABLE `df_wms_product_wave_task`
(
    `id`                   bigint(20) NOT NULL AUTO_INCREMENT,
    `unique_no`            varchar(30) DEFAULT NULL COMMENT '波次号',
    `collection_area_code` varchar(30) DEFAULT NULL COMMENT '集货区编码',
    `off_shelf_status`     varchar(20) DEFAULT NULL COMMENT '下架状态',
    `off_shelf_count`      int(5) DEFAULT NULL COMMENT '下架数量(按sn数)',
    `collection`           bit(1) DEFAULT NULL COMMENT '集货状态',
    `basket_status`        varchar(20) DEFAULT NULL COMMENT '分篮状态',
    `basket_count`         int(3) DEFAULT NULL COMMENT '分篮数量(按od数)',
    `sorting_status`       varchar(20) DEFAULT NULL COMMENT '分拣状态',
    `sorting_count`        int(5) DEFAULT NULL COMMENT '分拣数量(按sn数)',
    `total_od_count`       int(3) DEFAULT NULL COMMENT '订单总数量',
    `total_sn_count`       int(5) DEFAULT NULL COMMENT 'SN总数量',

    `create_time`          datetime    DEFAULT NULL,
    `created_by`           varchar(30) DEFAULT NULL,
    `update_time`          datetime    DEFAULT NULL,
    `update_by`            varchar(30) DEFAULT NULL,
    `version`              int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='出库波次任务';

-- ----------------------------
-- Table structure for df_wms_collection_area
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_collection_area`;
CREATE TABLE `df_wms_collection_area`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT,
    `code`              varchar(30) DEFAULT NULL COMMENT '集货区编码',
    `status`            varchar(20) DEFAULT NULL COMMENT '集货状态',
    `wave_task_unique_no` varchar(30) DEFAULT NULL COMMENT '波次号',

    `create_time`       datetime    DEFAULT NULL,
    `created_by`        varchar(30) DEFAULT NULL,
    `update_time`       datetime    DEFAULT NULL,
    `update_by`         varchar(30) DEFAULT NULL,
    `version`           int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='集货区';


INSERT INTO `uaa_role` (`code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('DICFREE_PDA_ALL', 'PDA客户端', NULL, NULL, NULL, NULL, 0);

INSERT INTO `df_wms_collection_area` (`code`, `status`, `wave_task_unique_no`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('COL-A01', 'EMPTY', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `df_wms_collection_area` (`code`, `status`, `wave_task_unique_no`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('COL-B01', 'EMPTY', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `df_wms_collection_area` (`code`, `status`, `wave_task_unique_no`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('COL-C01', 'EMPTY', NULL, NULL, NULL, NULL, NULL, 0);

INSERT INTO `df_wms_collection_area` (`code`, `status`, `wave_task_unique_no`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ( 'COL-A02', 'EMPTY', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `df_wms_collection_area` (`code`, `status`, `wave_task_unique_no`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ( 'COL-B02', 'EMPTY', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `df_wms_collection_area` (`code`, `status`, `wave_task_unique_no`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ( 'COL-C02', 'EMPTY', NULL, NULL, NULL, NULL, NULL, 0);

INSERT INTO `df_wms_collection_area` (`code`, `status`, `wave_task_unique_no`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ( 'COL-A03', 'EMPTY', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `df_wms_collection_area` (`code`, `status`, `wave_task_unique_no`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ( 'COL-B03', 'EMPTY', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `df_wms_collection_area` (`code`, `status`, `wave_task_unique_no`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ( 'COL-C03', 'EMPTY', NULL, NULL, NULL, NULL, NULL, 0);

alter table df_wms_product_wave_task add index idx_productwavetask_offshelfstatus(`off_shelf_status`);
alter table df_wms_product_delivery_order add index idx_productdeliveryorder_wtid_ofs(`wave_task_id`,`off_shelf_status`);
alter table df_wms_product_delivery_order_item add index idx_productdeliveryorder_pdoid_oss(`product_delivery_order_id`,`off_shelf_status`);
alter table df_wms_product_sn add index idx_productsn_productskucode(`product_sku_code`);
alter table df_wms_product_sn add index idx_productsn_doiid_ss(`delivery_order_item_id`,`shelf_status`);

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60213000, 'Outbound Sorting', NULL, '/admin/product-outbounds-sorting', b'1', b'0', b'1', 603, '/603/60213000/', 1, 'A', 250, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60207004, 'DICFREE_PRODUCT_SORTING', '电商-出库二检', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60213000, 60207004);

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60213001, '电商-波次任务-波次信息', NULL, '/ads/productWaveTask/info', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60213002, '电商-波次任务-未完成列表', NULL, '/ads/productWaveTask/orderUndoCalcList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60213003, '电商-波次任务-已完成列表', NULL, '/ads/productWaveTask/orderDoneCalcList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60213004, '电商-波次任务-商品列表', NULL, '/ads/productWaveTask/orderSnUndoList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60213005, '电商-波次任务-二次分拣', NULL, '/ads/productWaveTask/snSorting', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60213001, 60207004);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60213002, 60207004);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60213003, 60207004);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60213004, 60207004);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60213005, 60207004);