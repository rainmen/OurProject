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

	// ��ʼ
	@SuppressWarnings({ "deprecation", "static-access" } )
	public void start() {
		// ��ô�����������
		sensorManager = (SensorManager) context
				.getSystemService(context.SENSOR_SERVICE);
		if (sensorManager != null) {
			// ��ȡ���򴫸���
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		}
		// ע��
		if (sensor != null) {
			sensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_GAME);
		}

	}

	// ֹͣ���
	public void stop() {
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSensorChanged(SensorEvent event) {
		// ���շ��򴫸���������
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
