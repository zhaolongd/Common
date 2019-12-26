package com.quick.common.event;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/24 0024 11:21
 */
public class ScrollTopEvent extends BaseEvent {
    private Class clazz;
    private int position;

    public ScrollTopEvent(Class clazz, int position) {
        this.clazz = clazz;
        this.position = position;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
