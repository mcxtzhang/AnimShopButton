package com.mcxtzhang.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 介绍：仿饿了么加减Button
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/18.
 */

public class AnimShopButton extends View {
    protected static final String TAG = "zxt/" + AnimShopButton.class.getName();
    //控件 paddingLeft paddingTop + paint的width
    protected int mLeft, mTop;
    //宽高
    protected int mWidth, mHeight;


    //加减的圆的Path的Region
    protected Region mAddRegion, mDelRegion;
    protected Path mAddPath, mDelPath;

    /**
     * 加按钮
     */
    protected Paint mAddPaint;
    //加按钮是否开启fill模式 默认是stroke(xml)
    protected boolean isAddFillMode;
    //加按钮的背景色前景色(xml)
    protected int mAddEnableBgColor;
    protected int mAddEnableFgColor;
    //加按钮不可用时的背景色前景色(xml)
    protected int mAddDisableBgColor;
    protected int mAddDisableFgColor;

    /**
     * 减按钮
     */
    protected Paint mDelPaint;
    //按钮是否开启fill模式 默认是stroke(xml)
    protected boolean isDelFillMode;
    //按钮的背景色前景色(xml)
    protected int mDelEnableBgColor;
    protected int mDelEnableFgColor;
    //按钮不可用时的背景色前景色(xml)
    protected int mDelDisableBgColor;
    protected int mDelDisableFgColor;

    //最大数量和当前数量(xml)
    protected int mMaxCount;
    protected int mCount;

    //圆的半径
    protected float mRadius;
    //圆圈的宽度
    protected float mCircleWidth;
    //线的宽度
    protected float mLineWidth;


    /**
     * 两个圆之间的间距(xml)
     */
    protected float mGapBetweenCircle;
    protected float mTextSize;
    protected Paint mTextPaint;
    protected Paint.FontMetrics mFontMetrics;

    //动画的基准值 动画：减 0~1, 加 1~0 ,
    // 普通状态下都显示时是0
    protected ValueAnimator mAnimAdd, mAniDel;
    protected float mAnimFraction;

    //展开 加入购物车动画
    protected ValueAnimator mAnimExpandHint;
    protected ValueAnimator mAnimReduceHint;

    //是否处于HintMode下 count = 0 时，且第一段收缩动画做完了，是true
    protected boolean isHintMode;

    //提示语收缩动画 0-1 展开1-0
    //普通模式时，应该是1， 只在 isHintMode true 才有效
    protected float mAnimExpandHintFraction;

    //展开动画结束后 才显示文字
    protected boolean isShowHintText;

    //hint文字 背景色前景色(xml) 大小
    protected Paint mHintPaint;
    protected int mHintBgColor;
    protected int mHingTextSize;
    protected String mHintText;
    protected int mHintFgColor;
    /**
     * 圆角值(xml)
     */
    protected int mHintBgRoundValue;

    //点击回调
    protected IOnAddDelListener mOnAddDelListener;

    public AnimShopButton(Context context) {
        this(context, null);
    }

    public AnimShopButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimShopButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public int getCount() {
        return mCount;
    }

    /**
     * 设置当前数量
     *
     * @param count
     * @return
     */
    public AnimShopButton setCount(int count) {
        mCount = count;
        //复用机制的处理
        if (mCount == 0) {
            if (mAnimAdd != null && mAnimAdd.isRunning()) {
                mAnimAdd.cancel();
            }
            if (mAniDel != null && mAniDel.isRunning()) {
                mAniDel.cancel();
            }
            // 0 不显示 数字和-号
            mAnimFraction = 1;
        } else {
            mAnimFraction = 0;
        }
        initHintSettings();
        return this;
    }

    public IOnAddDelListener getOnAddDelListener() {
        return mOnAddDelListener;
    }

    public int getMaxCount() {
        return mMaxCount;
    }

    /**
     * 设置最大数量
     *
     * @param maxCount
     * @return
     */
    public AnimShopButton setMaxCount(int maxCount) {
        mMaxCount = maxCount;
        return this;
    }

