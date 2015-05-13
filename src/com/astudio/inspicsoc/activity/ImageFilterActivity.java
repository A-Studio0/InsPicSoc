package com.astudio.inspicsoc.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.utils.ActivityForResultUtil;
import com.astudio.inspicsoc.utils.PhotoUtil;

/**
 * 图片编辑类
 * 
 * @author rendongwei
 * 
 */
public class ImageFilterActivity extends InsActivity {
	private Button mCancel;
	private Button mFinish;
	private ImageButton mBack;
	private ImageButton mForward;
	private ImageView mDisplay;
	private Button mCut;
	private Button mEffect;
	private Button mFace;
	private Button mFrame;
	private Button mGraffiti;
	private Button mText;

	private String mOldPath;// 旧图片地址
	private Bitmap mOldBitmap;// 旧图片
	private String mNewPath;// 新图片地址
	private Bitmap mNewBitmap;// 新图片
	private boolean mIsOld = true;// 是否是选择了旧图片
	private boolean mIsSetResult = false;// 是否是要返回数据
	
	private boolean mIsMeasured;// 是否已经计算大小
	public static float mMaxWidth;// 图片最大宽度
	public static float mMaxHeight;// 图片最大高度
	private RelativeLayout mDisplayLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagefilter_activity);
		// this.setBehindContentView(R.layout.main_left_fragment);
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
	}

	private void findViewById() {
		mCancel = (Button) findViewById(R.id.imagefilter_cancel);
		mFinish = (Button) findViewById(R.id.imagefilter_finish);
		mBack = (ImageButton) findViewById(R.id.imagefilter_back);
		mForward = (ImageButton) findViewById(R.id.imagefilter_forward);
		mDisplay = (ImageView) findViewById(R.id.imagefilter_display);
		mCut = (Button) findViewById(R.id.imagefilter_cut);
		mEffect = (Button) findViewById(R.id.imagefilter_effect);
		mFace = (Button) findViewById(R.id.imagefilter_face);
		mFrame = (Button) findViewById(R.id.imagefilter_frame);
		mGraffiti = (Button) findViewById(R.id.imagefilter_graffiti);
		mText = (Button) findViewById(R.id.imagefilter_text);
		mDisplayLayout=(RelativeLayout) findViewById(R.id.imagefilter_display_layout);
	}
	
	public void showToast(){
		Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// Do something.
			showToast();
			this.finish();// 直接调用杀死当前activity方法.
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setListener() {
		mCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showToast();
				// 关闭当前界面
				finish();
			}
		});
		mFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 判断是否要返回数据
				if (mIsSetResult) {
					// 根据是否选择旧图片返回图片地址
					Intent intent = new Intent();
					if (mIsOld) {
						intent.putExtra("path", mOldPath);
					} else {
						intent.putExtra("path", mNewPath);
					}
					setResult(RESULT_OK, intent);
				} else {
					// 根据是否选择旧图片添加一个新的图片并跳转到上传图片界面
					Map<String, String> map = new HashMap<String, String>();
					if (mIsOld) {
						map.put("image_path", mOldPath);
					} else {
						map.put("image_path", mNewPath);
					}
					mKXApplication.mAlbumList.add(map);
					startActivityForResult(new Intent(ImageFilterActivity.this,
							PhotoShareActivity.class),ActivityForResultUtil.REQUESTCODE_MENU);
				}
			}
		});
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 选择旧图片
				mIsOld = true;
				mBack.setImageResource(R.drawable.image_action_back_arrow_normal);
				mForward.setImageResource(R.drawable.image_filter_action_forward_arrow);
				mBack.setEnabled(false);
				mForward.setEnabled(true);
				mDisplay.setImageBitmap(mOldBitmap);
			}
		});
		mForward.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 选择新图片
				mIsOld = false;
				mBack.setImageResource(R.drawable.image_filter_action_back_arrow);
				mForward.setImageResource(R.drawable.image_action_forward_arrow_normal);
				mBack.setEnabled(true);
				mForward.setEnabled(false);
				mDisplay.setImageBitmap(mNewBitmap);
			}
		});
		mCut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到裁剪界面,并传递图片地址
				Intent intent = new Intent();
				intent.setClass(ImageFilterActivity.this,
						ImageFilterCropActivity.class);
				if (mIsOld) {
					intent.putExtra("path", mOldPath);
				} else {
					intent.putExtra("path", mNewPath);
				}
				startActivityForResult(intent,
						ActivityForResultUtil.REQUESTCODE_IMAGEFILTER_CROP);
			}
		});
		mEffect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到特效界面,并传递图片地址
				Intent intent = new Intent();
				intent.setClass(ImageFilterActivity.this,
						ImageFilterEffectActivity.class);
				if (mIsOld) {
					intent.putExtra("path", mOldPath);
				} else {
					intent.putExtra("path", mNewPath);
				}
				startActivityForResult(intent,
						ActivityForResultUtil.REQUESTCODE_IMAGEFILTER_EFFECT);
			}
		});
		mFace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到表情界面,并传递图片地址
				Intent intent = new Intent();
				intent.setClass(ImageFilterActivity.this,
						ImageFilterFaceActivity.class);
				if (mIsOld) {
					intent.putExtra("path", mOldPath);
				} else {
					intent.putExtra("path", mNewPath);
				}
				startActivityForResult(intent,
						ActivityForResultUtil.REQUESTCODE_IMAGEFILTER_FACE);
			}
		});
		mFrame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到边框界面,并传递图片地址
				Intent intent = new Intent();
				intent.setClass(ImageFilterActivity.this,
						ImageFilterFrameActivity.class);
				if (mIsOld) {
					intent.putExtra("path", mOldPath);
				} else {
					intent.putExtra("path", mNewPath);
				}
				startActivityForResult(intent,
						ActivityForResultUtil.REQUESTCODE_IMAGEFILTER_FRAME);
			}
		});
		mGraffiti.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到边框界面,并传递图片地址
				Intent intent = new Intent();
				intent.setClass(ImageFilterActivity.this,
						ImageFilterGraffitiActivity.class);
				if (mIsOld) {
					intent.putExtra("path", mOldPath);
				} else {
					intent.putExtra("path", mNewPath);
				}
				startActivityForResult(intent,
						ActivityForResultUtil.REQUESTCODE_IMAGEFILTER_GRAFFITI);
			}
		});
		mText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到边框界面,并传递图片地址
				Intent intent = new Intent();
				intent.setClass(ImageFilterActivity.this,
						ImageFilterTextActivity.class);
				if (mIsOld) {
					intent.putExtra("path", mOldPath);
				} else {
					intent.putExtra("path", mNewPath);
				}
				startActivityForResult(intent,
						ActivityForResultUtil.REQUESTCODE_IMAGEFILTER_TEXT);
			}
		});
	}

	private void init() {
		// 初始化界面按钮设为不可用
		mBack.setImageResource(R.drawable.image_action_back_arrow_normal);
		mForward.setImageResource(R.drawable.image_action_forward_arrow_normal);
		mBack.setEnabled(false);
		mForward.setEnabled(false);
		// 获取是否返回数据
		mIsSetResult = getIntent().getBooleanExtra("isSetResult", false);
		// 接收传递的图片地址
		mOldPath = getIntent().getStringExtra("path");
		mNewPath = getIntent().getStringExtra("path");
		mOldBitmap = zoom(mKXApplication.getPhoneAlbum(mOldPath));
		mNewBitmap = mOldBitmap;
		mOldPath=PhotoUtil.saveToLocal(mOldBitmap);
		mNewPath=mOldPath;
		// 显示图片
		mDisplay.setImageBitmap(mOldBitmap);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==ActivityForResultUtil.REQUESTCODE_MENU&&resultCode==RESULT_OK){
				Intent i=new Intent();
				i.setClass(ImageFilterActivity.this,MenuActivity.class);
				setResult(RESULT_OK,i);
				finish();
		}else if(requestCode==ActivityForResultUtil.REQUESTCODE_MENU&&resultCode!=RESULT_OK){
			finish();
		}
		if (resultCode == RESULT_OK) {
			// 接收修改后的图片地址,并更新
			if (mIsOld) {
				mNewPath = data.getStringExtra("path");
				mNewBitmap = mKXApplication.getPhoneAlbum(mNewPath);
			} else {
				mOldPath = mNewPath;
				mOldBitmap = mNewBitmap;
				mNewPath = data.getStringExtra("path");
				mNewBitmap = mKXApplication.getPhoneAlbum(mNewPath);
			}
			mIsOld = false;
			mBack.setImageResource(R.drawable.image_filter_action_back_arrow);
			mForward.setImageResource(R.drawable.image_action_forward_arrow_normal);
			mBack.setEnabled(true);
			mForward.setEnabled(false);
			mDisplay.setImageBitmap(mNewBitmap);
		}
	}
}
