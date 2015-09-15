package com.example.smap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MyOrientationListener implements SensorEventListener {

	private Context context;
	private SensorManager sensorManager;
	private Sensor sensor;
	private float lastX;
	private OnOrientationListener onOrientationListener;

	public MyOrientationListener(Context context) {
		this.context = context;

	}

	// 开始
	@SuppressWarnings({ "deprecation", "static-access" } )
	public void start() {
		// 获得传感器管理器
		sensorManager = (SensorManager) context
				.getSystemService(context.SENSOR_SERVICE);
		if (sensorManager != null) {
			// 获取方向传感器
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		}
		// 注册
		if (sensor != null) {
			sensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_GAME);
		}

	}

	// 停止检测
	public void stop() {
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSensorChanged(SensorEvent event) {
		// 接收方向传感器的类型
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			float x = event.values[SensorManager.DATA_X];

			if (Math.abs(x - lastX) > 1.0) {

				onOrientationListener.onOrientationChanged(x);
			}
			lastX = x;
		}

	}

	public void setOnOrientationListener(
			OnOrientationListener onOrientationListener) {
		this.onOrientationListener = onOrientationListener;
	}

	public interface OnOrientationListener {
		void onOrientationChanged(float x);
	}

}
