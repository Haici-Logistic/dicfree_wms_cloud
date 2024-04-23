ALTER table df_wms_device_printer add column `username` varchar(60) DEFAULT NULL COMMENT '用户名/客户端名' after `user_id`;

INSERT INTO `sys_param` (`id`, `type`, `code`, `name`, `parent_code`, `value`, `default_item`, `sequence`, `remark`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (11, 'STRING', 'product-delivery-order-batch-dispatch', 'excel文件批量上传修改路径', NULL, 'object', b'0', 0, '开头不要带\"/\"，文件会上传至该路径下，且该路径会拼接在配置文件的access-domain参数后', NULL, NULL, NULL, NULL, 0);
