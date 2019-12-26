package com.quick.common.mvp.wx;

import com.quick.common.bean.main.ArticleListBean;
import com.quick.common.mvp.base.BaseView;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/24 0024 10:40
 */
public interface WxArticleView extends BaseView {
    void getWxArticleListSuccess(ArticleListBean data);
}
