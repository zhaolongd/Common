package com.quick.common.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.PermissionInterceptor;
import com.just.agentweb.WebChromeClient;
import com.quick.common.R;
import com.quick.common.app.BaseActivity;
import com.quick.common.bean.main.ArticleBean;

import butterknife.BindView;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/23 0023 15:11
 */
public class WebActivity extends BaseActivity{
    @BindView(R.id.web_content)
    LinearLayout webContent;
    protected AgentWeb mAgentWeb;
    private int mArticleId = -1;
    private String mTitle = "";
    private String mAuthor = "";
    private String mUrl = "";

    public static void start(Context context, ArticleBean article) {
        int articleId = article.getOriginId() != 0 ? article.getOriginId() : article.getId();
        start(context, articleId, article.getTitle(), article.getLink(), article.isCollect());
    }

    public static void start(Context context, int articleId, String title, String url, boolean collected) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("articleId", articleId);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("collected", collected);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web, false);
        init();
    }

    private void init() {
        mArticleId = getIntent().getIntExtra("articleId", -1);
        mTitle = getIntent().getStringExtra("title");
        mTitle = mTitle == null ? "" : mTitle;
        mAuthor = getIntent().getStringExtra("author");
        mAuthor = mAuthor == null ? "" : mAuthor;
        mUrl = getIntent().getStringExtra("url");
        mUrl = mUrl == null ? "" : mUrl;
        setTitle(mTitle);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(webContent,new LinearLayout.LayoutParams(-1,-1))
                .useDefaultIndicator()  // 使用默认进度条
                .setWebChromeClient(mWebChromeClient)
                .setPermissionInterceptor(mPermissionInterceptor)
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    private com.just.agentweb.WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    };

    protected PermissionInterceptor mPermissionInterceptor = new PermissionInterceptor() {
        @Override
        public boolean intercept(String url, String[] permissions, String action) {
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
