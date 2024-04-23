ALTER TABLE sys_admin_user modify COLUMN  `created_by` varchar(30) DEFAULT NULL;
ALTER TABLE sys_param modify COLUMN  `created_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_login_record modify COLUMN  `created_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_oauth2_client modify COLUMN  `created_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_privilege modify COLUMN  `created_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_resource modify COLUMN  `created_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_role modify COLUMN  `created_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_user modify COLUMN  `created_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_user_auth modify COLUMN  `created_by` varchar(30) DEFAULT NULL;
ALTER TABLE oss_uploaded_file modify COLUMN  `created_by` varchar(30) DEFAULT NULL;

ALTER TABLE sys_admin_user modify COLUMN  `update_by` varchar(30) DEFAULT NULL;
ALTER TABLE sys_param modify COLUMN  `update_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_login_record modify COLUMN  `update_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_oauth2_client modify COLUMN  `update_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_privilege modify COLUMN  `update_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_resource modify COLUMN  `update_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_role modify COLUMN  `update_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_user modify COLUMN  `update_by` varchar(30) DEFAULT NULL;
ALTER TABLE uaa_user_auth modify COLUMN  `update_by` varchar(30) DEFAULT NULL;
ALTER TABLE oss_uploaded_file modify COLUMN  `update_by` varchar(30) DEFAULT NULL;


ALTER TABLE df_wms_printer modify COLUMN  `created_by` varchar(30) DEFAULT NULL;
ALTER TABLE df_wms_sku_sn modify COLUMN  `created_by` varchar(30) DEFAULT NULL;
ALTER TABLE df_wms_sku_supplier modify COLUMN  `created_by` varchar(30) DEFAULT NULL;
ALTER TABLE df_wms_stocktake_record modify COLUMN  `created_by` varchar(30) DEFAULT NULL;

ALTER TABLE df_wms_printer modify COLUMN  `update_by` varchar(30) DEFAULT NULL;
ALTER TABLE df_wms_sku_sn modify COLUMN  `update_by` varchar(30) DEFAULT NULL;
ALTER TABLE df_wms_sku_supplier modify COLUMN  `update_by` varchar(30) DEFAULT NULL;
ALTER TABLE df_wms_stocktake_record modify COLUMN  `update_by` varchar(30) DEFAULT NULL;