-- ----------------------------
-- Table structure for df_wms_product_delivery_order
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_product_delivery_order`;
CREATE TABLE `df_wms_product_delivery_order`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT,
    `supplier`        varchar(20)    DEFAULT NULL COMMENT '客户',
    `third_order_no`  varchar(30)    DEFAULT NULL COMMENT '外部订单编号',
    `name`            varchar(60)    DEFAULT NULL COMMENT '收件人姓名',
    `phone1`          varchar(30)    DEFAULT NULL COMMENT '收件人主叫电话',
    `phone2`          varchar(30)    DEFAULT NULL COMMENT '收件人备用电话',
    `province`        varchar(120)   DEFAULT NULL COMMENT '收件人省份',
    `city`            varchar(120)   DEFAULT NULL COMMENT '收件人城市',
    `address`         varchar(240)   DEFAULT NULL COMMENT '收件人详细地址',
    `dispatched`      bit(1)         DEFAULT NULL COMMENT '是否已接单',
    `courier`         varchar(10)    DEFAULT NULL COMMENT '快运商',
    `waybill`         varchar(30)    DEFAULT NULL COMMENT '快递单号',
    `cod`             decimal(19, 2) DEFAULT NULL COMMENT '到付收款金额',
    `remark`          varchar(120)   DEFAULT NULL COMMENT '备注',

    `create_time`     datetime       DEFAULT NULL,
    `created_by`      varchar(30)    DEFAULT NULL,
    `update_time`     datetime       DEFAULT NULL,
    `update_by`       varchar(30)    DEFAULT NULL,
    `version`         int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='到货单';

-- ----------------------------
-- Table structure for df_wms_product_delivery_order_item
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_product_delivery_order_item`;
CREATE TABLE `df_wms_product_delivery_order_item`
(
    `id`                     bigint(20) NOT NULL AUTO_INCREMENT,
    `product_delivery_order_id` bigint(20) DEFAULT NULL COMMENT '电商商品出库订单',
    `product_sku_code`          varchar(50) DEFAULT NULL COMMENT '电商商品号',
    `total_count`            int(3) DEFAULT NULL COMMENT '总计数量',

    `create_time`            datetime    DEFAULT NULL,
    `created_by`             varchar(30) DEFAULT NULL,
    `update_time`            datetime    DEFAULT NULL,
    `update_by`              varchar(30) DEFAULT NULL,
    `version`                int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='到货单';


INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60207000, 'Product Delivery Order(Magage)', NULL, '/admin/product-delivery-orders', b'1', b'0', b'1', 603, '/603/60207000/', 1, 'A', 200, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60208000, 'Product Delivery Order', NULL, '/admin/member-product-delivery-orders', b'1', b'0', b'1', 603, '/603/60208000/', 1, 'S', 200, 'dicfree', NULL, NULL, NULL, NULL, 0);

INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60207001, 'DICFREE_DO_QUERY', '电商出库-查询', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60207000, 60207001);

INSERT INTO `sys_param` (`type`, `code`, `name`, `parent_code`, `value`, `default_item`, `sequence`, `remark`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('STRING', 'member-product-delivery-order-batch-add', 'excel文件批量上传修改路径', NULL, 'object', b'0', 0, '开头不要带\"/\"，文件会上传至该路径下，且该路径会拼接在配置文件的access-domain参数后', NULL, NULL, NULL, NULL, 0);

UPDATE `uaa_resource` SET `url` = '/*/common/**' WHERE `id` = 10001012;
UPDATE `uaa_resource` SET `url` = '/*/self/**' WHERE `id` = 10001013;
UPDATE `uaa_resource` SET `url` = '/*/pmall/**' WHERE `id` = 10001014;

UPDATE `uaa_resource` SET `permit_type` = 'A' WHERE `id` = 60206000;
UPDATE `uaa_resource` SET `permit_type` = 'A' WHERE `id` = 60202000;
UPDATE `uaa_resource` SET `permit_type` = 'A' WHERE `id` = 60203000;
UPDATE `uaa_resource` SET `permit_type` = 'A' WHERE `id` = 60201000;

INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60206001, 'DICFREE_PRODUCT_INBOUND', '商品-入库', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60206000, 60206001);

INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60202001, 'DICFREE_BOX_INBOUND', '箱货-入库', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60202000, 60202001);

INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60203001, 'DICFREE_BOX_OUTBOUND', '箱货-出库', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60203000, 60203001);

UPDATE `uaa_privilege` SET `id` = 60200003 WHERE `id` = 60201001;
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60201001, 'DICFREE_BOX_SKU_QUERY', '箱货-SKU&SN查询', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60201000, 60201001);

UPDATE `uaa_resource` SET `id` = 60200009 WHERE `id` = 60201001;
UPDATE `uaa_resource_privilege` SET `resource_id` = 60200009, `privilege_id` = 60200003 WHERE `resource_id` = 60201001 AND `privilege_id` = 60201001;

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60204004, '箱货-入库订单-未完成列表', NULL, '/ads/boxArrivalOrder/undoList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60204005, '箱货-入库订单-详情未完成列表', NULL, '/ads/boxArrivalOrder/itemUndoCalcList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60204006, '箱货-入库订单-详情已完成列表', NULL, '/ads/boxArrivalOrder/itemDoneCalcList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60204007, '箱货-入库订单-SN未完成列表', NULL, '/ads/boxArrivalOrder/itemSnUndoList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);

INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60204004, 60204001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60204005, 60204001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60204006, 60204001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60204007, 60204001);

INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60204004, 60202001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60204005, 60202001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60204006, 60202001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60204007, 60202001);

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60202001, '箱货-SN入库', NULL, '/ads/boxArrivalOrder/snInbound', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60202001, 60202001);

-- INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
-- VALUES (x, '箱货-入库订单-SN未完成单个打印', NULL, '/ads/boxArrivalOrder/boxSnUndoPrint', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
-- INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
-- VALUES (x, '箱货-入库订单-详情SN未完成打印', NULL, '/ads/boxArrivalOrder/itemSnUndoPrint', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
-- INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
-- VALUES (x, '箱货-入库订单-SN未完成全部打印', NULL, '/ads/boxArrivalOrder/itemSnUndoPrintAll', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
-- /ads/boxArrivalOrder/snInboundLog
-- /ads/boxArrivalOrder/snDownload

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60201001, '箱货-SKU分页', NULL, '/ads/boxSku/page', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60201002, '箱货-SN分页', NULL, '/ads/boxSn/page', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60201003, '箱货-SKU客户列表', NULL, '/ads/boxSku/supplierList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60201001, 60201001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60201002, 60201001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60201003, 60201001);

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60201004, '箱货-SKU批量导入', NULL, '/ads/boxSku/inboundBatch', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60201005, '箱货-SKU批量修改', NULL, '/ads/boxSku/editBatch', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60201002, 'DICFREE_BOX_SKU_ADD', '箱货-SKU&SN新增', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60201003, 'DICFREE_BOX_SKU_EDIT', '箱货-SKU&SN修改', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60201004, 60201002);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60201005, 60201003);

-- /ads/boxSku/download
-- /ads/boxSku/downloadAll
-- /ads/devicePrinter/info
-- /ads/devicePrinter/status
-- /ads/devicePrinter/clear



-- /ads/collectionTask/boxDeliveryOrder/snOutboundLog
-- /ads/collectionTask/snDownload

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60205010, '集货任务-未完成列表', NULL, '/ads/collectionTask/undoList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60205011, '集货任务-详情未完成列表', NULL, '/ads/collectionTask/itemUndoCalcList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60205012, '集货任务-详情已完成列表', NULL, '/ads/collectionTask/itemDoneCalcList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60205013, '集货任务-SN未完成列表', NULL, '/ads/collectionTask/itemSnUndoList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60205014, '集货任务-SN出库', NULL, '/ads/collectionTask/snOutbound', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205010, 60205001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205011, 60205001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205012, 60205001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205013, 60205001);

INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205010, 60203001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205011, 60203001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205012, 60203001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205013, 60203001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60205014, 60203001);



INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60209001, '电商-入库订单-未完成列表', NULL, '/ads/productArrivalOrder/undoList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60209002, '电商-入库订单-详情未完成列表', NULL, '/ads/productArrivalOrder/itemUndoCalcList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60209003, '电商-入库订单-详情已完成列表', NULL, '/ads/productArrivalOrder/itemDoneCalcList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60209004, '电商-入库订单-SN未完成列表', NULL, '/ads/productArrivalOrder/itemSnUndoList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60209005, '电商-入库订单-SN未完成打印', NULL, '/ads/productArrivalOrder/itemSnUndoPrint', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60209006, '电商-入库订单-SN入库', NULL, '/ads/productArrivalOrder/snInbound', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60209006, 60206001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60209001, 60206001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60209002, 60206001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60209003, 60206001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60209004, 60206001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60209005, 60206001);


UPDATE `uaa_privilege` SET `name` = '轻流-全量权限' WHERE `id` = 60200001;
UPDATE `uaa_privilege` SET `name` = 'PDA-全量权限' WHERE `id` = 60200002;
UPDATE `uaa_privilege` SET `name` = '其它-打印机绑定' WHERE `id` = 60200003;
UPDATE `uaa_privilege` SET `name` = '箱货-SKU&SN-查' WHERE `id` = 60201001;
UPDATE `uaa_privilege` SET `name` = '箱货-SKU&SN-增' WHERE `id` = 60201002;
UPDATE `uaa_privilege` SET `name` = '箱货-SKU&SN-改' WHERE `id` = 60201003;
UPDATE `uaa_privilege` SET `name` = '箱货-入库' WHERE `id` = 60202001;
UPDATE `uaa_privilege` SET `name` = '箱货-出库' WHERE `id` = 60203001;
UPDATE `uaa_privilege` SET `name` = '箱货-入库订单-查' WHERE `id` = 60204001;
UPDATE `uaa_privilege` SET `name` = '箱货-集货任务-查' WHERE `id` = 60205001;
UPDATE `uaa_privilege` SET `name` = '箱货-集货任务-增' WHERE `id` = 60205002;
UPDATE `uaa_privilege` SET `name` = '箱货-集货任务-改' WHERE `id` = 60205003;
UPDATE `uaa_privilege` SET `name` = '箱货-集货任务-删' WHERE `id` = 60205004;
UPDATE `uaa_privilege` SET `name` = '电商-入库' WHERE `id` = 60206001;
UPDATE `uaa_privilege` SET `name` = '电商-出库订单-查' WHERE `id` = 60207001;


