package com.quick.common.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.quick.common.R;
import com.quick.common.app.BaseActivity;
import com.quick.common.ui.adapter.ScreenAutoAdapter;

/**
 * Created by zhaolong.
 * Description: 启动页
 * Date: 2020/4/14 0014 13:34
 */
public class SplashActivity extends BaseActivity {

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenAutoAdapter.match(this, 375.0f);
        setContentView(R.layout.activity_splash, false);
        ImmersionBar.with(this)
                .titleBar(findViewById(R.id.top_view))
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init();
        mHandler.postDelayed(this::startToMain, 3000);
    }

    private void startToMain() {
        jumpActivity(MainActivity.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //activity销毁时移除所有消息,防止内存泄漏
        mHandler.removeCallbacksAndMessages(null);
    }
}
