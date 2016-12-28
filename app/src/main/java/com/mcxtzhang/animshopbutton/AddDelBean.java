package com.mcxtzhang.animshopbutton;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/22.
 */

public class AddDelBean {
    private int mCount;
    private int mMaxCount;

    public AddDelBean(int maxCount, int count) {
        mCount = count;
        mMaxCount = maxCount;
    }

    public int getCount() {
        return mCount;
    }

    public AddDelBean setCount(int count) {
        mCount = count;
        return this;
    }

    public int getMaxCount() {
        return mMaxCount;
    }

    public AddDelBean setMaxCount(int maxCount) {
        mMaxCount = maxCount;
        return this;
    }
}
