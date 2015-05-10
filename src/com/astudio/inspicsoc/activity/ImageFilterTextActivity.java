package com.astudio.inspicsoc.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.utils.PhotoUtil;

public class ImageFilterTextActivity extends InsActivity implements OnClickListener{
	private Button mCancel;
	private Button mDetermine;
	private ImageView mDisplay;
	private RelativeLayout mDisplayLayout;
	private EditText mEditText1;
	private ImageButton mOKBtn;
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
    private ImageButton smallBtn;
    private ImageButton middleBtn;
    private ImageButton bigBtn;
	
    public float mPosStartX;
    public float mPosStartY;

	private String mPath;// 图片地址
	private Bitmap mOldBitmap;// 旧图片
	private Bitmap mNewBitmap;// 新图片
	private int mTextId = 0;// 边框编号
	
	private boolean mIsMeasured;// 是否已经计算大小
	public static float mMaxWidth;// 图片最大宽度
	public static float mMaxHeight;// 图片最大高度

	private String mTextStr = null;
	private int mTextSize = 20;
	
	private int mScreenWidth;
	private int mScreenHeight;
	private int valuePixels;
	private float SCALE;
	private float pianyiX;
	private float pianyiY;
	
	private int mColor;
	private InputMethodManager imm;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagefilter_text_activity);
		findViewById();
		setListener();
		SCALE = this.getResources().getDisplayMetrics().density;
        DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		valuePixels = (int)(40 * SCALE + 0.5f);
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
		imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
	}

	private void findViewById() {
		mCancel = (Button) findViewById(R.id.imagefilter_text_cancel);
		mDetermine = (Button) findViewById(R.id.imagefilter_text_determine);
		mDisplay = (ImageView) findViewById(R.id.imagefilter_text_display);
		mDisplayLayout = (RelativeLayout) findViewById(R.id.imagefilter_text_display_layout);
		mEditText1 = (EditText)findViewById(R.id.edittext1);
        mOKBtn = (ImageButton)findViewById(R.id.okbtn);
        whiteBtn = (ImageButton)findViewById(R.id.white1);  
	    yellowBtn = (ImageButton)findViewById(R.id.yellow1);  
	    pinkBtn = (ImageButton)findViewById(R.id.pink1);  
	    redBtn = (ImageButton)findViewById(R.id.red1);  
	    silverBtn = (ImageButton)findViewById(R.id.silver1);  
	    grayBtn = (ImageButton)findViewById(R.id.gray1);  
	    purpleBtn = (ImageButton)findViewById(R.id.purple1);  
	    greenBtn = (ImageButton)findViewById(R.id.green1);  
	    blueBtn = (ImageButton)findViewById(R.id.blue1);  
	    blackBtn = (ImageButton)findViewById(R.id.black1);
	    smallBtn = (ImageButton)findViewById(R.id.small);  
	    middleBtn = (ImageButton)findViewById(R.id.middle);  
	    bigBtn = (ImageButton)findViewById(R.id.big);
	}

	private void setListener() {
		mOKBtn.setOnClickListener(this);
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
				if (mTextId == 0) {
					setResult(RESULT_CANCELED);
					finish();
				} else {
					mPath = PhotoUtil.saveToLocal(mNewBitmap);
					Intent intent = new Intent();
					intent.putExtra("path", mPath);
					setResult(RESULT_OK, intent);
					finish();
				}
			}
		});
		mDisplay.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

                System.out.println("mPosStartX"+mPosStartX+" "+mPosStartY);
				mPosStartX = event.getX()-pianyiX;
                mPosStartY = event.getY()-pianyiY;
                System.out.println("EVENT"+event.getX()+" "+event.getY());
                switch (action) {
					case MotionEvent.ACTION_DOWN:
						
						return true;
					case MotionEvent.ACTION_MOVE:      	
						drawText();
						return true;
					case MotionEvent.ACTION_UP:
						
						return true;
                }
				
				return false;
			}
		});
		
		smallBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mTextSize = 50;
				drawText();
			}
		});
		
		middleBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mTextSize = 100;
				drawText();
			}
		});
		
		bigBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mTextSize = 150;
				drawText();
			}
		});
		
		whiteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mColor=Color.WHITE;
				drawText();
			}
		});
	    yellowBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mColor=Color.YELLOW;
				drawText();
			}
		});
	    pinkBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mColor=0xffffc0cb;
				drawText();
			}
		}); 
	    redBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mColor=Color.RED;
				drawText();
			}
		});
	    silverBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mColor=0xffc0c0c0;
				drawText();
			}
		});
	    grayBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mColor=Color.GRAY;
				drawText();
			}
		});
	    purpleBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mColor=0xff800080;
				drawText();
			}
		}); 
	    greenBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mColor=Color.GREEN;
				drawText();
			}
		});
	    blueBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mColor=Color.BLUE;
				drawText();
			}
		});
	    blackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mColor=Color.BLACK;
				drawText();
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
		pianyiX=(mMaxWidth-valuePixels-mOldBitmap.getWidth())/2;
		pianyiY=(mMaxHeight-valuePixels-mOldBitmap.getHeight())/2;
		mTextSize = 100;
		mColor=Color.WHITE;
	}

	/**
	 * 返回对话框
	 */
	private void backDialog() {
		AlertDialog.Builder builder = new Builder(ImageFilterTextActivity.this);
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
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.okbtn:
			mTextId=1;
			mPosStartX=valuePixels;
			mPosStartY=valuePixels;
			drawText();
			imm.hideSoftInputFromWindow(mEditText1.getWindowToken(), 0);
			break;

		default:
			break;
		}
	}

    @SuppressLint("NewApi") private void drawText() {
    	
		mTextStr = mEditText1.getText().toString().isEmpty() ? "请输入文字" : mEditText1.getText().toString();
        Bitmap bitmap = generatorContactCountIcon(mOldBitmap);
        mNewBitmap=bitmap;
        mDisplay.setImageBitmap(mNewBitmap);
	}
    
    private Bitmap generatorContactCountIcon(Bitmap icon){
        Bitmap contactIcon = Bitmap.createBitmap(icon.getWidth(), icon.getHeight(), Config.ARGB_8888);    
        Canvas canvas = new Canvas(contactIcon);    
            
        Paint iconPaint = new Paint();    
        iconPaint.setDither(true);   
        iconPaint.setFilterBitmap(true);   
        Rect src = new Rect(0, 0, icon.getWidth(), icon.getHeight());    
        Rect dst = new Rect(0, 0, icon.getWidth(), icon.getHeight());    
        canvas.drawBitmap(icon, src, dst, iconPaint);    
        
        Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DEV_KERN_TEXT_FLAG);    
        countPaint.setColor(mColor);    
        countPaint.setTextSize(mTextSize);
        countPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(mTextStr, mPosStartX, mPosStartY, countPaint);    
        return contactIcon;    
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
