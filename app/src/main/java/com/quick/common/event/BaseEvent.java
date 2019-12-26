package com.quick.common.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/24 0024 11:20
 */
public class BaseEvent {
    public void post(){
        EventBus.getDefault().post(this);
    }
}
