package com.astudio.inspicsoc.activity;

import java.io.IOException;
import java.util.LinkedList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;  
import android.view.ViewTreeObserver.OnPreDrawListener;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.ui.base.FaceImage;
import com.astudio.inspicsoc.utils.PhotoUtil;
import com.astudio.inspicsoc.view.HandWriteView;

public class ImageFilterGraffitiActivity extends InsActivity{
	
	private Button mCancel;
	private Button mDetermine;
	private RelativeLayout mDisplayLayout;
	private HandWriteView mDisplay;
	
	private String mPath;// 图片地址
	private Bitmap mOldBitmap;// 旧图片
	private Bitmap mNewBitmap;// 新图片
//	private int mGraffitiId = 0;// 边框编号
	
	private boolean mIsMeasured;// 是否已经计算大小
	public static float mMaxWidth;// 图片最大宽度
	public static float mMaxHeight;// 图片最大高度
    private ImageButton clear = null;  
    private ImageButton whiteBtn;
    private ImageButton yellowBtn;
    private ImageButton pinkBtn;
    private ImageButton redBtn;
    private ImageButton silverBtn;
    private ImageButton grayBtn;
    private ImageButton purpleBtn;
    private ImageButton greenBtn;
    private ImageButton blueBtn;
    private ImageButton blackBtn;
    private ImageButton thinBtn;
    private ImageButton normalBtn;
    private ImageButton wideBtn;
    
    @Override  
    public void onCreate(Bundle savedInstanceState)  
    {  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagefilter_graffiti_activity); 
        findViewById();
		setListener();
		// 获取RelativeLayout的高度和宽度
		ViewTreeObserver vto = mDisplayLayout.getViewTreeObserver();
		vto.addOnPreDrawListener(new OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				if (mIsMeasured == false) {
					mMaxWidth = mDisplayLayout.getMeasuredWidth();
					mMaxHeight = mDisplayLayout.getMeasuredHeight();
					init();
					mIsMeasured = true;
				}
				return true;
			}
		});
		
          
        clear = (ImageButton)findViewById(R.id.clear);  
        clear.setOnClickListener(new clearListener());  
    }
    
    private void findViewById() {
		mCancel = (Button) findViewById(R.id.imagefilter_graffiti_cancel);
		mDetermine = (Button) findViewById(R.id.imagefilter_graffiti_determine);
		mDisplay = (HandWriteView) findViewById(R.id.imagefilter_graffiti_display);
		mDisplayLayout = (RelativeLayout) findViewById(R.id.imagefilter_graffiti_display_layout);
		whiteBtn = (ImageButton)findViewById(R.id.white);  
	    yellowBtn = (ImageButton)findViewById(R.id.yellow);  
	    pinkBtn = (ImageButton)findViewById(R.id.pink);  
	    redBtn = (ImageButton)findViewById(R.id.red);  
	    silverBtn = (ImageButton)findViewById(R.id.silver);  
	    grayBtn = (ImageButton)findViewById(R.id.gray);  
	    purpleBtn = (ImageButton)findViewById(R.id.purple);  
	    greenBtn = (ImageButton)findViewById(R.id.green);  
	    blueBtn = (ImageButton)findViewById(R.id.blue);  
	    blackBtn = (ImageButton)findViewById(R.id.black);
	    thinBtn = (ImageButton)findViewById(R.id.thin);  
	    normalBtn = (ImageButton)findViewById(R.id.normal);  
	    wideBtn = (ImageButton)findViewById(R.id.wide);
	}

	private void setListener() {
		mCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 返回对话框
				backDialog();
			}
		});
		mDetermine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 如果id为0,代表没做任何操作,则无需返回值,否则则保存当前修改的图片并返回地址
//				if (mGraffitiId == 0) {
//					setResult(RESULT_CANCELED);
//					finish();
//				} else {
					// 保存修改后的图片
					mNewBitmap = mDisplay.getResult();
					// 保存到本地
					mPath = PhotoUtil.saveToLocalPNG(mNewBitmap);
					// 返回图片地址并关闭当前界面
					Intent intent = new Intent();
					intent.putExtra("path", mPath);
					setResult(RESULT_OK, intent);
					finish();
//				}
			}
		});
		
		thinBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDisplay.setStrokeWidth(5.0f);
			}
		});
		
		normalBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDisplay.setStrokeWidth(10.0f);
			}
		});
		
		wideBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDisplay.setStrokeWidth(15.0f);
			}
		});
		
		whiteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDisplay.setColor(Color.WHITE);
			}
		});
	    yellowBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDisplay.setColor(Color.YELLOW);
			}
		});
	    pinkBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDisplay.setColor(0xffffc0cb);
			}
		}); 
	    redBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDisplay.setColor(Color.RED);
			}
		});
	    silverBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDisplay.setColor(0xffc0c0c0);
			}
		});
	    grayBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDisplay.setColor(Color.GRAY);
			}
		});
	    purpleBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDisplay.setColor(0xff800080);
			}
		}); 
	    greenBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDisplay.setColor(Color.GREEN);
			}
		});
	    blueBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDisplay.setColor(Color.BLUE);
			}
		});
	    blackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDisplay.setColor(Color.BLACK);
			}
		});
	}

	private void init() {
		// 获取图片地址
		mPath = getIntent().getStringExtra("path");
		// 获取图片
		mOldBitmap = zoom(mKXApplication.getPhoneAlbum(mPath));
		// 显示图片
		mDisplay.setImageBitmap(mOldBitmap);
	}

	/**
	 * 返回对话框
	 */
	private void backDialog() {
		AlertDialog.Builder builder = new Builder(ImageFilterGraffitiActivity.this);
		builder.setTitle("InsPicSoc");
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setMessage("你确定要取消编辑吗?");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}

	@Override
	public void onBackPressed() {
		// 返回对话框
		backDialog();
	}
       
      
    private class clearListener implements OnClickListener{  
  
        public void onClick(View v)  
        {  
        	mDisplay.clear();  
        }  
    }
    

	/**
	 * 缩放图片
	 * 
	 * @param bitmap
	 *            需要缩放的图片
	 * @return 缩放后的图片
	 */
	public Bitmap zoom(Bitmap bitmap) {
		// 获取图片的高度和宽度
		float width = bitmap.getWidth();
		float height = bitmap.getHeight();
		// 获取40dip的px值
		int padding = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
						.getDisplayMetrics());
		// 设置最大宽度和高度
		float maxWidth = mMaxWidth - padding;
		float maxHeight = mMaxHeight - padding;
		// 判断如果宽度或高度超过最大值,则缩放,否则返回原图片
		if (width > maxWidth || height > maxHeight) {
			// 获取缩放比例
			float scale = getScale(width, height, maxWidth, maxHeight);

			// 缩放后的图片的宽度和高度
			int bitmapWidth = (int) (width * scale);
			int bitmapHeight = (int) (height * scale);
			// 创建缩放的图片
			Bitmap zoomBitmap = Bitmap.createScaledBitmap(bitmap, bitmapWidth,
					bitmapHeight, true);
			return zoomBitmap;
		} else {
			return bitmap;
		}

	}

	/**
	 * 获取缩放比例
	 * 
	 * @param width
	 *            当前图片的宽度
	 * @param height
	 *            当前图片的高度
	 * @param maxWidth
	 *            最大宽度
	 * @param maxHeight
	 *            最大高度
	 * @return
	 */
	private float getScale(float width, float height, float maxWidth,
			float maxHeight) {
		float scaleWidth = maxWidth / width;
		float scaleHeight = maxHeight / height;
		return Math.min(scaleWidth, scaleHeight);
	}

}  