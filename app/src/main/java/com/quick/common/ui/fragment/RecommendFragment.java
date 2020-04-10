package com.quick.common.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.quick.common.R;
import com.quick.common.app.BaseBean;
import com.quick.common.app.MvpFragment;
import com.quick.common.mvp.home.RecommendPresenter;
import com.quick.common.mvp.home.RecommendView;
import com.quick.common.ui.adapter.ProviderRecommendAdapter;
import com.quick.core.utils.DisplayInfoUtils;
import com.quick.core.utils.SmartRefreshUtils;
import com.quick.core.widget.RecyclerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhaolong.
 * Description: 开眼推荐
 * Date: 2020/4/3 0003 15:54
 */
public class RecommendFragment extends MvpFragment<RecommendPresenter> implements RecommendView {
    Unbinder unbinder;
    @BindView(R.id.rv_daily_view)
    RecyclerView rvDailyView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private SmartRefreshUtils mSmartRefreshUtils;
    private ProviderRecommendAdapter adapter;
    private boolean isRefresh = true;

    @Override
    protected RecommendPresenter createPresenter() {
        return new RecommendPresenter(this, this, getActivity());
    }

    public static RecommendFragment create() {
        return new RecommendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_recommend, null);
        unbinder = ButterKnife.bind(this, root);
        init();
        return root;
    }

    private void init() {
        adapter = new ProviderRecommendAdapter();
        rvDailyView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvDailyView.setLayoutManager(layoutManager);
        rvDailyView.addItemDecoration(new RecyclerItemDecoration(0,0, (int)DisplayInfoUtils.getInstance().dp2px(5), (int)DisplayInfoUtils.getInstance().dp2px(15)));
        rvDailyView.setAdapter(adapter);
        mSmartRefreshUtils = SmartRefreshUtils.with(refreshLayout);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                mvpPresenter.getRecommendData(isRefresh);
            }
        });
        adapter.getLoadMoreModule().setEnableLoadMore(false);
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mvpPresenter.getRecommendData(isRefresh);
            }
        });
        mvpPresenter.getRecommendData(isRefresh);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getRecommendDataSuccess(List<BaseBean> data) {
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
}
