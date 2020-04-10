package com.quick.common.ui.adapter;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;
import com.quick.common.R;
import com.quick.common.bean.main.ArticleBean;
import com.quick.common.bean.main.NaviBean;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by zhaolong.
 * Description: 导航
 * Date: 2019/12/23 0023 14:23
 */
public class NaviAdapter extends BaseQuickAdapter<NaviBean, BaseViewHolder> implements LoadMoreModule {
    private LayoutInflater mInflater = null;
    private Queue<TextView> mFlexItemTextViewCaches = new LinkedList<>();
    private OnItemClickListener mOnItemClickListener = null;

    public NaviAdapter() {
        super(R.layout.rv_item_knowledge);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, NaviBean item) {
        helper.setText(R.id.tv_name, item.getName());
        FlexboxLayout fbl = helper.getView(R.id.fbl);
        for (int i = 0; i < item.getArticles().size(); i++) {
            final ArticleBean childItem = item.getArticles().get(i);
            TextView child = createOrGetCacheFlexItemTextView(fbl);
            child.setText(childItem.getTitle());
            int finalI = i;
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onClick(childItem, finalI);
                    }
                }
            });
            fbl.addView(child);
        }
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        FlexboxLayout fbl = holder.getView(R.id.fbl);
        for (int i = 0; i < fbl.getChildCount(); i++) {
            mFlexItemTextViewCaches.offer((TextView) fbl.getChildAt(i));
        }
        fbl.removeAllViews();
    }

    private TextView createOrGetCacheFlexItemTextView(FlexboxLayout fbl) {
        TextView tv = mFlexItemTextViewCaches.poll();
        if (tv != null) {
            return tv;
        }
        return createFlexItemTextView(fbl);
    }

    private TextView createFlexItemTextView(FlexboxLayout fbl) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(fbl.getContext());
        }
        return (TextView) mInflater.inflate(R.layout.rv_item_knowledge_child, fbl, false);
    }

    public interface OnItemClickListener {
        void onClick(ArticleBean bean, int pos);
    }

}
