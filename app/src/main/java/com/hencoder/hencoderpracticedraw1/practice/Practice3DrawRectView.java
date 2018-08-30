package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice3DrawRectView extends View {

    private int width, height;
    private Paint mPaint;

    public Practice3DrawRectView(Context context) {
        this(context, null);
    }

    public Practice3DrawRectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Practice3DrawRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawRect() 方法画矩形
        if (width != 0 && height != 0) {
            canvas.drawRect(width / 2 - 250, height / 2 - 250, width / 2 + 250, height / 2 + 250, mPaint);
        }
    }
}
