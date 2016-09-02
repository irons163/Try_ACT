package com.example.try_run.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.stage.StageManager;
import com.example.try_run.BitmapUtil;
import com.example.try_run.R;
import com.example.try_run.utils.CommonUtil;

public class Background extends Layer{
	//近处的背景
	List<Sprite> arrBG = new ArrayList<Sprite>();
    //远处的背景
	List<Sprite> arrFar = new ArrayList<Sprite>();
    
//    public Background {
//        super.init()
    public Background(float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
		
//        Bitmap farTexture = SKTexture(imageNamed: "background_f1")
		Context context = StageManager.getCurrentStage();
		Bitmap farTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_f1);
    	
		Sprite farBg0 = new Sprite(0, 0, false);
		farBg0.setBitmapAndAutoChangeWH(farTexture);
//        farBg0.position.y = 150
//        farBg0.zPosition = 9
//        farBg0.anchorPoint = CGPointMake(0, 0)
        farBg0.setY(CommonUtil.screenHeight - farTexture.getHeight() - 150);

//        var farBg1 = SKSpriteNode(texture: farTexture)
//        farBg1.position.y = 150
//        farBg1.zPosition = 9
//        farBg1.anchorPoint = CGPointMake(0, 0)
//        farBg1.position.x = farBg1.frame.width
        Sprite farBg1 = new Sprite(0, 0, false);
        farBg1.setBitmapAndAutoChangeWH(farTexture);
        farBg1.setY(CommonUtil.screenHeight - farTexture.getHeight() - 150);
        farBg1.setX(farBg1.getFrame().width());
        
//        var farBg2 = SKSpriteNode(texture: farTexture)
//        farBg2.position.y = 150
//        farBg2.zPosition = 9
//        farBg2.anchorPoint = CGPointMake(0, 0)
//        farBg2.position.x = farBg2.frame.width*2
        Sprite farBg2 = new Sprite(0, 0, false);
        farBg2.setBitmapAndAutoChangeWH(farTexture);
        farBg2.setY(CommonUtil.screenHeight - farTexture.getHeight()  - 150);
        farBg2.setX(farBg2.getFrame().width()*2);
        
        this.addChild(farBg0);
        this.addChild(farBg1);
        this.addChild(farBg2);
        arrFar.add(farBg0);
         arrFar.add(farBg1);
         arrFar.add(farBg2);
        
//        var texture = SKTexture(imageNamed: "background_f0")
//        var bg0 = SKSpriteNode(texture: texture)
//        bg0.anchorPoint = CGPointMake(0, 0)
//        bg0.position.y = 70
//        bg0.zPosition = 10
         Bitmap texture = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_f0);
//         Bitmap texture = BitmapUtil.createSpecificSizeBitmap(context.getResources().getDrawable(R.drawable.background_f0), CommonUtil.screenWidth, CommonUtil.screenHeight);
//        Sprite bg0 = new Sprite(texture, CommonUtil.screenWidth, CommonUtil.screenHeight, false);
         Sprite bg0 = new Sprite(0, 0, false);
         bg0.setBitmapAndAutoChangeWH(texture);
        bg0.setY(CommonUtil.screenHeight - texture.getHeight());
        
//        var bg1 = SKSpriteNode(texture: texture)
//        bg1.anchorPoint = CGPointMake(0, 0)
//        bg1.position.y = 70
//        bg1.zPosition = 10
//        bg1.position.x = bg0.frame.size.width
//        self.addChild(bg0)
//        self.addChild(bg1)
//        arrBG.append(bg0)
//        arrBG.append(bg1)
//        Sprite bg1 = new Sprite(texture, CommonUtil.screenWidth, CommonUtil.screenHeight, false);
        Sprite bg1 = new Sprite(0, 0, false);
        bg1.setBitmapAndAutoChangeWH(texture);
        bg1.setY(CommonUtil.screenHeight - texture.getHeight());
        bg1.setX(bg0.getFrame().width());
        
        this.addChild(bg0);
        this.addChild(bg1);
        arrBG.add(bg0);
        arrBG.add(bg1);
    }

    public void move(float speed){
//        //近景
//        for bg in arrBG {
//            bg.position.x -= speed
//        }
//        if arrBG[0].position.x + arrBG[0].frame.size.width < speed {
//            arrBG[0].position.x = 0
//            arrBG[1].position.x = arrBG[0].frame.size.width
//        }
//        //远景
//        for far in arrFar {
//            far.position.x -= speed/4
//            
//        }
//        if arrFar[0].position.x + arrFar[0].frame.size.width < speed/4 {
//            arrFar[0].position.x = 0
//            arrFar[1].position.x = arrFar[0].frame.size.width
//            arrFar[2].position.x = arrFar[0].frame.size.width * 2
//        }
        for(Sprite bg : arrBG){
        	bg.setX(bg.getX() - speed);
        }
        if(arrBG.get(0).getX() + arrBG.get(0).getFrame().width() < speed){
        	arrBG.get(0).setX(0);
        	arrBG.get(1).setX(arrBG.get(0).getFrame().width());
        }
        
        for(Sprite far : arrFar){
        	far.setX(far.getX() - speed/4);
        }
        if(arrFar.get(0).getX() + arrFar.get(0).getFrame().width() < speed/4){
        	arrFar.get(0).setX(0);
        	arrFar.get(1).setX(arrFar.get(0).getFrame().width());
        	arrFar.get(2).setX(arrFar.get(0).getFrame().width()*2);
        }
    }
}
