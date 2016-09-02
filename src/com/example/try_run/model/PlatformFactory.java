package com.example.try_run.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.stage.StageManager;
import com.example.try_run.R;
import com.example.try_run.utils.CommonUtil;

public class PlatformFactory extends Layer{

	public PlatformFactory(int w, int h, boolean autoAdd) {
		super(w, h, autoAdd);
		// TODO Auto-generated constructor stub
		textureLeft = BitmapFactory.decodeResource(StageManager.getCurrentStage().getResources(), R.drawable.platform_l);
	    textureMid = BitmapFactory.decodeResource(StageManager.getCurrentStage().getResources(), R.drawable.platform_m);
	    textureRight = BitmapFactory.decodeResource(StageManager.getCurrentStage().getResources(), R.drawable.platform_r);
	}

    Bitmap textureLeft;
    Bitmap textureMid;
    Bitmap textureRight;
    
    List<Platform> platforms = new ArrayList<Platform>();
    float screenWdith;
    ProtocolMainscreen delegate;
//    var delegate:ProtocolMainscreen?
    
    // 随机生成一定长度的平台
    public void createPlatformRandom(){
    	Random random = new Random();
        int midNum = random.nextInt()%4 + 1;
        float gap = random.nextInt()%8 + 1;
        float x = screenWdith + midNum*50 + gap + 100;
        float y = CommonUtil.screenHeight - (random.nextInt()%200 + 400);
        
        createPlatform(midNum, x, y);
    }
    
    //生成平台方法
    public void createPlatform(int midNum, float x, float y){
        Platform platform = new Platform(0, 0, false);
        platform.setCollisionRectFEnable(true);
        Sprite platform_left = new Sprite(0, 0, false);
        platform_left.setBitmapAndAutoChangeWH(textureLeft);
//        platform_left.anchorPoint = CGPointMake(0, 0.9);

        Sprite platform_right = new Sprite(0, 0, false);
        platform_right.setBitmapAndAutoChangeWH(textureRight);
//        platform_right.anchorPoint = CGPointMake(0, 0.9)
        
        List<Sprite> arrPlatform = new ArrayList<Sprite>();
        
        arrPlatform.add(platform_left);
        platform.setPosition(x, y);
        
        for (int i = 1; i < midNum; i++) {
            Sprite platform_mid = new Sprite(0, 0, false);
            platform_mid.setBitmapAndAutoChangeWH(textureMid);
//            platform_mid.anchorPoint = CGPointMake(0, 0.9)
            arrPlatform.add(platform_mid);
        }
        
        arrPlatform.add(platform_right);
        platform.onCreate(arrPlatform);
//        platform.name = "platform";
        this.addChild(platform);
        platform.calculateWHByChildern();
        
        platforms.add(platform);
        this.delegate.onGetData(platform.width + x - screenWdith,y);
        
    }
	
  //平台向左移动的方法
    public void move(float speed, Panda panda){
        for(Sprite p : platforms){
        	if(!p.isHidden() && panda.getFrame().top + panda.h > p.getFrame().top && panda.getFrame().top < p.getFrame().top + p.h && p.getX() - speed <= panda.getX() + panda.w && p.getX() + p.w >= panda.getX()){
//        		Log.e("moveCollistion", panda.getX() + ":" + speed);
        		panda.setX(panda.getX() - speed);
        		panda.isDisableAutoForward = true;
//        		Log.e("moveCollistion", panda.getX() + "");
        	}
            p.setPosition(p.getX() - speed, p.getY());
        }
        if(platforms.get(0).getX() < -platforms.get(0).width){
            platforms.get(0).removeFromParent();
            platforms.remove(0);
        }
    }
    //清楚所有的Node
    public void reset(){
        for(ILayer layer : this.getLayers()){
        	this.remove(layer);
        }
        platforms.clear();
    }

	public void setScreenWdith(float screenWdith){
		this.screenWdith = screenWdith;
	}

	public void setProtocolMainscreen(ProtocolMainscreen delegate){
		this.delegate = delegate;
	}
	
	public List<Platform> getPlatforms(){
		return platforms;
	}
	
	@Override
	protected void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
