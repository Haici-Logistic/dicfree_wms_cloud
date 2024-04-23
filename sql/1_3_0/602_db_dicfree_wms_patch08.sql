ALTER table df_wms_product_delivery_order add column `total_count` int(4) DEFAULT NULL COMMENT '总计数量' after `dispatched`;
ALTER table df_wms_product_delivery_order add column `verify_count` int(4) DEFAULT NULL COMMENT '已验证数量' after `dispatched`;
ALTER table df_wms_product_delivery_order add column `verify_status` varchar(20) DEFAULT NULL COMMENT '验证状态' after `dispatched`;
ALTER table df_wms_product_delivery_order add column `outbound` bit(1) DEFAULT NULL COMMENT '是否已出库' after `dispatched`;

ALTER table df_wms_product_delivery_order_item add column `verify_count` int(3) DEFAULT NULL COMMENT '已验证数量' after `product_sku_code`;
ALTER table df_wms_product_delivery_order_item add column `verify_status` varchar(20) DEFAULT NULL COMMENT '验证状态' after `product_sku_code`;

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60210000, 'Outbound Verify', NULL, '/admin/product-outbounds-verify', b'1', b'0', b'1', 603, '/603/60210000/', 1, 'A', 300, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60208001, 'DICFREE_PRODUCT_VERIFY', '电商-出库校验', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60210000, 60208001);

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60207004, '电商-出库订单-信息', NULL, '/ads/productDeliveryOrder/info', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60207005, '电商-出库订单-详情未完成列表', NULL, '/ads/productDeliveryOrder/itemUndoCalcList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60207006, '电商-出库订单-详情已完成列表', NULL, '/ads/productDeliveryOrder/itemDoneCalcList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60207007, '电商-出库订单-SN未完成列表', NULL, '/ads/productDeliveryOrder/itemSnUndoList', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60207008, '电商-出库订单-出库校验', NULL, '/ads/productDeliveryOrder/snVerify', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60207004, 60208001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60207005, 60208001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60207006, 60208001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60207007, 60208001);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60207008, 60208001);

ALTER table df_wms_product_delivery_order add column `waybill_url` varchar(100) DEFAULT NULL COMMENT '快递单号PDF地址' after `waybill`;

UPDATE `dicfree_wms`.`uaa_resource` SET `url` = '/admin/product-outbounds-verify' WHERE `id` = 60210000;

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (60211000, 'Waybill Print', NULL, '/admin/waybill-print', b'1', b'0', b'1', 603, '/603/60211000/', 1, 'A', 400, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60211000, 60207001);

ALTER table df_wms_product_delivery_order add column `proxy` bit(1) DEFAULT NULL COMMENT '是否代理发货' after `address`;

ALTER table df_wms_product_delivery_order add column `dispatch_time` datetime DEFAULT NULL COMMENT '派单时间' after `dispatched`;
ALTER table df_wms_product_delivery_order add column `outbound_time` datetime DEFAULT NULL COMMENT '出库时间' after `outbound`;