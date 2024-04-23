ALTER table df_wms_product_sn add column `remark` varchar(100) DEFAULT NULL COMMENT '备注' after `serial_no`;
ALTER table df_wms_product_sn add column `quality` bit(1) DEFAULT NULL COMMENT '是否完整货物' after `shelf_no`;

update df_wms_product_sn set quality = 1 where quality is null;

ALTER table df_wms_product_wave_task add column `type` enum('C','B') DEFAULT NULL COMMENT '波次类型' after `unique_no`;