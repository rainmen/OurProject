package com.example.smap;

import com.baidu.location.e.r;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class LoginActivity extends Activity {
	private ImageButton back;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		back = (ImageButton) findViewById(R.id.btn_myzone_back);
		back.setOnClickListener(new MyButtonListener());
		
	}
	private class MyButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if (v.getId()==R.id.btn_myzone_back) {
				startActivity(new Intent(LoginActivity.this,MainActivity.class));
				overridePendingTransition(R.anim.in_from_left,R.anim.out_from_right);
			}
			
		}

	}
}
