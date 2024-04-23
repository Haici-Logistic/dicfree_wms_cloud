-- df_wms_sku_supplier: table
DROP TABLE IF EXISTS `df_wms_sku_supplier`;
CREATE TABLE `df_wms_sku_supplier`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `sku_code`     varchar(50) DEFAULT NULL COMMENT 'SKU唯一编码',
    `product_code` varchar(30) DEFAULT NULL COMMENT '产品编码',
    `supplier`     varchar(20) DEFAULT NULL COMMENT '客户',
    `pick_up_code` varchar(4) DEFAULT NULL COMMENT '随机取件码',
    `location`     varchar(20) DEFAULT NULL COMMENT '位置',
    `delivery_to`  varchar(20) DEFAULT NULL COMMENT '目的地',
    `sorting_to`   varchar(20) DEFAULT NULL COMMENT '中转',

    `create_time`  datetime  DEFAULT NULL,
    `created_by`   varchar(30)   DEFAULT NULL,
    `update_time`  datetime  DEFAULT NULL,
    `update_by`    varchar(30)   DEFAULT NULL,
    `version`      int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='SKU客户关系表';


-- df_wms_sku_sn: table
DROP TABLE IF EXISTS `df_wms_sku_sn`;
CREATE TABLE `df_wms_sku_sn`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `sn_code`          varchar(20) DEFAULT NULL COMMENT 'SN唯一编码',
    `sku_code`         varchar(50) DEFAULT NULL COMMENT 'SKU唯一编码',
    `serial_no`        varchar(3)      DEFAULT NULL COMMENT '序号',
    `pcs`              int(3)      DEFAULT NULL COMMENT '同一批总数',
    `supplier_sn_code` varchar(40) DEFAULT NULL COMMENT '客户SN唯一编码',
    `batch_no`         varchar(20) DEFAULT NULL COMMENT '导入批次号',

    `create_time`      datetime  DEFAULT NULL,
    `created_by`       varchar(30)   DEFAULT NULL,
    `update_time`      datetime  DEFAULT NULL,
    `update_by`    varchar(30)   DEFAULT NULL,
    `version`          int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='SKU详细SN表';

-- df_wms_stocktake_record: table
DROP TABLE IF EXISTS `df_wms_stocktake_record`;
CREATE TABLE `df_wms_stocktake_record`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `type`             varchar(20) DEFAULT NULL COMMENT '盘点类型',
    `direction`        varchar(5) DEFAULT NULL COMMENT '盘点方向',
    `trigger_time`     datetime  DEFAULT NULL COMMENT '触发时间',
    `sku_code`         varchar(50) DEFAULT NULL COMMENT 'SKU唯一编码',
    `sn_code`          varchar(20) DEFAULT NULL COMMENT 'SN唯一编码',
    `product_code`     varchar(30) DEFAULT NULL COMMENT '产品编码',
    `supplier_sn_code` varchar(40) DEFAULT NULL COMMENT '客户SN唯一编码',
    `hub`              varchar(30) DEFAULT NULL COMMENT '仓库名',

    `create_time`      datetime  DEFAULT NULL,
    `created_by`       varchar(30)   DEFAULT NULL,
    `update_time`      datetime  DEFAULT NULL,
    `update_by`    varchar(30)   DEFAULT NULL,
    `version`          int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='库存操作记录';

-- df_wms_printer: table
DROP TABLE IF EXISTS `df_wms_printer`;
CREATE TABLE `df_wms_printer`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20)   DEFAULT NULL COMMENT '用户id',
    `sn`          varchar(20) DEFAULT NULL COMMENT '打印机编码',
    `key`         varchar(20) DEFAULT NULL COMMENT '打印机识别码',
    `name`        varchar(20) DEFAULT NULL COMMENT '打印机名称',
    `sim`         varchar(20) DEFAULT NULL COMMENT '打印机sim卡',

    `create_time` datetime  DEFAULT NULL,
    `created_by`  varchar(30)   DEFAULT NULL,
    `update_time` datetime  DEFAULT NULL,
    `update_by`    varchar(30)   DEFAULT NULL,
    `version`     int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='打印机表';


INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('602', 'Warehouse', '🚚 ', '#1', true, true, false, '0', '/602/', '0', 'S', '1', 'dicfree', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('60201000', 'SKU&SN', NULL, '/admin/skus', true, false, true, '602', '/602/60201000/', '1', 'S', '100', 'dicfree', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('60202000', 'Inbounds', NULL, '/admin/inbounds', true, false, true, '602', '/602/60202000/', '1', 'S', '200', 'dicfree', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('60203000', 'Outbounds', NULL, '/admin/outbounds', true, false, true, '602', '/602/60203000/', '1', 'S', '300', 'dicfree', NULL, NULL, NULL, NULL, '0');


INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('60201001', '打印机-绑定', NULL, '/ads/printer/bind', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, '0');

INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('60201001', 'DICFREE_PRINT_BIND', '打印机-绑定', NULL, NULL, NULL, NULL, '0');

INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('60201001', '60201001');