package com.quick.common.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quick.common.R;
import com.quick.common.app.MvpFragment;
import com.quick.common.bean.main.ChapterBean;
import com.quick.common.mvp.knowledge.KnowledgePresenter;
import com.quick.common.mvp.knowledge.KnowledgeView;
import com.quick.common.ui.adapter.KnowledgeAdapter;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhaolong.
 * Description:体系
 * Date: 2019/12/21 0021 15:28
 */
public class KnowledgeFragment extends MvpFragment<KnowledgePresenter> implements KnowledgeView {

    Unbinder unbinder;
    @BindView(R.id.rv)
    RecyclerView rv;
    private KnowledgeAdapter mAdapter;

    public static KnowledgeFragment create() {
        return new KnowledgeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_knowledge, null);
        unbinder = ButterKnife.bind(this, root);
        init();
        return root;
    }

    private void init() {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new KnowledgeAdapter();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        mAdapter.setOnItemClickListener(new KnowledgeAdapter.OnItemClickListener() {
            @Override
            public void onClick(ChapterBean bean, int pos) {
//                KnowledgeArticleActivity.start(getContext(), bean, pos);
            }
        });
        rv.setAdapter(mAdapter);
        mvpPresenter.getKnowledgeList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected KnowledgePresenter createPresenter() {
        return new KnowledgePresenter(this, this, getActivity());
    }

    @Override
    public void getKnowledgeListSuccess(List<ChapterBean> data) {
        mAdapter.setNewData(data);
    }
}
