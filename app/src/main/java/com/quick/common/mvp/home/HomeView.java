package com.quick.common.mvp.home;

import com.quick.common.bean.home.BannerBean;
import com.quick.common.bean.main.ArticleListBean;
import com.quick.common.mvp.base.BaseView;
import java.util.List;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/10 0010 15:32
 */
public interface HomeView extends BaseView{
    void getBannerSuccess(List<BannerBean> data);
    void getArticleListSuccess(ArticleListBean data);
}
