package com.quick.common.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.quick.common.R;
import com.quick.common.app.MvpFragment;
import com.quick.common.bean.home.BannerBean;
import com.quick.common.bean.main.ArticleBean;
import com.quick.common.bean.main.ArticleListBean;
import com.quick.common.mvp.home.HomePresenter;
import com.quick.common.mvp.home.HomeView;
import com.quick.common.ui.activity.WebActivity;
import com.quick.common.ui.activity.home.CommunityActivity;
import com.quick.common.ui.activity.home.SearchActivity;
import com.quick.common.ui.adapter.ArticleAdapter;
import com.quick.common.utils.ImageLoader;
import com.quick.core.utils.DisplayInfoUtils;
import com.quick.core.utils.SmartRefreshUtils;
import com.quick.core.widget.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/10 0010 15:20
 */
public class HomeFragment extends MvpFragment<HomePresenter> implements HomeView {
    private static final int PAGE_START = 0;
    Unbinder unbinder;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.abc)
    ActionBarCommon abc;
    private ArticleAdapter mAdapter;
    private int currPage = PAGE_START;
    private SmartRefreshUtils mSmartRefreshUtils;
    private Banner mBanner;
    private List<BannerBean> mBannerBeans;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, root);
        init();
        return root;
    }

    private void init() {
        abc.setOnRightIconClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(getContext());
            }
        });
        abc.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {
                CommunityActivity.start(getContext());
            }
        });
        mSmartRefreshUtils = SmartRefreshUtils.with(srl);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                currPage = PAGE_START;
                mvpPresenter.getArticleList(currPage);
            }
        });
        rv.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(getContext(), R.color.line)));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ArticleAdapter();
        mAdapter.getLoadMoreModule().setEnableLoadMore(true);
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mvpPresenter.getArticleList(currPage);
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
        createHeaderBanner();
        rv.setAdapter(mAdapter);
        mvpPresenter.getArticleList(currPage);
        mvpPresenter.getBanner();
    }

    public static HomeFragment create() {
        return new HomeFragment();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this, this, getActivity());
    }

    @Override
    public void getBannerSuccess(List<BannerBean> data) {
        mBannerBeans = data;
        List<String> urls = new ArrayList<>(data.size());
        List<String> titles = new ArrayList<>(data.size());
        for (BannerBean bean : data) {
            urls.add(bean.getImagePath());
            titles.add(bean.getTitle());
        }
        mBanner.setImages(urls);
        mBanner.setBannerTitles(titles);
        mBanner.start();
    }

    @Override
    public void getArticleListSuccess(ArticleListBean data) {
        currPage = data.getCurPage() + PAGE_START;
        if (data.getCurPage() == 1) {
            mAdapter.setNewData(data.getDatas());
        } else {
            mAdapter.addData(data.getDatas());
            mAdapter.getLoadMoreModule().loadMoreComplete();

        }
        if (data.isOver()) {
            mAdapter.getLoadMoreModule().loadMoreEnd();
        } else {
            if (!mAdapter.getLoadMoreModule().isEnableLoadMore()) {
                mAdapter.getLoadMoreModule().setEnableLoadMore(true);
            }
        }
        mSmartRefreshUtils.success();
    }

    private void createHeaderBanner() {
        if (mBanner == null) {
            mBanner = new Banner(getContext());
            int height = (int) (DisplayInfoUtils.getInstance().getWidthPixels() * (9F / 16F));
            mBanner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
            mBanner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
                @Override
                public void displayImage(Context context, Object url, ImageView imageView) {
                    ImageLoader.banner(imageView, (String) url);
                }
            });
            mBanner.setIndicatorGravity(BannerConfig.CENTER);
            mBanner.setBannerAnimation(Transformer.Default);
            mBanner.startAutoPlay();
            mBanner.setDelayTime(5000);
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    BannerBean bean = mBannerBeans.get(position);
//                    WebActivity.start(getContext(), bean.getTitle(), bean.getUrl());
                }
            });
            mAdapter.addHeaderView(mBanner, 0);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
