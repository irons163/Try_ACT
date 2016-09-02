package com.example.try_run;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;

import com.example.try_gameengine.framework.CommonUtil;

public class BitmapUtil {
	static Context context;
	
public static Bitmap bar;
	public static Bitmap s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,dot;
	public static Bitmap common,happy,away;
	
	public static void initBitmap(Context context){
		BitmapUtil.context = context;
//		initBitmap();
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		options.inJustDecodeBounds = false;
		
		s0 = BitmapFactory.decodeResource(context.getResources(), R.drawable.s0);
		s1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.s1);
		s2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.s2);
		s3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.s3);
		s4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.s4);
		s5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.s5);
		s6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.s6);
		s7 = BitmapFactory.decodeResource(context.getResources(), R.drawable.s7);
		s8 = BitmapFactory.decodeResource(context.getResources(), R.drawable.s8);
		s9 = BitmapFactory.decodeResource(context.getResources(), R.drawable.s9);
		dot = BitmapFactory.decodeResource(context.getResources(), R.drawable.dot);
		
		common = BitmapFactory.decodeResource(context.getResources(), R.drawable.common);
		happy = BitmapFactory.decodeResource(context.getResources(), R.drawable.happy);
		away = BitmapFactory.decodeResource(context.getResources(), R.drawable.away);
	}
	
	public static void resizeToolBmp(){
		
	}
	
	public static Bitmap createSpecificSizeBitmap(Drawable drawable, int width, int height) {
		
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap); 
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas); 
		return bitmap;
	}
	
	public static Bitmap getBitmap(String path) {
		try {
			InputStream is = context.getAssets().open(path);

			return BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Bitmap getBitmapFromRes(int resId){
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
		return bitmap;
	}
	
	public static Bitmap createBitmap(Bitmap bmp, float scale){
		
		Matrix matrix = new Matrix(); 
		
		matrix.postScale(scale, scale); 
			
		Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmp.getWidth(), 
		
				bmp.getHeight(),matrix,true);
		return resizeBmp; 
	}
	
	public static void createTimeCounterBitmap(int w, int h){
		float scaleX, scaleY, scale; 
		scaleX = (float)w/s0.getWidth();
		scaleY = (float)h/s0.getHeight();
		
		scale = scaleX < scaleY ? scaleX : scaleY;
		
		s0 = createBitmap(s0, scale);
		s1 = createBitmap(s1, scale);
		s2 = createBitmap(s2, scale);
		s3 = createBitmap(s3, scale);
		s4 = createBitmap(s4, scale);
		s5 = createBitmap(s5, scale);
		s6 = createBitmap(s6, scale);
		s7 = createBitmap(s7, scale);
		s8 = createBitmap(s8, scale);
		s9 = createBitmap(s9, scale);
		dot = createBitmap(dot, scale);

	}
	
}
