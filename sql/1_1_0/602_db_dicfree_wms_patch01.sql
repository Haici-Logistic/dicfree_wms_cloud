
#客户端权限
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('60200001', '客户端-商品新增', NULL, '/client/skuSupplier/add', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('60200002', '客户端-商品修改收货地', NULL, '/client/skuSupplier/deliveryEdit', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('60200003', '客户端-商品修改库位', NULL, '/client/skuSupplier/locationEdit', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('60200001', 'DICFREE_CLIENT_ALL', '客户端-全权限', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('60200001', '60200001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('60200002', '60200001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('60200003', '60200001');

#密码3MMoCFo4nTNjRtGZ
INSERT INTO uaa_oauth2_client (id, client_id, client_id_issued_date, client_name, client_secret, client_secret_exp_date, scopes, auth_grant_types, client_authn_methods, redirect_uris, post_logout_redirect_uris, cs_require_proof_key, cs_require_auth_consent, ts_auth_code_validity, ts_access_token_validity, ts_reuse_refresh_tokens, ts_refresh_token_validity, create_time, created_by, update_by, update_time, version) VALUES (2, 'qingflow_001', '2023-08-07 12:08:23.000000', '轻流客户端001号', '{bcrypt}$2a$10$VCVHpWipe9Bi3r7cPwA9suUvnX5jvpocyzHtVa4RaBU8QrEEB5LuW', '2099-08-07 12:08:23.000000', 'DICFREE_CLIENT_ALL', 'client_credentials', 'client_secret_post', 'http://localhost:8081/api/sso/admin/sometest', null, false, false, 300, 43200, true, 2592000, null, null, null, null, 0);
INSERT INTO `uaa_role` (`id`, `code`, `name`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (5, 'DICFREE_CLIENT_ALL', '轻流客户端', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES (5, 60200001);

