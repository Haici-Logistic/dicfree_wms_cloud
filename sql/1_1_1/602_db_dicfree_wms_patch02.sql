-- ----------------------------
-- Table structure for df_wms_arrival_order
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_arrival_order`;
CREATE TABLE `df_wms_arrival_order`
(
    `id`                   bigint(20) NOT NULL AUTO_INCREMENT,
    `supplier`             varchar(20) DEFAULT NULL COMMENT '客户',
    `third_order_no`       varchar(30) DEFAULT NULL COMMENT '轻流订单号',
    `container_no_virtual` varchar(5)  DEFAULT NULL COMMENT '虚拟车号',
    `container_no_real`    varchar(20) DEFAULT NULL COMMENT '真实车号',
    `arriving_date`        datetime    DEFAULT NULL COMMENT '计划到货日期',
    `inbound_count`        int(3)      DEFAULT NULL COMMENT '已入库数量',
    `total_count`          int(3)      DEFAULT NULL COMMENT '总计数量',
    `status`               varchar(20) DEFAULT NULL COMMENT '状态',

    `create_time`          datetime    DEFAULT NULL,
    `created_by`           varchar(30) DEFAULT NULL,
    `update_time`          datetime    DEFAULT NULL,
    `update_by`            varchar(30) DEFAULT NULL,
    `version`              int(11)     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT ='入库订单';

-- ----------------------------
-- Table structure for df_wms_arrival_order_item
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_arrival_order_item`;
CREATE TABLE `df_wms_arrival_order_item`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `arrival_order_id` bigint(20)  DEFAULT NULL COMMENT '入库订单id',
    `sku_code`         varchar(50) DEFAULT NULL COMMENT '产品编码 +‘-’+ 客户',
    `inbound_count`    int(3)      DEFAULT NULL COMMENT '已入库数量',
    `total_count`      int(3)      DEFAULT NULL COMMENT '总计数量',
    `status`           varchar(20) DEFAULT NULL COMMENT '状态',

    `create_time`      datetime    DEFAULT NULL,
    `created_by`       varchar(30) DEFAULT NULL,
    `update_time`      datetime    DEFAULT NULL,
    `update_by`        varchar(30) DEFAULT NULL,
    `version`          int(11)     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT ='入库订单详情';

-- ----------------------------
-- Table structure for df_wms_container_task
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_container_task`;
CREATE TABLE `df_wms_container_task`
(
    `id`                   bigint(20) NOT NULL AUTO_INCREMENT,
    `container_no_virtual` varchar(5)   DEFAULT NULL COMMENT '虚拟车号',
    `container_no_real`    varchar(20)  DEFAULT NULL COMMENT '真实车号',
    `departure_date`       datetime     DEFAULT NULL COMMENT '出发日期',
    `delivery_to_array`    varchar(100) DEFAULT NULL COMMENT '出发目的地',
    `outbound_count`       int(3)       DEFAULT NULL COMMENT '已出库数量',
    `total_count`          int(3)       DEFAULT NULL COMMENT '总计数量',
    `status`               varchar(20)  DEFAULT NULL COMMENT '状态',

    `create_time`          datetime     DEFAULT NULL,
    `created_by`           varchar(30)  DEFAULT NULL,
    `update_time`          datetime     DEFAULT NULL,
    `update_by`            varchar(30)  DEFAULT NULL,
    `version`              int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT ='集货任务';

-- ----------------------------
-- Table structure for df_wms_delivery_order
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_delivery_order`;
CREATE TABLE `df_wms_delivery_order`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT,
    `container_task_id` bigint(20)  DEFAULT NULL COMMENT '集货任务id',
    `supplier`             varchar(20) DEFAULT NULL COMMENT '客户',
    `third_order_no`    varchar(30) DEFAULT NULL COMMENT '轻流订单号',
    `delivery_date`     datetime    DEFAULT NULL COMMENT '计划派送时间',
    `delivery_to`       varchar(20) DEFAULT NULL COMMENT '出发目的地',
    `appointment_id`    varchar(30) DEFAULT NULL COMMENT '送仓预约号',
    `sorting_member`    varchar(30) DEFAULT NULL COMMENT '分拣人',
    `outbound_count`    int(3)      DEFAULT NULL COMMENT '已出库数量',
    `total_count`       int(3)      DEFAULT NULL COMMENT '总计数量',
    `status`            varchar(20) DEFAULT NULL COMMENT '状态',

    `create_time`       datetime    DEFAULT NULL,
    `created_by`        varchar(30) DEFAULT NULL,
    `update_time`       datetime    DEFAULT NULL,
    `update_by`         varchar(30) DEFAULT NULL,
    `version`           int(11)     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT ='出库订单';

