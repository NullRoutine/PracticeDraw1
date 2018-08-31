package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Practice10HistogramView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//画线画笔
    private Paint mPaintRect = new Paint(Paint.ANTI_ALIAS_FLAG);//画直方图画笔
    private Path mPath = new Path();
    private Rect mRect = new Rect();
    private String[] mStrings = {"Froyo", "GB", "ICS", "JB", "KitKat", "L", "M"};
    private int[] mLeftString = new int[7];
    private int mLeft = 58;
    private int mRight = mLeft + 75;
    private int mBottom = 400;
    private int mBottomText = 420;
    private int mTop;
    private int width, height;
    private Context context;
    private int rectWidth;

    public Practice10HistogramView(Context context) {
        this(context, null);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
//        init(context);
    }

    private void init(Context context) {
        this.context = context;
        mPath.moveTo(150, height - 200);
        mPath.lineTo(150, 200);
        mPath.moveTo(150, height - 200);
        mPath.lineTo(width - 150, height - 200);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.WHITE);
        mPaintRect.setColor(Color.argb(200, 84, 237, 118));
        mPaintRect.setTextSize(sp2px(context, 12));
        for (int i = 0; i < mStrings.length; i++) {
            mPaintRect.getTextBounds(mStrings[i], 0, mStrings[i].length(), mRect);
            mLeftString[i] = mRect.width() / 2;
        }
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        init(context);
        mLeft = 180;
        mRight = mLeft + ((width - 460) / 7);
        rectWidth = (width - 460) / 7;
        Log.e("TAG", width + "========" + height + "=====" + rectWidth);
        mBottom = height - 200;
        mBottomText = height - 150;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        canvas.drawPath(mPath, mPaint);
        int textCenter;
        for (int i = 0; i < 7; i++) {
            textCenter = (mLeft + mRight) / 2 - mLeftString[i];
            if (i == 0) {
                mTop = mBottom - 3;
            }
            if (i == 1) {
                mTop = mBottom - 10;
            }
            if (i == 2) {
                mTop = mBottom - 8;
            }
            if (i == 3) {
                mTop = mBottom - 150;
            }
            if (i == 4) {
                mTop = mBottom - 300;
            }
            if (i == 5) {
                mTop = mBottom - 350;
            }
            if (i == 6) {
                mTop = mBottom - 130;
            }
            canvas.drawText(mStrings[i], textCenter, mBottomText, mPaintRect);
            canvas.drawRect(mLeft, mTop, mRight, mBottom, mPaintRect);

            mLeft += rectWidth + 20;
            mRight += rectWidth + 20;
        }
        mLeft = rectWidth + 20;
        mRight = rectWidth + 20;
    }
}
