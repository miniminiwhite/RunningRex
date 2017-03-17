package com.jamescho.game.state;

import android.graphics.Color;
import android.view.MotionEvent;

import com.jamescho.framework.util.Painter;
import com.jamescho.game.model.Rex;
import com.jamescho.simpleandroidgdf.Assets;
import com.jamescho.simpleandroidgdf.GameMainActivity;

/**
 * Created by White! on 2016/12/9.
 */

public class StartState extends State {
    @Override
    public void init() {
        System.out.println("Hello I'm start state.");
    }

    @Override
    public void update(float delta) {
        Assets.readyAnim.update(delta);
    }

    @Override
    public void render(Painter g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        Assets.readyAnim.render(g, Rex.X, Rex.BASELINE - Rex.HEIGHT, Rex.WIDTH, Rex.HEIGHT);
        g.drawImage(Assets.tap_to_start, (GameMainActivity.GAME_WIDTH - Assets.tap_to_start.getWidth()) / 2, (GameMainActivity.GAME_HEIGHT - Assets.tap_to_start.getHeight()) / 2);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY, int c) {
        setCurrentState(new PlayState(), true);
        return true;
    }

    @Override
    public int getStateNum() {
        return State.STARTSTATE;
    }
}
