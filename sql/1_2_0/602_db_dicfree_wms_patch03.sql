ALTER table df_wms_sku_supplier change column `sku_code` `code` varchar(50) DEFAULT NULL COMMENT '箱号';
ALTER table df_wms_sku_supplier change column `product_code` `supplier_box_code` varchar(30) DEFAULT NULL COMMENT '客户箱号';

ALTER table df_wms_sku_sn change column `sn_code` `code` varchar(20) DEFAULT NULL COMMENT '序列箱号';
ALTER table df_wms_sku_sn change column `sku_code` `box_sku_code` varchar(50) DEFAULT NULL COMMENT '箱号';
ALTER table df_wms_sku_sn change column `supplier_sn_code` `supplier_box_sn_code` varchar(40) DEFAULT NULL COMMENT '客户序列箱号';

ALTER table df_wms_stocktake_record change column `sku_code` `box_sku_code` varchar(50) DEFAULT NULL COMMENT '箱号';
ALTER table df_wms_stocktake_record change column `sn_code` `box_sn_code` varchar(20) DEFAULT NULL COMMENT '序列箱号';
ALTER table df_wms_stocktake_record change column `product_code` `supplier_box_code` varchar(30) DEFAULT NULL COMMENT '客户箱号';
ALTER table df_wms_stocktake_record change column `supplier_sn_code` `supplier_box_sn_code` varchar(40) DEFAULT NULL COMMENT '客户序列箱号';

ALTER table df_wms_arrival_order_item change column `sku_code` `box_sku_code` varchar(50) DEFAULT NULL COMMENT '箱号';

ALTER table df_wms_delivery_order change column `container_task_id` `collection_task_id` bigint(20) DEFAULT NULL COMMENT '集货任务id';

ALTER table df_wms_delivery_order_item change column `container_task_id` `collection_task_id` bigint(20) DEFAULT NULL COMMENT '集货任务id';
ALTER table df_wms_delivery_order_item change column `sku_code` `box_sku_code` varchar(50) DEFAULT NULL COMMENT '箱号';

ALTER table df_wms_container_task change column `container_no_virtual` `collection_no_virtual` varchar(5) DEFAULT NULL COMMENT '虚拟集货任务号';
ALTER table df_wms_container_task change column `container_no_real` `collection_no_real` varchar(20) DEFAULT NULL COMMENT '实际集货任务号';

rename table df_wms_sku_supplier to df_wms_box_sku;
rename table df_wms_sku_sn to df_wms_box_sn;
rename table df_wms_container_task to df_wms_collection_task;


UPDATE `uaa_resource` SET `url` = '/client/boxSku/add' WHERE `id` = 60200001;
UPDATE `uaa_resource` SET `url` = '/client/boxSku/deliveryEdit' WHERE `id` = 60200002;
UPDATE `uaa_resource` SET `url` = '/client/boxSku/locationEdit' WHERE `id` = 60200003;

UPDATE `uaa_resource` SET `url` = '/ads/collectionTask/page' WHERE `id` = 60205001;
UPDATE `uaa_resource` SET `url` = '/ads/collectionTask/itemList' WHERE `id` = 60205002;
UPDATE `uaa_resource` SET `url` = '/ads/collectionTask/itemSnDownload' WHERE `id` = 60205003;
UPDATE `uaa_resource` SET `url` = '/ads/collectionTask/add*' WHERE `id` = 60205004;
UPDATE `uaa_resource` SET `url` = '/ads/collectionTask/edit*' WHERE `id` = 60205005;
UPDATE `uaa_resource` SET `url` = '/ads/collectionTask/detail' WHERE `id` = 60205006;
UPDATE `uaa_resource` SET `url` = '/ads/collectionTask/delete' WHERE `id` = 60205007;

UPDATE `uaa_resource` SET `name` = 'BoxSku&BoxSn', `url` = '/admin/box-skus' WHERE `id` = 60201000;
UPDATE `uaa_resource` SET `name` = 'Collection Task', `url` = '/admin/collection-tasks' WHERE `id` = 60205000;

ALTER table df_wms_box_sn add column `printed` bit(1) DEFAULT NULL COMMENT '是否已打印';
ALTER table df_wms_arrival_order add column `printed` bit(1) DEFAULT NULL COMMENT '是否已打印';
ALTER table df_wms_arrival_order_item add column `printed` bit(1) DEFAULT NULL COMMENT '是否已打印';

UPDATE `df_wms_box_sn` SET `printed` = false WHERE printed is null;
UPDATE `df_wms_arrival_order` SET `printed` = false WHERE printed is null;
UPDATE `df_wms_arrival_order_item` SET `printed` = false WHERE printed is null;