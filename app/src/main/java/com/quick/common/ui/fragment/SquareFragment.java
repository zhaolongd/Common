package com.quick.common.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.quick.common.R;
import com.quick.common.app.MvpFragment;
import com.quick.common.bean.main.ArticleBean;
import com.quick.common.bean.main.ArticleListBean;
import com.quick.common.mvp.square.SquarePresenter;
import com.quick.common.mvp.square.SquareView;
import com.quick.common.ui.activity.WebActivity;
import com.quick.common.ui.adapter.ArticleAdapter;
import com.quick.core.utils.SmartRefreshUtils;
import com.quick.core.widget.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import per.goweii.actionbarex.common.ActionBarCommon;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/25 0025 16:46
 */
public class SquareFragment extends MvpFragment<SquarePresenter> implements SquareView {
    Unbinder unbinder;
    @BindView(R.id.abc)
    ActionBarCommon abc;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    private static final int PAGE_START = 0;
    private SmartRefreshUtils mSmartRefreshUtils;
    private ArticleAdapter mAdapter;
    private int currPage = PAGE_START;

    public static SquareFragment create() {
        return new SquareFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_square, null);
        unbinder = ButterKnife.bind(this, root);
        init();
        return root;
    }

    private void init() {
        mSmartRefreshUtils = SmartRefreshUtils.with(srl);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                currPage = PAGE_START;
                getSquareArticleList(true);
            }
        });
        rv.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(getContext(), R.color.line)));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ArticleAdapter();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getSquareArticleList(false);
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean item = mAdapter.getItem(position);
                if (item != null) {
                    WebActivity.start(getContext(), item);
                }
            }
        });
        rv.setAdapter(mAdapter);
        getSquareArticleList(true);
    }

    public void getSquareArticleList(boolean refresh) {
        if(refresh){
            currPage = PAGE_START;
        }
        mvpPresenter.getSquareArticleList(currPage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected SquarePresenter createPresenter() {
        return new SquarePresenter(this, this, getActivity());
    }

    @Override
    public void getSquareArticleListSuccess(ArticleListBean data) {
        currPage = data.getCurPage() + PAGE_START;
        if (data.getCurPage() == 1) {
            mAdapter.setNewData(data.getDatas());
            mAdapter.getLoadMoreModule().setEnableLoadMore(true);
        } else {
            mAdapter.addData(data.getDatas());
            mAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (data.isOver()) {
            mAdapter.getLoadMoreModule().loadMoreEnd();
        }
        mSmartRefreshUtils.success();
    }
}
