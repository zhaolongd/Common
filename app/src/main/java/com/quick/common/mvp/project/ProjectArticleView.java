package com.quick.common.mvp.project;

import com.quick.common.bean.main.ArticleListBean;
import com.quick.common.mvp.base.BaseView;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/24 0024 15:05
 */
public interface ProjectArticleView extends BaseView {
    void getProjectArticleListSuccess(ArticleListBean data);
}
