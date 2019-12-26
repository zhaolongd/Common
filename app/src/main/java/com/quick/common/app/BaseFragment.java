package com.quick.common.app;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import com.quick.common.config.HttpConfig;
import com.quick.common.config.KeyConfig;
import com.quick.core.base.CoreFragment;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/7 16:55
 */
public class BaseFragment extends CoreFragment implements KeyConfig, HttpConfig {

    public View root;

    public void jumpActivity(String activityUrl) {
        if (activityUrl == null || activityUrl.equals("")) {
            Toast.makeText(getActivity(),"功能暂未开放", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Class clz = Class.forName(activityUrl);
            startActivity(new Intent(getContext(), clz));
        } catch (ClassNotFoundException e) {
            Toast.makeText(getActivity(),"功能暂未开放", Toast.LENGTH_SHORT).show();
        }
    }

}
