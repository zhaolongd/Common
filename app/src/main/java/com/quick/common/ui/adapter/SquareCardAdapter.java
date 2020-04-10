package com.quick.common.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.quick.common.R;
import com.quick.common.bean.kaiyan.SquareContentCard;
import com.quick.common.utils.ImageLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2020/4/8 0008 13:53
 */
public class SquareCardAdapter extends BaseQuickAdapter<SquareContentCard, BaseViewHolder> {

    public SquareCardAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, @Nullable SquareContentCard squareContentCard) {
        if (squareContentCard == null){
            return;
        }
        ImageLoader.image(baseViewHolder.getView(R.id.iv_cover_bg), squareContentCard.getData().getBgPicture());
        baseViewHolder.setText(R.id.tv_title, squareContentCard.getData().getTitle());
        baseViewHolder.setText(R.id.tv_subtitle, squareContentCard.getData().getSubTitle());
    }
}
