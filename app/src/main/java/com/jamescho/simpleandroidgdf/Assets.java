package com.jamescho.simpleandroidgdf;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioManager;
import android.media.SoundPool;

import com.jamescho.framework.animation.Animation;
import com.jamescho.framework.animation.Frame;

public class Assets {
	private static SoundPool soundPool;
	public static Bitmap hi, hit, jump, cloud, pause, blank, duck_hit, game_over, tap_to_start,
            run1, run2, duck1, duck2, bird1, bird2, ready1, ready2;
    public static Bitmap[] cacti, ground, number;
    public static Animation runAnim, duckAnim, birdAnim, readyAnim, pauseAnim;
	public static int hitID, onJumpID, bonusID;

	public static void load() {
        hi = loadBitmap("HI.png", true);
        hit = loadBitmap("hit.png", true);
        jump = loadBitmap("jump.png", true);
        cloud = loadBitmap("cloud.png", true);
        pause = loadBitmap("pause.png", true);
        blank = loadBitmap("blank.png", true);
        duck_hit = loadBitmap("duck_hit.png", true);
        game_over = loadBitmap("game_over.png", true);
        tap_to_start = loadBitmap("tap_to_start.png", true);

        run1 = loadBitmap("run1.png", true);
        run2 = loadBitmap("run2.png", true);
        duck1 = loadBitmap("duck1.png", true);
        duck2 = loadBitmap("duck2.png", true);
        bird1 = loadBitmap("bird1.png", true);
        bird2 = loadBitmap("bird2.png", true);
        ready1 = loadBitmap("ready1.png", true);
        ready2 = loadBitmap("ready2.png", true);

        cacti = new Bitmap[5];
        cacti[0] = loadBitmap("cacti0.png", true);
        cacti[1] = loadBitmap("cacti1.png", true);
        cacti[2] = loadBitmap("cacti2.png", true);
        cacti[3] = loadBitmap("cacti3.png", true);
        cacti[4] = loadBitmap("cacti4.png", true);

        ground = new Bitmap[3];
        ground[0] = loadBitmap("ground0.png", true);
        ground[1] = loadBitmap("ground1.png", true);
        ground[2] = loadBitmap("ground2.png", true);

        number = new Bitmap[10];
        number[0] = loadBitmap("0.png", true);
        number[1] = loadBitmap("1.png", true);
        number[2] = loadBitmap("2.png", true);
        number[3] = loadBitmap("3.png", true);
        number[4] = loadBitmap("4.png", true);
        number[5] = loadBitmap("5.png", true);
        number[6] = loadBitmap("6.png", true);
        number[7] = loadBitmap("7.png", true);
        number[8] = loadBitmap("8.png", true);
        number[9] = loadBitmap("9.png", true);

		Frame f1 = new Frame(run1, .1f);
		Frame f2 = new Frame(run2, .1f);
        Frame d1 = new Frame(duck1, .1f);
        Frame d2 = new Frame(duck2, .1f);
        Frame b1 = new Frame(bird1, .2f);
        Frame b2 = new Frame(bird2, .2f);
        Frame r1 = new Frame(ready1, 3f);
        Frame r2 = new Frame(ready2, .3f);
        Frame p1 = new Frame(pause, .7f);
        Frame p2 = new Frame(blank, .7f);

        runAnim = new Animation(f1, f2);
        duckAnim = new Animation(d1, d2);
        birdAnim = new Animation(b1, b2);
        readyAnim = new Animation(r1, r2);
        pauseAnim = new Animation(p1, p2);
		hitID = loadSound("hit.mp3");
		onJumpID = loadSound("jump.mp3");
        bonusID = loadSound("bonus.mp3");
	}

	private static Bitmap loadBitmap(String filename, boolean transparency) {
		InputStream inputStream = null;
		try {
			inputStream = GameMainActivity.assets.open(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Options options = new Options();
		if (transparency) {
			options.inPreferredConfig = Config.ARGB_8888;
		} else {
			options.inPreferredConfig = Config.RGB_565;
		}
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
				options);
		return bitmap;
	}

	private static int loadSound(String filename) {
		int soundID = 0;
		if (soundPool == null) {
			soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
		}
		try {
			soundID = soundPool.load(GameMainActivity.assets.openFd(filename),
					1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return soundID;
	}

	public static void playSound(int soundID) {
		soundPool.play(soundID, 1, 1, 1, 0, 1);
	}

    public static int getRexHeight() {
        return run1.getHeight();
    }

    public static int getRexWidth() {
        return run1.getWidth();
    }

    public static int getDuckHeight() {
        return duck1.getHeight();
    }

    public static int getDuckWidth() {
        return duck1.getWidth();
    }

    public static int getBirdHeight() {
        return bird1.getHeight();
    }

    public static int getBirdWidth() {
        return bird2.getWidth();
    }

}