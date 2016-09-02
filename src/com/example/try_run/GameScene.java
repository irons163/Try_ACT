package com.example.try_run;




import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.try_gameengine.action.MAction;
import com.example.try_gameengine.action.MAction2;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementAtionController;
import com.example.try_gameengine.avg.GraphicsUtils;
import com.example.try_gameengine.avg.NumberUtils;
import com.example.try_gameengine.framework.ALayer.LayerParam;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.GameModel;
import com.example.try_gameengine.framework.GameView;
import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;
import com.example.try_gameengine.framework.LabelLayer;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.framework.LightImage;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.scene.EasyScene;
import com.example.try_run.Map.Coin;
import com.example.try_run.Map.Item;
import com.example.try_run.model.AppleFactory;
import com.example.try_run.model.Background;
import com.example.try_run.model.Panda;
import com.example.try_run.model.Panda.Status;
import com.example.try_run.model.Platform;
import com.example.try_run.model.PlatformFactory;
import com.example.try_run.model.ProtocolMainscreen;
import com.example.try_run.utils.AudioUtil;
import com.example.try_run.utils.CommonUtil;
import com.example.try_run.utils.LTimer;
import com.example.try_run.utils.MyCanvas;



public class GameScene extends EasyScene implements ButtonLayer.OnClickListener, ProtocolMainscreen{
	
	final class ACTKey {
		
	public static final int MODE_NORMAL = 0;

	public static final int MODE_PRESS_ONLY = 1;

	private static final int STATE_RELEASED = 0;

	private static final int STATE_PRESSED = 1;

	private static final int STATE_WAITING_FOR_RELEASE = 2;

	private int mode;

	private int amount;

	private int state;

	public ACTKey() {
		this(MODE_NORMAL);
	}

	public ACTKey(int mode) {
		this.mode = mode;
		this.reset();
	}

	public void reset() {
		this.state = 0;
		this.amount = 0;
	}

	public void press() {
		if (this.state != STATE_WAITING_FOR_RELEASE) {
			++this.amount;
			this.state = STATE_PRESSED;
		}

	}

	public void release() {
		this.state = 0;
	}

