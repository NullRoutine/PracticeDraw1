package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Practice11PieChartView extends View {

    private Paint mPaint;//画饼状图画笔
    private int width, height;
    private int[] degreeRange = {15, 20, 22, 28, 30, 70, 50};//转过的角度
    private String[] mStrings = {"Froyo", "Gingerbread", "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow"};
    List<PieDataEntity> mDataEntityList;
    private float mTotal;//计算总的角度比例
    private RectF mRect;
    private float radius = 300;//半径
    private float max;//最大饼状图
    private Context mContext;

    public Practice11PieChartView(Context context) {
        this(context, null);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mTotal = 0f;
        max = Float.MIN_VALUE;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mRect = new RectF(-300, -300, 300, 300);//确定扇形原点
        getPieData();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        dealData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图
        if (width != 0 && height != 0) {//判断再绘制
            canvas.translate(width / 2, height / 2);
            drawPiePath(canvas);
        }
    }

    private float halfAngle = 0f;//圆弧中点角度
    float lineStartX = 0f; // 直线开始的X坐标
    float lineStartY = 0f; // 直线开始的Y坐标
    float lineEndX;        // 直线结束的X坐标
    float lineEndY; // 直线结束的Y坐标

    private void drawPiePath(Canvas canvas) {
        float startAngle = 0;
        for (int i = 0; i < mDataEntityList.size(); i++) {
            PieDataEntity pieDataEntity = mDataEntityList.get(i);
            float sweepAngle = mDataEntityList.get(i).getValue() / mTotal * 360 - 2;//每个扇形的角度
//            sweepAngle = sweepAngle * percent;
            mPaint.setColor(mDataEntityList.get(i).getColor());
            halfAngle = startAngle + sweepAngle / 2;
            //角度=弧度*180/Math.PI
            lineStartX = radius * (float) Math.cos(halfAngle / 180 * Math.PI);//圆弧中点的X坐标
            lineStartY = radius * (float) Math.sin(halfAngle / 180 * Math.PI);//圆弧中点的Y坐标
            lineEndX = (radius + 50) * (float) Math.cos(halfAngle / 180 * Math.PI);
            lineEndY = (radius + 50) * (float) Math.sin(halfAngle / 180 * Math.PI);
            if (max == mDataEntityList.get(i).getValue()) {
                canvas.save();
                canvas.translate(lineStartX * 0.1f, lineStartY * 0.1f);
                canvas.drawArc(mRect, startAngle, sweepAngle, true, mPaint);
            } else {
                canvas.drawArc(mRect, startAngle, sweepAngle, true, mPaint);
            }
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(3);
            canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, mPaint);
            mPaint.setTextSize(Practice10HistogramView.sp2px(mContext, 11));
            if (halfAngle > 90 && halfAngle <= 270) {
                canvas.drawLine(lineEndX, lineEndY, lineEndX - 50, lineEndY, mPaint);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawText(pieDataEntity.getName(), lineEndX - 50 - 10 - mPaint.measureText(pieDataEntity.getName()), lineEndY, mPaint);
            } else {
                canvas.drawLine(lineEndX, lineEndY, lineEndX + 50, lineEndY, mPaint);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawText(pieDataEntity.getName(), lineEndX + 50 + 10, lineEndY, mPaint);
            }
            if (max == pieDataEntity.getValue()) {
                canvas.restore();//恢复save之前的状态
            }
            startAngle += sweepAngle + 2;
        }
    }

    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    /**
     * 初始化数据
     */
    private void getPieData() {
        mDataEntityList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            PieDataEntity pieDataEntity = new PieDataEntity(mStrings[i], degreeRange[i], mColors[i]);
            mDataEntityList.add(pieDataEntity);
        }
    }

    /**
     * 处理数据
     */
    private void dealData() {
        if (mDataEntityList.size() > 0) {
            for (PieDataEntity pieDataEntity : mDataEntityList) {
                mTotal += pieDataEntity.getValue();
                max = Math.max(max, pieDataEntity.getValue());
            }
        }
        postInvalidate();//开始绘制
    }

    public class PieDataEntity {
        private String name;//名字
        private float value;//值
        private float percent;
        private int color = 0;//颜色
        private float angle = 0;
        private String sum;

        public PieDataEntity(String name, float value, int color) {
            this.name = name;
            this.value = value;
            this.color = color;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }

        public float getPercent() {
            return percent;
        }

        public void setPercent(float percent) {
            this.percent = percent;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public float getAngle() {
            return angle;
        }

        public void setAngle(float angle) {
            this.angle = angle;
        }
    }
}
