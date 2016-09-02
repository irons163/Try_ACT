package com.example.try_run.model;

import java.math.BigDecimal;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.stage.StageManager;
import com.example.try_gameengine.utils.GameTimeUtil;
import com.example.try_run.R;

public class AppleFactory extends Layer {
	Bitmap appleTexture;
    float sceneWidth = 0.0f;
    public List<Sprite> arrApple = new ArrayList<Sprite>();
//    Thread timer = NSTimer()
    public float theY = 0.0f;
    private GameTimeUtil createAppleTimeUtil;
    
    public AppleFactory(float x, float y, float sceneWidth, boolean autoAdd) {
		super(x, y, autoAdd);
    	Context context = StageManager.getCurrentStage();
    	appleTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
    	
        this.sceneWidth = sceneWidth;
        this.theY = y;
//        timer = NSTimer.scheduledTimerWithTimeInterval( 0.2, target: self, selector: "createApple", userInfo: nil, repeats: true)
        createAppleTimeUtil = new GameTimeUtil(200);
    }
    
    private void createApple(){
    	Random random = new Random();
        int randomInt = random.nextInt() % 10;
        if (randomInt > 8) {
            Sprite apple = new Sprite(0, 0, false);
            apple.setBitmapAndAutoChangeWH(appleTexture);
//            apple.physicsBody = SKPhysicsBody(rectangleOfSize: apple.size)
//            apple.physicsBody!.restitution = 0
//            apple.physicsBody!.categoryBitMask = BitMaskType.apple
//            apple.physicsBody!.dynamic = false
//            apple.anchorPoint = CGPointMake(0, 0)
//            apple.zPosition = 40
//            apple.position  = CGPointMake(sceneWidth+apple.frame.width , theY + 150)
            apple.setPosition(sceneWidth+apple.w , theY - 150);
            arrApple.add(apple);
            this.addChild(apple);
        }
    }
    
    public void process(){
    	if(createAppleTimeUtil.isArriveExecuteTime()){
    		createApple();
		}
    }
    
    public void move(float speed){
        for (Sprite apple : arrApple) {
            apple.setX(apple.getX() - speed);
        }
        
        if (arrApple.size() > 0 && arrApple.get(0).getX() < -20){
            arrApple.get(0).removeFromParent();
            arrApple.remove(0);
        }
        
    }
    
    public void reSet(){
    	for(ILayer layer : this.getLayers()){
        	this.remove(layer);
        }
        arrApple.clear();
    }
}
