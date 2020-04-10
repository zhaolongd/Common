package com.quick.common.mvp.home;

import com.quick.common.app.BaseBean;
import com.quick.common.mvp.base.BaseView;
import java.util.List;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2020/4/3 0003 15:55
 */
public interface RecommendView extends BaseView {
    void getRecommendDataSuccess(List<BaseBean> data);
}
