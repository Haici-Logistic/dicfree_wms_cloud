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
package com.dicfree.ms.wms.common.dto.ex;

import cn.jzyunqi.common.model.ValidatorDto;
import com.dicfree.ms.wms.common.dto.BoxSnDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
@Setter
public class ExBoxSnDto extends BoxSnDto implements ValidatorDto{
    @Serial
    private static final long serialVersionUID = -212951198247566900L;

    public interface Add {
    }

    public interface Edit {
    }

    @Getter
    private ExBoxSkuDto boxSku;

    @Getter
    private ExBoxDeliveryOrderDto boxDeliveryOrder;
}
