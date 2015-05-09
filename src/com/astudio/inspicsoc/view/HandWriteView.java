
package com.astudio.inspicsoc.view;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.activity.ImageFilterGraffitiActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class HandWriteView extends View
{
    private Paint paint = null;
    private Bitmap originalBitmap = null;
    private Bitmap new1Bitmap = null;
    private Bitmap new2Bitmap = null;
    private float clickX = 0,clickY = 0;
    private float startX = 0,startY = 0;
    private boolean isMove = true;
    private boolean isClear = false;
    private int color = Color.WHITE;
    private float strokeWidth = 10.0f;
    private float pianyiX,pianyiY;
    private float mScreenWidth,mScreenHeight;
    private float SCALE;
	public HandWriteView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		SCALE = context.getResources().getDisplayMetrics().density;
	}
	
	public void setImageBitmap(Bitmap value){
		originalBitmap = value.copy(Bitmap.Config.ARGB_8888, true);
		new1Bitmap = Bitmap.createBitmap(originalBitmap);
		mScreenWidth=ImageFilterGraffitiActivity.mMaxWidth;
		mScreenHeight=ImageFilterGraffitiActivity.mMaxHeight;
		
		int valuePixels = (int)(40 * SCALE + 0.5f); 
		pianyiX=(mScreenWidth-valuePixels-originalBitmap.getWidth())/2;
		pianyiY=(mScreenHeight-valuePixels-originalBitmap.getHeight())/2;
	}

	public void setColor(int value){
		color=value;
	}
	
	public void setStrokeWidth(float value){
		strokeWidth=value;
	}

    public void clear(){
    	isClear = true;
    	new2Bitmap = Bitmap.createBitmap(originalBitmap);
    	invalidate();
    }
    public void setstyle(float strokeWidth){
    	this.strokeWidth = strokeWidth;
    }
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawBitmap(HandWriting(new1Bitmap), pianyiX, pianyiY,null);
	}
	
	public Bitmap getResult(){
		return new1Bitmap;
	}

	public Bitmap HandWriting(Bitmap originalBitmap)
	{
		Canvas canvas = null;
		
		if(isClear){
			canvas = new Canvas(new2Bitmap);
		}
		else{
			canvas = new Canvas(originalBitmap);
		}
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		paint.setColor(color);
		paint.setStrokeWidth(strokeWidth);
		if(isMove){
			canvas.drawLine(startX, startY, clickX, clickY, paint);
		}
		
		startX = clickX;
		startY = clickY;
		
		if(isClear){
			return new2Bitmap;
		}
		return originalBitmap;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		clickX = event.getX() - pianyiX;
		clickY = event.getY() - pianyiY;
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			
			isMove = false;
			invalidate();
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE){
			
			isMove = true;
			invalidate();
			return true;
		}
		
		return super.onTouchEvent(event);
	}
   
}
