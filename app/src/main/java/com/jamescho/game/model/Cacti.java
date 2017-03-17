package com.jamescho.game.model;

import com.jamescho.framework.util.RandomNumberGenerator;
import com.jamescho.simpleandroidgdf.Assets;
import com.jamescho.simpleandroidgdf.GameMainActivity;

import java.util.ArrayList;

public class Cacti extends model {
	private int offset, picNum;
	private static final int BASELINE = (int)(GameMainActivity.GAME_HEIGHT * .97);

	public Cacti(float x) {
		offset = RandomNumberGenerator.getRandIntBetween(0, 100);
		this.x = GameMainActivity.GAME_WIDTH + x + offset;

		picNum = RandomNumberGenerator.getRandInt(5);
		width = Assets.cacti[picNum].getWidth();
		height = Assets.cacti[picNum].getHeight();
		visible = true;
	}

	@Override
	public void update(float delta, int speed) {}

	public void update(float delta, ArrayList<Bird> b, int speed) {
		x += speed * delta;
		if (x <= - width) {
			reset(b);
		}
	}

	@Override
	protected void reset() {}

	private void reset(ArrayList<Bird> birds) {
        visible = RandomNumberGenerator.getRandInt(5) >= 2;
        if (birds != null) {
            for (Bird b : birds) {
                if (Math.abs(b.getX() - x) < 400) {
                    visible = false;
                    break;
                }
            }
        }
        offset = RandomNumberGenerator.getRandIntBetween(0, 400) - 200;
		x = GameMainActivity.GAME_WIDTH + offset + 200;
		picNum = RandomNumberGenerator.getRandInt(5);
		width = Assets.cacti[picNum].getWidth();
		height = Assets.cacti[picNum].getHeight();
	}

	@Override
	public float getY() {
		return BASELINE - height;
	}

	public int getPicNum() {
		return picNum;
	}
}