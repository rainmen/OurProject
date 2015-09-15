package com.example.smap;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * 1、测量onMeasure 2、布局onLayout 3、绘制onDraw
 * 
 */
public class MyRadarView extends View {
	private int width;
	private int height;
	private Paint paintCircle;
	private Paint paintScan;
	private float start ;
	private Matrix matrix;

	public MyRadarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		width = context.getResources().getDisplayMetrics().widthPixels;
		height = context.getResources().getDisplayMetrics().heightPixels;
		initPaint();
		Handler handler =new Handler();
		handler.post(run());

	}

	private void initPaint() {
		paintCircle = new Paint();
		paintCircle.setColor(Color.LTGRAY);
		paintCircle.setStrokeWidth(10);
		paintCircle.setAntiAlias(true);
		paintCircle.setStyle(Style.STROKE);

		paintScan = new Paint();
		paintScan.setColor(0x9D00ff00);
		paintScan.setAntiAlias(true);

	}

	private Runnable run(){
		start+=4;
		if (start-360>0) {
			start =0;
		}
		matrix=new Matrix();
		matrix.postRotate(start, width/2, height/2);
		Handler handler = new Handler();
		handler.postDelayed(run(), 20);
		return null ;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(width, height);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(width / 2, height / 2, width / 5, paintCircle);
		canvas.drawCircle(width / 2, height / 2, width / 4, paintCircle);
		canvas.drawCircle(width / 2, height / 2, width / 3, paintCircle);
		canvas.drawCircle(width / 2, height / 2, width / 2, paintCircle);
		Shader mShader = new SweepGradient(width / 2, height / 2,
				Color.TRANSPARENT, color.transparent);
		paintScan.setShader(mShader);
		canvas.concat(matrix);
		canvas.drawCircle(width/2, height/2, width/2, paintScan);

	}

}