    /**
     * 设置加减监听器
     *
     * @param IOnAddDelListener
     * @return
     */
    public AnimShopButton setOnAddDelListener(IOnAddDelListener IOnAddDelListener) {
        mOnAddDelListener = IOnAddDelListener;
        return this;
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {

        //模拟参数传入
        mGapBetweenCircle = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 34, context.getResources().getDisplayMetrics());

        isAddFillMode = true;
        mAddEnableBgColor = 0xFFFFDC5B;
        mAddEnableFgColor = Color.BLACK;
        mAddDisableBgColor = 0xff979797;
        mAddDisableFgColor = Color.BLACK;

        isDelFillMode = false;
        mDelEnableBgColor = 0xff979797;
        mDelEnableFgColor = 0xff979797;
        mDelDisableBgColor = 0xff979797;
        mDelDisableFgColor = 0xff979797;

        mMaxCount = 4;
        mCount = 1;

        mHintText = "加入购物车";
        mHintBgColor = mAddEnableBgColor;
        mHintFgColor = mAddEnableFgColor;
        mHingTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, context.getResources().getDisplayMetrics());
        mHintBgRoundValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics());
        //end

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AnimShopButton, defStyleAttr, 0);
        int indexCount = ta.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = ta.getIndex(i);
            if (index == R.styleable.AnimShopButton_gapBetweenCircle) {
                mGapBetweenCircle = ta.getDimension(index, mGapBetweenCircle);
            }
        }

        ta.recycle();


        mAddRegion = new Region();
        mDelRegion = new Region();
        mAddPath = new Path();
        mDelPath = new Path();

        mAddPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (isAddFillMode) {
            mAddPaint.setStyle(Paint.Style.FILL);
        } else {
            mAddPaint.setStyle(Paint.Style.STROKE);
        }
        mDelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (isDelFillMode) {
            mDelPaint.setStyle(Paint.Style.FILL);
        } else {
            mDelPaint.setStyle(Paint.Style.STROKE);
        }

        mHintPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHintPaint.setStyle(Paint.Style.FILL);
        mHintPaint.setTextSize(mHingTextSize);

        mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12.5f, getResources().getDisplayMetrics());
        mCircleWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, getResources().getDisplayMetrics());
        mLineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, getResources().getDisplayMetrics());


        mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14.5f, getResources().getDisplayMetrics());
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mFontMetrics = mTextPaint.getFontMetrics();


        //动画 +
        mAnimAdd = ValueAnimator.ofFloat(1, 0);
        mAnimAdd.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimFraction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimAdd.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
        mAnimAdd.setDuration(350);

        //提示语收缩动画 0-1
        mAnimReduceHint = ValueAnimator.ofFloat(0, 1);
        mAnimReduceHint.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimExpandHintFraction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimReduceHint.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mCount == 1) {
                    //然后底色也不显示了
                    isHintMode = false;
                }
                if (mCount == 1) {
                    Log.d(TAG, "现在还是1 开始收缩动画");
                    if (mAnimAdd != null && !mAnimAdd.isRunning()) {
                        mAnimAdd.start();
                    }
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (mCount == 1) {
                    //先不显示文字了
                    isShowHintText = false;
                }
            }
        });
        mAnimReduceHint.setDuration(350);


        //动画 -
        mAniDel = ValueAnimator.ofFloat(0, 1);
        mAniDel.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimFraction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        //1-0的动画
        mAniDel.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mCount == 0) {
                    Log.d(TAG, "现在还是0onAnimationEnd() called with: animation = [" + animation + "]");
                    if (mAnimExpandHint != null && !mAnimExpandHint.isRunning()) {
                        mAnimExpandHint.start();
                    }
                }
            }
        });
        mAniDel.setDuration(350);
        //提示语展开动画
        //分析这个动画，最初是个圆。 就是left 不断减小
        mAnimExpandHint = ValueAnimator.ofFloat(1, 0);
        mAnimExpandHint.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimExpandHintFraction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimExpandHint.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mCount == 0) {
                    isShowHintText = true;
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (mCount == 0) {
                    isHintMode = true;
                }
            }
        });
        mAnimExpandHint.setDuration(350);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                int computeSize = (int) (getPaddingLeft() + mRadius * 2 +/* mGap * 2 + mTextPaint.measureText(mCount + "")*/mGapBetweenCircle + mRadius * 2 + getPaddingRight() + mCircleWidth * 2);
                wSize = computeSize < wSize ? computeSize : wSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                computeSize = (int) (getPaddingLeft() + mRadius * 2 + /*mGap * 2 + mTextPaint.measureText(mCount + "")*/mGapBetweenCircle + mRadius * 2 + getPaddingRight() + mCircleWidth * 2);
                wSize = computeSize;
                break;
        }
        switch (hMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                int computeSize = (int) (getPaddingTop() + mRadius * 2 + getPaddingBottom() + mCircleWidth * 2);
                hSize = computeSize < hSize ? computeSize : hSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                computeSize = (int) (getPaddingTop() + mRadius * 2 + getPaddingBottom() + mCircleWidth * 2);
                hSize = computeSize;
                break;
        }


        setMeasuredDimension(wSize, hSize);

        //复用时会走这里，所以初始化一些UI显示的参数
        mAnimFraction = 0;
        initHintSettings();
    }

    /**
     * 根据当前count数量 初始化 hint提示语相关变量
     */
    private void initHintSettings() {
        if (mCount == 0) {
            isHintMode = true;
            isShowHintText = true;
            mAnimExpandHintFraction = 0;
        } else {
            isHintMode = false;
            isShowHintText = false;
            mAnimExpandHintFraction = 1;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLeft = (int) (getPaddingLeft() + mCircleWidth);
        mTop = (int) (getPaddingTop() + mCircleWidth);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isHintMode) {
            //add hint 展开动画
            //if (mCount == 0) {
            //背景
            mHintPaint.setColor(mHintBgColor);
            RectF rectF = new RectF((mWidth - mRadius * 2) * mAnimExpandHintFraction, 0, getWidth(), getHeight());
            canvas.drawRoundRect(rectF, mHintBgRoundValue, mHintBgRoundValue, mHintPaint);
            if (isShowHintText) {
                //前景文字
                mHintPaint.setColor(mHintFgColor);
                // 计算Baseline绘制的起点X轴坐标
                int baseX = (int) (mWidth / 2 - mHintPaint.measureText(mHintText) / 2);
                // 计算Baseline绘制的Y坐标
                int baseY = (int) ((mHeight / 2) - ((mHintPaint.descent() + mHintPaint.ascent()) / 2));
                canvas.drawText(mHintText, baseX, baseY, mHintPaint);
            }
            //}
        } else {

            //动画 mAnimFraction ：减 0~1, 加 1~0 ,
            //动画位移Max,
            float animOffsetMax = (mRadius * 2 + /*mGap * 2 + mTextPaint.measureText(mCount + "")*/mGapBetweenCircle);
            //透明度动画的基准
            int animAlphaMax = 255;
            int animRotateMax = 360;

            //左边
            //背景 圆
            if (mCount > 0) {
                mDelPaint.setColor(mDelEnableBgColor);
            } else {
                mDelPaint.setColor(mDelDisableBgColor);
            }
            mDelPaint.setAlpha((int) (animAlphaMax * (1 - mAnimFraction)));

            mDelPaint.setStrokeWidth(mCircleWidth);
            mDelPath.reset();
            mDelPath.addCircle(animOffsetMax * mAnimFraction + mLeft + mRadius, mTop + mRadius, mRadius, Path.Direction.CW);
            mDelRegion.setPath(mDelPath, new Region(mLeft, mTop, mWidth - getPaddingRight(), mHeight - getPaddingBottom()));
            //canvas.drawCircle(mAnimOffset + mLeft + mRadius, mTop + mRadius, mRadius, mPaint);
            canvas.drawPath(mDelPath, mDelPaint);

            //前景 +
            if (mCount > 0) {
                mDelPaint.setColor(mDelEnableFgColor);
            } else {
                mDelPaint.setColor(mDelDisableFgColor);
            }
            mDelPaint.setStrokeWidth(mLineWidth);
            //旋转动画
            canvas.save();
            canvas.translate(animOffsetMax * mAnimFraction + mLeft + mRadius, mTop + mRadius);
            canvas.rotate((int) (animRotateMax * (1 - mAnimFraction)));
        /*canvas.drawLine(mAnimOffset + mLeft + mRadius / 2, mTop + mRadius,
                mAnimOffset + mLeft + mRadius / 2 + mRadius, mTop + mRadius,
                mPaint);*/
            canvas.drawLine(-mRadius / 2, 0,
                    +mRadius / 2, 0,
                    mDelPaint);
            canvas.restore();


            //数量
            canvas.save();
            //平移动画
            canvas.translate(mAnimFraction * (/*mGap*/mGapBetweenCircle / 2 - mTextPaint.measureText(mCount + "") / 2 + mRadius), 0);
            //旋转动画,旋转中心点，x 是绘图中心,y 是控件中心
            canvas.rotate(360 * mAnimFraction,
                /*mGap*/ mGapBetweenCircle / 2 + mLeft + mRadius * 2 /*+ mTextPaint.measureText(mCount + "") / 2*/,
                    mTop + mRadius);
            //透明度动画
            mTextPaint.setAlpha((int) (255 * (1 - mAnimFraction)));
            //是没有动画的普通写法,x left, y baseLine
            canvas.drawText(mCount + "", /*mGap*/ mGapBetweenCircle / 2 - mTextPaint.measureText(mCount + "") / 2 + mLeft + mRadius * 2, mTop + mRadius - (mFontMetrics.top + mFontMetrics.bottom) / 2, mTextPaint);
            canvas.restore();

            //右边
            //背景 圆
            if (mCount < mMaxCount) {
                mAddPaint.setColor(mAddEnableBgColor);
            } else {
                mAddPaint.setColor(mAddDisableBgColor);
            }
            mAddPaint.setStrokeWidth(mCircleWidth);
            float left = mLeft + mRadius * 2 + /*mGap * 2 + mTextPaint.measureText(mCount + "")*/ mGapBetweenCircle;
            mAddPath.reset();
            mAddPath.addCircle(left + mRadius, mTop + mRadius, mRadius, Path.Direction.CW);
            mAddRegion.setPath(mAddPath, new Region(mLeft, mTop, mWidth - getPaddingRight(), mHeight - getPaddingBottom()));
            //canvas.drawCircle(left + mRadius, mTop + mRadius, mRadius, mPaint);
            canvas.drawPath(mAddPath, mAddPaint);
            //前景 +
            if (mCount < mMaxCount) {
                mAddPaint.setColor(mAddEnableFgColor);
            } else {
                mAddPaint.setColor(mAddDisableFgColor);
            }
            mAddPaint.setStrokeWidth(mLineWidth);
            canvas.drawLine(left + mRadius / 2, mTop + mRadius, left + mRadius / 2 + mRadius, mTop + mRadius, mAddPaint);
            canvas.drawLine(left + mRadius, mTop + mRadius / 2, left + mRadius, mTop + mRadius / 2 + mRadius, mAddPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //文字模式
                if (isHintMode) {
                    onAddClick();
                    return true;
                } else {
                    if (mAddRegion.contains((int) event.getX(), (int) event.getY())) {
                        onAddClick();
                        return true;
                    } else if (mDelRegion.contains((int) event.getX(), (int) event.getY())) {
                        onDelClick();
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }


        return super.onTouchEvent(event);

    }

    protected void onDelClick() {
        if (mCount > 0) {
            mCount--;
            onCountDelListener();
            if (null != mOnAddDelListener) {
                mOnAddDelListener.onDelSuccess(mCount);
            }
        } else {
            if (null != mOnAddDelListener) {
                mOnAddDelListener.onDelFaild(mCount, IOnAddDelListener.FailType.COUNT_MIN);
            }
        }

    }

    protected void onAddClick() {
        if (mCount < mMaxCount) {
            mCount++;
            onCountAddListener();
            if (null != mOnAddDelListener) {
                mOnAddDelListener.onAddSuccess(mCount);
            }
        } else {
            if (null != mOnAddDelListener) {
                mOnAddDelListener.onAddFailed(mCount, IOnAddDelListener.FailType.COUNT_MAX);
            }
        }
    }

    protected void onCountAddListener() {
        if (mCount == 1) {
            if (mAniDel != null && mAniDel.isRunning()) {
                mAniDel.cancel();
            }
            mAnimReduceHint.start();
        } else {
            mAnimFraction = 0;
            invalidate();
        }
    }

    protected void onCountDelListener() {
        if (mCount == 0) {
            if (mAnimAdd != null && mAnimAdd.isRunning()) {
                mAnimAdd.cancel();
            }
            mAniDel.start();
        } else {
            mAnimFraction = 0;
            invalidate();
        }
    }


}
