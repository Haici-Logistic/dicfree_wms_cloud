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
package com.dicfree.ms.wms.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.support.hibernate.persistence.id.SnowflakeIdWorker;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExProductSkuDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import com.dicfree.ms.wms.common.enums.ShelfStatus;
import com.dicfree.ms.wms.common.enums.StockStatus;
import com.dicfree.ms.wms.repository.jpa.dao.ProductSkuDao;
import com.dicfree.ms.wms.repository.jpa.dao.ProductSnDao;
import com.dicfree.ms.wms.repository.jpa.entity.ProductSku;
import com.dicfree.ms.wms.repository.jpa.entity.ProductSn;
import com.dicfree.ms.wms.service.ProductSnService;
import jakarta.annotation.Resource;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
@Service("productSnService")
public class ProductSnServiceImpl implements ProductSnService {

    @Resource
    private ProductSnDao productSnDao;

    @Resource
    private ProductSkuDao productSkuDao;

    @Resource
    private SnowflakeIdWorker snowflakeIdWorker;

    @Resource
    private DateTimeProvider dateTimeProvider;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public List<ExProductSnDto> productSnAdd(Long productArrivalOrderId, String productSkuCode, Integer totalCount) throws BusinessException {
        //检查sku是否存在
        productSkuDao.findByCode(productSkuCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SKU_NOT_FOUND, productSkuCode));

