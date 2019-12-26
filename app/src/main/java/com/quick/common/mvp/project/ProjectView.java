package com.quick.common.mvp.project;

import com.quick.common.bean.main.ChapterBean;
import com.quick.common.mvp.base.BaseView;
import java.util.List;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/10 0010 15:41
 */
public interface ProjectView extends BaseView {
    void getProjectChaptersSuccess(List<ChapterBean> data);
}
