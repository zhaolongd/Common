package com.quick.common.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.quick.common.R;
import com.quick.common.config.HttpConfig;
import com.quick.common.config.KeyConfig;
import com.quick.core.base.CoreActivity;
import com.quick.core.widget.LoadingDialog;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/6 17:17
 */
public class BaseActivity extends CoreActivity implements HttpConfig, KeyConfig {

    private boolean isScroll = true;
    public ImageView baseLeft, baseRight;
    public TextView baseTitle, baseRightText;
    public ViewGroup root, baseTitleLayout;
    public FrameLayout contentView;
    public View topMod, leftView, rightView;
    private LoadingDialog loadingDialog;
    private Unbinder unbinder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setContentView(View view, boolean isScroll) {
        this.isScroll = isScroll;
        setContentView(view);
    }

    public void setContentView(int layoutResID, boolean isScroll) {
        this.isScroll = isScroll;
        setContentView(layoutResID);
    }

    public void setContentViewNative(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        root = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        topMod = root.findViewById(R.id.top_mod);
        leftView = root.findViewById(R.id.left_view);
        rightView = root.findViewById(R.id.right_view);
        if (isScroll) {
            contentView = root.findViewById(R.id.base_scroll_content);
            contentView.setVisibility(View.VISIBLE);
        } else {
            contentView = root.findViewById(R.id.base_content);
            contentView.setVisibility(View.VISIBLE);
        }
        contentView.addView(view);
        initTitle();
        super.setContentView(root);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        root = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        topMod = root.findViewById(R.id.top_mod);
        leftView = root.findViewById(R.id.left_view);
        rightView = root.findViewById(R.id.right_view);
        if (isScroll) {
            contentView = root.findViewById(R.id.base_scroll_content);
            NestedScrollView scrollView = root.findViewById(R.id.base_scroll);
            scrollView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.VISIBLE);
        } else {
            contentView = root.findViewById(R.id.base_content);
            contentView.setVisibility(View.VISIBLE);
        }
        contentView.addView(LayoutInflater.from(this).inflate(layoutResID, null));
        initTitle();
        super.setContentView(root);
        unbinder = ButterKnife.bind(this);
    }

    private void initTitle() {
        if (root == null) {
            return;
        }
        baseLeft = root.findViewById(R.id.base_left);
        baseTitle = root.findViewById(R.id.base_title);
        baseRight = root.findViewById(R.id.base_right);
        baseRightText = root.findViewById(R.id.base_right_text);
        baseTitleLayout = root.findViewById(R.id.base_title_layout);
        baseLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTitle(int titleRid) {
        setTitle(getString(titleRid));
    }

    public void setTitle(String title) {
        if (baseTitle != null) {
            baseTitle.setText(title);
            baseTitleLayout.setVisibility(View.VISIBLE);
            topMod.setVisibility(View.VISIBLE);
        }
    }

    public void setRight(int icon, View.OnClickListener clickListener) {
        baseRight.setImageResource(icon);
        baseRight.setOnClickListener(clickListener);
        baseRight.setVisibility(View.VISIBLE);
    }

    public void setRight(String rightText, View.OnClickListener clickListener) {
        baseRightText.setText(rightText);
        baseRightText.setTextSize(16);
        baseRightText.setTextColor(getResources().getColor(R.color.colorAccent));
        baseRightText.setOnClickListener(clickListener);
        baseRightText.setVisibility(View.VISIBLE);
    }

    public void setLeft(int icon, View.OnClickListener clickListener) {
        baseLeft.setImageResource(icon);
        baseLeft.setOnClickListener(clickListener);
        baseLeft.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoad() {
        if (this.isFinishing()) {
            return;
        }
        handler.removeCallbacks(showLoading);
        loadingDialog.show();
    }

    Handler handler = new Handler();
    Thread showLoading = new Thread() {
        @Override
        public void run() {
            super.run();
            loadingDialog.dismiss();
        }
    };

    @Override
    public void hintLoad() {
        if (this.isFinishing()) {
            return;
        }
        //延迟100毫秒关闭，避免连续请求 出现闪屏情况
        handler.postDelayed(showLoading, 300);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if(loadingDialog != null){
            loadingDialog.dismiss();
        }
    }
}
