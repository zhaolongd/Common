package com.quick.common.mvp.home;

import com.quick.common.bean.home.HotKeyBean;
import com.quick.common.mvp.base.BaseView;
import java.util.List;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/25 0025 15:16
 */
public interface SearchHistoryView extends BaseView {
    void getHotKeyListSuccess(List<HotKeyBean> data);
}
