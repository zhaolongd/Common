package com.quick.common.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.quick.common.R;
import com.quick.common.config.Config;
import com.quick.common.ui.fragment.AttentionFragment;
import com.quick.common.ui.fragment.RecommendFragment;
import com.quick.common.utils.ScrollTop;
import com.quick.common.utils.TabLayoutUtils;
import com.quick.common.utils.lister.SimpleCallback;
import com.quick.core.adapter.FixedFragmentPagerAdapter;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhaolong.
 * Description: 开眼视频社区 推荐、关注
 * Date: 2020/4/3 0003 14:28
 */
public class CommunityActivity extends AppCompatActivity implements ScrollTop {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tl)
    TabLayout tl;
    @BindView(R.id.appbar_layout_toolbar)
    Toolbar appbarLayoutToolbar;
    @BindView(R.id.collapse_layout)
    CollapsingToolbarLayout collapseLayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    private FixedFragmentPagerAdapter mAdapter;
    private long lastClickTime = 0L;
    private int lastClickPos = 0;
    private String[] titles = new String[]{"推荐", "关注"};

    public static void start(Context context) {
        Intent intent = new Intent(context, CommunityActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ImmersionBar.with(this).titleBar(appbarLayoutToolbar).init();
        setSupportActionBar(appbarLayoutToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mAdapter = new FixedFragmentPagerAdapter(getSupportFragmentManager());
        mAdapter.setTitles(titles);
        mAdapter.setFragmentList(
                RecommendFragment.create(),
                AttentionFragment.create()
        );
        vp.setAdapter(mAdapter);
        TabLayoutUtils.initTabLayout(titles, tl, vp, new SimpleCallback<Integer>() {
            @Override
            public void onResult(Integer data) {
                notifyScrollTop(data);
            }
        });
        appbarLayoutToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void scrollTop() {
        Fragment fragment = mAdapter.getItem(vp.getCurrentItem());
        if (fragment instanceof ScrollTop) {
            ScrollTop scrollTop = (ScrollTop) fragment;
            scrollTop.scrollTop();
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
