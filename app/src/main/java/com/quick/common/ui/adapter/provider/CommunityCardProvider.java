package com.quick.common.ui.adapter.provider;

import android.view.ViewGroup;
import android.widget.ImageView;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.quick.common.R;
import com.quick.common.app.BaseBean;
import com.quick.common.bean.kaiyan.CloumnsCard;
import com.quick.common.utils.ImageLoader;
import com.quick.core.utils.DisplayInfoUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2020/4/8 0008 15:26
 */
public class CommunityCardProvider extends BaseItemProvider<BaseBean> {
    @Override
    public int getItemViewType() {
        return IRecommendItemType.COMMUNITY_CARD_VIEW;
    }

    @Override
    public int getLayoutId() {
        return R.layout.community_item_community_view;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseBean baseBean) {
        if (baseBean == null){
            return;
        }
        ImageView ivCoverBg = baseViewHolder.getView(R.id.iv_cover_bg);
        ViewGroup.LayoutParams layoutParams = ivCoverBg.getLayoutParams();
        CloumnsCard viewModel = (CloumnsCard) baseBean;
        int itemWidth = DisplayInfoUtils.getInstance().getWidthPixels() / 2;
        float scale = (itemWidth+0f)/viewModel.imgWidth;
        layoutParams.height = (int) (viewModel.imgHeight*scale);
        ivCoverBg.setLayoutParams(layoutParams);
        ImageLoader.image(baseViewHolder.getView(R.id.iv_cover_bg), viewModel.getCoverUrl());
        ImageLoader.image(baseViewHolder.getView(R.id.iv_avatar), viewModel.getAvatarUrl());
        baseViewHolder.setText(R.id.tv_description, viewModel.getDescription());
        baseViewHolder.setText(R.id.tv_nickname, viewModel.getNickName());
        baseViewHolder.setText(R.id.tv_collection_num, viewModel.getCollectionCount()+ "");
    }
}
