package com.quick.common.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.quick.common.R;
import com.quick.common.app.BaseActivity;
import com.quick.common.ui.fragment.SearchHistoryFragment;
import com.quick.common.ui.fragment.SearchResultFragment;
import com.quick.core.utils.EditTextUtils;
import com.quick.core.utils.InputMethodUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarSearch;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/25 0025 15:08
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.abs)
    ActionBarSearch abs;
    @BindView(R.id.fl)
    FrameLayout fl;

    private SearchHistoryFragment mSearchHistoryFragment;
    private SearchResultFragment mSearchResultFragment;
    private FragmentManager mFragmentManager;

    private boolean mIsResultPage = false;

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search, false);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        abs.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsResultPage) {
                    showHistoryFragment();
                } else {
                    finish();
                }
            }
        });
        abs.setOnRightTextClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {
                String key = abs.getEditTextView().getText().toString();
                search(key);
            }
        });
        abs.getEditTextView().setSingleLine();
        abs.getEditTextView().setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        abs.getEditTextView().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    String key = abs.getEditTextView().getText().toString();
                    search(key);
                    return true;
                }
                return false;
            }
        });
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment searchHistoryFragment = mFragmentManager.findFragmentByTag(SearchHistoryFragment.class.getName());
        if (searchHistoryFragment == null) {
            mSearchHistoryFragment = SearchHistoryFragment.create();
            transaction.add(R.id.fl, mSearchHistoryFragment, SearchHistoryFragment.class.getName());
        } else {
            mSearchHistoryFragment = (SearchHistoryFragment) searchHistoryFragment;
        }
        Fragment searchResultFragment = mFragmentManager.findFragmentByTag(SearchResultFragment.class.getName());
        if (searchResultFragment == null) {
            mSearchResultFragment = SearchResultFragment.create();
            transaction.add(R.id.fl, mSearchResultFragment, SearchResultFragment.class.getName());
        } else {
            mSearchResultFragment = (SearchResultFragment) searchResultFragment;
        }
        transaction.show(mSearchHistoryFragment);
        transaction.hide(mSearchResultFragment);
        transaction.commit();
    }

    public void search(String key) {
        InputMethodUtils.hide(abs.getEditTextView());
        abs.getEditTextView().clearFocus();
        if (TextUtils.isEmpty(key)) {
            if (mIsResultPage) {
                showHistoryFragment();
            }
        } else {
            EditTextUtils.setTextWithSelection(abs.getEditTextView(), key);
            if (!mIsResultPage) {
                showResultFragment();
            }
            mSearchHistoryFragment.addHistory(key);
            mSearchResultFragment.search(key);
        }
    }

    @Override
    public void onBackPressed() {
        if (mIsResultPage) {
            showHistoryFragment();
        } else {
            super.onBackPressed();
        }
    }

    private void showHistoryFragment() {
        mIsResultPage = false;
        FragmentTransaction t = mFragmentManager.beginTransaction();
        t.hide(mSearchResultFragment);
        t.show(mSearchHistoryFragment);
        t.commit();
    }

    private void showResultFragment() {
        mIsResultPage = true;
        FragmentTransaction t = mFragmentManager.beginTransaction();
        t.hide(mSearchHistoryFragment);
        t.show(mSearchResultFragment);
        t.commit();
    }
}
