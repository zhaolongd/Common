package com.quick.common.mvp.home;

import android.app.Activity;
import androidx.lifecycle.LifecycleOwner;
import rxhttp.wrapper.param.RxHttp;
import com.quick.common.app.BaseBean;
import com.quick.common.bean.kaiyan.CloumnsCard;
import com.quick.common.bean.kaiyan.CommunityColumnsCard;
import com.quick.common.bean.kaiyan.HorizontalScrollCard;
import com.quick.common.config.HttpConfig;
import com.quick.common.mvp.base.BasePresenter;
import com.quick.common.utils.GsonUtils;
import com.quick.core.rxhttp.OnError;
import com.rxjava.rxlife.RxLife;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2020/4/3 0003 15:56
 */
public class RecommendPresenter extends BasePresenter<RecommendView> {
    private String nextPageUrl = "";
    /**
     * 上拉刷新 true or 下拉加载 false
     */
    private boolean isRefresh = true;
    public RecommendPresenter(LifecycleOwner owner, RecommendView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }

    public void getRecommendData(boolean isRefresh) {
        RxHttp.get(isRefresh ? HttpConfig.KAIYAN_RECOMMEND : nextPageUrl)
            .asString()
            .as(RxLife.asOnMain(this))
            .subscribe(s -> {
                //成功回调
                parseJson(s);
            }, (OnError) error -> {
                //失败回调
            });
    }

    private void parseJson(String s)
    {
        List<BaseBean> viewModels = new ArrayList<>();
        JSONObject jsonObject = null;
        try
        {
            jsonObject = new JSONObject(s);
            nextPageUrl = jsonObject.optString("nextPageUrl", "");
            JSONArray itemList = jsonObject.optJSONArray("itemList");
            if (itemList != null)
            {
                for (int i = 0; i < itemList.length(); i++)
                {
                    JSONObject ccurrentObject = itemList.getJSONObject(i);
                    switch (ccurrentObject.optString("type"))
                    {
                        case "horizontalScrollCard":
                            HorizontalScrollCard scrollCard = GsonUtils
                                    .fromLocalJson(ccurrentObject.toString(),
                                            HorizontalScrollCard.class);
                            viewModels.add(scrollCard);
                            break;
                        case "communityColumnsCard":
                            CommunityColumnsCard communityColumnsCard =
                                    GsonUtils.fromLocalJson(
                                            ccurrentObject.toString(),
                                            CommunityColumnsCard.class);
                            parseCard(viewModels, communityColumnsCard);
                            break;
                        default:
                            break;
                    }
                }
                mvpView.getRecommendDataSuccess(viewModels);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    private void parseCard(List<BaseBean> viewModels,
                           CommunityColumnsCard columnsCard)
    {
        CloumnsCard cardViewModel = new CloumnsCard();
        if (columnsCard != null)
        {
            cardViewModel.coverUrl = columnsCard.getData()
                    .getContent()
                    .getData()
                    .getCover()
                    .getFeed();
            cardViewModel.description =
                    columnsCard.getData().getContent().getData().getDescription();
            cardViewModel.nickName = columnsCard.getData()
                    .getContent()
                    .getData()
                    .getOwner()
                    .getNickname();
            cardViewModel.avatarUrl = columnsCard.getData()
                    .getContent()
                    .getData()
                    .getOwner()
                    .getAvatar();
            cardViewModel.collectionCount = columnsCard.getData()
                    .getContent()
                    .getData()
                    .getConsumption()
                    .getCollectionCount();
            cardViewModel.imgWidth =
                    columnsCard.getData().getContent().getData().getWidth();
            cardViewModel.imgHeight =
                    columnsCard.getData().getContent().getData().getHeight();
            viewModels.add(cardViewModel);
        }
    }
}