	public boolean isPressed() {
		if (this.amount != 0) {
			if (this.state == STATE_RELEASED) {
				this.amount = 0;
			} else if (this.mode == MODE_PRESS_ONLY) {
				this.state = STATE_WAITING_FOR_RELEASE;
				this.amount = 0;
			}
			return true;
		} else {
			return false;
		}
	}
}

private int s_X;

private int s_Y;

private String map_st;

private LightImage blockImg;

private LightImage iboxImg;

private LightImage coinImg;

private LightImage heroImg;

private LightImage stickImg;

private LightImage upbarImg;

private LightImage underbarImg;

private LightImage enemyImgE;

private LightImage enemyImgF;

private LightImage springImg;

private LightImage goalImg;

private LightImage titleStImg;

private LightImage retryStImg;

private LightImage gaugeImg;

private LightImage itemImg;

private LightImage halfImg;

private LightImage tile1Img;

private LightImage tileImg;

private LightImage backImg;

private LightImage back2img;

private LightImage back3Img;

private LightImage hitImg;

private LightImage numImg;

private LightImage poseImg;

private int countClick;

private boolean onClick;

private int cNum;

private int score;

private int time;

private int life;

private int combo;

private int combo_h;

private int stage;

private long nowTime;

private long nextTime;

private int dGauge;

private boolean dashf;

private long w_nowTime;

private long w_nextTime;

private boolean w_timef;

private long[] ew_nowTime;

private long[] ew_nextTime;

private boolean[] ew_timef;

private long d_nowTime;

private long d_nextTime;

private boolean d_timef;

private long com_nowTime;

private long com_nextTime;

private boolean com_timef;

private long hit_nowTime;

private long hit_nextTime;

private boolean enterf;

private boolean d_se;

private boolean posef;

private int gameST;

private int cursorPos;

private int cursorCount;

private Map map;

private Coin coin;

private Item item;

private Enemy enemy;

private Player player;

public static final int TITLE = 11;

public static final int GAME_S = 21;

public static final int GAME = 31;

public static final int GAME_OVER = 41;

public static final int GAME_CLEAR = 51;

public static final int SCORE = 61;

public static final int MENU = 81;

public static final int SHO = 91;

public static final String PASS = "";

public static final String PASS2 = "/";

public static final int NORMAL_JUMP = 0;

public static final int E_DETH_JUMP = 1;

private ACTKey enterKey;

private ACTKey leftKey;

private ACTKey rightKey;

private ACTKey dashKey;

private ACTKey jumpKey;

private ACTKey downKey;

private ACTKey poseKey;

//private EmulatorListener emulatorListener;

private boolean initGame;

public void onLoad() {

//	LTexture.AUTO_LINEAR();
	this.gameST = MENU;
	this.cursorPos = 0;
	this.cursorCount = 0;
	this.stage = 0;
	this.w_nowTime = System.currentTimeMillis();
	this.backImg = new LightImage("assets/tra.png");
	this.back2img = new LightImage("assets/city.png");
	this.back3Img = new LightImage("assets/cave.png");
	this.poseImg = new LightImage("assets/pose.png");
	this.enterf = true;
	this.leftKey = new ACTKey();
	this.rightKey = new ACTKey();
	this.dashKey = new ACTKey();
	this.enterKey = new ACTKey(1);
	this.jumpKey = new ACTKey(1);
	this.downKey = new ACTKey(1);
	this.poseKey = new ACTKey(1);
}

public synchronized void draw(MyCanvas g) {

	if (gameST != MENU && !initGame) {
		this.blockImg = new LightImage("assets/block.png");
		this.iboxImg = new LightImage("assets/ibox.png");
		this.coinImg = new LightImage("assets/coin.png");
		this.heroImg = new LightImage(GraphicsUtils.filterColorToWhite("assets/hero.png",
				Color.BLACK));
		this.upbarImg = new LightImage("assets/upbar.png");
		this.underbarImg = new LightImage("assets/underbar.png");
		this.enemyImgE = new LightImage(GraphicsUtils.filterColorToWhite("assets/e1.png",
				Color.BLACK));
		this.enemyImgF = new LightImage(GraphicsUtils.filterColorToWhite("assets/e2.png",
				Color.BLACK));
		this.springImg = new LightImage("assets/spring.png");
		this.goalImg = new LightImage("assets/goal.png");
		this.titleStImg = new LightImage("assets/title_st.png");
		this.retryStImg = new LightImage("assets/retry_st.png");
		this.gaugeImg = new LightImage("assets/bar2.png");
		this.itemImg = new LightImage("assets/honey.png");
		this.halfImg = new LightImage("assets/half2.png");
		this.tileImg = new LightImage("assets/tile.png");
		this.tile1Img = new LightImage("assets/tile1.png");
		this.stickImg = new LightImage("assets/stick.png");
		this.hitImg = new LightImage("assets/hit.png");
		this.numImg = new LightImage("assets/num.png");
//		if (emulatorListener == null) {
//			emulatorListener = new EmulatorListener() {
//
//				public void onCancelClick() {
//					poseKey.press();
//
//				}
//
//				public void onCircleClick() {
//
//				}
//
//				public void onDownClick() {
//					downKey.press();
//				}
//
//				public void onLeftClick() {
//					leftKey.press();
//				}
//
//				public void onRightClick() {
//					rightKey.press();
//				}
//
//				public void onUpClick() {
//					jumpKey.press();
//				}
//
//				public void onSquareClick() {
//					enterKey.press();
//				}
//
//				public void onTriangleClick() {
//					dashKey.press();
//				}
//
//				public void unCancelClick() {
//					poseKey.release();
//				}
//
//				public void unCircleClick() {
//
//				}
//
//				public void unDownClick() {
//					downKey.release();
//				}
//
//				public void unLeftClick() {
//					leftKey.release();
//				}
//
//				public void unRightClick() {
//					rightKey.release();
//				}
//
//				public void unSquareClick() {
//					enterKey.release();
//				}
//
//				public void unTriangleClick() {
//					dashKey.release();
//				}
//
//				public void unUpClick() {
//					jumpKey.release();
//				}
//
//			};
//			setEmulatorListener(emulatorListener);
//			EmulatorButtons ebs = getEmulatorButtons();
//			if (ebs != null) {
//				ebs.getCircle().disable(true);
//				ebs.getTriangle().setY(ebs.getTriangle().getY() + 120);
//				ebs.getSquare().setY(ebs.getSquare().getY() + 110);
//				ebs.getCancel().setY(ebs.getCancel().getY() + 90);
//			}
//		}
		this.initGame = true;
	} else if (gameST == MENU) {
//		setRepaintMode(SCREEN_CANVAS_REPAINT);
//		EmulatorButtons ebs = getEmulatorButtons();
//		if (ebs != null && ebs.isVisible()) {
//			ebs.setVisible(false);
//		}
	} else if (gameST != MENU) {
//		setRepaintMode(SCREEN_NOT_REPAINT);
//		EmulatorButtons ebs = getEmulatorButtons();
//		if (ebs != null && !ebs.isVisible()) {
//			ebs.setVisible(true);
//		}
	}
//	if (timer.action(super.elapsedTime)) {
	if (timer.action(((GameModel)gameModel).getInterval())) {

		switch (this.gameST) {

		case 21:
			this.map = new Map(this.blockImg, this.stickImg, this.iboxImg,
					this.springImg, this.goalImg, this.halfImg,
					this.tile1Img, this.tileImg, this.map_st);
			this.coin = new Coin(this.coinImg, this.map);
			this.item = new Item(this.itemImg, this.map);
			this.enemy = new Enemy(this.enemyImgE, this.enemyImgF, this.map);
			this.player = new Player(this.s_X, this.s_Y, this.heroImg,
					this.map);
			this.ew_nowTime = new long[this.map.getECount()];
			this.ew_nextTime = new long[this.map.getECount()];
			this.ew_timef = new boolean[this.map.getECount()];
			this.combo = 1;
			this.combo_h = 0;
			this.score = 0;
			this.cNum = 0;
			if (this.map_st == "map_h.dat") {
				this.life = 5;
			} else {
				this.life = 3;
			}

			this.time = 401000;
			this.nowTime = System.currentTimeMillis();
			this.nextTime = 0L;
			this.dGauge = 200;
			this.dashf = false;
			this.w_nowTime = 0L;
			this.w_nextTime = 0L;
			this.w_timef = false;
			this.d_nowTime = 0L;
			this.d_nextTime = 0L;
			this.d_timef = false;
			this.com_nowTime = 0L;
			this.com_nextTime = 0L;
			this.com_timef = false;
			this.hit_nowTime = 0L;
			this.hit_nextTime = 0L;
			this.d_se = false;

			this.posef = false;

			this.gameST = 31;
			break;
		case 31:
			if (this.poseKey.isPressed()) {
				if (this.posef) {
					this.posef = false;
					this.nowTime = System.currentTimeMillis();
					this.w_nowTime = System.currentTimeMillis();
				} else {
					this.posef = true;
				}
			}

			if (this.posef) {
				this.cursorCount = 2;
				if (this.enterKey.isPressed()) {
					switch (this.cursorPos) {
					case 0:
						ACTWavSound.getInstance().enter();
						this.gameST = 21;
						break;
					case 1:
						ACTWavSound.getInstance().enter();
						this.gameST = MENU;
					}

					this.map.setClearf();
					ACTWavSound.getInstance().enter();
					this.map = new Map(this.blockImg, this.stickImg,
							this.iboxImg, this.springImg, this.goalImg,
							this.halfImg, this.tile1Img, this.tileImg,
							this.map_st);
					this.coin = new Coin(this.coinImg, this.map);
					this.item = new Item(this.itemImg, this.map);
					this.enemy = new Enemy(this.enemyImgE, this.enemyImgF,
							this.map);
					this.player = new Player(this.s_X, this.s_Y,
							this.heroImg, this.map);
				}

				if (this.jumpKey.isPressed()) {
					ACTWavSound.getInstance().select();
					--this.cursorPos;
					if (this.cursorPos < 0) {
						this.cursorPos = this.cursorCount - 1;
					}
				} else if (this.downKey.isPressed()) {
					ACTWavSound.getInstance().select();
					++this.cursorPos;
					if (this.cursorPos > this.cursorCount - 1) {
						this.cursorPos = 0;
					}
				}
			}

			if (!this.player.getDeth() && !this.posef) {
				if (this.leftKey.isPressed()) {
					this.player.leftmove();
				} else if (this.rightKey.isPressed()) {
					this.player.rightmove();
				} else {
					this.player.stop();
				}

				if (this.jumpKey.isPressed()) {
					this.player.jump(0);
				}

				if (this.downKey.isPressed()) {
					if (this.d_timef) {
						this.d_nextTime = System.currentTimeMillis();
						if (this.d_nextTime - this.d_nowTime <= 500L) {
							this.player.onDown();
							this.d_timef = false;
						} else if (this.d_nextTime - this.d_nowTime > 500L) {
							this.d_timef = false;
						}
					} else {
						this.d_nowTime = System.currentTimeMillis();
						this.d_timef = true;
					}
				}

				this.player.dash(this.dashf);
			}

			if (!this.posef) {
				if (this.w_timef) {
					if (this.w_nextTime - this.w_nowTime > 500L) {
						this.player.update();
					}
				} else {
					this.player.update();
				}

				if (!this.player.getDeth()) {
					int e;
					for (e = 0; e < this.map.getECount(); ++e) {
						this.enemy.update(e);
					}

					for (e = 0; e < this.map.getICount(); ++e) {
						this.item.update(e);
					}

					if (this.dashKey.isPressed() && this.dGauge > 0) {
						if (!this.d_se) {
							ACTWavSound.getInstance().dash();
							this.d_se = true;
						}

						this.dGauge -= 3;
						this.dashf = true;
					} else {
						this.dashf = false;
					}

					if (!this.dashKey.isPressed() && this.dGauge < 200) {
						this.d_se = false;
						this.dGauge += 2;
					}

					for (e = this.coin.getFirstY(); e < this.coin
							.getLastY(); ++e) {
						for (int j = this.coin.getFirstX(); j < this.coin
								.getLastX(); ++j) {
							if (this.coin.collideWith(this.player, e, j)) {
								this.coin.del(e, j);
								++this.cNum;
								if (this.cNum == 100) {
									ACTWavSound.getInstance().up();
									this.cNum = 0;
									++this.life;
								} else {
									ACTWavSound.getInstance().coin();
								}
							}
						}
					}

					for (e = 0; e < this.map.getICount(); ++e) {
						if (this.map.getCount(e) == 1
								&& this.item.collideWith(this.player, e)) {
							this.score += 1000;
							ACTWavSound.getInstance().item();
							this.item.del(e);
							this.player.setItemGetf(true);
						}
					}

					for (e = 0; e < this.map.getECount(); ++e) {
						if ((!this.enemy.collideWith(this.player, e) || this.player
								.getY() >= this.enemy.getY(e))
								&& !this.ew_timef[e]) {
							if (this.enemy.collideWith(this.player, e)
									&& !this.player.getTimef()) {
								if (!this.player.getItemGetf()) {
									this.player.setDethf(true);
									this.player.dethJump();
									ACTWavSound.getInstance().deth();
									this.w_nowTime = System
											.currentTimeMillis();
									this.w_timef = true;
								} else {
									ACTWavSound.getInstance().item_d();
									this.player.setItemGetf(false);
									this.player.setNowTime(System
											.currentTimeMillis());
									this.player.setTimef(true);
								}
							}
						} else if (this.ew_timef[e]) {
							this.ew_nextTime[e] = System
									.currentTimeMillis();
							if (this.enemy.getDethf(e)
									&& this.ew_nextTime[e]
											- this.ew_nowTime[e] > 1000L) {
								this.enemy.del(e);
								this.ew_timef[e] = false;
							} else if (!this.enemy.getDethf(e)) {
								this.ew_timef[e] = false;
							}
						} else {
							if (this.com_timef) {
								this.com_nextTime = System
										.currentTimeMillis();
								if (this.com_nextTime - this.com_nowTime < 2500L) {
									++this.combo;
									this.com_nowTime = this.com_nextTime;
								} else {
									this.combo = 1;
									this.com_nowTime = this.com_nextTime;
								}
							} else {
								this.com_nowTime = System
										.currentTimeMillis();
								this.com_timef = true;
							}

							if (this.combo > this.combo_h) {
								this.combo_h = this.combo;
							}

							if (this.combo >= 20) {
								if (this.combo % 5 == 0) {
									++this.life;
									ACTWavSound.getInstance().up();
								} else {
									ACTWavSound.getInstance().e_deth();
								}
							} else {
								ACTWavSound.getInstance().e_deth();
							}

							if (this.enemy.getScore(e) * this.combo > 1000) {
								this.score += 1000;
							} else {
								this.score += this.enemy.getScore(e)
										* this.combo;
							}

							this.enemy.setCombo(e, this.combo);
							this.player.jump(1);
							this.enemy.setStf(e);
							this.ew_nowTime[e] = System.currentTimeMillis();
							this.hit_nowTime = System.currentTimeMillis();
							this.ew_timef[e] = true;
						}
					}
				}

				if (this.player.getDeth()) {
					if (this.w_timef) {
						this.w_nextTime = System.currentTimeMillis();
						if (this.w_nextTime - this.w_nowTime > 2000L) {
							this.reset();
							this.w_timef = false;
						}
					} else {
						ACTWavSound.getInstance().deth();
						this.w_nowTime = System.currentTimeMillis();
						this.w_timef = true;
					}
				}

				if (this.time / 1000 <= 0) {
					if (this.w_timef) {
						this.w_nextTime = System.currentTimeMillis();
						if (this.w_nextTime - this.w_nowTime > 2000L) {
							this.reset();
							this.w_timef = false;
						}
					} else {
						this.player.setDethf(true);
						this.player.dethJump();
						this.w_nowTime = System.currentTimeMillis();
						this.w_timef = true;
					}
				}

				if (this.map.getClear()) {
					if (this.w_timef) {
						this.w_nextTime = System.currentTimeMillis();
						if (this.w_nextTime - this.w_nowTime > 1000L) {
							this.gameST = 51;
							this.w_nowTime = System.currentTimeMillis();
						}
					} else {
						ACTWavSound.getInstance().goal();
						this.w_nowTime = System.currentTimeMillis();
						this.w_timef = true;
					}
				}
			}
			break;
		case 41:
			if (this.enterKey.isPressed()) {
				ACTWavSound.getInstance().enter();
				this.gameST = 61;
				this.cursorPos = 0;
			}
			break;
		case 51:
			if (this.enterKey.isPressed()) {
				ACTWavSound.getInstance().enter();
				this.gameST = 61;
				this.cursorPos = 0;
			}
			break;
		case 61:
			this.cursorCount = 0;
			ACTWavSound.getInstance().enter();
			this.map.setClearf();
			ACTWavSound.getInstance().enter();
			this.map = new Map(this.blockImg, this.stickImg, this.iboxImg,
					this.springImg, this.goalImg, this.halfImg,
					this.tile1Img, this.tileImg, this.map_st);
			this.coin = new Coin(this.coinImg, this.map);
			this.item = new Item(this.itemImg, this.map);
			this.enemy = new Enemy(this.enemyImgE, this.enemyImgF, this.map);
			this.player = new Player(this.s_X, this.s_Y, this.heroImg,
					this.map);
		case MENU:
			this.cursorCount = 5;
			if (this.enterKey.isPressed()) {
				switch (this.cursorPos) {
				case 1:
					ACTWavSound.getInstance().enter();
					this.map_st = "map_t.dat";
					this.stage = 0;
					this.s_X = 32;
					this.s_Y = CommonUtil.screenWidth;
					this.gameST = 21;
					break;
				case 2:
					ACTWavSound.getInstance().enter();
					this.map_st = "map_e.dat";
					this.stage = 1;
					this.s_X = 64;
					this.s_Y = 64;
					this.gameST = 21;
					break;
				case 3:
					ACTWavSound.getInstance().enter();
					this.map_st = "map.dat";
					this.stage = 2;
					this.s_X = 64;
					this.s_Y = 1536;
					this.gameST = 21;
					break;
				case 4:
					ACTWavSound.getInstance().enter();
					this.map_st = "map_h.dat";
					this.stage = 3;
					this.s_X = 64;
					this.s_Y = 64;
					this.gameST = 21;
				}
			}

			if (this.jumpKey.isPressed()) {
				ACTWavSound.getInstance().select();
				--this.cursorPos;
				if (this.cursorPos < 0) {
					this.cursorPos = this.cursorCount - 1;
				}
			} else if (this.downKey.isPressed()) {
				ACTWavSound.getInstance().select();
				++this.cursorPos;
				if (this.cursorPos > this.cursorCount - 1) {
					this.cursorPos = 0;
				}
			}
			break;
		}
	}

	switch (this.gameST) {

	case 31:
	case 41:
		switch (stage) {
		case 0:
			g.drawImage(this.backImg, 0, 0);
			break;
		case 1:
			g.drawImage(this.back2img, 0, 0);
			break;
		case 2:
			g.drawImage(this.back2img, 0, 0);
			break;
		case 3:
			g.drawImage(this.back3Img, 0, 0);
			break;
		}
		int offsetX = 320 - this.player.getX();
		offsetX = Math.min(offsetX, 0);
		offsetX = Math.max(offsetX, CommonUtil.screenWidth
				- this.map.getWidth());
		int offsetY = 224 - this.player.getY();
		offsetY = Math.min(offsetY, 0);
		offsetY = Math.max(offsetY, CommonUtil.screenHeight
				- this.map.getHeight() - 32);
		this.coin.draw(((GameModel)gameModel).getInterval(), g, offsetX, offsetY);
		this.item.draw(g, offsetX, offsetY);
		if (this.player.getDeth()) {
			this.map.draw(g, offsetX, offsetY);
			this.enemy.draw(((GameModel)gameModel).getInterval(), g, offsetX, offsetY);
			if (this.gameST == 31) {
				this.player.draw(((GameModel)gameModel).getInterval(), g, offsetX, offsetY);
			}
		} else {
			if (this.gameST == 31
					&& (!this.player.getTimef() || (this.player
							.getNextTimef() - this.player.getNowTime()) % 10L >= 2L)) {
				this.player.draw(((GameModel)gameModel).getInterval(), g, offsetX, offsetY);
			}

			this.map.draw(g, offsetX, offsetY);
			this.enemy.draw(((GameModel)gameModel).getInterval(), g, offsetX, offsetY);
			this.enemy.drawScore(g, offsetX, offsetY);
		}

		if (Config.fps >= 15) {
			g.drawImage(this.upbarImg, 0, 0);
//			g.setColor(GLColor.white);
//			g.setFont(LFont.getDefaultFont());
			g.drawText("" + this.life, 16, 24);
			g.drawText(NumberUtils.addZeros(this.score, 8), 105, 24);

			if (this.cNum < 10) {
				g.drawText("0" + this.cNum, 289, 24);
			} else if (this.cNum < 100) {
				g.drawText("" + this.cNum, 289, 24);
			}

			if (this.time / 1000 > 0) {
				if (this.gameST == 31 && !this.posef) {
					this.nextTime = System.currentTimeMillis();
					this.time = (int) ((long) this.time - (this.nextTime - this.nowTime));
					this.nowTime = this.nextTime;
				}
				if (this.time / 1000 < 100) {
//					g.setColor(GLColor.red);
				}
				if (this.time / 1000 < 10) {
					g.drawText("00" + this.time / 1000, 400, 24);
				} else if (this.time / 1000 < 100) {
					g.drawText("0" + this.time / 1000, 400, 24);
				} else {
					g.drawText("" + this.time / 1000, 400, 24);
				}
			} else {
//				g.setColor(GLColor.red);
				g.drawText("000", 400, 24);
			}
//			g.setFont(LFont.getFont(15));
			this.hit_nextTime = System.currentTimeMillis();
			if (this.hit_nextTime - this.hit_nowTime < 2500L) {
				if (this.combo < 10) {
					g.drawImage(this.numImg, 370, 40, 32, 32,
							this.combo * 32, 0, this.combo * 32 + 32, 32);
					g.drawImage(this.hitImg, 400, 44);
				} else if (this.combo >= 10) {
					g.drawImage(this.numImg, 370, 40, 32, 32,
							this.combo % 10 * 32, 0,
							this.combo % 10 * 32 + 32, 32);
					g.drawImage(this.numImg, 347, 40, 32, 32,
							this.combo / 10 * 32, 0,
							this.combo / 10 * 32 + 32, 32);
					g.drawImage(this.hitImg, 400, 44);
				}
			}

			int top = CommonUtil.screenHeight - 32;

//			g.setColor(GLColor.darkGray);
			g.fillRect(0, top, CommonUtil.screenWidth, 32, null);
			if (this.dGauge < 75) {
				g.drawImage(this.gaugeImg, 57, top, this.dGauge, 385, 0,
						32, this.dGauge, 64);
			} else if (this.dashf) {
				g.drawImage(this.gaugeImg, 57, top, this.dGauge, 32, 0,
						64, this.dGauge, 96);
			} else {
				g.drawImage(this.gaugeImg, 57, top, this.dGauge, 32, 0,
						0, this.dGauge, 32);
			}

			g.drawImage(this.underbarImg, 0, CommonUtil.screenHeight
					- underbarImg.getBitmap().getHeight());
			if (this.player.getItemGetf()) {
				g.drawImage(this.itemImg, CommonUtil.screenWidth - 33,
						CommonUtil.screenHeight - 24, 16, 16, 0, 0, 32, 32);
			}

//			g.setFont(LFont.getDefaultFont());
//			g.setColor(GLColor.white);
			g.drawText("STAGE " + this.stage, 300, CommonUtil.screenHeight - 8);
			if (this.posef) {
				g.drawImage(this.poseImg, 0, 0);
				g.drawText("[X] CANCEL", 170, 160);
				if (this.cursorPos == 0) {
					g.drawImage(this.retryStImg, 205, 220, 96, 32, 0, 32,
							96, 64);
				} else {
					g.drawImage(this.retryStImg, 205, 220, 96, 32, 0, 0,
							96, 32);
				}
				if (this.cursorPos == 1) {
					g.drawImage(this.titleStImg, 202, 270, 96, 32, 0, 32,
							96, 64);
				} else {
					g.drawImage(this.titleStImg, 202, 270, 96, 32, 0, 0,
							96, 32);
				}
			}

			if (this.gameST == 41) {
				this.w_nextTime = System.currentTimeMillis();
				if (this.w_nextTime - this.w_nowTime > 600L) {
					if (this.enterf) {
						this.enterf = false;
						this.w_nowTime = this.w_nextTime;
					} else {
						this.enterf = true;
						this.w_nowTime = this.w_nextTime;
					}
				}
			}
		}
		break;
	case 51:
		this.w_nextTime = System.currentTimeMillis();
		if (this.w_nextTime - this.w_nowTime > 600L) {
			if (this.enterf) {
				this.enterf = false;
				this.w_nowTime = this.w_nextTime;
			} else {
				this.enterf = true;
				this.w_nowTime = this.w_nextTime;
			}
		}
		break;

	case MENU:
		drawMenu(g);
		break;
	}

}

private LightImage howWinImg;

private LightImage howPlayImg;

private LightImage traiPlayImg;

private LightImage traiWinImg;

private LightImage stage1Img;

private LightImage stage2Img;

private LightImage stage3Img;

private LightImage stage1WinImg;

private LightImage stage2WinImg;

private LightImage stage3WinImg;

private RectF howButton;

private RectF traiButton;

private RectF s1Button;

private RectF s2Button;

private RectF s3Button;

private boolean initMenu;

private int menuStateX;

private int menuStateY;

final void drawMenu(MyCanvas g) {
	if (!initMenu) {
		int menuLeft = 5;
		int menuTop = 15;
		int width = 150;
		int height = 40;
		this.menuStateX = menuLeft + width;
		this.menuStateY = 5;
		this.howButton = GraphicsUtils.createRectF(menuLeft, menuTop, width, height);
		menuTop += height;
		this.traiButton = GraphicsUtils.createRectF(menuLeft, menuTop, width, height);
		menuTop += height;
		this.s1Button = GraphicsUtils.createRectF(menuLeft, menuTop, width, height);
		menuTop += height;
		this.s2Button = GraphicsUtils.createRectF(menuLeft, menuTop, width, height);
		menuTop += height;
		this.s3Button = GraphicsUtils.createRectF(menuLeft, menuTop, width, height);
		menuTop += height;
		this.howWinImg = new LightImage("assets/how_win.png");
		this.howPlayImg = new LightImage("assets/how.png");
		this.traiPlayImg = new LightImage("assets/trai.png");
		this.traiWinImg = new LightImage("assets/trai_win.png");
		this.stage1Img = new LightImage("assets/stage1.png");
		this.stage2Img = new LightImage("assets/stage2.png");
		this.stage3Img = new LightImage("assets/stage3.png");
		this.stage1WinImg = new LightImage("assets/stage1_win.png");
		this.stage2WinImg = new LightImage("assets/stage2_win.png");
		this.stage3WinImg = new LightImage("assets/stage3_win.png");
		this.initMenu = true;
	}

	if (this.cursorPos == 0) {
		g.drawImage(this.howWinImg, menuStateX, menuStateY);
		g.drawImage(this.howPlayImg, howButton.left, howButton.top,
				howButton.width(), howButton.height(), 0, howButton.height(),
				howButton.width(), howButton.height() * 2);
	} else {
		g.drawImage(this.howPlayImg, howButton.left, howButton.top,
				howButton.width(), howButton.height(), 0, 0, howButton.width(),
				howButton.height());
	}

	if (this.cursorPos == 1) {
		g.drawImage(this.traiWinImg, menuStateX, menuStateY);
		g.drawImage(this.traiPlayImg, traiButton.left, traiButton.top,
				traiButton.width(), traiButton.height(), 0, traiButton.height(),
				traiButton.width(), traiButton.height() * 2);
	} else {
		g.drawImage(this.traiPlayImg, traiButton.left, traiButton.top,
				traiButton.width(), traiButton.height(), 0, 0,
				traiButton.width(), traiButton.height());
	}

	if (this.cursorPos == 2) {
		g.drawImage(this.stage1WinImg, menuStateX, menuStateY);
		g.drawImage(this.stage1Img, s1Button.left, s1Button.top,
				s1Button.width(), s1Button.height(), 0, s1Button.height(),
				s1Button.width(), s1Button.height() * 2);
	} else {
		g.drawImage(this.stage1Img, s1Button.left, s1Button.top,
				s1Button.width(), s1Button.height(), 0, 0, s1Button.width(),
				s1Button.height());
	}

	if (this.cursorPos == 3) {
		g.drawImage(this.stage2WinImg, menuStateX, menuStateY);
		g.drawImage(this.stage2Img, s2Button.left, s2Button.top,
				s2Button.width(), s2Button.height(), 0, s2Button.height(),
				s2Button.width(), s2Button.height() * 2);
	} else {
		g.drawImage(this.stage2Img, s2Button.left, s2Button.top,
				s2Button.width(), s2Button.height(), 0, 0, s2Button.width(),
				s2Button.height());
	}

	if (this.cursorPos == 4) {
		g.drawImage(this.stage3WinImg, menuStateX, menuStateY);
		g.drawImage(this.stage3Img, s3Button.left, s3Button.top,
				s3Button.width(), s3Button.height(), 0, s3Button.height(),
				s3Button.width(), s3Button.height() * 2, null);
	} else {
		g.drawImage(this.stage3Img, s3Button.left, s3Button.top,
				s3Button.width(), s3Button.height(), 0, 0, s3Button.width(),
				s3Button.height(), null);
	}

}



private LTimer timer = new LTimer(60);

public void reset() {
	--this.life;
	if (this.score > 100000) {
		this.score = (int) ((double) this.score - (double) this.score * 0.3D);
	} else {
		this.score = (int) ((double) this.score - (double) this.score * 0.2D);
	}

	if (this.score < 0) {
		this.score = 0;
	}

	if (this.life < 0) {
		++this.life;
		this.gameST = 41;
		this.w_nowTime = System.currentTimeMillis();
	} else {
		this.time = 401000;
		this.player.setDethf(false);
		this.dGauge = 200;
		this.player.deth();
		this.player.setItemGetf(false);
		this.enemy = new Enemy(this.enemyImgE, this.enemyImgF, this.map);
		this.item = new Item(this.itemImg, this.map);
		this.map.countReset();
	}

}


