rename table df_wms_arrival_order_item to df_wms_box_arrival_order_item;
rename table df_wms_arrival_order to df_wms_box_arrival_order;

ALTER table df_wms_box_arrival_order_item change column `arrival_order_id` `box_arrival_order_id` bigint(20)  DEFAULT NULL COMMENT '箱货入库单号';
ALTER table df_wms_box_sn change column `arrival_order_item_id` `box_arrival_order_item_id` bigint(20)  DEFAULT NULL COMMENT '序列箱入库id';

rename table df_wms_delivery_order to df_wms_box_delivery_order;
rename table df_wms_delivery_order_item to df_wms_box_delivery_order_item;

ALTER table df_wms_box_delivery_order_item change column `delivery_order_id` `box_delivery_order_id` bigint(20)  DEFAULT NULL COMMENT '箱货出库单号';
ALTER table df_wms_box_sn change column `delivery_order_item_id` `box_delivery_order_item_id` bigint(20)  DEFAULT NULL COMMENT '序列箱出库id';

rename table df_wms_printer to df_wms_device_printer;


UPDATE `uaa_resource` SET `name` = 'Box Arrival Order', `url` = '/admin/box-arrival-orders' WHERE `id` = 60204000;
UPDATE `uaa_resource` SET `name` = 'Box Inbounds', `url` = '/admin/box-inbounds' WHERE `id` = 60202000;
UPDATE `uaa_resource` SET `name` = 'Box Outbounds', `url` = '/admin/box-outbounds' WHERE `id` = 60203000;
UPDATE `uaa_resource` SET `name` = '客户端-整装箱入库订单新增', `icon` = NULL, `url` = '/client/boxArrivalOrder/add' WHERE `id` = 60200004;
UPDATE `uaa_resource` SET `name` = '客户端-整装箱入库订单状态修改', `icon` = NULL, `url` = '/client/boxArrivalOrder/statusEdit' WHERE `id` = 60200005;
UPDATE `uaa_resource` SET `name` = '客户端-整装箱出库订单新增', `icon` = NULL, `url` = '/client/boxDeliveryOrder/add' WHERE `id` = 60200006;
UPDATE `uaa_resource` SET `name` = '客户端-整装箱出库订单状态修改', `icon` = NULL, `url` = '/client/boxDeliveryOrder/statusEdit' WHERE `id` = 60200007;
UPDATE `uaa_resource` SET `url` = '/ads/devicePrinter/bind' WHERE `id` = 60201001;
UPDATE `uaa_resource` SET `name` = '整装箱入库订单-分页',  `url` = '/ads/boxArrivalOrder/page' WHERE `id` = 60204001;
UPDATE `uaa_resource` SET `name` = '整装箱入库订单-详情列表', `url` = '/ads/boxArrivalOrder/itemList' WHERE `id` = 60204002;
UPDATE `uaa_resource` SET `name` = '整装箱入库订单-详情SN下载', `url` = '/ads/boxArrivalOrder/itemSnDownload' WHERE `id` = 60204003;
UPDATE `uaa_resource` SET `name` = '整装箱出库订单-未完成列表',  `url` = '/ads/boxDeliveryOrder/undoList' WHERE `id` = 60205008;
UPDATE `uaa_resource` SET `name` = '整装箱出库订单-未完成目的地', `url` = '/ads/boxDeliveryOrder/undoDeliveryToList' WHERE `id` = 60205009;
UPDATE `uaa_privilege` SET `code` = 'DICFREE_BAO_QUERY', `name` = '整装箱入库订单-查询' WHERE `id` = 60204001;
