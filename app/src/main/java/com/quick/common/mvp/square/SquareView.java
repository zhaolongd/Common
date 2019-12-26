package com.quick.common.mvp.square;

import com.quick.common.bean.main.ArticleListBean;
import com.quick.common.mvp.base.BaseView;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/25 0025 16:47
 */
public interface SquareView extends BaseView {
    void getSquareArticleListSuccess(ArticleListBean data);
}