    Panda panda;
    PlatformFactory platformFactory = new PlatformFactory(0, 0, false);
//    lazy var sound = SoundManager()
    Background bg = new Background(0, 0, false);
    AppleFactory appleFactory;
    LabelLayer scoreLab = new LabelLayer(0, 0, false); 
    LabelLayer appLab  = new LabelLayer(0, 0, false); 
    LabelLayer myLabel  = new LabelLayer(0, 0, false); 
    int appleNum = 0;
    
    float moveSpeed = 15.0f;
    float maxSpeed = 50.0f;
    float distance = 0.0f;
    float lastDis = 0.0f;
    float theY = 0.0f;
    boolean isLose = false;
	boolean isReadyToJump = false;

    
	public GameScene(Context context, String id, int level) {
		super(context, id, level);
		// TODO Auto-generated constructor stub
		this.addAutoDraw(bg);
		
        int skyColor = Color.argb(255, 113, 197, 207);
        this.setBackgroundColor(skyColor);
        scoreLab.getPaint().setTextAlign(Align.LEFT);
        scoreLab.setPosition(20, 150);
        scoreLab.setText("run: 0 km");
        this.addAutoDraw(scoreLab);
        
        appLab.getPaint().setTextAlign(Align.LEFT);
        appLab.setPosition(400, 150);
        appLab.setText("eat: apple");
        this.addAutoDraw(appLab);
        
        myLabel.setText("");
        myLabel.setTextSize(100);
        myLabel.setzPosition(100);
        myLabel.setAutoHWByText();
        LayerParam layerParam = new LayerParam();
        layerParam.setPercentageX(0.5f);
        layerParam.setEnabledPercentagePositionX(true);
        myLabel.setLayerParam(new LayerParam());
        myLabel.setPosition(CommonUtil.screenWidth/2, CommonUtil.screenHeight/2);
        myLabel.setAnchorPoint(0.5f, 0);
        this.addAutoDraw(myLabel);
        
        panda = new Panda(210, 100, true);
        
        appleFactory = new AppleFactory(0, 0, CommonUtil.screenWidth, false);
        this.addAutoDraw(appleFactory);
        
//        self.addChild(panda)
        this.addAutoDraw(platformFactory);
        platformFactory.setScreenWdith(CommonUtil.screenWidth);
        platformFactory.setProtocolMainscreen(this);
        platformFactory.createPlatform(3, 0, CommonUtil.screenHeight - 400);

        AudioUtil.playBackgroundMusic();
        
        isEnableRemoteController(false);
        
        onLoad();

	}
	GameView gameView;
	
	
void checkCollistion(){
	
	for(Platform platform : platformFactory.getPlatforms()){
	if(platform.isEnable() && RectF.intersects(panda.getCollisionRectF(), platform.getFrame())){
//        if (contact.bodyA.categoryBitMask | contact.bodyB.categoryBitMask) == (BitMaskType.platform | BitMaskType.panda){
//		Log.e("Collistion", panda.getCollisionRectF() + ":" + platform.getFrame());
        boolean isDown = false;
        boolean canRun = false;
        
        panda.setY(platform.getY()-panda.h);
        
            if (platform.isDown) {
                isDown = true;
                platform.setEnable(false);
                platform.setHidden(true);
//                platform.physicsBody!.dynamic = true
//                platform.physicsBody!.collisionBitMask = 0
            }else if (platform.isShock) {
            	platform.isShock = false;
//                downAndUp(platform, 50, 0.2f, -100, 1, true);
            }
            if (panda.getY() < platform.getY()) {
                canRun=true;
            }
            

        
	        panda.jumpEnd = panda.getY();
//	        if (panda.jumpEnd-panda.jumpStart <= -70) {
	        if (panda.jumpEnd-panda.jumpStart >= 70) {
	            panda.roll();
	            AudioUtil.playRoll();
	            
	            if (!isDown) {
	            	downAndUp(panda,50,0.05f,-50,0.1f,false);
	                downAndUp(platform,50,0.05f,-50,0.1f,false);
	                Log.e("Run", "DownUp");
	            }
	            
	        }else{
	            if (canRun) {
	                panda.run();
	            }
	            
	        }
	        
	      //落地后jumpstart数据要设为当前位置，防止自由落地计算出错
	        panda.jumpStart = panda.getY();
	        		break;
		}
	}
	
	//熊猫和苹果碰撞
	for(Sprite apple : appleFactory.arrApple){
		if (apple.isEnable() && RectF.intersects(panda.getCollisionRectF(), apple.getFrame())){
		    AudioUtil.playEat();
		    this.appleNum++;
		    apple.setHidden(true);
		}
	}

}

public void downAndUp(final Sprite sprite,float down, float downTime, float up, float upTime, boolean isRepeat){
    MovementAction downAct = MAction.moveByY(down, (long)(downTime*1000));
    MovementAction upAct = MAction.moveByY(up, (long)(upTime*1000));
    MovementAction downUpAct = MAction2.sequence(new MovementAction[]{downAct,upAct});
    downUpAct.setMovementActionController(new MovementAtionController());
    if (isRepeat) {
    	sprite.runMovementActionAndAppend(MAction.repeatForever(downUpAct));
    }else {
    	sprite.runMovementActionAndAppend(downUpAct);
    }
}

