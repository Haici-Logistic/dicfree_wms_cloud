ALTER table ai_chat_record change column `role` `role` enum('system','user','assistant','function') DEFAULT NULL COMMENT 'AI角色';

ALTER table sys_param change column `type` `type` enum('STRING','INTEGER','DATE','BOOLEAN') DEFAULT NULL COMMENT '类型';

ALTER table uaa_resource change column `permit_type` `permit_type` enum('N','A','S') DEFAULT NULL COMMENT '可访问类型';
ALTER table uaa_user change column `type` `type` enum('B','F','C') DEFAULT NULL COMMENT '用户类型';
ALTER table uaa_user_auth change column `type` `type` enum('WX_OPEN','PHONE','ACCOUNT') DEFAULT NULL COMMENT '授权类型';

ALTER table oss_uploaded_file change column `upload_status` `upload_status` enum('PREPARE','DONE','DUPLICATE','FAILED') DEFAULT NULL COMMENT '上传状态';

ALTER table df_mct_weixin change column `type` `type` enum('OPEN','PUBLIC','MP') DEFAULT NULL COMMENT '微信类型';



ALTER table df_wms_box_arrival_order change column `inbound_status` `inbound_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '状态';
ALTER table df_wms_box_arrival_order_item change column `inbound_status` `inbound_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '状态';
ALTER table df_wms_box_arrival_order_item change column `location_type` `location_type` enum('Whole','Bulk') DEFAULT NULL COMMENT '库位类型';
ALTER table df_wms_box_delivery_order change column `outbound_status` `outbound_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '状态';
ALTER table df_wms_box_delivery_order_item change column `outbound_status` `outbound_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '状态';
ALTER table df_wms_box_sn change column `status` `status` enum('WAITING','INBOUND','OUTBOUND') DEFAULT NULL COMMENT '货物状态';
ALTER table df_wms_collection_area change column `status` `status` enum('EMPTY','FULL') DEFAULT NULL COMMENT '集货区状态';
ALTER table df_wms_collection_task change column `status` `status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '状态';
ALTER table df_wms_product_arrival_order change column `inbound_status` `inbound_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '入库状态';
ALTER table df_wms_product_arrival_order change column `on_shelf_status` `on_shelf_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '上架状态';
ALTER table df_wms_product_arrival_order_item change column `inbound_status` `inbound_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '入库状态';
ALTER table df_wms_product_arrival_order_item change column `on_shelf_status` `on_shelf_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '上架状态';
ALTER table df_wms_product_delivery_order change column `type` `type` enum('OO','OM','MM') DEFAULT NULL COMMENT '订单类型';
ALTER table df_wms_product_delivery_order change column `off_shelf_status` `off_shelf_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '下架状态';
ALTER table df_wms_product_delivery_order change column `sorting_status` `sorting_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '分拣状态';
ALTER table df_wms_product_delivery_order change column `verify_status` `verify_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '核验状态';
ALTER table df_wms_product_delivery_order_item change column `off_shelf_status` `off_shelf_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '下架状态';
ALTER table df_wms_product_delivery_order_item change column `sorting_status` `sorting_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '分拣状态';
ALTER table df_wms_product_delivery_order_item change column `verify_status` `verify_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '核验状态';

ALTER table df_wms_product_wave_task change column `off_shelf_status` `off_shelf_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '下架状态';
ALTER table df_wms_product_wave_task change column `basket_status` `basket_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '分篮状态';
ALTER table df_wms_product_wave_task change column `sorting_status` `sorting_status` enum('PENDING','IN_PROCESSING','DONE') DEFAULT NULL COMMENT '分拣状态';

ALTER table df_wms_product_sn change column `stock_status` `stock_status` enum('WAITING','INBOUND','OUTBOUND') DEFAULT NULL COMMENT '货物库存状态';
ALTER table df_wms_product_sn change column `shelf_status` `shelf_status` enum('WAITING','ON_SHELF','OFF_SHELF') DEFAULT NULL COMMENT '货物货架状态';

ALTER table df_wms_stocktake_record change column `type` `type` enum('BOX_SN_CODE','BOX_SKU_CODE','SUPPLIER_BOX_CODE','SUPPLIER_BOX_SN_CODE') DEFAULT NULL COMMENT '盘点类型';
ALTER table df_wms_stocktake_record change column `direction` `direction` enum('IN','OUT') DEFAULT NULL COMMENT '盘点方向';

ALTER table df_cts_transport_order change column `transport_mode` `transport_mode` enum('navigation','aviation') DEFAULT NULL COMMENT '运单模式';
ALTER table df_cts_transport_order change column `status` `status` enum('pending','firstInbound','firstDelivery','lastInbound','lastDelivery','lastSigning') DEFAULT NULL COMMENT '运单状态';
