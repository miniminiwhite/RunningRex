package com.jamescho.game.state;

import android.view.MotionEvent;
import com.jamescho.framework.util.Painter;
import com.jamescho.simpleandroidgdf.Assets;

public class LoadState extends State {

	@Override
	public void init() {
		curScore = 0;
		Assets.load();
		System.out.println("finished.");
	}

	@Override
	public void update(float delta) {
		setCurrentState(new StartState(), true);
	}

	@Override
	public void render(Painter g) {
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY, int c) {
		return false;
	}

	@Override
	public int getStateNum() {
		return State.LOADSTATE;
	}

}