package com.quick.common.mvp.wx;

import com.quick.common.bean.main.ChapterBean;
import com.quick.common.mvp.base.BaseView;
import java.util.List;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/10 0010 15:38
 */
public interface WxView extends BaseView {
    void getWxArticleChaptersSuccess(List<ChapterBean> data);
}
