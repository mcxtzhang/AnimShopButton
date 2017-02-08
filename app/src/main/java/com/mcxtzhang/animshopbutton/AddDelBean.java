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
    private String name;
    private boolean isReplenish;

    public AddDelBean(int count, int maxCount, boolean isReplenish) {
        this(maxCount, count);
        this.isReplenish = isReplenish;
    }

    public boolean isReplenish() {
        return isReplenish;
    }

    public AddDelBean setReplenish(boolean replenish) {
        isReplenish = replenish;
        return this;
    }

    public AddDelBean(int maxCount, int count) {
        mCount = count;
        mMaxCount = maxCount;
        setName("第:" + count + "个");
    }

    public String getName() {
        return name;
    }

    public AddDelBean setName(String name) {
        this.name = name;
        return this;
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
