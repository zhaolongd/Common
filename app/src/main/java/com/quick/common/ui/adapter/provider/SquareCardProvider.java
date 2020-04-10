package com.quick.common.ui.adapter.provider;

import android.view.ViewGroup;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.quick.common.R;
import com.quick.common.app.BaseBean;
import com.quick.common.bean.kaiyan.HorizontalScrollCard;
import com.quick.common.ui.adapter.SquareCardAdapter;
import com.quick.core.utils.DisplayInfoUtils;
import com.quick.core.widget.RecyclerItemDecoration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2020/4/8 0008 13:25
 */
public class SquareCardProvider extends BaseItemProvider<BaseBean> {

    @Override
    public int getItemViewType() {
        return IRecommendItemType.SQUARE_CARD_VIEW;
    }

    @Override
    public int getLayoutId() {
        return R.layout.community_item_square_card_view;
    }

    @Override
    public void onViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        StaggeredGridLayoutManager.LayoutParams layoutParams =
                new StaggeredGridLayoutManager.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int)DisplayInfoUtils.getInstance().dp2px(80));
        layoutParams.setFullSpan(true);
        viewHolder.itemView.setLayoutParams(layoutParams);
        RecyclerView rvSquareView = viewHolder.getView(R.id.rv_square_view);
        rvSquareView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvSquareView.setLayoutManager(layoutManager);
        rvSquareView.addItemDecoration(new RecyclerItemDecoration(0,0, (int)DisplayInfoUtils.getInstance().dp2px(5),0));
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, @Nullable BaseBean baseBean) {
        if (baseBean == null){
            return;
        }
        RecyclerView rvSquareView = (RecyclerView) baseViewHolder.itemView.findViewById(R.id.rv_square_view);
        SquareCardAdapter adapter = new SquareCardAdapter(R.layout.community_item_square_item_card_view);
        rvSquareView.setAdapter(adapter);
        HorizontalScrollCard scrollCard = (HorizontalScrollCard) baseBean;
        adapter.addData(scrollCard.getData().getItemList());
    }
}
