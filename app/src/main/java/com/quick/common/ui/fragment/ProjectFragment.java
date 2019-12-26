package com.quick.common.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.quick.common.R;
import com.quick.common.app.MvpFragment;
import com.quick.common.bean.main.ChapterBean;
import com.quick.common.config.Config;
import com.quick.common.event.ScrollTopEvent;
import com.quick.common.mvp.project.ProjectPresenter;
import com.quick.common.mvp.project.ProjectView;
import com.quick.common.utils.MagicIndicatorUtils;
import com.quick.common.utils.ScrollTop;
import com.quick.common.utils.lister.SimpleCallback;
import com.quick.core.adapter.MultiFragmentPagerAdapter;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhaolong.
 * Description: 项目
 * Date: 2019/12/10 0010 15:42
 */
public class ProjectFragment extends MvpFragment<ProjectPresenter> implements ProjectView, ScrollTop {
    Unbinder unbinder;
    @BindView(R.id.mi)
    MagicIndicator mi;
    @BindView(R.id.vp)
    ViewPager vp;

    private MultiFragmentPagerAdapter<ChapterBean, ProjectArticleFragment> mAdapter;
    private CommonNavigator mCommonNavigator;
    private long lastClickTime = 0L;
    private int lastClickPos = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_project, null);
        unbinder = ButterKnife.bind(this, root);
        init();
        return root;
    }

    private void init() {
        mAdapter = new MultiFragmentPagerAdapter<>(
                getChildFragmentManager(),
                new MultiFragmentPagerAdapter.FragmentCreator<ChapterBean, ProjectArticleFragment>() {
                    @Override
                    public ProjectArticleFragment create(ChapterBean data, int pos) {
                        return ProjectArticleFragment.create(data, pos);
                    }

                    @Override
                    public String getTitle(ChapterBean data) {
                        return data.getName();
                    }
                });
        vp.setAdapter(mAdapter);
        mCommonNavigator = MagicIndicatorUtils.commonNavigator(mi, vp, mAdapter, new SimpleCallback<Integer>() {
            @Override
            public void onResult(Integer data) {
                notifyScrollTop(data);
            }
        });
        mvpPresenter.getProjectChapters();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static ProjectFragment create() {
        return new ProjectFragment();
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter(this, this, getActivity());
    }

    @Override
    public void getProjectChaptersSuccess(List<ChapterBean> data) {
        mAdapter.setDataList(data);
        mCommonNavigator.notifyDataSetChanged();
    }

    @Override
    public void scrollTop() {
        if (isAdded() && !isDetached()) {
            new ScrollTopEvent(ProjectArticleFragment.class, vp.getCurrentItem()).post();
        }
    }

    private void notifyScrollTop(int pos) {
        long currClickTime = System.currentTimeMillis();
        if (lastClickPos == pos && currClickTime - lastClickTime <= Config.SCROLL_TOP_DOUBLE_CLICK_DELAY) {
            new ScrollTopEvent(ProjectArticleFragment.class, vp.getCurrentItem()).post();
        }
        lastClickPos = pos;
        lastClickTime = currClickTime;
    }
}
