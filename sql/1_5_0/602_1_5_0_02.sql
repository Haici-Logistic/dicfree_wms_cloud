delete from uaa_resource_privilege where resource_id in (60200001,60200002,60200003,60200004,60200005,60200006,60200007);
delete from uaa_resource where id in (60200001,60200002,60200003,60200004,60200005,60200006,60200007);
delete from uaa_privilege where id = 60200001;

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200001, '客户端-箱货接口', NULL, '/client/boxSku/**', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200002, '客户端-箱货入库接口', NULL, '/client/boxArrivalOrder/**', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200003, '客户端-箱货出库接口', NULL, '/client/boxDeliveryOrder/**', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200004, '客户端-电商货接口', NULL, '/client/productSku/**', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200005, '客户端-电商货入库接口', NULL, '/client/productArrivalOrder/**', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200006, '客户端-电商货出库接口', NULL, '/client/productDeliveryOrder/**', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200011, '客户端-会员接口', NULL, '/client/member/**', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200012, '客户端-会员地址接口', NULL, '/client/memberAddress/**', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200013, '客户端-转运订单接口', NULL, '/client/transportOrder/**', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);


INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200005, 'SI_QINGFLOW_ALL', '轻流-全量权限', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200006, 'SI_ZION_ALL', 'ZION-全量权限', NULL, NULL, NULL, NULL, 0);

INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60200001, 60200005);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60200002, 60200005);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60200003, 60200005);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60200004, 60200005);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60200005, 60200005);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60200013, 60200005);

INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60200011, 60200006);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60200012, 60200006);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60200013, 60200006);