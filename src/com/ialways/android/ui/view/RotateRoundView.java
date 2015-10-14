package com.ialways.android.ui.view;

import com.ialways.android.R;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

public class RotateRoundView extends RelativeLayout {

    private Context mCtx;
    private LayoutInflater mInflater;
    private TypedArray mAttributes;

    private RelativeLayout mLeftView;
    private RelativeLayout mRightView;
    private RelativeLayout mCenterView;

    private int rotateAnimDuration;
    private AnimationSet mLeftAnimator;
    private AnimationSet mRightAnimator;
    private AnimationSet mCenterAnimator;

    private GestureDetector mGestureDetector;

    private int mLeftTvColor;
    private int mRightTvColor;
    private int mCenterTvColor;
    private String mLeftTvContent;
    private String mRightTvContent;
    private String mCenterTvContent;
    private float mLeftTvSize;
    private float mRightTvSize;
    private float mCenterTvSize;
    private float mMaxRadius;
    private float mScale;
    private int mLeftColor;
    private int mRightColor;
    private int mCenterColor;
    private int mAnimDuration;

    private float leftX;
    private float centerX;
    private float rightX;
    
    private boolean hasNum = false;
    
    private int i = 0;
    
    private RelativeLayout.LayoutParams mLeftLayout;
    private RelativeLayout.LayoutParams mRightLayout;
    private RelativeLayout.LayoutParams mCenterLayout;
    
