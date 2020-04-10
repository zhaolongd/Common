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
import com.quick.common.bean.main.ChapterBean;
import com.quick.common.mvp.wx.WxArticlePresenter;
import com.quick.common.mvp.wx.WxArticleView;
import com.quick.common.ui.activity.WebActivity;
import com.quick.common.ui.adapter.ArticleAdapter;
import com.quick.core.utils.SmartRefreshUtils;
import com.quick.core.widget.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/24 0024 10:39
 */
public class WxArticleFragment extends MvpFragment<WxArticlePresenter> implements WxArticleView {

    Unbinder unbinder;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;

    private static final int PAGE_START = 1;
    private SmartRefreshUtils mSmartRefreshUtils;
    private ArticleAdapter mAdapter;
    private ChapterBean mChapterBean;
    private int mPosition = -1;
    private int currPage = PAGE_START;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_wx_article, null);
        unbinder = ButterKnife.bind(this, root);
        init();
        return root;
    }

    private void init() {
        Bundle args = getArguments();
        if (args != null) {
            mChapterBean = (ChapterBean) args.getSerializable("chapterBean");
            mPosition = args.getInt("position", -1);
        }
        mSmartRefreshUtils = SmartRefreshUtils.with(srl);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                currPage = PAGE_START;
                getWxArticleList(true);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.HORIZONTAL,2, ContextCompat.getColor(getContext(),R.color.line)));
        mAdapter = new ArticleAdapter();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getWxArticleList(false);
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
        getWxArticleList(false);
    }

    public void getWxArticleList(boolean refresh) {
        if (mChapterBean != null) {
            if(refresh){
                currPage = PAGE_START;
            }
            mvpPresenter.getWxArticleList(mChapterBean.getId(), currPage);
        }
    }

    public static WxArticleFragment create(ChapterBean chapterBean, int position) {
        WxArticleFragment fragment = new WxArticleFragment();
        Bundle args = new Bundle(2);
        args.putSerializable("chapterBean", chapterBean);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected WxArticlePresenter createPresenter() {
        return new WxArticlePresenter(this, this, getActivity());
    }

    @Override
    public void getWxArticleListSuccess(ArticleListBean data) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
