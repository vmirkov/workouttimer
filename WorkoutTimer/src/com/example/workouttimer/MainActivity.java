package com.example.workouttimer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workouttimer.service.SessionManager;

public class MainActivity extends ActionBarActivity {

	static View rootView;
	static long time = 0;
	static String state = "PREPARE";
	static Handler timerHandler = new Handler();
	static Runnable timerRunnable;
	static TextView timerTextView;
	
	
	
	static TextView tv;
	static TextView tvCycle;
	static TextView tvWorkout;
	static long startTime = 0;
	static long currentTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//ResetTimer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intent);
			return true;
		}
		else if (id == R.id.action_reset) {
			timerHandler.removeCallbacks(timerRunnable);
			ResetTimer();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void ResetTimer(){
		time = SessionManager.get(this).getPrepare();
		state = "";
		tv.setText(state);
		timerHandler.removeCallbacks(timerRunnable);
		TextView tv1 = (TextView)rootView.findViewById(R.id.textView1);
		tv1.setText("00:00");
		TextView tv2 = (TextView)rootView.findViewById(R.id.textView2);
		tv2.setText("00");
		TextView tv3 = (TextView)rootView.findViewById(R.id.TextView01);
		tv3.setText("00");
		startTime = 0;
		currentTime = 0;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	
	
	
	public static class PlaceholderFragment extends Fragment {
		
		Boolean started = false;
        
		public PlaceholderFragment(){
			
		    timerRunnable = new Runnable() {
		        @Override
		        public void run(){
		        	
		            if (time == 0)
		            {
		            	if(state.equals("PREPARE"))
		            	{
		            		tv.setTextColor(Color.RED);
		            		state = "WORK";
		            		time = SessionManager.get(getActivity().getApplicationContext()).getWork();
		            	}
		            	else if(state.equals("WORK"))
		            	{
		            		state = "REST";
		            		long cycles = Long.parseLong(tvCycle.getText().toString());
		            		long workouts = Long.parseLong(tvWorkout.getText().toString());
		            		cycles -= 1;
		            		if(cycles == 0){
		            			time = SessionManager.get(getActivity().getApplicationContext()).getPrepare();
		            			final Button b = (Button)rootView.findViewById(R.id.ButtonStart);
		            			cycles = SessionManager.get(getActivity().getApplicationContext()).getCycles();
		            			b.setText("START");
								timerHandler.removeCallbacks(timerRunnable);
								tvCycle.setText(Long.toString(SessionManager.get(getActivity().getApplicationContext()).getCycles()));
				            	   workouts -=1;
				            	   tvWorkout.setText(Long.toString(workouts));
				            	   if(workouts == 0){
				            		   timerHandler.removeCallbacks(timerRunnable);
				            		    MainActivity m = (MainActivity)getActivity();
				            		    m.ResetTimer();
				            		   return;
				            	   }
								ShowAlert();
								return;
								
		            		}
		            		tvWorkout.setText(Long.toString(workouts));
		            		tvCycle.setText(Long.toString(cycles));
		            		tv.setTextColor(Color.GREEN);
		            		time = SessionManager.get(getActivity().getApplicationContext()).getRest();
		            	}else{
		            		state = "WORK";
		            		tv.setTextColor(Color.RED);
		            		time = SessionManager.get(getActivity().getApplicationContext()).getWork();
		            	}
		            }
		            
		            long min = time / 60;
		        	long sek = time - min * 60;
		            timerTextView.setText(String.format("%02d:%02d", min, sek));
		        	tv.setText(state);
		        	time = time - 1 ;
		            timerHandler.postDelayed(this, 1000);
		        }
		    };
		}
		
		
	    private void ShowAlert(){
	    	 AlertDialog.Builder builder = new AlertDialog.Builder(
	                 getActivity());
	    	 builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   dialog.dismiss();
	               }
	           });
	        builder.setPositiveButton("YES",  new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   final Button b = (Button)rootView.findViewById(R.id.ButtonStart);
	            	   b.setText("STOP");
	            	   state = "PREPARE";
	            	   tv.setTextColor(Color.WHITE);
	            	   timerHandler.postDelayed(timerRunnable, 0);
	               }
	           });
	        builder.setMessage("Do you want to continue workout?");
	        builder.show();
	    }
	    
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			tvCycle = (TextView) rootView.findViewById(R.id.textView2);
			tvWorkout = (TextView) rootView.findViewById(R.id.TextView01);
			timerTextView = (TextView) rootView.findViewById(R.id.textView1);
			tv = (TextView) rootView.findViewById(R.id.textView4);
			final Button b = (Button)rootView.findViewById(R.id.ButtonStart);
			
			b.setText("START");
			started = false;
			
			b.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) { 
					if(SessionManager.get(getActivity().getApplicationContext()).getWork() == 0){
						Toast.makeText(getActivity().getApplicationContext(), "Enter work time", Toast.LENGTH_LONG).show();
						return;
					}
					if(b.getText() == "START")
					{
						if(!started)
						{
							tvWorkout.setText(Long.toString(SessionManager.get(getActivity().getApplicationContext()).getWorkout()));
							tvCycle.setText(Long.toString(SessionManager.get(getActivity().getApplicationContext()).getCycles()));
							time = SessionManager.get(getActivity().getApplicationContext()).getPrepare();
							Log.i("PREPARE", Long.toString(time));
							started = true;
						}
			        	long min = time / 60;
			        	long sek = time - min * 60;
			        	state = "PREPARE";
			            timerTextView.setText(String.format("%02d:%02d", min, sek));
			            tv.setText(state);
						b.setText("STOP");
						timerHandler.postDelayed(timerRunnable, 1000);
					}
					else
					{
						b.setText("START");
						timerHandler.removeCallbacks(timerRunnable);
					}
				}
				
				
			});
			return rootView;
		}
		
		
		
	}

}