	@Override
	public void initGameView(Activity activity, IGameController gameController,
			IGameModel gameModel) {
		// TODO Auto-generated method stub
		gameView = new GameView(activity, gameController, gameModel);
	}

	public void action(){
//		gameDog.alone();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			onClick = false;

			switch (gameST) {
			case MENU:
				if (howButton != null) {
					if (howButton.contains(event.getX(), event.getY())) {
						cursorPos = 0;
					}
				}
				if (traiButton != null) {
					if (traiButton.contains(event.getX(), event.getY())) {
						onClick = true;
						cursorPos = 1;
					}
				}
				if (s1Button != null) {
					if (s1Button.contains(event.getX(), event.getY())) {
						onClick = true;
						cursorPos = 2;
					}
				}
				if (s2Button != null) {
					if (s2Button.contains(event.getX(), event.getY())) {
						onClick = true;
						cursorPos = 3;
					}
				}
				if (s3Button != null) {
					if (s3Button.contains(event.getX(), event.getY())) {
						onClick = true;
						cursorPos = 4;
					}
				}
				break;
			}

			if (onClick) {
				if (countClick > 0) {
					enterKey.press();
					countClick = 0;
				}
				countClick++;
			}
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			if (enterKey.isPressed()) {
				enterKey.release();
			}
		}
		
