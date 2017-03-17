package com.jamescho.game.model;

import com.jamescho.simpleandroidgdf.Assets;
import com.jamescho.simpleandroidgdf.GameMainActivity;

import android.graphics.Rect;

public class Rex extends model {
	private int velY;
	private boolean isAlive;
	private boolean isDucked;
	private static final int GRAVITY = 3600;
	private static final int JUMP_VELOCITY = -1600;

    public static final int X = 160;
    public static final int WIDTH = Assets.getRexWidth();
	public static final int HEIGHT = Assets.getRexHeight();
	public static final int DUCK_WIDTH = Assets.getDuckWidth();
	public static final int DUCK_HEIGHT = Assets.getDuckHeight();
	public static final int BASELINE = (int)(GameMainActivity.GAME_HEIGHT * .97f);

	public Rex() {
		width = WIDTH;
		height = HEIGHT;
		x = X;
		y = BASELINE - height;
		isAlive = true;
		isDucked = false;
	}

	@Override
	public void update(float delta, int speed) {}

	public void update(float delta) {
		if (y + height < BASELINE) {
			velY += GRAVITY * delta;
		} else {
			y = BASELINE - height;
			velY = 0;
		}
		y += velY * delta;
	}

	@Override
	protected void reset() {}

	public void jump() {
		if (isGrounded()) {
			Assets.playSound(Assets.onJumpID);
			unduck();
			y -= 10;
			velY = JUMP_VELOCITY;
		}
	}

	public void duck() {
		if (isGrounded()) {
            if (!isDucked) {
                isDucked = true;
                width = DUCK_WIDTH;
                height = DUCK_HEIGHT;
            } else {
                unduck();
            }
		}
	}

	public void die() {
		Assets.playSound(Assets.hitID);
		isAlive = false;
		System.out.println("OMG.");

	}

	public boolean isGrounded() {
		return y == BASELINE - height;
	}

	public boolean isDucked() {
		return isDucked;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isAlive() {
		return isAlive;
	}

    public boolean hit(model m) {
        float mX, mY, mWidth, mHeight, head_offset, tail_offset;
        mX = m.getX();
        mY = m.getY();
        mWidth = m.getWidth();
        mHeight = m.getHeight();
        head_offset = width * 2 / 9;
        tail_offset = width / 3;
        if ((x + tail_offset >= mX && x + tail_offset <= mX + mWidth) || (x + width - head_offset  >= mX && x + width - head_offset <= mX +mWidth)) {
            if ((y >= mY && y <= mY + mHeight) || (y + height >= mY && y + height <= mY + mHeight)) {
                return true;
            }
        }
        return false;
    }

    private void unduck() {
        isDucked = false;
        width = WIDTH;
        height = HEIGHT;
    }
}