-- ----------------------------
-- Table structure for df_wms_delivery_order_item
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_delivery_order_item`;
CREATE TABLE `df_wms_delivery_order_item`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT,
    `container_task_id` bigint(20)  DEFAULT NULL COMMENT '集货任务id',
    `delivery_order_id` bigint(20)  DEFAULT NULL COMMENT '出库订单id',
    `sku_code`          varchar(50) DEFAULT NULL COMMENT '产品编码 +‘-’+ 客户',
    `outbound_count`    int(3)      DEFAULT NULL COMMENT '已出库数量',
    `total_count`       int(3)      DEFAULT NULL COMMENT '总计数量',
    `status`            varchar(20) DEFAULT NULL COMMENT '状态',

    `create_time`       datetime    DEFAULT NULL,
    `created_by`        varchar(30) DEFAULT NULL,
    `update_time`       datetime    DEFAULT NULL,
    `update_by`         varchar(30) DEFAULT NULL,
    `version`           int(11)     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT ='出库订单详情';


-- ----------------------------
-- Table structure for uaa_login_record
-- ----------------------------
DROP TABLE IF EXISTS `uaa_login_record`;
CREATE TABLE `uaa_login_record` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `client_id` varchar(30) DEFAULT NULL COMMENT '客户端id',
    `uid` VARCHAR(30) DEFAULT NULL COMMENT '用户唯一id',
    `username` varchar(60) DEFAULT NULL COMMENT '用户名',
    `token` varchar(60) DEFAULT NULL COMMENT 'token',
    `token_issue_time` datetime DEFAULT NULL COMMENT 'token发出时间',
    `token_expires_time` datetime DEFAULT NULL COMMENT 'token失效时间',
    `ip` varchar(30) DEFAULT NULL COMMENT 'ip',
    `network` varchar(20) DEFAULT NULL COMMENT '网络',
    `user_agent` varchar(500) DEFAULT NULL COMMENT 'App版本，系统类型，系统版本等',
    `device_id` varchar(60) DEFAULT NULL COMMENT '设备id',

    `create_time` datetime DEFAULT NULL,
    `created_by` VARCHAR(30) DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    `update_by` VARCHAR(30) DEFAULT NULL,
    `version` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COMMENT='登录记录';

alter table df_wms_arrival_order add index idx_arrivalorder_thirdorderno(`third_order_no`);
alter table df_wms_arrival_order_item add index idx_arrivalorderitem_arrivalorderid(`arrival_order_id`);
alter table df_wms_delivery_order add index idx_deliveryorder_containertaskid(`container_task_id`);
alter table df_wms_delivery_order add index idx_deliveryorder_thirdorderno(`third_order_no`);
alter table df_wms_delivery_order_item add index idx_deliveryorderitm_taskid_orderid(`container_task_id`,`delivery_order_id`);
alter table df_wms_delivery_order_item add index idx_deliveryorderitm_sku_code(`sku_code`);

alter table df_wms_sku_sn add index idx_skusn_skucode(`sku_code`);




ALTER table df_wms_sku_sn add column `location` varchar(20) DEFAULT NULL COMMENT '存放位置' after `sn_code`;
ALTER table df_wms_sku_sn add column `status` varchar(10) DEFAULT NULL COMMENT '当前状态' after `sn_code`;
ALTER table df_wms_sku_sn add column `outbound_time` datetime DEFAULT NULL COMMENT '出库时间' after `sn_code`;
ALTER table df_wms_sku_sn add column `delivery_order_item_id` bigint(20)  DEFAULT NULL COMMENT '出库订单id' after `sn_code`;
ALTER table df_wms_sku_sn add column `inbound_time` datetime DEFAULT NULL COMMENT '入库时间' after `sn_code`;
ALTER table df_wms_sku_sn add column `arrival_order_item_id` bigint(20)  DEFAULT NULL COMMENT '入库订单id' after `sn_code`;

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('60204000', 'Arrival Order', NULL, '/admin/arrival-orders', true, false, true, '602', '/602/60204000/', '1', 'A', '400', 'dicfree', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('60205000', 'Container Task', NULL, '/admin/container-tasks', true, false, true, '602', '/602/60205000/', '1', 'A', '500', 'dicfree', NULL, NULL, NULL, NULL, '0');

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('60200004', '客户端-入库订单新增', NULL, '/client/arrivalOrder/add', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('60200005', '客户端-入库订单状态修改', NULL, '/client/arrivalOrder/statusEdit', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('60200006', '客户端-出库订单新增', NULL, '/client/deliveryOrder/add', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('60200007', '客户端-出库订单状态修改', NULL, '/client/deliveryOrder/statusEdit', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('60200004', '60200001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('60200005', '60200001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('60200006', '60200001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('60200007', '60200001');

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60204001, '入库订单-分页', NULL, '/ads/arrivalOrder/page', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60204002, '入库订单-详情列表', NULL, '/ads/arrivalOrder/itemList', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60204003, '入库订单-详情SN下载', NULL, '/ads/arrivalOrder/itemSnDownload', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60205001, '集货任务-分页', NULL, '/ads/containerTask/page', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60205002, '集货任务-详情列表', NULL, '/ads/containerTask/itemList', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60205003, '集货任务-详情SN下载', NULL, '/ads/containerTask/itemSnDownload', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60205004, '集货任务-新增', NULL, '/ads/containerTask/add*', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60205005, '集货任务-编辑', NULL, '/ads/containerTask/edit*', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60205006, '集货任务-详情', NULL, '/ads/containerTask/detail', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60205007, '集货任务-删除', NULL, '/ads/containerTask/delete', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60205008, '出库订单-未完成列表', NULL, '/ads/deliveryOrder/undoList', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60205009, '出库订单-未完成目的地', NULL, '/ads/deliveryOrder/undoDeliveryToList', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);

INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60204001, 'DICFREE_AO_QUERY', '入库订单-查询', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60205001, 'DICFREE_CT_QUERY', '集货任务-查询', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60205002, 'DICFREE_CT_ADD', '集货任务-新增', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60205003, 'DICFREE_CT_EDIT', '集货任务-编辑', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60205004, 'DICFREE_CT_DEL', '集货任务-删除', NULL, NULL, NULL, NULL, '0');

INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60204000, 60204001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60204001, 60204001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60204002, 60204001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60204003, 60204001);

INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205000, 60205001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205001, 60205001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205002, 60205001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205003, 60205001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205004, 60205002);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205005, 60205003);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205006, 60205001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205007, 60205004);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205008, 60205001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205009, 60205001);