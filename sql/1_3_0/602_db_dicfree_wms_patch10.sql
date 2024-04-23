INSERT INTO `uaa_oauth2_client` (`id`, `client_id`, `client_id_issued_date`, `client_name`, `client_secret`, `client_secret_exp_date`, `scopes`, `auth_grant_types`, `client_authn_methods`, `redirect_uris`, `post_logout_redirect_uris`, `cs_require_proof_key`, `cs_require_auth_consent`, `ts_auth_code_validity`, `ts_access_token_validity`, `ts_reuse_refresh_tokens`, `ts_refresh_token_validity`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (4, 'ANTASK', '2023-11-13 21:35:50', '外部客户001', '{bcrypt}$2a$10$J3HU5fXXKJh3WLmkNgjnvuKq0Ad6WW4oo31Qjq9I.xIJv3/3T1lPm', '2099-08-07 12:08:23', 'DICFREE_SE_SUPPLIER', 'client_credentials', 'client_secret_post', 'http://localhost:8081/api/sso/admin/sometest', NULL, b'0', b'0', 300, 43200, b'1', 2592000, NULL, NULL, NULL, NULL, 0);

INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200004, 'SE_CLIENT_ALL', '外部系统-全量权限', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200010, '外部系统-全匹配', NULL, '/third/**', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60200010, 60200004);

INSERT INTO `uaa_role` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (8, 'SE_CLIENT_ALL', '客户端-外部系统', NULL, NULL, NULL, NULL, 0);

ALTER table df_wms_box_arrival_order add column `unique_no` varchar(40) DEFAULT NULL COMMENT '订单号' after `id`;
ALTER table df_wms_box_delivery_order add column `unique_no` varchar(40) DEFAULT NULL COMMENT '订单号' after `collection_task_id`;

ALTER table df_wms_box_arrival_order change column `status` `inbound_status` varchar(20)  DEFAULT NULL COMMENT '入库状态';
ALTER table df_wms_box_arrival_order_item change column `status` `inbound_status` varchar(20)  DEFAULT NULL COMMENT '入库状态';

ALTER table df_wms_box_delivery_order change column `status` `outbound_status` varchar(20)  DEFAULT NULL COMMENT '出库状态';
ALTER table df_wms_box_delivery_order_item change column `status` `outbound_status` varchar(20)  DEFAULT NULL COMMENT '出库状态';
