package com.jamescho.simpleandroidgdf;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.WindowManager;

import com.jamescho.game.state.State;

public class GameMainActivity extends Activity {
	public static final int GAME_WIDTH = 1280;
	public static final int GAME_HEIGHT = 768;
	public static GameView sGame;
	public static AssetManager assets;

	private static SharedPreferences prefs;
	private static final String highScoreKey = "highScoreKey";
	private static int highScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = getPreferences(Activity.MODE_PRIVATE); // New line!
		highScore = retrieveHighScore();
		assets = getAssets();
		sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
		setContentView(sGame);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	public static void setHighScore(int highScore) {
		GameMainActivity.highScore = highScore;
		Editor editor = prefs.edit();
		editor.putInt(highScoreKey, highScore);
		editor.commit();
	}

	private int retrieveHighScore() {
		return prefs.getInt(highScoreKey, 0);
	}

	public static int getHighScore() {
		return highScore;
	}

	@Override
	public void onBackPressed() {
		try {
			switch (sGame.getCurrentStateNum()) {
				case State.PLAYSTATE:
					sGame.setCurrentState(State.PAUSESTATE);
					break;
				case State.PAUSESTATE:
					sGame.setCurrentState(State.STARTSTATE);
					break;
				case State.STARTSTATE:
				case State.LOADSTATE:
					super.onBackPressed();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
