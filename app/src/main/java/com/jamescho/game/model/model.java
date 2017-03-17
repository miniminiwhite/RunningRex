package com.jamescho.game.model;

/**
 * Created by White! on 2016/12/1.
 */

public abstract class model {
    protected float x, y;
    protected int width, height;
    protected boolean visible;


    public abstract void update(float delta, int speed);

    protected abstract void reset();

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isVisible() {
        return visible;
    }
}
