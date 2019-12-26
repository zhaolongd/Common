package com.quick.common.mvp.home;

import com.quick.common.bean.main.ArticleListBean;
import com.quick.common.mvp.base.BaseView;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/25 0025 15:52
 */
public interface SearchResultView extends BaseView {
    void searchSuccess(ArticleListBean data);
}
