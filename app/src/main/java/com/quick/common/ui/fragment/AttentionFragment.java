package com.quick.common.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.quick.common.R;
import com.quick.common.app.BaseBean;
import com.quick.common.app.MvpFragment;
import com.quick.common.mvp.home.AttentionPresenter;
import com.quick.common.mvp.home.AttentionView;
import com.quick.common.ui.adapter.AttentionRecyclerAdapter;
import com.quick.core.utils.SmartRefreshUtils;
import com.quick.core.widget.RecyclerItemDecoration;
import com.quick.video.helper.ScrollCalculatorHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhaolong.
 * Description: 开眼视频 关注列表
 * Date: 2020/4/10 0010 14:12
 */
public class AttentionFragment extends MvpFragment<AttentionPresenter> implements AttentionView {
    Unbinder unbinder;
    @BindView(R.id.rv_attention_view)
    RecyclerView rvAttentionView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private AttentionRecyclerAdapter adapter;
    private ScrollCalculatorHelper scrollCalculatorHelper;
    private LinearLayoutManager linearLayoutManager;
    boolean mFull = false;
    private boolean isRefresh = true;
    private SmartRefreshUtils mSmartRefreshUtils;

    public static AttentionFragment create() {
        return new AttentionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @
            Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_attention, null);
        unbinder = ButterKnife.bind(this, root);
        init();
        return root;
    }

    private void init() {
        rvAttentionView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvAttentionView.setLayoutManager(linearLayoutManager);
        adapter =
                new AttentionRecyclerAdapter(R.layout.community_item_attention_card_view);
        int decoration = DensityUtil.dp2px(10);
        rvAttentionView.addItemDecoration(
                new RecyclerItemDecoration(decoration, 0, decoration, decoration));
        rvAttentionView.setAdapter(adapter);
        mSmartRefreshUtils = SmartRefreshUtils.with(refreshLayout);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                mvpPresenter.getAttentionData(isRefresh);
            }
        });
        adapter.getLoadMoreModule().setEnableLoadMore(false);
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mvpPresenter.getAttentionData(isRefresh);
        });
        // 限定范围为屏幕一半的上下偏移180
        int playTop = CommonUtil.getScreenHeight(getContext()) / 2
                - CommonUtil.dip2px(getContext(), 180);
        int playBottom = CommonUtil.getScreenHeight(getContext()) / 2
                + CommonUtil.dip2px(getContext(), 180);
        // 控制自动播放帮助类
        scrollCalculatorHelper = new ScrollCalculatorHelper(
                R.id.video_item_player, playTop, playBottom);

        // 滑动监听
        rvAttentionView
                .addOnScrollListener(new RecyclerView.OnScrollListener()
                {
                    int firstVisibleItem, lastVisibleItem;

                    @Override
                    public void onScrollStateChanged(
                            @NonNull RecyclerView recyclerView, int newState)
                    {
                        super.onScrollStateChanged(recyclerView, newState);
                        scrollCalculatorHelper.onScrollStateChanged(recyclerView,
                                newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView,
                                           int dx, int dy)
                    {
                        super.onScrolled(recyclerView, dx, dy);
                        firstVisibleItem =
                                linearLayoutManager.findFirstVisibleItemPosition();
                        lastVisibleItem =
                                linearLayoutManager.findLastVisibleItemPosition();

                        // 这是滑动自动播放的代码
                        if (!mFull)
                        {
                            scrollCalculatorHelper.onScroll(recyclerView,
                                    firstVisibleItem,
                                    lastVisibleItem,
                                    lastVisibleItem - firstVisibleItem);
                        }
                    }
                });
        mvpPresenter.getAttentionData(isRefresh);
    }

    @Override
    protected AttentionPresenter createPresenter() {
        return new AttentionPresenter(this, this, getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getAttentionDataSuccess(List<BaseBean> data) {
        if (isRefresh) {
            adapter.setNewData(data);
            adapter.getLoadMoreModule().setEnableLoadMore(true);
        } else {
            adapter.addData(data);
            adapter.getLoadMoreModule().loadMoreComplete();
        }
        isRefresh = false;
        mSmartRefreshUtils.success();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }
}
