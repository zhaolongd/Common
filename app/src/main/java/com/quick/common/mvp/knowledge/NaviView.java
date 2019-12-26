package com.quick.common.mvp.knowledge;

import com.quick.common.bean.main.NaviBean;
import com.quick.common.mvp.base.BaseView;
import java.util.List;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/21 0021 15:39
 */
public interface NaviView extends BaseView{
    void getNaviListSuccess(List<NaviBean> data);
}
