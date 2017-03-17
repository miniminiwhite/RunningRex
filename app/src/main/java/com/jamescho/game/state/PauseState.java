package com.jamescho.game.state;

import android.view.MotionEvent;

import com.jamescho.framework.util.Painter;
import com.jamescho.simpleandroidgdf.Assets;
import com.jamescho.simpleandroidgdf.GameMainActivity;

/**
 * Created by White! on 2016/12/9.
 */

public class PauseState extends State {
    private float RexX, RexY;
    private PlayState ps;
    private boolean initNeeded, isDucked;

    public PauseState(PlayState formerState, boolean initNeeded) {
        this.initNeeded = initNeeded;
        this.ps = formerState;
    }

    public PauseState(PlayState ps, float RexX, float RexY, boolean isDucked) {
        this.ps = ps;
        this.RexX = RexX;
        this.RexY = RexY;
        this.isDucked = isDucked;
        this.initNeeded = true;
    }

    @Override
    public void init() {
        System.out.println("Hello I'm pause state.");
    }

    @Override
    public void update(float delta) {
        Assets.pauseAnim.update(delta);
    }

    @Override
    public void render(Painter g) {
        if (initNeeded) {
            g.drawImage(Assets.game_over, (GameMainActivity.GAME_WIDTH - Assets.game_over.getWidth()) / 2, (GameMainActivity.GAME_HEIGHT - Assets.game_over.getHeight()) / 2 - 100);
            if (!isDucked) {
                g.drawImage(Assets.hit, (int) RexX, (int) RexY);
            } else {
                g.drawImage(Assets.duck_hit, (int) RexX, (int) RexY);
            }
        } else {
            Assets.pauseAnim.render(g, (GameMainActivity.GAME_WIDTH - Assets.pause.getWidth()) / 2, (GameMainActivity.GAME_HEIGHT - Assets.pause.getHeight()) / 2 - 100);
        }
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY, int c) {
        if (c == 2) {
            setCurrentState(ps, initNeeded);
        }
        return true;
    }

    @Override
    public int getStateNum() {
        return State.PAUSESTATE;
    }
}
