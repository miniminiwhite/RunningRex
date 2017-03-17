package com.jamescho.game.model;

import com.jamescho.framework.util.RandomNumberGenerator;
import com.jamescho.simpleandroidgdf.Assets;
import com.jamescho.simpleandroidgdf.GameMainActivity;

/**
 * Created by White! on 2016/12/8.
 */

public class Ground extends model {
    private int picNum;
    public static final int WIDTH = Assets.ground[0].getWidth();
    private static final int Y = (int)(GameMainActivity.GAME_HEIGHT * .966f) - Assets.ground[0].getHeight();

    public Ground(int x) {
        this.picNum = RandomNumberGenerator.getRandInt(3);
        this.x = x;
        this.y = Y;
    }

    @Override
    public void update(float delta, int speed) {}

    public void update(float delta, int speed, int lastX) {
        x += speed * delta;
        if (x <= -WIDTH) {
            reset(lastX);
        }
    }

    @Override
    protected void reset() {}

    private void reset(int lastX) {
        x = WIDTH + lastX - 100;
        picNum = RandomNumberGenerator.getRandInt(3);
    }

    public int getPicNum() {
        return picNum;
    }
}