        List<ExProductSnDto> productSnDtoList = new ArrayList<>();
        for (int i = 1; i <= totalCount; i++) {
            ProductSn productSn = new ProductSn();
            productSn.setSerialNo(StringUtilPlus.leftPad(i, 3, '0'));

            productSn.setProductSkuCode(productSkuCode);
            productSn.setCode(StringUtilPlus.leftPad(snowflakeIdWorker.nextHexId(), 14, '0') + productSn.getSerialNo());
            productSn.setShelfAreaCode(null);
            productSn.setShelfNo(null);
            productSn.setStockStatus(StockStatus.WAITING);
            productSn.setInboundTime(null);
            productSn.setOutboundTime(null);
            productSn.setShelfStatus(ShelfStatus.WAITING);
            productSn.setOnShelfTime(null);
            productSn.setOffShelfTime(null);
            productSn.setSorted(Boolean.FALSE);
            productSn.setVerified(Boolean.FALSE);
            productSn.setArrivalOrderItemId(productArrivalOrderId);
            productSn.setDeliveryOrderItemId(null);
            productSn.setPcs(totalCount);
            productSnDao.save(productSn);

            ExProductSnDto productSnDto = toExSnFullDto(productSn);
            productSnDtoList.add(productSnDto);
        }
        return productSnDtoList;
    }

    @Override
    public List<ExProductSnDto> productSnUnArrivalList(Long productArrivalOrderItemId) {
        List<ProductSn> productSnList = productSnDao.findAllUnArrivalByArrivalOrderItemId(productArrivalOrderItemId);
        return productSnList.stream().map(this::toExProductSnUndoDto).toList();
    }

    @Override
    public List<ExProductSnDto> productSnUnArrivalPrintList(Long productArrivalOrderItemId) {
        List<Object> productSnList = productSnDao.findAllUnArrivalDetailByArrivalOrderItemId(productArrivalOrderItemId);
        return productSnList.stream().map(this::toExProductSnTemplateDto).toList();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public String productSnInbound(Long productArrivalOrderItemId, Boolean quality) throws BusinessException {
        ProductSn productSn = productSnDao.findAllUnArrivalByArrivalOrderItemId(productArrivalOrderItemId, PageRequest.ofSize(1)).getContent().stream().findFirst().orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_ITEM_SN_INBOUND_NOT_MATCH));

        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));
        productSn.setInboundTime(currTime);
        productSn.setStockStatus(StockStatus.INBOUND);
        productSn.setQuality(quality);
        productSnDao.save(productSn);

        return productSn.getCode();
    }

    @Override
    public List<ExProductSnDto> productSnOnShelfList(String productSkuCode) {
        List<ProductSn> productSnList = productSnDao.findAllOnShelfByProductSkuCode(productSkuCode);
        return productSnList.stream().map(this::toExSnFullDto).toList();
    }

    @Override
    public ExProductSnDto productSnOnShelf(String productSkuCode, String shelfNo) throws BusinessException {
        ProductSn productSn = productSnDao.findAllUnOnShelfBySkuCode(productSkuCode, PageRequest.ofSize(1)).getContent().stream().findFirst().orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_ITEM_SN_ON_SHELF_NOT_ENOUGH));
        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));
        productSn.setShelfAreaCode(StringUtilPlus.splitGetFirst(shelfNo, StringUtilPlus.HYPHEN));
        productSn.setShelfNo(shelfNo);
        productSn.setOnShelfTime(currTime);
        productSn.setShelfStatus(ShelfStatus.ON_SHELF);
        productSnDao.save(productSn);

        return toExSnFullDto(productSn);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void productSnShelfChange(String productSnCode, String shelfNo) throws BusinessException {
        ProductSn productSn = productSnDao.findByCode(productSnCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SN_NOT_FOUND, productSnCode));
        if (productSn.getStockStatus() != StockStatus.INBOUND) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SN_NOT_INBOUND, productSnCode);
        }
        if (productSn.getShelfStatus() != ShelfStatus.ON_SHELF) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SN_NOT_ON_SHELF, productSnCode);
        }
        productSn.setShelfAreaCode(StringUtilPlus.splitGetFirst(shelfNo, StringUtilPlus.HYPHEN));
        productSn.setRemark(StringUtilPlus.join(productSn.getRemark(), ";货架更换，原货架为->", productSn.getSerialNo()));
        productSn.setShelfNo(shelfNo);
        productSnDao.save(productSn);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void productSnProductSkuCodeChange(String productSnCode, String productSkuCode) throws BusinessException {
        ProductSn productSn = productSnDao.findByCode(productSnCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SN_NOT_FOUND, productSnCode));
        productSkuDao.findByCode(productSkuCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SKU_NOT_FOUND, productSkuCode));

        productSn.setRemark(StringUtilPlus.join(productSn.getRemark(), ";skuCode更换，原skuCode为->", productSn.getProductSkuCode()));
        productSn.setProductSkuCode(productSkuCode);
        productSnDao.save(productSn);
    }

    @Override
    public void productSnQualityChange(String productSnCode, Boolean quality) throws BusinessException {
        ProductSn productSn = productSnDao.findByCode(productSnCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SN_NOT_FOUND, productSnCode));
        productSn.setRemark(StringUtilPlus.join(productSn.getRemark(), ";quality更换，原quality为->", productSn.getQuality().toString()));
        productSn.setQuality(quality);
        productSnDao.save(productSn);
    }

    @Override
    public void productSnReOnShelf(String productSnCode, String shelfNo) throws BusinessException {
        ProductSn productSn = productSnDao.findByCode(productSnCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SN_NOT_FOUND, productSnCode));
        if(productSn.getStockStatus() != StockStatus.INBOUND){
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SN_NOT_INBOUND, productSnCode);
        }
        if(productSn.getShelfStatus() != ShelfStatus.OFF_SHELF){
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SN_NOT_OFF_SHELF, productSnCode);
        }
        productSn.setShelfStatus(ShelfStatus.ON_SHELF);
        productSn.setOnShelfTime(LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now())));
        productSn.setShelfAreaCode(StringUtilPlus.splitGetFirst(shelfNo, StringUtilPlus.HYPHEN));
        productSn.setShelfNo(shelfNo);
        productSn.setRemark(StringUtilPlus.join(productSn.getRemark(), ";重新上架，原下架订单为->", productSn.getDeliveryOrderItemId()));

        productSn.setOffShelfTime(null);
        productSn.setSorted(Boolean.FALSE);
        productSn.setVerified(Boolean.FALSE);
        productSn.setDeliveryOrderItemId(null);
        productSnDao.save(productSn);
    }

    @Override
    public List<ExProductSnDto> productSnUnVerifyList(String productSkuCode) {
        List<ProductSn> productSnList = productSnDao.findAllUnVerifyByProductSkuCode(productSkuCode);
        return productSnList.stream().map(productSn -> {
            ExProductSnDto productSnDto = new ExProductSnDto();
            productSnDto.setId(productSn.getId());
            productSnDto.setCode(productSn.getCode());
            productSnDto.setProductSkuCode(productSn.getProductSkuCode());
            return productSnDto;
        }).toList();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void productSnSorting(List<Long> productDeliveryOrderItemId) throws BusinessException {
        productSnDao.findAllByDeliveryOrderItemIdIn(productDeliveryOrderItemId).forEach(productSn -> {
            //productSn.setDeliveryOrderItemId(productDeliveryOrderItemId);//这里的出库订单id直接使用下架时候分配的id即可，不需要再设置了
            productSn.setSorted(Boolean.TRUE);
            productSnDao.save(productSn);
        });
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void productSnSorting(Long productDeliveryOrderItemId, String productSkuCode) throws BusinessException {
        ProductSn productSn = productSnDao.findAllUnSortByProductSkuCode(productSkuCode, PageRequest.ofSize(1)).getContent().stream().findFirst().orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_ITEM_SN_VERIFY_NOT_MATCH));
        productSn.setDeliveryOrderItemId(productDeliveryOrderItemId);//这里的出库订单id是最终分拣后确认的
        productSn.setSorted(Boolean.TRUE);
        productSnDao.save(productSn);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public String productSnVerify(Long productDeliveryOrderItemId, String productSkuCode) throws BusinessException {
        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));

        ProductSn productSn = productSnDao.findAllUnVerifyByProductSkuCodeAndDeliveryOrderItemId(productSkuCode, productDeliveryOrderItemId, PageRequest.ofSize(1)).getContent().stream().findFirst().orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_ITEM_SN_VERIFY_NOT_MATCH));
        productSn.setVerified(Boolean.TRUE);
        productSn.setOutboundTime(currTime);
        productSn.setStockStatus(StockStatus.OUTBOUND);//核验的时候同时出库
        productSnDao.save(productSn);

        return productSn.getCode();
    }

    @Override
    public List<ExProductSnDto> productSnOffShelfUndoList(String productSkuCode, int count) {
        List<ProductSn> productSnList = productSnDao.findAllUnOffShelfByProductSkuCode(productSkuCode, PageRequest.ofSize(count)).getContent();
        return productSnList.stream().map(this::toProductSnOffShelfDto).toList();
    }

    @Override
    public List<ExProductSnDto> productSnOffShelfDoneList(Long productDeliveryOrderItemId) {
        List<ProductSn> productSnList = productSnDao.findAllOffShelfByDeliveryOrderItemId(productDeliveryOrderItemId);
        return productSnList.stream().map(this::toProductSnOffShelfDto).toList();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public String productSnOffShelf(Long productDeliveryOrderItemId, String shelfNo, String productSkuCode) throws BusinessException {
        List<ProductSn> productSnList = productSnDao.findByProductSkuCodeAndShelfNo(productSkuCode, shelfNo);
        if (CollectionUtilPlus.Collection.isEmpty(productSnList)) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SN_NOT_FOUND, productSkuCode);
        }
        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));
        ProductSn productSn = productSnList.get(0);
        productSn.setShelfStatus(ShelfStatus.OFF_SHELF);
        productSn.setOffShelfTime(currTime);
        productSn.setDeliveryOrderItemId(productDeliveryOrderItemId); //这里的出库订单id，不是最终的id，临时过度的，最后会由分拣确认
        productSnDao.save(productSn);

        return productSn.getCode();
    }

    @Override
    public List<ExProductSnDto> productSnUnSortingList(List<Long> productDeliveryOrderItemIdList) {
        List<ProductSn> productSnList = productSnDao.findAllUnSortByDeliveryOrderItemIdIn(productDeliveryOrderItemIdList);
        return productSnList.stream().map(productSn -> {
            ExProductSnDto productSnDto = new ExProductSnDto();
            productSnDto.setId(productSn.getId());
            productSnDto.setCode(productSn.getCode());
            productSnDto.setProductSkuCode(productSn.getProductSkuCode());
            return productSnDto;
        }).toList();
    }

    /**
     * DB数据转DTO数据
     *
     * @param productSn DB数据
     * @return DTO数据
     */
    private ExProductSnDto toExSnFullDto(ProductSn productSn) {
        ExProductSnDto productSnDto = new ExProductSnDto();
        productSnDto.setId(productSn.getId());
        productSnDto.setProductSkuCode(productSn.getProductSkuCode());
        productSnDto.setCode(productSn.getCode());
        productSnDto.setShelfAreaCode(productSn.getShelfAreaCode());
        productSnDto.setShelfNo(productSn.getShelfNo());
        productSnDto.setStockStatus(productSn.getStockStatus());
        productSnDto.setInboundTime(productSn.getInboundTime());
        productSnDto.setOutboundTime(productSn.getOutboundTime());
        productSnDto.setShelfStatus(productSn.getShelfStatus());
        productSnDto.setOnShelfTime(productSn.getOnShelfTime());
        productSnDto.setOffShelfTime(productSn.getOffShelfTime());
        productSnDto.setArrivalOrderItemId(productSn.getArrivalOrderItemId());
        productSnDto.setDeliveryOrderItemId(productSn.getDeliveryOrderItemId());
        productSnDto.setPcs(productSn.getPcs());
        return productSnDto;
    }

    /**
     * DB数据转DTO数据
     *
     * @param productSn DB数据
     * @return DTO数据
     */
    private ExProductSnDto toExProductSnUndoDto(ProductSn productSn) {
        ExProductSnDto productSnDto = new ExProductSnDto();
        productSnDto.setId(productSn.getId());
        productSnDto.setProductSkuCode(productSn.getProductSkuCode());
        productSnDto.setCode(productSn.getCode());
        return productSnDto;
    }

    /**
     * 获取打印数据
     *
     * @param fullInfo 打印数据
     * @return 组装数据
     */
    private ExProductSnDto toExProductSnTemplateDto(Object fullInfo) {
        ProductSn productSn = (ProductSn) ((Object[]) fullInfo)[0];
        ExProductSnDto productSnDto = new ExProductSnDto();
        productSnDto.setArrivalOrderItemId(productSn.getArrivalOrderItemId());
        productSnDto.setSerialNo(productSn.getSerialNo());
        productSnDto.setPcs(productSn.getPcs());
        productSnDto.setCode(productSn.getCode());
        productSnDto.setProductSkuCode(productSn.getProductSkuCode());

        ProductSku productSku = (ProductSku) ((Object[]) fullInfo)[1];
        ExProductSkuDto productSkuDto = new ExProductSkuDto();
        productSkuDto.setCode(productSku.getCode());
        productSkuDto.setProductCode(productSku.getProductCode());
        productSkuDto.setSupplier(productSku.getSupplier());
        productSnDto.setProductSku(productSkuDto);
        return productSnDto;
    }

    /**
     * DB数据转DTO数据
     *
     * @param productSn DB数据
     * @return DTO数据
     */
    private ExProductSnDto toProductSnOffShelfDto(ProductSn productSn) {
        ExProductSnDto productSnDto = new ExProductSnDto();
        productSnDto.setId(productSn.getId());
        productSnDto.setCode(productSn.getCode());
        productSnDto.setShelfNo(productSn.getShelfNo());
        productSnDto.setShelfStatus(productSn.getShelfStatus());
        productSnDto.setProductSkuCode(productSn.getProductSkuCode());
        productSnDto.setShelfAreaCode(productSn.getShelfAreaCode());
        return productSnDto;
    }
}