    private Handler mHander = new Handler() {
        
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 0:
                mRightView.bringToFront();
                break;
            case 1:
                mLeftView.bringToFront();
                break;
            case 2:
                mCenterView.bringToFront();
                break;
            }
        }
    };

    public RotateRoundView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public RotateRoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public RotateRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub

        this.mCtx = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mInflater.inflate(R.layout.rotate_round_view, this);
        mAttributes = mCtx.obtainStyledAttributes(attrs, R.styleable.RotateRoundView, defStyleAttr, 0);
        parseData();
        initView();
        initListener();
    }

    //    public RotateRoundView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    //        super(context, attrs, defStyleAttr, defStyleRes);
    //        // TODO Auto-generated constructor stub
    //
    //        this.mCtx = context;
    //        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    //        this.mInflater.inflate(R.layout.rotate_round_view, this);
    //        mAttributes = mCtx.obtainStyledAttributes(attrs, R.styleable.RotateRoundView, defStyleAttr, defStyleRes);
    //        parseData();
    //        initView();
    //        initListener();
    //    }

    private void parseData() {

        if (mAttributes != null) {

            mLeftTvColor = mAttributes.getColor(R.styleable.RotateRoundView_left_tv_color, -1);
            mRightTvColor = mAttributes.getColor(R.styleable.RotateRoundView_right_tv_color, -1);
            mCenterTvColor = mAttributes.getColor(R.styleable.RotateRoundView_center_tv_color, -1);

            mLeftTvContent = mAttributes.getString(R.styleable.RotateRoundView_left_tv_content);
            mRightTvContent = mAttributes.getString(R.styleable.RotateRoundView_right_tv_content);
            mCenterTvContent = mAttributes.getString(R.styleable.RotateRoundView_center_tv_content);

            mLeftTvSize = mAttributes.getDimension(R.styleable.RotateRoundView_left_tv_size, -1);
            mRightTvSize = mAttributes.getDimension(R.styleable.RotateRoundView_right_tv_size, -1);
            mCenterTvSize = mAttributes.getDimension(R.styleable.RotateRoundView_center_tv_size, -1);

            mMaxRadius = mAttributes.getDimension(R.styleable.RotateRoundView_max_radius, -1);
            mScale = mAttributes.getFloat(R.styleable.RotateRoundView_zoom_scale, 0f);

            mLeftColor = mAttributes.getColor(R.styleable.RotateRoundView_left_color, -1);
            mRightColor = mAttributes.getColor(R.styleable.RotateRoundView_right_color, -1);
            mCenterColor = mAttributes.getColor(R.styleable.RotateRoundView_center_color, -1);

            mAnimDuration = mAttributes.getInt(R.styleable.RotateRoundView_anim_duration, 10);

            mAttributes.recycle();
        }
    }

    private void initView() {
        this.mLeftView = (RelativeLayout) findViewById(R.id.left_view_container);
        this.mRightView = (RelativeLayout) findViewById(R.id.right_view_container);
        this.mCenterView = (RelativeLayout) findViewById(R.id.center_view_container);

        int minRadius = (int) (mMaxRadius * mScale);

        mLeftLayout = (LayoutParams) mLeftView.getLayoutParams();
        mLeftLayout.width = minRadius;
        mLeftLayout.height = minRadius;
        this.mLeftView.setLayoutParams(mLeftLayout);

        mRightLayout = (LayoutParams) mRightView.getLayoutParams();
        mRightLayout.width = minRadius;
        mRightLayout.height = minRadius;
        this.mRightView.setLayoutParams(mRightLayout);

        mCenterLayout = (LayoutParams) mCenterView.getLayoutParams();
        mCenterLayout.width = (int) mMaxRadius;
        mCenterLayout.height = (int) mMaxRadius;
        this.mCenterView.setLayoutParams(mCenterLayout);

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasWindowFocus);

        if (!hasNum) {
            this.leftX = mLeftView.getLeft();
            this.rightX = mRightView.getLeft();
            this.centerX = mCenterView.getLeft();
            hasNum = true;
        }
    }

    private void initListener() {
        
    }

    private void startAnimator() {

        int index = i++;
        Animation rightScaleAnimation = null;
        Animation rightTranslateAnimation = null; // 移动
        
        Animation leftScaleAnimation = null;
        Animation leftTranslateAnimation = null; // 移动
        
        Animation centerScaleAnimation = null;
        Animation centerTranslateAnimation = null; // 移动
        
        
        if (index % 3 == 0) {
            leftTranslateAnimation = new TranslateAnimation(0, rightX - leftX, 0, 0); // 移动
            leftScaleAnimation = new ScaleAnimation(1f, 1f, 1f, 1f,// 整个屏幕就0.0到1.0的大小//缩放
                    Animation.INFINITE, 0.5f, Animation.INFINITE, 0.5f);
            
            rightScaleAnimation = new ScaleAnimation(1f, 1 / mScale, 1f, 1 / mScale,// 整个屏幕就0.0到1.0的大小//缩放
                    Animation.INFINITE, 0.5f, Animation.INFINITE, 0.5f);
            rightTranslateAnimation = new TranslateAnimation(0, centerX - rightX, 0, -(mMaxRadius - mMaxRadius * mScale) / 2); // 移动
            
            centerScaleAnimation = new ScaleAnimation(1f, mScale, 1f,
                    mScale,// 整个屏幕就0.0到1.0的大小//缩放
                    Animation.INFINITE, 0.5f,
                    Animation.INFINITE, 0.5f);
            centerTranslateAnimation = new TranslateAnimation(0, leftX - centerX, 0, (mMaxRadius - mMaxRadius * mScale) / 2); // 移动

            mHander.sendEmptyMessageDelayed(0, mAnimDuration / 2);
        } else if (index % 3 == 1) {
            leftTranslateAnimation = new TranslateAnimation(rightX - leftX, centerX - leftX, 0, -(mMaxRadius - mMaxRadius * mScale) / 2); // 移动
            leftScaleAnimation = new ScaleAnimation(1f, 1 / mScale, 1f, 1 / mScale,// 整个屏幕就0.0到1.0的大小//缩放
                    Animation.INFINITE, 0.5f, Animation.INFINITE, 0.5f);
            
            rightScaleAnimation = new ScaleAnimation(1 / mScale, 1f, 1 / mScale, 1f,// 整个屏幕就0.0到1.0的大小//缩放
                    Animation.INFINITE, 0.5f, Animation.INFINITE, 0.5f);
            rightTranslateAnimation = new TranslateAnimation(centerX - rightX, leftX - rightX, -(mMaxRadius - mMaxRadius * mScale) / 2, 0); // 移动
            
            centerScaleAnimation = new ScaleAnimation(mScale, mScale, mScale,
                    mScale,// 整个屏幕就0.0到1.0的大小//缩放
                    Animation.INFINITE, 0.5f,
                    Animation.INFINITE, 0.5f);
            centerTranslateAnimation = new TranslateAnimation(leftX - centerX, rightX - centerX, (mMaxRadius - mMaxRadius * mScale) / 2, (mMaxRadius - mMaxRadius * mScale) / 2); // 移动
            
            mHander.sendEmptyMessageDelayed(1, mAnimDuration / 2);
        } else if (index % 3 == 2) {
            leftTranslateAnimation = new TranslateAnimation(centerX - leftX, 0, -(mMaxRadius - mMaxRadius * mScale) / 2, 0); // 移动
            leftScaleAnimation = new ScaleAnimation(1 / mScale, 1f, 1 / mScale, 1f,// 整个屏幕就0.0到1.0的大小//缩放
                    Animation.INFINITE, 0.5f, Animation.INFINITE, 0.5f);
            
            
            rightScaleAnimation = new ScaleAnimation(1f, 1f, 1f, 1f,// 整个屏幕就0.0到1.0的大小//缩放
                    Animation.INFINITE, 0.5f, Animation.INFINITE, 0.5f);
            rightTranslateAnimation = new TranslateAnimation(leftX - rightX, 0, 0, 0); // 移动
            
            centerScaleAnimation = new ScaleAnimation(mScale, 1f, mScale,
                    1f,// 整个屏幕就0.0到1.0的大小//缩放
                    Animation.INFINITE, 0.5f,
                    Animation.INFINITE, 0.5f);
            centerTranslateAnimation = new TranslateAnimation(rightX - centerX, 0, (mMaxRadius - mMaxRadius * mScale) / 2, 0); // 移动
            
            mHander.sendEmptyMessageDelayed(2, mAnimDuration / 2);
        }
        
        leftScaleAnimation.setDuration(mAnimDuration);
        leftScaleAnimation.setFillAfter(true);
        leftTranslateAnimation.setDuration(mAnimDuration);
        mLeftAnimator = new AnimationSet(false);
        mLeftAnimator.addAnimation(leftScaleAnimation);
        mLeftAnimator.setFillAfter(true);
        mLeftAnimator.addAnimation(leftTranslateAnimation);
        mLeftView.startAnimation(mLeftAnimator);
        
        rightScaleAnimation.setDuration(mAnimDuration);
        rightScaleAnimation.setFillAfter(true);
        rightTranslateAnimation.setDuration(mAnimDuration);
        mRightAnimator = new AnimationSet(false);
        mRightAnimator.addAnimation(rightScaleAnimation);
        mRightAnimator.setFillAfter(true);
        mRightAnimator.addAnimation(rightTranslateAnimation);
        mRightView.startAnimation(mRightAnimator);
        
        centerScaleAnimation.setDuration(mAnimDuration);
        centerScaleAnimation.setFillAfter(true);
        centerTranslateAnimation.setDuration(mAnimDuration);
        mCenterAnimator= new AnimationSet(false);
        mCenterAnimator.addAnimation(centerScaleAnimation);
        mCenterAnimator.setFillAfter(true);
        mCenterAnimator.addAnimation(centerTranslateAnimation);
        mCenterView.startAnimation(mCenterAnimator);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
//        mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            startAnimator();
            break;
        }
        return true;
    }

    public void setAnimDuration(int duration) {
        this.rotateAnimDuration = duration;
    }
}
