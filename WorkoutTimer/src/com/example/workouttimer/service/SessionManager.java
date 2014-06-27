package com.example.workouttimer.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * @author gdjonovic
 * 
 */
public class SessionManager {

	private static final String TAG = "SessionManager";

	private static final String PREFERENCES = "timer_files";

	private static final String BEST_TIME = "best_time";

	private Editor editor;
	private SharedPreferences pref;

	private SessionManager(Context context) {
		pref = context.getSharedPreferences(PREFERENCES, 0);
		editor = pref.edit();
	}

	public static SessionManager get(Context context) {
		return new SessionManager(context);
	}
	
	/**
	 * This is JavaDoc example for you guys
	 * if make this kind of comments description will be 
	 * visible when you call this method.
	 * writing comments is good practice and it should be used.
	 * BTW this method will return best time
	 * @param bestTime
	 */
	public void savePrepare(long bestTime){
		editor.putLong("PREPARE", bestTime);
		editor.commit();
		Log.i("prepare", Long.toString(bestTime));
	}

	public long getPrepare(){
		long bestTime = pref.getLong("PREPARE", 0);
		Log.i("getPrepare", Long.toString(bestTime));
		return bestTime;
	}
	
	public void saveWork(long bestTime){
		editor.putLong("WORK", bestTime);
		editor.commit();
	}

	public long getWork(){
		long bestTime = pref.getLong("WORK", 0);
		return bestTime;
	}
	
	public void saveRest(long bestTime){
		editor.putLong("REST", bestTime);
		editor.commit();
	}

	public long getRest(){
		long bestTime = pref.getLong("REST", 0);
		return bestTime;
	}
	
	public void saveCycles(long bestTime){
		editor.putLong("CYCLES", bestTime);
		editor.commit();
	}

	public long getCycles(){
		long bestTime = pref.getLong("CYCLES", 0);
		return bestTime;
	}
	
	public void saveWorkout(long bestTime){
		editor.putLong("WORKOUT", bestTime);
		editor.commit();
	}

	public long getWorkout(){
		long bestTime = pref.getLong("WORKOUT", 0);
		return bestTime;
	}

}
