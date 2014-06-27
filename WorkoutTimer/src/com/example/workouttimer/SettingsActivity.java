package com.example.workouttimer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.example.workouttimer.service.SessionManager;

public class SettingsActivity extends Activity {

	EditText editText1;
	EditText editText2;
	EditText editText3;
	EditText editText4;
	EditText editText5;
	
	SessionManager manager;

	@Override
	protected void onPause() {

		super.onPause();
		editText1 = (EditText) findViewById(R.id.EditText01);
		final long prepare = Long.valueOf(editText1.getText().toString());
		SessionManager.get(this).savePrepare(prepare);
		editText2 = (EditText) findViewById(R.id.EditText02);
		final long work = Long.valueOf(editText2.getText().toString());
		SessionManager.get(this).saveWork(work);
		editText3 = (EditText) findViewById(R.id.EditText03);
		final long rest = Long.valueOf(editText3.getText().toString());
		SessionManager.get(this).saveRest(rest);
		editText4 = (EditText) findViewById(R.id.EditText04);
		final long cycles = Long.valueOf(editText4.getText().toString());
		SessionManager.get(this).saveCycles(cycles);
		editText5 = (EditText) findViewById(R.id.EditText05);
		final long workout = Long.valueOf(editText5.getText().toString());
		SessionManager.get(this).saveWorkout(workout);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		editText1.setText(Long.toString(manager.getPrepare()));
		editText2.setText(Long.toString(manager.getWork()));
		editText3.setText(Long.toString(manager.getRest()));
		editText4.setText(Long.toString(manager.getCycles()));
		editText5.setText(Long.toString(manager.getWorkout()));
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		editText1 = (EditText) findViewById(R.id.EditText01);
		editText1.setOnClickListener(listener);
		editText2 = (EditText) findViewById(R.id.EditText02);
		editText2.setOnClickListener(listener);
		editText3 = (EditText) findViewById(R.id.EditText03);
		editText3.setOnClickListener(listener);
		editText4 = (EditText) findViewById(R.id.EditText04);
		editText4.setOnClickListener(listener);
		editText5 = (EditText) findViewById(R.id.EditText05);
		editText5.setOnClickListener(listener);
		manager = SessionManager.get(this);
		setTitle("Workout settings");
	}

	View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			EditText e = (EditText) v;
			e.setText("");

		}
	};
}
