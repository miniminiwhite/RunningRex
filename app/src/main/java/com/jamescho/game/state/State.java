package com.jamescho.game.state;

import android.view.MotionEvent;
import com.jamescho.framework.util.Painter;
import com.jamescho.simpleandroidgdf.GameMainActivity;

public abstract class State {
	protected int curScore;
	public static final int LOADSTATE = 0;
	public static final int STARTSTATE = 1;
	public static final int PLAYSTATE = 2;
	public static final int PAUSESTATE = 3;

	protected void setCurrentState(State newState, boolean initNeeded) {
		GameMainActivity.setHighScore(Math.max(GameMainActivity.getHighScore(), GameMainActivity.sGame.getCurrentScore()));
        GameMainActivity.sGame.setCurrentState(newState, initNeeded);
    }

	public abstract void init();

	public abstract void update(float delta);

	public abstract void render(Painter g);

	public abstract boolean onTouch(MotionEvent e, int scaledX, int scaledY, int c);

	public abstract int getStateNum();

	public int getScore() {
		return curScore;
	}
}