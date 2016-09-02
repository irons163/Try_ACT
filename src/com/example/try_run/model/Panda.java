package com.example.try_run.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.drm.DrmStore.Playback;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.util.Log;

import com.example.try_gameengine.action.MAction;
import com.example.try_gameengine.action.MAction2;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionInfo;
import com.example.try_gameengine.action.MovementActionItemAnimate;
import com.example.try_gameengine.action.MovementActionItemBaseReugularFPS;
import com.example.try_gameengine.action.MovementActionItemBaseReugularFPS.FrameTimesType;
import com.example.try_gameengine.action.MovementAtionController;
import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.stage.StageManager;
import com.example.try_run.R;

public class Panda extends Sprite{
	public enum Status{
		run, jump, jump2,roll
	}
	
	public Panda(float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
		// TODO Auto-generated constructor stub
		init();
	}

    //定义跑，跳，滚动等动作动画
//    Bitmap runAtlas = SKTextureAtlas(named: "run.atlas")
    List<Bitmap> runFrames = new ArrayList<Bitmap>();
//    List<Bitmap> jumpAtlas = SKTextureAtlas(named: "jump.atlas")
    		List<Bitmap> jumpFrames = new ArrayList<Bitmap>();
//    				List<Bitmap> rollAtlas = SKTextureAtlas(named: "roll.atlas")
    		List<Bitmap> rollFrames = new ArrayList<Bitmap>();
    //增加跳起的逼真效果动画
//    List<Bitmap> jumpEffectAtlas = SKTextureAtlas(named: "jump_effect.atlas");
    List<Bitmap> jumpEffectFrames = new ArrayList<Bitmap>();
    List<Bitmap> downFrames = new ArrayList<Bitmap>();
    Sprite jumpEffect;
    
    public Status status = Status.run;
    public float jumpStart = 1000.0f;
    public float jumpEnd = 0.0f;
    public boolean isDisableAutoForward;
    
    public void init(){
    	Context context = StageManager.getCurrentStage();
    	//跑
    	Bitmap runTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_run_01);
    	runFrames.add(runTexture);
    	runTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_run_02);
    	runFrames.add(runTexture);
    	runTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_run_03);
    	runFrames.add(runTexture);
    	runTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_run_04);
    	runFrames.add(runTexture);
    	runTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_run_05);
    	runFrames.add(runTexture);
    	runTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_run_06);
    	runFrames.add(runTexture);
    	runTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_run_07);
    	runFrames.add(runTexture);
    	runTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_run_08);
    	runFrames.add(runTexture);
    	//跳
    	Bitmap jumpTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_jump_01);
    	jumpFrames.add(jumpTexture);
    	jumpTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_jump_02);
    	jumpFrames.add(jumpTexture);
    	jumpTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_jump_03);
    	jumpFrames.add(jumpTexture);
    	jumpTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_jump_04);
    	jumpFrames.add(jumpTexture);
    	jumpTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_jump_05);
    	jumpFrames.add(jumpTexture);
    	jumpTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_jump_06);
    	jumpFrames.add(jumpTexture);
    	jumpTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_jump_07);
    	jumpFrames.add(jumpTexture);
    	jumpTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_jump_08);
    	jumpFrames.add(jumpTexture);
    	//滚
    	Bitmap rollTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_roll_01);
    	rollFrames.add(rollTexture);
    	rollTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_roll_02);
    	rollFrames.add(rollTexture);
    	rollTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_roll_03);
    	rollFrames.add(rollTexture);
    	rollTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_roll_04);
    	rollFrames.add(rollTexture);
    	rollTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_roll_05);
    	rollFrames.add(rollTexture);
    	rollTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_roll_06);
    	rollFrames.add(rollTexture);
    	rollTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_roll_07);
    	rollFrames.add(rollTexture);
    	rollTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_roll_08);
    	rollFrames.add(rollTexture);
    	// 跳的时候的点缀效果
    	Bitmap jumpEffectTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.jump_effect_01);
    	jumpEffectFrames.add(jumpEffectTexture);
    	jumpEffectTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.jump_effect_02);
    	jumpEffectFrames.add(jumpEffectTexture);
    	jumpEffectTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.jump_effect_03);
    	jumpEffectFrames.add(jumpEffectTexture);
    	jumpEffectTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.jump_effect_04);
    	jumpEffectFrames.add(jumpEffectTexture);
    	//DOWN
    	Bitmap downTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_jump_04);
    	downFrames.add(downTexture);
    	downTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_jump_05);
    	downFrames.add(downTexture);
    	downTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_jump_06);
    	downFrames.add(downTexture);
    	downTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_jump_07);
    	downFrames.add(downTexture);
    	downTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.panda_jump_08);
    	downFrames.add(downTexture);
    	
