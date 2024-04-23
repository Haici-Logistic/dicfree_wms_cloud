/**
 * Copyright 2024 Wuhan Haici Technology Co., Ltd 
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dicfree.ms.wms.common.constant;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
public class WmsMessageConstant {

    public static final String ERROR_DEVICE_PRINTER_NOT_FOUND = "wms_error_device_printer_not_found";
    public static final String ERROR_DEVICE_PRINTER_ADD_FAILED = "wms_error_device_printer_add_failed";
    public static final String ERROR_DEVICE_PRINTER_DELETE_FAILED = "wms_error_device_printer_delete_failed";
    public static final String ERROR_DEVICE_PRINTER_STATUS_FAILED = "wms_error_device_printer_status_failed";
    public static final String ERROR_DEVICE_PRINTER_CLEAN_FAILED = "wms_error_device_printer_clean_failed";
    public static final String ERROR_DEVICE_PRINTER_ALREADY_BIND = "wms_error_device_printer_already_bind";
    public static final String ERROR_DEVICE_PRINTER_PRINT_FAILED = "wms_error_device_printer_print_failed";

    public static final String ERROR_DEVICE_PDA_NOT_FOUND = "wms_error_device_pda_not_found";
    public static final String ERROR_DEVICE_PDA_SHELF_AREA_NOT_MATCH = "wms_error_device_pda_shelf_area_not_match";

    public static final String ERROR_QING_FLOW_INBOUND_NOTICE_FAILED = "wms_error_qing_flow_inbound_notice_failed";
    public static final String ERROR_QING_FLOW_OUTBOUND_NOTICE_FAILED = "wms_error_qing_flow_outbound_notice_failed";
    public static final String ERROR_QING_FLOW_SN_NOTICE_FAILED = "wms_error_qing_flow_sn_notices_failed";
    public static final String ERROR_QING_FLOW_SN_ON_SHELF_NOTICE_FAILED = "wms_error_qing_flow_sn_on_shelf_notice_failed";
    public static final String ERROR_QING_FLOW_SN_OFF_SHELF_NOTICE_FAILED = "wms_error_qing_flow_sn_off_shelf_notice_failed";
    public static final String ERROR_QING_FLOW_PRODUCT_SN_INBOUND_NOTICE_FAILED = "wms_error_qing_flow_product_sn_inbound_notice_failed";
    public static final String ERROR_QING_FLOW_PRODUCT_SN_OUTBOUND_NOTICE_FAILED = "wms_error_qing_flow_product_sn_outbound_notice_failed";
    public static final String ERROR_QING_FLOW_WAVE_TASK_WEIGHING_NOTICE_FAILED = "wms_error_qing_flow_wave_task_weighing_notice_failed";
    public static final String ERROR_QING_FLOW_QUERY_APP_DATA_FAILED = "wms_error_qing_flow_query_app_data_failed";
    public static final String ERROR_QING_FLOW_DELETE_APP_DATA_FAILED = "wms_error_qing_flow_delete_app_data_failed";
    public static final String ERROR_QING_FLOW_TRACE_NOTICE_FAILED = "wms_error_qing_flow_trace_notice_failed";
    public static final String ERROR_QING_FLOW_LOCATION_LOCK_NOTICE_FAILED = "wms_error_qing_flow_location_lock_notice_failed";
    public static final String ERROR_QING_FLOW_WHOLE_LOCATION_ASSIGN_NOTICE_FAILED = "wms_error_qing_flow_whole_location_assign_notice_failed";
    public static final String ERROR_QING_FLOW_BULK_LOCATION_ASSIGN_NOTICE_FAILED = "wms_error_qing_flow_bulk_location_assign_notice_failed";
    public static final String ERROR_QING_FLOW_ARRIVAL_ORDER_ADD_NOTICE_FAILED = "wms_error_qing_flow_arrival_order_add_notice_failed";
    public static final String ERROR_QING_FLOW_DELIVERY_ORDER_ADD_NOTICE_FAILED = "wms_error_qing_flow_delivery_order_add_notice_failed";
    public static final String ERROR_QING_FLOW_QUERY_ADDRESS_FAILED = "wms_error_qing_flow_query_address_failed";
    public static final String ERROR_QING_FLOW_TRANSPORT_ORDER_ADD_NOTICE_FAILED = "wms_error_qing_flow_transport_order_add_notice_failed";

    public static final String ERROR_STOCKTAKE_TYPE_ERROR = "wms_error_stocktake_type_error";

    public static final String ERROR_BOX_SN_NOT_FOUND = "wms_error_box_sn_not_found";
    public static final String ERROR_BOX_SN_ALREADY_INBOUND = "wms_error_box_sn_already_inbound";
    public static final String ERROR_BOX_SN_ALREADY_OUTBOUND = "wms_error_box_sn_already_outbound";

    public static final String ERROR_BOX_SKU_NOT_FOUND = "wms_error_box_sku_not_found";
    public static final String ERROR_BOX_SKU_FILE_PARSER_ERROR = "wms_error_box_sku_file_parser_error";
    public static final String ERROR_BOX_SKU_FILE_DUPLICATE_RECORD = "wms_error_box_sku_file_duplicate_record";
    public static final String ERROR_BOX_SKU_FILE_EXISTS_RECORD = "wms_error_box_sku_file_exists_record";
    public static final String ERROR_BOX_SKU_FILE_PCS_INVALID = "wms_error_box_sku_file_pcs_invalid";
    public static final String ERROR_BOX_SKU_EXISTS = "wms_error_box_sku_exists";
    public static final String ERROR_BOX_SKU_LOCATION_NOT_ALLOCATE = "wms_error_box_sku_location_not_allocate";

    public static final String ERROR_BOX_ARRIVAL_ORDER_NOT_FOUND = "wms_error_box_arrival_order_not_found";
    public static final String ERROR_BOX_ARRIVAL_ORDER_ALREADY_DONE = "wms_error_box_arrival_order_already_done";
    public static final String ERROR_BOX_ARRIVAL_ORDER_LOCATION_NOT_FOUND = "wms_error_box_arrival_order_location_not_found";
    public static final String ERROR_BOX_ARRIVAL_ORDER_LOCATION_LOCKED = "wms_error_box_arrival_order_location_locked";
    public static final String ERROR_BOX_ARRIVAL_ORDER_LOCATION_NOT_LOCK = "wms_error_box_arrival_order_location_not_lock";
    public static final String ERROR_BOX_ARRIVAL_ORDER_LOCATION_COUNT_NOT_MATCH = "wms_error_box_arrival_order_location_count_not_match";
    public static final String ERROR_BOX_ARRIVAL_ORDER_ITEM_NOT_FOUND = "wms_error_box_arrival_order_item_not_found";
    public static final String ERROR_BOX_ARRIVAL_ORDER_ITEM_SN_INBOUND_NOT_MATCH = "wms_error_box_arrival_order_item_sn_inbound_not_match";

    public static final String ERROR_BOX_DELIVERY_ORDER_NOT_FOUND = "wms_error_box_delivery_order_not_found";
    public static final String ERROR_BOX_DELIVERY_ORDER_ITEM_NOT_FOUND = "wms_error_box_delivery_order_item_not_found";
    public static final String ERROR_BOX_DELIVERY_ORDER_DELIVERY_TO_NOT_MATCH = "wms_error_box_delivery_order_delivery_to_not_match";
    public static final String ERROR_BOX_DELIVERY_ORDER_AT_LAST_ONE = "wms_error_box_delivery_order_at_last_one";
    public static final String ERROR_BOX_DELIVERY_ORDER_IS_IN_OTHER_COLLECTION = "wms_error_box_delivery_order_is_in_other_collection";
    public static final String ERROR_BOX_DELIVERY_ORDER_ALREADY_DONE = "wms_error_box_delivery_order_already_done";
    public static final String ERROR_BOX_DELIVERY_ORDER_ITEM_SN_NOT_ENOUGH = "wms_error_box_delivery_order_item_sn_not_enough";

    public static final String ERROR_COLLECTION_TASK_NOT_FOUND = "wms_error_collection_task_not_found";
    public static final String ERROR_COLLECTION_TASK_CANT_EDIT = "wms_error_collection_task_cant_edit";
    public static final String ERROR_COLLECTION_TASK_CANT_DELETE = "wms_error_collection_task_cant_delete";
    public static final String ERROR_COLLECTION_TASK_ITEM_SN_OUTBOUND_NOT_MATCH = "wms_error_collection_task_item_sn_outbound_not_match";

    public static final String ERROR_PRODUCT_ARRIVAL_ORDER_NOT_FOUND = "wms_error_product_arrival_order_not_found";
    public static final String ERROR_PRODUCT_ARRIVAL_ORDER_INBOUND_DONE = "wms_error_product_arrival_order_inbound_done";
    public static final String ERROR_PRODUCT_ARRIVAL_ORDER_ON_SHELF_DONE = "wms_error_product_arrival_order_on_shelf_done";
    public static final String ERROR_PRODUCT_ARRIVAL_ORDER_THIRD_ORDER_NO_EXISTS = "wms_error_product_arrival_order_third_order_no_exists";

    public static final String ERROR_PRODUCT_ARRIVAL_ORDER_ITEM_SN_ON_SHELF_NOT_ENOUGH = "wms_error_product_arrival_order_item_sn_on_shelf_not_enough";
    public static final String ERROR_PRODUCT_ARRIVAL_ORDER_ITEM_NOT_FOUND = "wms_error_product_arrival_order_item_not_found";
    public static final String ERROR_PRODUCT_ARRIVAL_ORDER_ITEM_SN_INBOUND_NOT_MATCH = "wms_error_product_arrival_order_item_sn_inbound_not_match";

    public static final String ERROR_PRODUCT_SKU_NOT_FOUND = "wms_error_product_sku_not_found";
    public static final String ERROR_PRODUCT_SKU_EXISTS = "wms_error_product_sku_exists";

    public static final String ERROR_PRODUCT_SN_NOT_FOUND = "wms_error_product_sn_not_found";
    public static final String ERROR_PRODUCT_SN_NOT_INBOUND = "wms_error_product_sn_not_inbound";
    public static final String ERROR_PRODUCT_SN_NOT_ON_SHELF = "wms_error_product_sn_not_on_shelf";
    public static final String ERROR_PRODUCT_SN_NOT_OFF_SHELF = "wms_error_product_sn_not_off_shelf";

    public static final String ERROR_PRODUCT_DELIVERY_ORDER_NOT_FOUND = "wms_error_product_delivery_order_not_found";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_WAYBILL_EXISTS = "wms_error_product_delivery_order_waybill_exists";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_HAVE_NOT_DISPATCH = "wms_error_product_delivery_order_have_not_dispatch";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_ALREADY_DISPATCH = "wms_error_product_delivery_order_already_dispatch";

    public static final String ERROR_PRODUCT_DELIVERY_ORDER_SORTING_HAVE_NOT_DONE = "wms_error_product_delivery_order_sorting_have_not_done";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_VERIFY_ALREADY_DONE = "wms_error_product_delivery_order_verify_already_done";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_VERIFY_HAVE_NOT_DONE = "wms_error_product_delivery_order_verify_have_not_done";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_COURIER_NOT_SUPPORT = "wms_error_product_delivery_order_courier_not_support";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_THIRD_ORDER_NO_EXISTS = "wms_error_product_delivery_order_third_order_no_exists";

    public static final String ERROR_PRODUCT_DELIVERY_ORDER_ITEM_NOT_FOUND = "wms_error_product_delivery_order_item_not_found";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_ITEM_SN_NOT_ENOUGH = "wms_error_product_delivery_order_item_sn_not_enough";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_ITEM_SN_VERIFY_NOT_MATCH = "wms_error_product_delivery_order_item_sn_verify_not_match";

    public static final String ERROR_PRODUCT_DELIVERY_ORDER_FILE_PARSER_ERROR = "wms_error_product_delivery_order_file_parser_error";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_FILE_DUPLICATE_RECORD = "wms_error_product_delivery_order_file_duplicate_record";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_FILE_EXISTS_RECORD = "wms_error_product_delivery_order_file_exists_record";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_FILE_PCS_INVALID = "wms_error_product_delivery_order_file_pcs_invalid";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_FILE_SKU_NOT_FOUND = "wms_error_product_delivery_order_file_sku_not_found";
    public static final String ERROR_PRODUCT_DELIVERY_ORDER_BASKET_ALREADY_BIND = "wms_error_product_delivery_order_basket_already_bind";

    public static final String ERROR_COLLECTION_AREA_NOT_FOUND = "wms_error_collection_area_not_found";
    public static final String ERROR_COLLECTION_AREA_NO_EMPTY = "wms_error_collection_area_no_empty";

    public static final String ERROR_PRODUCT_WAVE_TASK_NOT_FOUND = "wms_error_product_wave_task_not_found";
    public static final String ERROR_PRODUCT_WAVE_TASK_OFF_SHELF_NOT_DONE = "wms_error_product_wave_task_off_shelf_not_done";
    public static final String ERROR_PRODUCT_WAVE_TASK_COLLECTION_NOT_DONE = "wms_error_product_wave_task_collection_not_done";
    public static final String ERROR_PRODUCT_WAVE_TASK_COLLECTION_ALREADY_DONE = "wms_error_product_wave_task_collection_already_done";
    public static final String ERROR_PRODUCT_WAVE_TASK_BASKET_NOT_DONE = "wms_error_product_wave_task_basket_not_done";
    public static final String ERROR_PRODUCT_WAVE_TASK_BASKET_ALREADY_DONE = "wms_error_product_wave_task_basket_already_done";
    public static final String ERROR_PRODUCT_WAVE_TASK_SORTING_ALREADY_DONE = "wms_error_product_wave_task_sorting_already_done";
}