		LayerManager.onTouchLayers(event);
		return true;
	}
	
	private void checkGameOver(){
	    if (panda.getX() + panda.w < 0 || panda.getY() > CommonUtil.screenHeight) {
		    System.out.println("game over");
		    myLabel.setText("game over");
		    AudioUtil.playDead();
		    isLose = true;
		    AudioUtil.stopBackgroundMusic();
	    }
	}
	
	//重新开始游戏
	public void reSet(){
        isLose = false;
        panda.setPosition(200, 400);
        panda.reset();
        myLabel.setText("");
        moveSpeed  = 15.0f;
        distance = 0.0f;
        lastDis = 0.0f;
        appleNum = 0;
        platformFactory.reset();
        appleFactory.reSet();
        platformFactory.createPlatform(3, 0, 400);
        AudioUtil.playBackgroundMusic();
    }
	
	@Override
	public void process() {
		// TODO Auto-generated method stub
		if (isLose) {
			//do nothing
        }else{
        	LayerManager.processLayers();
        	
            if (panda.getX() < 200 && !panda.isDisableAutoForward) {
                float x = panda.getX() + 1;
                panda.setX(x);
            }
            panda.isDisableAutoForward = false;
            distance += moveSpeed;
            lastDis -= moveSpeed;
            float tempSpeed = 5+(int)(distance/2000);
            if (tempSpeed > maxSpeed) {
                tempSpeed = maxSpeed;
            }
            if (moveSpeed < tempSpeed) {
                moveSpeed = tempSpeed;
            }
            
            if (lastDis < 0) {
                platformFactory.createPlatformRandom();
            }
            distance += moveSpeed;
            int runKM = (int)((distance/1000*10)/10);
            scoreLab.setText("run: " + runKM + "km");
            appLab.setText("eat: " + appleNum + "apple");
            platformFactory.move(moveSpeed, panda);
            bg.move(moveSpeed/5);
            appleFactory.move(moveSpeed);
            appleFactory.process();
            
            checkCollistion();
            
            boolean isOnGround = false;
            for(Platform platform : platformFactory.getPlatforms()){
            	if(!platform.isHidden() && panda.getY()==platform.getY()-panda.h && platform.getX() <= panda.getCollisionRectF().left + panda.getCollisionRectF().width() && platform.getX() + platform.w >= panda.getCollisionRectF().left){
            		isOnGround = true;
            		break;
            	}
            }
            if(!isOnGround)
            	panda.down();
            
            if(isReadyToJump){
            	panda.jump(platformFactory);
            	isReadyToJump = false;
            }
            
            checkGameOver();
        }
	}
	
	@Override
	public void onGetData(float dist, float theY) {
		// TODO Auto-generated method stub
		this.lastDis = dist;
		this.theY = theY;
        appleFactory.theY = theY;
	}

	@Override
	public void doDraw(Canvas canvas) {
		// TODO Auto-generated method stub
//		LayerManager.drawLayers(canvas, null);
		
		MyCanvas myCanvas = new MyCanvas(canvas);
		draw(myCanvas);
		
//		fight.drawSelf(canvas, null);
	}

	@Override
	public void beforeGameStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arrangeView(Activity activity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setActivityContentView(Activity activity) {
		// TODO Auto-generated method stub
		activity.setContentView(gameView);
	}

	@Override
	public void afterGameStart() {
		// TODO Auto-generated method stub
		Log.e("game scene", "game start");
		AudioUtil.playBackgroundMusic();
	}
	
	@Override
	protected void beforeGameStop() {
		// TODO Auto-generated method stub
		Log.e("game scene", "game stop");
		AudioUtil.stopBackgroundMusic();
	}
	
	@Override
	protected void afterGameStop() {
		// TODO Auto-generated method stub
//		AudioUtil.stopBackgroundMusic();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(ButtonLayer buttonLayer) {
		// TODO Auto-generated method stub

	}


}
