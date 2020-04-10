package com.quick.common.ui.adapter;

import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.quick.common.app.BaseBean;
import com.quick.common.bean.kaiyan.CloumnsCard;
import com.quick.common.bean.kaiyan.HorizontalScrollCard;
import com.quick.common.ui.adapter.provider.CommunityCardProvider;
import com.quick.common.ui.adapter.provider.IRecommendItemType;
import com.quick.common.ui.adapter.provider.SquareCardProvider;
import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2020/4/7 0007 14:59
 */

public class ProviderRecommendAdapter extends BaseProviderMultiAdapter<BaseBean> implements LoadMoreModule {

    public ProviderRecommendAdapter()
    {
        super();
        addItemProvider(new SquareCardProvider());
        addItemProvider(new CommunityCardProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseBean> data, int position) {
        if (data.get(position) instanceof HorizontalScrollCard)
        {

            return IRecommendItemType.SQUARE_CARD_VIEW;
        }
        else if (data.get(position) instanceof CloumnsCard)
        {

            return IRecommendItemType.COMMUNITY_CARD_VIEW;
        }
        return 0;
    }
}
