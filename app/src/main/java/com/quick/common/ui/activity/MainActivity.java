package com.quick.common.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.mmin18.widget.RealtimeBlurView;
import com.quick.common.R;
import com.quick.common.app.BaseActivity;
import com.quick.common.ui.fragment.HomeFragment;
import com.quick.common.ui.fragment.KnowledgeNavigationFragment;
import com.quick.common.ui.fragment.ProjectFragment;
import com.quick.common.ui.fragment.SquareFragment;
import com.quick.common.ui.fragment.WxFragment;
import com.quick.core.adapter.FixedFragmentPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import per.goweii.percentimageview.percentimageview.PercentImageView;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.vp_tab)
    ViewPager vpTab;
    @BindView(R.id.blurView)
    RealtimeBlurView blurView;
    @BindView(R.id.iv_bb_home)
    PercentImageView ivBbHome;
    @BindView(R.id.tv_bb_home)
    TextView tvBbHome;
    @BindView(R.id.ll_bb_home)
    LinearLayout llBbHome;
    @BindView(R.id.iv_bb_knowledge)
    PercentImageView ivBbKnowledge;
    @BindView(R.id.tv_bb_knowledge)
    TextView tvBbKnowledge;
    @BindView(R.id.ll_bb_knowledge)
    LinearLayout llBbKnowledge;
    @BindView(R.id.iv_bb_wechat)
    PercentImageView ivBbWechat;
    @BindView(R.id.tv_bb_wechat)
    TextView tvBbWechat;
    @BindView(R.id.ll_bb_wechat)
    LinearLayout llBbWechat;
    @BindView(R.id.iv_bb_project)
    PercentImageView ivBbProject;
    @BindView(R.id.tv_bb_project)
    TextView tvBbProject;
    @BindView(R.id.ll_bb_project)
    LinearLayout llBbProject;
    @BindView(R.id.iv_bb_square)
    PercentImageView ivBbSquare;
    @BindView(R.id.tv_bb_square)
    TextView tvBbSquare;
    @BindView(R.id.ll_bb_square)
    LinearLayout llBbSquare;
    @BindView(R.id.ll_bb)
    LinearLayout llBb;

    private FixedFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        vpTab.addOnPageChangeListener(this);
        vpTab.setOffscreenPageLimit(4);
        mPagerAdapter = new FixedFragmentPagerAdapter(getSupportFragmentManager());
        vpTab.setAdapter(mPagerAdapter);
        mPagerAdapter.setFragmentList(
                HomeFragment.create(),
                KnowledgeNavigationFragment.create(),
                WxFragment.create(),
                ProjectFragment.create(),
                SquareFragment.create()
        );
        vpTab.setCurrentItem(0);
        onPageSelected(vpTab.getCurrentItem());
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        ivBbHome.setColorFilter(ContextCompat.getColor(this, R.color.third));
        tvBbHome.setTextColor(ContextCompat.getColor(this, R.color.third));
        ivBbKnowledge.setColorFilter(ContextCompat.getColor(this, R.color.third));
        tvBbKnowledge.setTextColor(ContextCompat.getColor(this, R.color.third));
        ivBbWechat.setColorFilter(ContextCompat.getColor(this, R.color.third));
        tvBbWechat.setTextColor(ContextCompat.getColor(this, R.color.third));
        ivBbProject.setColorFilter(ContextCompat.getColor(this, R.color.third));
        tvBbProject.setTextColor(ContextCompat.getColor(this, R.color.third));
        ivBbSquare.setColorFilter(ContextCompat.getColor(this, R.color.third));
        tvBbSquare.setTextColor(ContextCompat.getColor(this, R.color.third));
        switch (i) {
            default:
                break;
            case 0:
                ivBbHome.setColorFilter(ContextCompat.getColor(this, R.color.main));
                tvBbHome.setTextColor(ContextCompat.getColor(this, R.color.main));
                break;
            case 1:
                ivBbKnowledge.setColorFilter(ContextCompat.getColor(this, R.color.main));
                tvBbKnowledge.setTextColor(ContextCompat.getColor(this, R.color.main));
                break;
            case 2:
                ivBbWechat.setColorFilter(ContextCompat.getColor(this, R.color.main));
                tvBbWechat.setTextColor(ContextCompat.getColor(this, R.color.main));
                break;
            case 3:
                ivBbProject.setColorFilter(ContextCompat.getColor(this, R.color.main));
                tvBbProject.setTextColor(ContextCompat.getColor(this, R.color.main));
                break;
            case 4:
                ivBbSquare.setColorFilter(ContextCompat.getColor(this, R.color.main));
                tvBbSquare.setTextColor(ContextCompat.getColor(this, R.color.main));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @OnClick({R.id.ll_bb_home, R.id.ll_bb_knowledge, R.id.ll_bb_wechat, R.id.ll_bb_project, R.id.ll_bb_square})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_bb_home:
                vpTab.setCurrentItem(0);
                break;
            case R.id.ll_bb_knowledge:
                vpTab.setCurrentItem(1);
                break;
            case R.id.ll_bb_wechat:
                vpTab.setCurrentItem(2);
                break;
            case R.id.ll_bb_project:
                vpTab.setCurrentItem(3);
                break;
            case R.id.ll_bb_square:
                vpTab.setCurrentItem(4);
                break;
        }
    }
}