//        Bitmap texture = runAtlas.textureNamed("panda_run_01")
    	 Bitmap texture = runFrames.get(0);
        PointF size = new PointF(texture.getWidth(), texture.getHeight());
        setCollisionRectFEnable(true);
        setBitmapAndAutoChangeWH(texture);
        setCollisionRectF(getCollisionRectF().left+5, getCollisionRectF().top, getCollisionRectF().right-5, getCollisionRectF().bottom);
        setCollisionOffsetX(5);
        setCollisionRectFWidth(getCollisionRectF().width());
        
//        super.init(texture: texture, color: SKColor.whiteColor(), size: size);
        //跑
//        for (int i = 1; i<=runFrames.size(); i++) {
//            String tempName = String.format("panda_run_%.2d", i);
//            Bitmap runTexture = runAtlas.textureNamed(tempName);
//            
//            if (runTexture != nil) {
//                runFrames.append(runTexture)
//            }
//        }
        
//        //跳
//        for var i = 1; i<=jumpAtlas.textureNames.count; i++ {
//            let tempName = String(format: "panda_jump_%.2d", i)
//            let jumpTexture = jumpAtlas.textureNamed(tempName)
//            
//            if (jumpTexture != nil) {
//                jumpFrames.append(jumpTexture)
//            }
//        }
//        //滚
//        for var i = 1; i<=rollAtlas.textureNames.count; i++ {
//            let tempName = String(format: "panda_roll_%.2d", i)
//            let rollTexture = rollAtlas.textureNamed(tempName)
//            
//            if (rollTexture != nil) {
//                rollFrames.append(rollTexture)
//            }
//        }
//        // 跳的时候的点缀效果
//        for var i=1 ; i <= jumpEffectAtlas.textureNames.count ; i++ {
//            let tempName = String(format: "jump_effect_%.2d", i)
//            let effectexture = jumpEffectAtlas.textureNamed(tempName)
//            if (effectexture != nil) {
//                jumpEffectFrames.append(effectexture)
//            }
//        }
        jumpEffect = new Sprite(-80,0,false);
        jumpEffect.setBitmapAndAutoChangeWH(jumpEffectFrames.get(0));
//        jumpEffect.position = CGPointMake(-80, -30)
//        jumpEffect.hidden = true
        jumpEffect.setHidden(true);
        this.addChild(jumpEffect);
        
//        self.physicsBody = SKPhysicsBody(rectangleOfSize: size)
//        self.physicsBody?.dynamic = true
//        self.physicsBody?.allowsRotation = false
//        self.physicsBody?.restitution = 0.1 //反弹力
//        self.physicsBody?.categoryBitMask = BitMaskType.panda
//        self.physicsBody?.contactTestBitMask = BitMaskType.scene | BitMaskType.platform | BitMaskType.apple
//        self.physicsBody?.collisionBitMask = BitMaskType.platform
//        self.zPosition = 20
        run();
        
    }

