package com.jamescho.game.model;

import com.jamescho.framework.util.RandomNumberGenerator;
import com.jamescho.simpleandroidgdf.Assets;
import com.jamescho.simpleandroidgdf.GameMainActivity;

import java.util.ArrayList;

public class Bird extends model {
	private static final int BASELINE = (int)(GameMainActivity.GAME_HEIGHT * .67);

	public Bird(float x) {
		this.x = GameMainActivity.GAME_WIDTH + x;
		this.y = BASELINE - height;
		width = Assets.getBirdWidth();
		height = Assets.getBirdHeight();

		visible = RandomNumberGenerator.getRandInt(4) == 0;
	}

	@Override
	public void update(float delta, int speed) {}

	public void update(float delta, ArrayList<Cacti> cacti, int speed) {
		x += speed * delta;
		if (x <= -50) {
			reset(cacti);
		}
	}

	@Override
	protected void reset() {}

	private void reset(ArrayList<Cacti> cacti) {
		visible = RandomNumberGenerator.getRandInt(4) == 0;
		for (Cacti i: cacti) {
			if (Math.abs(i.getX() - x) < 400) {
				visible = false;
				break;
			}
		}
		x = GameMainActivity.GAME_WIDTH + RandomNumberGenerator.getRandIntBetween(50, 300);
	}
}