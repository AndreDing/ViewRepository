package com.ialways.android.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.ialways.android.R;

/**
 * 随进度显示反色效果的TextView
 * @author dingchao
 * @email gmdingchao@gmail.com
 */
public class ProgressTextView extends View {

    private Context mCtx;
    private float mProgress;
    private float mWidth;
    private float mHeight;
    private float mStrokeWidth;
    // 默认显示的背景
    private int mInColor;
    // 进度背景色
    private int mOutColor;
    // 默认字体颜色
    private int mInTvColor;
    // 进度字体色
    private int mOutTvColor;
    // 边框色
    private int mStrokeColor;
    // 字体大小
    private float mTvSize;
//    private int mLeft;
//    private int mTop;
//    private int mRight;
//    private int mBottom;
    // TextView内容
    private String mTvContent;

    public ProgressTextView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public ProgressTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.progressTVStyle);
        // TODO Auto-generated constructor stub
    }

    public ProgressTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ProgressTextView, defStyleAttr, 0);
        if (attributes != null) {
            mInColor = attributes.getColor(R.styleable.ProgressTextView_in_color, Color.YELLOW);

            mOutColor = attributes.getColor(R.styleable.ProgressTextView_out_color, Color.WHITE);

            mInTvColor = attributes.getColor(R.styleable.ProgressTextView_in_tv_color, Color.YELLOW);

            mOutTvColor = attributes.getColor(R.styleable.ProgressTextView_out_tv_color, Color.WHITE);

            mStrokeColor = attributes.getColor(R.styleable.ProgressTextView_stroke_color, Color.WHITE);

            mProgress = attributes.getFloat(R.styleable.ProgressTextView_tv_progress, 0f);

            mWidth = attributes.getDimension(R.styleable.ProgressTextView_tv_width, 0);

            mHeight = attributes.getDimension(R.styleable.ProgressTextView_tv_height, 0);

            mTvSize = attributes.getDimension(R.styleable.ProgressTextView_tv_size, 0);

            mTvContent = attributes.getString(R.styleable.ProgressTextView_tv_content);

            mStrokeWidth = attributes.getDimension(R.styleable.ProgressTextView_stroke_width, 0);
        }
    }

    public void setText(String textContent) {
        this.mTvContent = textContent;
        this.invalidate();
    }

    public void setText(int textRes) {
        this.mTvContent = mCtx.getString(textRes);
        this.invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);

//        this.mLeft = left;
//        this.mTop = top;
//        this.mRight = right;
//        this.mBottom = bottom;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        this.invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        // 绘制背景色
        Paint bgPaint = new Paint();
        bgPaint.setColor(mInColor);
        bgPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, mWidth, mHeight, bgPaint);

        // 绘制进度
        float progressRight = mProgress * mWidth;
        Paint progressPaint = new Paint();
        progressPaint.setColor(mOutColor);
        progressPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, mWidth, mHeight, progressPaint);

        // 绘制边框
        Paint strokePaint = new Paint();
        strokePaint.setColor(mStrokeColor);
        strokePaint.setStrokeWidth(mStrokeWidth);
        strokePaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, mWidth, mHeight, strokePaint);

        // 绘制背景文字
        Paint textPaint = new Paint();
        textPaint.setColor(mInTvColor);
        textPaint.setTextSize(mTvSize);
        textPaint.setAntiAlias(true);
        Rect mBound = new Rect();
        textPaint.getTextBounds(mTvContent, 0, mTvContent.length() - 1, mBound);
        canvas.drawText(mTvContent, mWidth / 2 - (mBound.right + mBound.left) / 2, mHeight / 2 - (mBound.bottom + mBound.top) / 2, textPaint);

        // 绘制反白效果的文字
        canvas.save();
        textPaint.setColor(mOutTvColor);
        canvas.clipRect(0, 0, mWidth * mProgress, mHeight);
        textPaint.getTextBounds(mTvContent, 0, mTvContent.length() - 1, mBound);
        canvas.drawText(mTvContent, mWidth / 2 - (mBound.right + mBound.left) / 2, mHeight / 2 - (mBound.bottom + mBound.top) / 2, textPaint);
        canvas.restore();

    }
}
