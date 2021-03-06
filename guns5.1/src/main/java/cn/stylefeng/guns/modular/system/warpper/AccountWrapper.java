/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
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
package cn.stylefeng.guns.modular.system.warpper;

import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class AccountWrapper extends BaseControllerWrapper {

    public AccountWrapper(Map<String, Object> single) {
        super(single);
    }

    public AccountWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public AccountWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public AccountWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        map.put("payStatus", ConstantFactory.me().getDictsByName("资金记录状态",(String) map.get("payStatus")));
        map.put("amountType", ConstantFactory.me().getDictsByName("资金记录类型",(String)map.get("amountType")));
        map.put("payWay", ConstantFactory.me().getDictsByName("支付渠道",(String)map.get("payWay")));
    }

}
