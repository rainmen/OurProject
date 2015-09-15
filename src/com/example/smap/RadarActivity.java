package com.example.smap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RadarActivity extends Activity {
	private Button exit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_radar);
		exit=(Button) findViewById(R.id.btn_radar_exit);
		exit.setOnClickListener(new MyButtonListener());
	}

	private class MyButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.btn_myzone_back) {
				startActivity(new Intent(RadarActivity.this, MainActivity.class));
				overridePendingTransition(R.anim.in_from_left,
						R.anim.out_from_right);
			}

		}
	}
}
