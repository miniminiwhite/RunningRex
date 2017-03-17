package com.jamescho.game.state;

import java.util.ArrayList;
import android.graphics.Color;
import android.view.MotionEvent;
import com.jamescho.framework.util.Painter;
import com.jamescho.game.model.Cacti;
import com.jamescho.game.model.Cloud;
import com.jamescho.game.model.Bird;
import com.jamescho.game.model.Ground;
import com.jamescho.game.model.Rex;
import com.jamescho.simpleandroidgdf.Assets;
import com.jamescho.simpleandroidgdf.GameMainActivity;

public class PlayState extends State {
	private Rex rex;
	private ArrayList<Ground> ground;
	private ArrayList<Cacti> cacti;
	private ArrayList<Cloud> cloud;
	private ArrayList<Bird> bird;
	private int rexScore, blinkTime;
    private int[] blink;
	private boolean CactiShown, BirdShown;
	private static int SPEED;
	private static final int MAX_SPEED = -1200;

	private float recentTouchY;

	@Override
	public void init() {
		rex = new Rex();
        cloud = new ArrayList<Cloud>();
		for (int i = 0; i != 3; ++i) {
			Cloud c = new Cloud(300);
			cloud.add(c);
		}
        curScore = 0;
        rexScore = 0;
        SPEED = -500;
        CactiShown = false;
		BirdShown = false;
        blink = new int[3];
        init_ground();
	}

	private void init_ground() {
		int n = GameMainActivity.GAME_WIDTH / Ground.WIDTH + 1;
		ground = new ArrayList<Ground>();
		for (int i = 0; i != n; ++i) {
			Ground g = new Ground(i * Ground.WIDTH);
			ground.add(g);
		}
	}

	private void init_cacti() {
        cacti = new ArrayList<Cacti>();
        for (int i = 0; i != 2; ++i) {
			Cacti c = new Cacti(i * 800);
			cacti.add(c);
		}
	}

	private void init_bird() {
        bird = new ArrayList<Bird>();
		for (int i = 0; i != 2; ++i) {
			Bird b = new Bird(i * 400);
			bird.add(b);
		}
	}

	@Override
	public void update(float delta) {
		if (!rex.isAlive()) {
			// Dead.
            setCurrentState(new PauseState(this, rex.getX(), rex.getY(), rex.isDucked()), true);
		}
		rexScore += 1;
        curScore = rexScore / 4;
		if (rexScore % 200 == 0 && SPEED > MAX_SPEED) {
			SPEED -=5;
		}
        rex.update(delta);
        if (curScore > 40) {
			if (!CactiShown) {
				init_cacti();
				CactiShown = true;
			} else {
				updateCacti(delta);
			}
		}
		if (curScore > 250) {
			if (!BirdShown) {
				init_bird();
				BirdShown = true;
			} else {
				Assets.birdAnim.update(delta);
				updateBird(delta);
			}
		}

		Assets.runAnim.update(delta);
		Assets.duckAnim.update(delta);
		for (Cloud i: cloud) {
			i.update(delta);
		}
		updateGround(delta);
	}

	private void updateCacti(float delta) {
		for (Cacti i: cacti) {
            if (BirdShown) {
                i.update(delta, bird, SPEED);
            } else {
                i.update(delta, null, SPEED);
            }
            if (i.isVisible() && rex.hit(i)) {
				rex.die();
                System.out.println("hit cactus. ");
                System.out.println("is duck: " + rex.isDucked());
				System.out.println("R: " + rex.getX() + "\t" + rex.getY() + "\t" + (rex.getX() + rex.getWidth()) + "\t" + (rex.getY() + rex.getHeight()));
				System.out.println("C: " + i.getX() + "\t" + i.getY() + "\t" + (i.getX() + i.getWidth()) + "\t" + (i.getY() + i.getHeight()));
			}
		}
	}

	private void updateBird(float delta) {
		for (Bird i: bird) {
			i.update(delta, cacti, SPEED);
            if (i.isVisible() && rex.hit(i)) {
				rex.die();
				System.out.println("hit bird. ");
                System.out.println("is duck: " + rex.isDucked());
                System.out.println("R: " + rex.getX() + "\t" + rex.getY() + "\t" + (rex.getX() + rex.getWidth()) + "\t" + (rex.getY() + rex.getHeight()));
				System.out.println("B: " + i.getX() + "\t" + i.getY() + "\t" + (i.getX() + i.getWidth()) + "\t" + (i.getY() + i.getHeight()));
			}
		}
	}