//    required init?(coder aDecoder: NSCoder) {
//        fatalError("init(coder:) has not been implemented")
//    }
    
    public void run(){
    	if(this.currentAction!=null && this.getActionName()!=null && this.getActionName().equals("run"))
    		return;
        //清楚所有动作
//    	if(this.action!=null)
//    		this.action.controller.cancelAllMove();
    	removeAllMovementActions();
    	
        this.status = Status.run;
        //重复跑动动作
//        this.runAction(SKAction.repeatActionForever(SKAction.animateWithTextures(runFrames, timePerFrame: 0.05)))
        
//        if(this.currentAction!=null && this.getActionName()!=null && !this.getActionName().equals("run")){
        	this.addActionFPS("run", (Bitmap[]) runFrames.toArray(new Bitmap[runFrames.size()]), new int[]{2,2,2,2,2,2,2,2}, true);
            this.setAction("run");
//        }    
        Log.e("Panda", "run.");
    }
    public void jump(final PlatformFactory platformFactory){
//        this.removeAllActions();
    	
        if (status != Status.jump2) {
//        	if(this.action!=null)
//            	this.action.controller.cancelAllMove();
        	removeAllMovementActions();
            //Adds an action to the list of actions executed by the node.
            //Creates an action that animates changes to a sprite’s texture.
            
//            self.runAction(SKAction.animateWithTextures(jumpFrames, timePerFrame: 0.05),withKey:"jump")
        	this.addActionFPS("jump", (Bitmap[]) jumpFrames.toArray(new Bitmap[jumpFrames.size()]), new int[]{2,2,2,2,2,2,2,2}, false);
            this.setAction("jump");
            //The physics body’s velocity vector, measured in meters per second.
//            self.physicsBody?.velocity = CGVectorMake(0, 450)
            MovementActionItemBaseReugularFPS jump = new MovementActionItemBaseReugularFPS(new MovementActionInfo(40, 2, 0, -60, "jump", true));
            jump.setMovementActionController(new MovementAtionController());
            jump.setTimerOnTickListener(new MovementAction.TimerOnTickListener() {
				
				@Override
				public void onTick(float dx, float dy) {
					// TODO Auto-generated method stub
					boolean isChanged = false;
					for(Platform platform : platformFactory.getPlatforms()){
//						if(Panda.this.getFrame().top + Panda.this.h >= platform.getFrame().top && Panda.this.getFrame().top <= platform.getFrame().top + platform.h){
//							if(dx>0){
//								if(Panda.this.getFrame().left + Panda.this.w + dx >= platform.getFrame().left){
//									dx = platform.getFrame().left - (Panda.this.getFrame().left + Panda.this.w);
//									isChanged = true;
//								}
//							}
////							else if(dx<0){
////								if(Panda.this.getFrame().left + Panda.this.w + dx >= platform.getFrame().left && Panda.this.getFrame().left + Panda.this.w < platform.getFrame().left){
////									dx = platform.getFrame().left - (Panda.this.getFrame().left + Panda.this.w);
////									isChanged = true;
////								}
////							}
//							
//						}
						if(Panda.this.getFrame().left + Panda.this.w >= platform.getFrame().left && Panda.this.getFrame().left <= platform.getFrame().left + platform.w){
							if(dy>0){
								if(Panda.this.getFrame().top + Panda.this.h + dy > platform.getFrame().top && Panda.this.getFrame().top  + Panda.this.h <= platform.getFrame().top + platform.h){
									dy = platform.getFrame().top - (Panda.this.getFrame().top + Panda.this.h) + 1;
//									Panda.this.setY(platform.getY()-Panda.this.h);
//									dy = 0;
									isChanged = true;
								}
							}else if(dy<0){
								if(Panda.this.getFrame().top + dy <= platform.getFrame().top + platform.h && Panda.this.getFrame().top >= platform.getFrame().top){
									dy = platform.getFrame().top + platform.h - Panda.this.getFrame().top;
									isChanged = true;
								}

							}
						}	
						
						if(isChanged)
							break;
					}
				
					Panda.this.move(dx, dy);
					Log.e("Panda", "jump " + dy);
				}
			});
            jump.setName("jump");
            jump.setFrameTimesType(FrameTimesType.FrameTimesIntervalAfterAction);
            this.runMovementAction(jump);
            Log.e("Panda", "jump.");
            
            if (status == Status.jump) {
//                this.runAction(SKAction.animateWithTextures(rollFrames, timePerFrame: 0.05))
            	this.addActionFPS("roll", (Bitmap[]) rollFrames.toArray(new Bitmap[rollFrames.size()]), new int[]{2,2,2,2,2,2,2,2}, false);
                this.setAction("roll");
                status = Status.jump2;
                this.jumpStart = this.getY();
            }else {
                showJumpEffect();
                status = Status.jump;
            }
        }
    }
    public void roll(){
//        self.removeAllActions()
//    	if(this.action!=null)
//        this.action.controller.cancelAllMove();
    	removeAllMovementActions();
    	
    	this.status = Status.roll;
//        self.runAction(SKAction.animateWithTextures(rollFrames, timePerFrame: 0.05),completion:{
//            self.run()
//            })
    	
//    	MovementAction roll = MAction.sequence(new MovementAction[]{MAction.animateAction((Bitmap[]) rollFrames.toArray(new Bitmap[rollFrames.size()]), 0.05f), MAction.runBlockNoDelay(new MAction.MActionBlock() {
//			
//			@Override
//			public void runBlock() {
//				// TODO Auto-generated method stub
//				Log.e("Panda", "run after roll.");
//				Panda.this.run();
//				
//			}
//		})});
    	MovementAction roll = MAction2.sequence(new MovementAction[]{MAction.animateAction((Bitmap[]) rollFrames.toArray(new Bitmap[rollFrames.size()]), 0.05f), MAction.runBlockNoDelay(new MAction.MActionBlock() {
			
			@Override
			public void runBlock() {
				// TODO Auto-generated method stub
				Log.e("Panda", "run after roll.");
				Panda.this.run();
				
			}
		})});
    	roll.setName("roll");
    	this.runMovementAction(roll);
    	Log.e("Panda", "roll.");
    }
    
    void showJumpEffect(){
        jumpEffect.setHidden(false);
//        var ectAct = SKAction.animateWithTextures( jumpEffectFrames, timePerFrame: 0.05)
//        var removeAct = SKAction.runBlock({() in
//            self.jumpEffect.hidden = true
//        })
//        // 执行两个动作，先显示，后隐藏
//        jumpEffect.runAction(SKAction.sequence([ectAct,removeAct]))
        
        jumpEffect.runMovementAction(MAction.sequence(new MovementAction[]{MAction.animateAction((Bitmap[]) jumpEffectFrames.toArray(new Bitmap[jumpEffectFrames.size()]), 0.05f), MAction.runBlockNoDelay(new MAction.MActionBlock() {
			
			@Override
			public void runBlock() {
				// TODO Auto-generated method stub
				jumpEffect.setHidden(true);
			}
		})}));
    }
    
    public void down(){
//    	if(this.action!=null && this.action.getName().equals("roll") && !this.action.isFinish())
//    		this.action.controller.cancelAllMove();
    	if(this.action!=null && (this.action.getName().equals("jump") || this.action.getName().equals("down")) && !this.action.isFinish())
    		return;

    	this.addActionFPS("down", (Bitmap[]) downFrames.toArray(new Bitmap[downFrames.size()]), new int[]{2,2,2,2,2}, false);
        this.setAction("down");
        MovementAction down = new MovementActionItemBaseReugularFPS(new MovementActionInfo(40, 5, 0, 0, "down", true));
        down.setMovementActionController(new MovementAtionController());
        down.setTimerOnTickListener(new MovementAction.TimerOnTickListener() {
			
			@Override
			public void onTick(float dx, float dy) {
				// TODO Auto-generated method stub
				Panda.this.move(dx, dy);
			}
		});
        down.setName("down");
        this.runMovementAction(down);
        Log.e("Panda", "down.");
    }
    
    public void reset(){
    	if(currentAction!=null)
    		currentAction = null;
//    	if(action!=null)
//    		action.controller.cancelAllMove();
    	removeAllMovementActions();
    }
}
