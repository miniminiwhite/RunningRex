package com.jamescho.game.model;

import com.jamescho.framework.util.RandomNumberGenerator;
import com.jamescho.simpleandroidgdf.Assets;
import com.jamescho.simpleandroidgdf.GameMainActivity;

public class Cloud extends model {
	private int self_speed;

	public Cloud(float x) {
		this.self_speed = -RandomNumberGenerator.getRandIntBetween(50, 200);
		this.x = GameMainActivity.GAME_WIDTH + x;
		this.y = RandomNumberGenerator.getRandIntBetween((int)(GameMainActivity.GAME_HEIGHT * .1), (int)(GameMainActivity.GAME_HEIGHT * .6));
	}

	public void update(float delta) {
		x += self_speed * delta;
		if (x <= -200) {
			reset();
		}
	}

	@Override
	public void update(float delta, int speed) {}

	@Override
	protected void reset() {
		self_speed = -RandomNumberGenerator.getRandIntBetween(50, 200);
		x = GameMainActivity.GAME_WIDTH + Assets.cloud.getWidth() + RandomNumberGenerator.getRandIntBetween(200, 1000);
		y = RandomNumberGenerator.getRandIntBetween((int)(GameMainActivity.GAME_HEIGHT * .1), (int)(GameMainActivity.GAME_HEIGHT * .6));
	}
}