	private void  updateGround(float delta) {
		int len = ground.size();
		for (int i = 0; i != len; ++i) {
			ground.get(i).update(delta, SPEED, (int) ground.get((i + len - 1) % len).getX());
		}
	}

	@Override
	public void render(Painter g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
		renderClouds(g);
        renderGround(g);
        if (CactiShown) {
			renderCacti(g);
		}
		if (BirdShown) {
			renderBird(g);
		}
        renderRex(g);
        if (curScore > 0 && rexScore % 400 == 0) {
            Assets.playSound(Assets.bonusID);
            System.out.println("wow!");
            blinkTime = 6;
            for (int i = 0, j = 100; i != 3 && curScore / j != 0; ++i, j *= 10) {
                blink[i] = (curScore / j) % 10;
            }
        }
        renderScore(g);
	}

	private void renderGround(Painter g) {
		for (Ground i: ground) {
			g.drawImage(Assets.ground[i.getPicNum()], (int)i.getX(), (int)i.getY());
		}
	}

	private void renderScore(Painter g) {
        int drawingPosX, drawingPosY, highScore, currentScore;
        drawingPosX = 850;
        drawingPosY = 60;
        currentScore = curScore;
        highScore = GameMainActivity.getHighScore();
        g.drawImage(Assets.hi, drawingPosX, drawingPosY);
        drawingPosX += Assets.hi.getWidth() + 50;
        for (int i = 10000; i != 0; i /= 10) {
            g.drawImage(Assets.number[highScore / i], drawingPosX, drawingPosY);
            drawingPosX += Assets.number[highScore / i].getWidth();
            highScore %= i;
        }

        drawingPosX += 50;
        if (blinkTime == 0) {
            for (int i = 10000; i != 0; i /= 10) {
                g.drawImage(Assets.number[currentScore / i], drawingPosX, drawingPosY);
                drawingPosX += Assets.number[currentScore / i].getWidth();
                currentScore %= i;
            }
        } else {
            if ((blinkTime & 1) == 0) {
                for (int i = 0; i != 3; ++i) {
                    g.drawImage(Assets.number[blink[2 - i]], drawingPosX, drawingPosY);
                    drawingPosX += Assets.number[blink[2 - i]].getWidth();
                }
                for (int i = 0; i != 2; ++i) {
                    g.drawImage(Assets.number[0], drawingPosX, drawingPosY);
                    drawingPosX += Assets.number[0].getWidth();
                }
            }
            if (rexScore % 20 == 0) {
                --blinkTime;
            }
        }
	}

	private void renderRex(Painter g) {
		if (!rex.isGrounded()) {
            g.drawImage(Assets.jump, (int)rex.getX(), (int)rex.getY(), rex.getWidth(),
                    rex.getHeight());
		} else {
            if (rex.isDucked()) {
                Assets.duckAnim.render(g, (int)rex.getX(), (int)rex.getY(), rex.getWidth(),
                        rex.getHeight());
            } else {
                Assets.runAnim.render(g, (int)rex.getX(), (int)rex.getY(), rex.getWidth(),
                        rex.getHeight());
            }
		}
	}

	private void renderCacti(Painter g) {
		for (Cacti i: cacti) {
            if (i.isVisible()) {
                g.drawImage(Assets.cacti[i.getPicNum()], (int) i.getX(), (int) i.getY(),
						i.getWidth(), i.getHeight());
            }
		}
	}

	private void renderBird(Painter g) {
		for (Bird i: bird) {
			if (i.isVisible()) {
				Assets.birdAnim.render(g, (int)i.getX(), (int)i.getY(), i.getWidth(),
						i.getHeight());
			}
		}
	}

	private void renderClouds(Painter g) {
		for (Cloud i: cloud) {
			g.drawImage(Assets.cloud, (int)i.getX(), (int)i.getY(),
					Assets.cloud.getWidth(), Assets.cloud.getHeight());
		}
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY, int c) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            recentTouchY = scaledY;
        }
        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (recentTouchY - scaledY > 100) {
                if (rex.isDucked())  {
                    rex.duck();
                } else {
                    rex.jump();
                }
            } else if (scaledY - recentTouchY > 100) {
                rex.duck();
            }
        }
		return true;
	}

    @Override
    public int getStateNum() {
        return State.PLAYSTATE;
    }
}