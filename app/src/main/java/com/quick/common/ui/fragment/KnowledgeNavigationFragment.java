package com.quick.common.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.quick.common.R;
import com.quick.common.app.MvpFragment;
import com.quick.common.utils.MagicIndicatorUtils;
import com.quick.common.utils.ScrollTop;
import com.quick.common.config.Config;
import com.quick.common.mvp.knowledge.KnowledgeNavigationPresenter;
import com.quick.common.mvp.knowledge.KnowledgeNavigationView;
import com.quick.common.utils.lister.SimpleCallback;
import com.quick.core.adapter.FixedFragmentPagerAdapter;
import net.lucode.hackware.magicindicator.MagicIndicator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/10 0010 15:34
 */
public class KnowledgeNavigationFragment extends MvpFragment<KnowledgeNavigationPresenter> implements KnowledgeNavigationView, ScrollTop {
    Unbinder unbinder;
    @BindView(R.id.mi)
    MagicIndicator mi;
    @BindView(R.id.vp)
    ViewPager vp;

    private FixedFragmentPagerAdapter mAdapter;
    private long lastClickTime = 0L;
    private int lastClickPos = 0;

    public static KnowledgeNavigationFragment create() {
        return new KnowledgeNavigationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_knowledge_navigation, null);
        unbinder = ButterKnife.bind(this, root);
        init();
        return root;
    }

    private void init() {
        mAdapter = new FixedFragmentPagerAdapter(getChildFragmentManager());
        mAdapter.setTitles("体系", "导航");
        mAdapter.setFragmentList(
                KnowledgeFragment.create(),
                NaviFragment.create()
        );
        vp.setAdapter(mAdapter);
        MagicIndicatorUtils.commonNavigator(mi, vp, mAdapter, new SimpleCallback<Integer>(){
            @Override
            public void onResult(Integer data) {
                notifyScrollTop(data);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected KnowledgeNavigationPresenter createPresenter() {
        return new KnowledgeNavigationPresenter(this, this, getActivity());
    }

    @Override
    public void scrollTop() {
        if (isAdded() && !isDetached()) {
            Fragment fragment = mAdapter.getItem(vp.getCurrentItem());
            if (fragment instanceof ScrollTop) {
                ScrollTop scrollTop = (ScrollTop) fragment;
                scrollTop.scrollTop();
            }
        }
    }

    private void notifyScrollTop(int pos) {
        long currClickTime = System.currentTimeMillis();
        if (lastClickPos == pos && currClickTime - lastClickTime <= Config.SCROLL_TOP_DOUBLE_CLICK_DELAY) {
            Fragment fragment = mAdapter.getItem(vp.getCurrentItem());
            if (fragment instanceof ScrollTop) {
                ScrollTop scrollTop = (ScrollTop) fragment;
                scrollTop.scrollTop();
            }
        }
        lastClickPos = pos;
        lastClickTime = currClickTime;
    }
}
