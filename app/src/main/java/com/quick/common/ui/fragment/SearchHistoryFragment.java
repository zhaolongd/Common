package com.quick.common.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.quick.common.R;
import com.quick.common.app.MvpFragment;
import com.quick.common.bean.home.HotKeyBean;
import com.quick.common.mvp.home.SearchHistoryPresenter;
import com.quick.common.mvp.home.SearchHistoryView;
import com.quick.common.ui.activity.home.SearchActivity;
import com.quick.common.utils.SettingUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/25 0025 15:16
 */
public class SearchHistoryFragment extends MvpFragment<SearchHistoryPresenter> implements SearchHistoryView {
    Unbinder unbinder;
    @BindView(R.id.rv_hot)
    RecyclerView rvHot;
    @BindView(R.id.tv_down)
    TextView tvDown;
    @BindView(R.id.tv_clean)
    TextView tvClean;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;

    private BaseQuickAdapter<HotKeyBean, BaseViewHolder> mHotAdapter;
    private BaseQuickAdapter<String, BaseViewHolder> mHistoryAdapter;

    private boolean mRemoveMode = false;
    private boolean mRemoveModeChanging = false;

    public static SearchHistoryFragment create() {
        return new SearchHistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search_history, null);
        unbinder = ButterKnife.bind(this, root);
        init();
        return root;
    }

    private void init() {
        rvHot.setNestedScrollingEnabled(false);
        rvHot.setHasFixedSize(true);
        rvHot.setLayoutManager(new FlexboxLayoutManager(getContext()));
        mHotAdapter = new BaseQuickAdapter<HotKeyBean, BaseViewHolder>(R.layout.rv_item_search_hot) {
            @Override
            protected void convert(BaseViewHolder helper, HotKeyBean item) {
                helper.setText(R.id.tv_key, item.getName());
            }
        };
        mHotAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HotKeyBean item = mHotAdapter.getItem(position);
                if (item != null) {
                    search(item.getName());
                }
            }
        });
        rvHot.setAdapter(mHotAdapter);
        rvHistory.setLayoutManager(new FlexboxLayoutManager(getContext()));
        mHistoryAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_item_search_history) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_key, item);
//                helper.addOnClickListener(R.id.iv_remove);
                ImageView iv_remove = helper.getView(R.id.iv_remove);
                if (!mRemoveModeChanging) {
                    helper.setVisible(R.id.iv_remove, mRemoveMode);
                } else {
                    if (mRemoveMode) {
                        ScaleAnimation scaleAnimation = new ScaleAnimation(
                                0F, 1F, 0F, 1F,
                                Animation.RELATIVE_TO_SELF, 0.5F,
                                Animation.RELATIVE_TO_SELF, 0.5F
                        );
                        scaleAnimation.setDuration(300);
                        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                iv_remove.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mRemoveModeChanging = false;
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        iv_remove.startAnimation(scaleAnimation);
                    } else {
                        ScaleAnimation scaleAnimation = new ScaleAnimation(
                                1F, 0F, 1F, 0F,
                                Animation.RELATIVE_TO_SELF, 0.5F,
                                Animation.RELATIVE_TO_SELF, 0.5F
                        );
                        scaleAnimation.setDuration(300);
                        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mRemoveModeChanging = false;
                                iv_remove.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        iv_remove.startAnimation(scaleAnimation);
                    }
                }
            }
        };
        mHistoryAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                changeRemoveMode(!mRemoveMode);
                return true;
            }
        });
        mHistoryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mRemoveMode) {
                    String key = mHistoryAdapter.getItem(position);
                    search(key);
                }
            }
        });
        mHistoryAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mHistoryAdapter.remove(position);
                mvpPresenter.saveHistory(mHistoryAdapter.getData());
            }
        });
        rvHistory.setAdapter(mHistoryAdapter);
        mvpPresenter.getHotKeyList();
        mHistoryAdapter.setNewData(mvpPresenter.getHistory());
        changeHistoryVisible();
    }

    private void changeHistoryVisible() {
        if (mHistoryAdapter == null) {
            llHistory.setVisibility(View.GONE);
        } else {
            if (mHistoryAdapter.getData().isEmpty()) {
                llHistory.setVisibility(View.GONE);
            } else {
                llHistory.setVisibility(View.VISIBLE);
            }
        }
    }

    private void changeRemoveMode(boolean removeMode) {
        if (mRemoveMode == removeMode) {
            return;
        }
        mRemoveModeChanging = true;
        mRemoveMode = removeMode;
        mHistoryAdapter.notifyDataSetChanged();
        if (removeMode) {
            tvDown.setVisibility(View.VISIBLE);
            tvClean.setVisibility(View.GONE);
        } else {
            tvDown.setVisibility(View.GONE);
            tvClean.setVisibility(View.VISIBLE);
        }
    }

    private void search(String key) {
        if (getActivity() instanceof SearchActivity) {
            SearchActivity activity = (SearchActivity) getActivity();
            activity.search(key);
        }
    }

    public void addHistory(String key) {
        List<String> datas = mHistoryAdapter.getData();
        int index = datas.indexOf(key);
        if (index == 0) {
            return;
        }
        if (index > 0) {
            mHistoryAdapter.remove(index);
        }
        mHistoryAdapter.addData(0, key);
        int max = SettingUtils.getInstance().getSearchHistoryMaxCount();
        List<String> list = mHistoryAdapter.getData();
        if (list.size() > max) {
            mHistoryAdapter.remove(list.size() - 1);
        }
//        RvScrollTopUtils.smoothScrollTop(rvHistory);
        mvpPresenter.saveHistory(mHistoryAdapter.getData());
        changeHistoryVisible();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected SearchHistoryPresenter createPresenter() {
        return new SearchHistoryPresenter(this, this, getActivity());
    }

    @Override
    public void getHotKeyListSuccess(List<HotKeyBean> data) {
        mHotAdapter.setNewData(data);
    }
}