UPDATE `uaa_resource` SET `name` = 'PDA-全匹配' WHERE `id` = 60200008;
UPDATE `uaa_resource` SET `name` = '客户端-整装箱修改库位' WHERE `id` = 60200003;
UPDATE `uaa_resource` SET `name` = '客户端-整装箱修改收货地' WHERE `id` = 60200002;
UPDATE `uaa_resource` SET `name` = '客户端-整装箱入库订单新增' WHERE `id` = 60200004;
UPDATE `uaa_resource` SET `name` = '客户端-整装箱入库订单状态修改' WHERE `id` = 60200005;
UPDATE `uaa_resource` SET `name` = '客户端-整装箱出库订单新增' WHERE `id` = 60200006;
UPDATE `uaa_resource` SET `name` = '客户端-整装箱出库订单状态修改' WHERE `id` = 60200007;
UPDATE `uaa_resource` SET `name` = '客户端-整装箱新增' WHERE `id` = 60200001;
UPDATE `uaa_resource` SET `name` = '打印机-绑定' WHERE `id` = 60200009;
UPDATE `uaa_resource` SET `name` = '电商-入库订单-SN入库' WHERE `id` = 60206001;
UPDATE `uaa_resource` SET `name` = '电商-入库订单-SN未完成列表' WHERE `id` = 60209004;
UPDATE `uaa_resource` SET `name` = '电商-入库订单-SN未完成打印' WHERE `id` = 60209005;
UPDATE `uaa_resource` SET `name` = '电商-入库订单-未完成列表' WHERE `id` = 60209001;
UPDATE `uaa_resource` SET `name` = '电商-入库订单-详情已完成列表' WHERE `id` = 60209003;
UPDATE `uaa_resource` SET `name` = '电商-入库订单-详情未完成列表' WHERE `id` = 60209002;
UPDATE `uaa_resource` SET `name` = '箱货-SKU分页' WHERE `id` = 60201001;
UPDATE `uaa_resource` SET `name` = '箱货-SKU客户列表' WHERE `id` = 60201003;
UPDATE `uaa_resource` SET `name` = '箱货-SKU批量修改' WHERE `id` = 60201005;
UPDATE `uaa_resource` SET `name` = '箱货-SKU批量导入' WHERE `id` = 60201004;
UPDATE `uaa_resource` SET `name` = '箱货-SN入库' WHERE `id` = 60202001;
UPDATE `uaa_resource` SET `name` = '箱货-SN分页' WHERE `id` = 60201002;
UPDATE `uaa_resource` SET `name` = '箱货-入库订单-SN未完成列表' WHERE `id` = 60204007;
UPDATE `uaa_resource` SET `name` = '箱货-入库订单-分页' WHERE `id` = 60204001;
UPDATE `uaa_resource` SET `name` = '箱货-入库订单-未完成列表' WHERE `id` = 60204004;
UPDATE `uaa_resource` SET `name` = '箱货-入库订单-详情SN下载' WHERE `id` = 60204003;
UPDATE `uaa_resource` SET `name` = '箱货-入库订单-详情列表' WHERE `id` = 60204002;
UPDATE `uaa_resource` SET `name` = '箱货-入库订单-详情已完成列表' WHERE `id` = 60204006;
UPDATE `uaa_resource` SET `name` = '箱货-入库订单-详情未完成列表' WHERE `id` = 60204005;
UPDATE `uaa_resource` SET `name` = '箱货-出库订单-未完成列表' WHERE `id` = 60205008;
UPDATE `uaa_resource` SET `name` = '箱货-出库订单-未完成目的地' WHERE `id` = 60205009;
UPDATE `uaa_resource` SET `name` = '箱货-集货任务-SN出库' WHERE `id` = 60205014;
UPDATE `uaa_resource` SET `name` = '箱货-集货任务-SN未完成列表' WHERE `id` = 60205013;
UPDATE `uaa_resource` SET `name` = '箱货-集货任务-分页' WHERE `id` = 60205001;
UPDATE `uaa_resource` SET `name` = '箱货-集货任务-删除' WHERE `id` = 60205007;
UPDATE `uaa_resource` SET `name` = '箱货-集货任务-新增' WHERE `id` = 60205004;
UPDATE `uaa_resource` SET `name` = '箱货-集货任务-未完成列表' WHERE `id` = 60205010;
UPDATE `uaa_resource` SET `name` = '箱货-集货任务-编辑' WHERE `id` = 60205005;
UPDATE `uaa_resource` SET `name` = '箱货-集货任务-详情' WHERE `id` = 60205006;
UPDATE `uaa_resource` SET `name` = '箱货-集货任务-详情SN下载' WHERE `id` = 60205003;
UPDATE `uaa_resource` SET `name` = '箱货-集货任务-详情列表' WHERE `id` = 60205002;
UPDATE `uaa_resource` SET `name` = '箱货-集货任务-详情已完成列表' WHERE `id` = 60205012;
UPDATE `uaa_resource` SET `name` = '箱货-集货任务-详情未完成列表' WHERE `id` = 60205011;


INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60207501, 'T_DICFREE_DO_QUERY', '*电商-出库订单-查', NULL, NULL, NULL, NULL, 0);
UPDATE `uaa_resource` SET `permit_type` = 'A' WHERE `id` = 60208000;
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60208000, 60207501);

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60207001, '电商-出库订单-分页', NULL, '/ads/productDeliveryOrder/page', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60207002, '电商-出库订单-派单', NULL, '/ads/productDeliveryOrder/dispatch', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60207003, '电商-出库订单-批量派单', NULL, '/ads/productDeliveryOrder/dispatchBatch', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60207001, 60207001);
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60207002, 'DICFREE_DO_EDIT', '电商-出库订单-改', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60207002, 60207002);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60207003, 60207002);

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (10001015, '全局-通用接口', NULL, '/common/**', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (10001016, '全局-个人接口', NULL, '/self/**', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (10001017, '全局-无限制接口', NULL, '/pmall/**', b'0', NULL, NULL, NULL, NULL, NULL, 'N', NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (10001015, 10001012);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (10001016, 10001013);