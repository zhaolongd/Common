package com.quick.common.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quick.core.utils.SPUtils;

import java.util.List;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/25 0025 15:41
 */
public class SearchHistoryUtils {
    private static final String SP_NAME = "search_history";
    private static final String KEY_HISTORY = "KEY_HISTORY";

    private final SPUtils mSPUtils = SPUtils.newInstance(SP_NAME);
    private final Gson mGson = new Gson();

    public static SearchHistoryUtils newInstance() {
        return new SearchHistoryUtils();
    }

    private SearchHistoryUtils() {
    }

    public void save(List<String> historys) {
        if (historys == null || historys.isEmpty()) {
            mSPUtils.clear();
            return;
        }
        String json = mGson.toJson(historys);
        mSPUtils.save(KEY_HISTORY, json);
    }

    public List<String> get() {
        String json = mSPUtils.get(KEY_HISTORY, "");
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            return mGson.fromJson(json, new TypeToken<List<String>>(){}.getType());
        } catch (Exception e){
            mSPUtils.clear();
            return null;
        }
    }
}
