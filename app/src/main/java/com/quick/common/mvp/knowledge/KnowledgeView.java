package com.quick.common.mvp.knowledge;

import com.quick.common.bean.main.ChapterBean;
import com.quick.common.mvp.base.BaseView;
import java.util.List;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/21 0021 15:29
 */
public interface KnowledgeView extends BaseView{
    void getKnowledgeListSuccess(List<ChapterBean> data);
}
