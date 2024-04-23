//删除 /ads/boxDeliveryOrder/undoDeliveryToList
//修改 /ads/boxSku/inboundBatch -> "/ads/boxSku/addBatch"

ALTER table df_wms_box_delivery_order change column `delivery_to` `address_info` varchar(500)  DEFAULT NULL COMMENT '目的地';
