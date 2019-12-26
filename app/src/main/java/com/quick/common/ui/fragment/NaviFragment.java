package com.quick.common.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.quick.common.R;
import com.quick.common.app.MvpFragment;
import com.quick.common.bean.main.ArticleBean;
import com.quick.common.bean.main.NaviBean;
import com.quick.common.mvp.knowledge.NaviPresenter;
import com.quick.common.mvp.knowledge.NaviView;
import com.quick.common.ui.activity.WebActivity;
import com.quick.common.ui.adapter.NaviAdapter;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/21 0021 15:39
 */
public class NaviFragment extends MvpFragment<NaviPresenter> implements NaviView {
    Unbinder unbinder;
    @BindView(R.id.rv)
    RecyclerView rv;
    private NaviAdapter mAdapter;

    public static NaviFragment create() {
        return new NaviFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_navi, null);
        unbinder = ButterKnife.bind(this, root);
        init();
        return root;
    }

    private void init() {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NaviAdapter();
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnItemClickListener(new NaviAdapter.OnItemClickListener() {
            @Override
            public void onClick(ArticleBean bean, int pos) {
               WebActivity.start(getContext(), bean);
            }
        });
        rv.setAdapter(mAdapter);
        mvpPresenter.getNaviList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected NaviPresenter createPresenter() {
        return new NaviPresenter(this, this, getActivity());
    }

    @Override
    public void getNaviListSuccess(List<NaviBean> data) {
        mAdapter.setNewData(data);
    }
}
