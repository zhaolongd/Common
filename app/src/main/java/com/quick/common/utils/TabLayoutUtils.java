package com.quick.common.utils;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import com.quick.common.utils.lister.SimpleCallback;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2020/4/7 0007 14:29
 */
public class TabLayoutUtils {
    public static void initTabLayout (String[] titles, TabLayout tl, ViewPager vp, SimpleCallback<Integer> onClickCallback){
        for(int i = 0; i < titles.length; i++){
            TabLayout.Tab tab= tl.newTab();
            tab.setTag(i);
            tab.setText(titles[i]);
            tl.addTab(tab);
        }
        tl.setupWithViewPager(vp,false);
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (onClickCallback != null) {
                    onClickCallback.onResult(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
