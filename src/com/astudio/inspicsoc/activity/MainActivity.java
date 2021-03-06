package com.astudio.inspicsoc.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.view.slidingmenu.SlidingMenu;
import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.R.drawable;
import com.astudio.inspicsoc.common.InsUrl;
import com.astudio.inspicsoc.model.ActionItem;
import com.astudio.inspicsoc.model.UploadItem;
import com.astudio.inspicsoc.service.UploadUtil;
import com.astudio.inspicsoc.service.UploadUtil.OnUploadProcessListener;
import com.astudio.inspicsoc.utils.ActivityForResultUtil;
import com.astudio.inspicsoc.utils.PhotoUtil;
import com.astudio.inspicsoc.view.TitlePopup;

@SuppressLint("SdCardPath")
public class MainActivity extends InsActivity implements OnClickListener,
		OnUploadProcessListener {

	protected SlidingMenu mSlidingMenu;
	private ImageButton ivTitleBtnLeft;
	protected Context mContext = this;
	protected Activity mActivity = this;
	UploadUtil uploadUtil;

	private int[] selectList;
	private ViewPager viewPager;

	private FragmentManager fm;

	private Button firstPageBtn;
	private Button personalPageBtn;
	private Button cameraBtn;
	private Button messageBtn;
	private Button findBtn;

	private TextView ivTitleName;

	// 定义标题栏上的按钮
	private ImageButton titleBtn;
	// 定义标题栏弹窗按钮
	private TitlePopup titlePopup;

	private int fragmentFlag;
	private Fragment mFrag;// 侧滑栏

	/**
	 * 退出时间
	 */
	private long mExitTime;

	/**
	 * 退出间隔
	 */
	private static final int INTERVAL = 2000;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initSlidingMenu();
		initView();

		firstPageBtn = (Button) findViewById(R.id.tab_rb_a);
		cameraBtn = (Button) findViewById(R.id.camera_btn);
		personalPageBtn = (Button) findViewById(R.id.tab_rb_c);
		messageBtn = (Button) findViewById(R.id.message);
		findBtn = (Button) findViewById(R.id.find);
		ivTitleName = (TextView) findViewById(R.id.ivTitleName);
		selectList = new int[] { 0, 1, 2, 3, 4 };
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		fm = getSupportFragmentManager();
		viewPager.setAdapter(adapter);
		firstPageBtn.setOnClickListener(listener);
		personalPageBtn.setOnClickListener(listener);
		messageBtn.setOnClickListener(listener);
		findBtn.setOnClickListener(listener);
		ivTitleName.setOnClickListener(listener);
		viewPager.setOnPageChangeListener(changeListener);

		uploadUtil = UploadUtil.getInstance();
		uploadUtil.setOnUploadProcessListener(this); // 设置监听器监听上传状态
		// 实例化标题栏弹窗

		titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// 实例化标题栏按钮并设置监听
		titleBtn = (ImageButton) findViewById(R.id.ivTitleBtnRight);
		titleBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// titlePopup.show(v);
			}
		});

		initData();
		/*
		 * OnItemOnClickListener myListener = new OnItemOnClickListener() {
		 * 
		 * @Override public void onItemClick(ActionItem item, int position) {
		 * Intent intent; switch (position) { case 0: intent = new
		 * Intent(MainActivity.this, BaiduMapActivity.class);
		 * mActivity.startActivity(intent); break; case 1: intent = new
		 * Intent(MainActivity.this, PhotoHalfActivity.class);
		 * mActivity.startActivity(intent); break; case 2: intent = new
		 * Intent(MainActivity.this, PhotoCircleActivity.class);
		 * mActivity.startActivity(intent); break; case 3: intent = new
		 * Intent(MainActivity.this, Photo_exchange.class);
		 * mActivity.startActivity(intent); break; case 4: intent = new
		 * Intent(MainActivity.this, ShakeActivity.class);
		 * mActivity.startActivity(intent); break; default: break; } } };
		 * 
		 * titlePopup.setItemOnClickListener(myListener);
		 */
		cameraBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MenuActivity.class);
				mActivity.startActivityForResult(intent,
						ActivityForResultUtil.REQUESTCODE_MENU);
				mActivity.overridePendingTransition(R.anim.activity_open, 0);
			}
		});
		fragmentFlag = 0;
		setFragment(fragmentFlag);
	}

	private FragmentPagerAdapter adapter = new FragmentPagerAdapter(
			getSupportFragmentManager()) {
		@Override
		public int getCount() {
			return selectList.length;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new FirstPage();
				break;
			case 1:
				fragment = new SecondPage();
				break;
			case 2:

				fragment = new MessageFragment(MainActivity.this);
				break;
			case 3:
				fragment = new FindFragment();
				break;
			case 4:
				fragment = new PerCenfragment();
				// PerCenfragment.adapter.notifyDataSetChanged();
				break;

			}
			return fragment;
		}

	};

	private SimpleOnPageChangeListener changeListener = new SimpleOnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			// setSelectedTitle(position);
			if (viewPager.getCurrentItem() == 0) {
				fragmentFlag = 0;
			} else if (viewPager.getCurrentItem() == 1) {
				fragmentFlag = 1;
			} else if (viewPager.getCurrentItem() == 2) {
				fragmentFlag = 2;
			} else if (viewPager.getCurrentItem() == 3) {
				fragmentFlag = 3;
			} else if (viewPager.getCurrentItem() == 4) {
				fragmentFlag = 4;
			}
			setFragment(fragmentFlag);
		}
	};

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tab_rb_a:
				fragmentFlag = 0;
				setFragment(fragmentFlag);
				viewPager.setCurrentItem(0);
				break;
			case R.id.message:
				fragmentFlag = 2;
				setFragment(fragmentFlag);
				viewPager.setCurrentItem(2);
				break;
			case R.id.find:
				fragmentFlag = 3;
				setFragment(fragmentFlag);
				viewPager.setCurrentItem(3);
				break;
			case R.id.tab_rb_c:
				fragmentFlag = 4;
				setFragment(fragmentFlag);
				viewPager.setCurrentItem(4);
				break;
			case R.id.ivTitleName:
				if (fragmentFlag == 0) {
					fragmentFlag = 1;
					viewPager.setCurrentItem(1);
				} else if (fragmentFlag == 1) {
					fragmentFlag = 0;
					viewPager.setCurrentItem(0);
				}
				setFragment(fragmentFlag);
				break;
			}
		}
	};

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		titlePopup.dismiss();
		return super.onTouchEvent(event);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 给标题栏弹窗添加子类
		titlePopup.addAction(new ActionItem(this, "附近的图", R.drawable.nearimg));
		titlePopup.addAction(new ActionItem(this, "拼图", R.drawable.pingtu));
		titlePopup.addAction(new ActionItem(this, "圈圈", R.drawable.quanquan));
		titlePopup.addAction(new ActionItem(this, "照片兑换", R.drawable.changimg));
		titlePopup.addAction(new ActionItem(this, "摇一摇", R.drawable.yaoyiyao));
	}

	private void initView() {
		ivTitleBtnLeft = (ImageButton) this.findViewById(R.id.ivTitleBtnLeft);
		ivTitleBtnLeft.setOnClickListener(this);

	}

	private void initSlidingMenu() {

		int slidingmenu_offset;
		setBehindContentView(R.layout.main_left_layout);
		FragmentTransaction mFragementTransaction = getSupportFragmentManager()
				.beginTransaction();
		mFrag = new LeftSlidingMenuFragment(this, mContext, mKXApplication);
		mFragementTransaction.replace(R.id.main_left_fragment, mFrag);
		mFragementTransaction.commit();
		// customize the SlidingMenu
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT);// 设置是左滑还是右滑，还是左右都可以滑，我这里只做了左滑
		slidingmenu_offset = (int) (0.2 * mScreenWidth);
		mSlidingMenu.setBehindOffset(slidingmenu_offset);// 设置菜单宽度
		mSlidingMenu.setFadeDegree(0.55f);// 设置淡入淡出的比例
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置手势模式
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);// 设置左菜单阴影图片
		mSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
		mSlidingMenu.setBehindScrollScale(0.333f);// 设置滑动时拖拽效果

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivTitleBtnLeft:
			mSlidingMenu.showMenu(true);
			break;
		default:
			break;
		}
	}

	public void setFragment(int flag) {
		switch (flag) {
		case 0:
			ivTitleName.setText("");
			ivTitleName.setPadding(60, 0, 60, 0);
			ivTitleName.setBackgroundResource(drawable.jingxuan);
			firstPageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_index_pressed, 0, 0);
			firstPageBtn.setTextColor(0xff4cc0a4);
			messageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.message_normal, 0, 0);
			messageBtn.setTextColor(0xff767676);
			findBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_menu_find_normal, 0, 0);
			findBtn.setTextColor(0xff767676);
			personalPageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.personinfo_normal, 0, 0);
			personalPageBtn.setTextColor(0xff767676);
			break;
		case 1:
			ivTitleName.setText("");
			ivTitleName.setPadding(60, 0, 60, 0);
			ivTitleName.setBackgroundResource(drawable.guanzhu);
			firstPageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_index_pressed, 0, 0);
			firstPageBtn.setTextColor(0xff4cc0a4);
			messageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.message_normal, 0, 0);
			messageBtn.setTextColor(0xff767676);
			findBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_menu_find_normal, 0, 0);
			findBtn.setTextColor(0xff767676);
			personalPageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.personinfo_normal, 0, 0);
			personalPageBtn.setTextColor(0xff767676);
			break;
		case 2:
			ivTitleName.setBackgroundResource(drawable.text_default);
			ivTitleName.setText("消息");
			firstPageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_index_normal, 0, 0);
			firstPageBtn.setTextColor(0xff767676);
			messageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.message_pressed, 0, 0);
			messageBtn.setTextColor(0xff4cc0a4);
			findBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_menu_find_normal, 0, 0);
			findBtn.setTextColor(0xff767676);
			personalPageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.personinfo_normal, 0, 0);
			personalPageBtn.setTextColor(0xff767676);
			break;
		case 3:
			ivTitleName.setBackgroundResource(drawable.text_default);
			ivTitleName.setText("发现");
			firstPageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_index_normal, 0, 0);
			firstPageBtn.setTextColor(0xff767676);
			messageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.message_normal, 0, 0);
			messageBtn.setTextColor(0xff767676);
			findBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_menu_find_pressed, 0, 0);
			findBtn.setTextColor(0xff4cc0a4);
			personalPageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.personinfo_normal, 0, 0);
			personalPageBtn.setTextColor(0xff767676);
			break;
		case 4:
			ivTitleName.setBackgroundResource(drawable.text_default);
			ivTitleName.setText("个人");
			firstPageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_index_normal, 0, 0);
			firstPageBtn.setTextColor(0xff767676);
			messageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.message_normal, 0, 0);
			messageBtn.setTextColor(0xff767676);
			findBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_menu_find_normal, 0, 0);
			findBtn.setTextColor(0xff767676);
			personalPageBtn.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.personinfo_pressed, 0, 0);
			personalPageBtn.setTextColor(0xff4cc0a4);
			break;
		}
	}

	/**
	 * 返回键监听
	 */
	@Override
	public void onBackPressed() {
		exit();
	}

	/**
	 * 判断两次返回时间间隔,小于两秒则退出程序
	 */
	private void exit() {
		if (System.currentTimeMillis() - mExitTime > INTERVAL) {
			Toast.makeText(this, "再按一次返回键,可直接退出程序", Toast.LENGTH_SHORT).show();
			mExitTime = System.currentTimeMillis();
		} else {
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		/**
		 * 通过照相修改头像
		 */
		case ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CAMERA:
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					Toast.makeText(this, "SD不可用", Toast.LENGTH_SHORT).show();
					return;
				}
				File file = new File(mKXApplication.mUploadPhotoPath);
				startPhotoZoom(Uri.fromFile(file));
			} else {
				Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
			}
			break;
		/**
		 * 通过本地修改头像
		 */
		case ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_LOCATION:
			Uri uri = null;
			if (data == null) {
				Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
				return;
			}
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					Toast.makeText(this, "SD不可用", Toast.LENGTH_SHORT).show();
					return;
				}
				uri = data.getData();
				startPhotoZoom(uri);
			} else {
				Toast.makeText(this, "照片获取失败", Toast.LENGTH_SHORT).show();
			}
			break;
		/**
		 * 裁剪修改的头像
		 */
		case ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CROP:
			if (data == null) {
				Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
				return;
			} else {
				saveCropPhoto(data);
			}
			break;
		case ActivityForResultUtil.REQUESTCODE_MENU:
			if (resultCode == RESULT_OK) {
				viewPager.setCurrentItem(4);
				PerCenfragment.adapter.notifyDataSetChanged();
			}
			break;
		}
	}

	/**
	 * 系统裁剪照片
	 * 
	 * @param uri
	 */
	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("scale", true);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		startActivityForResult(intent,
				ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CROP);
	}

	/**
	 * 保存裁剪的照片
	 * 
	 * @param data
	 */
	private void saveCropPhoto(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap bitmap = extras.getParcelable("data");
			bitmap = PhotoUtil.toRoundCorner(bitmap, 15);
			if (bitmap != null) {
				uploadPhoto(bitmap);
			}
		} else {
			Toast.makeText(this, "获取裁剪照片错误", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 更新头像
	 */
	private void uploadPhoto(Bitmap bitmap) {
		mKXApplication.mHeadBitmap = PhotoUtil.saveToLocal(bitmap);
		((LeftSlidingMenuFragment) mFrag).setHeadBitmap(bitmap);

		// UploadUtil util = UploadUtil.getInstance();

		Map<String, String> param = new HashMap<String, String>();
		param.put("username", mKXApplication.userName);
		File file = new File(PhotoUtil.saveToLocal(bitmap));

		if (file.exists()) {
			Log.e("不ucnzai", "图片不在");
			final List<UploadItem> files = new ArrayList<UploadItem>();
			UploadItem item = new UploadItem();
			item.setFile(file);
			item.setPath("image");
			files.add(item);
			uploadUtil.uploadFiles(files, InsUrl.CHANGE_USER_HEADPIC, param);
		}
	}

	@Override
	public void onUploadDone(int responseCode, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUploadProcess(int uploadSize) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initUpload(int fileSize) {
		// TODO Auto-generated method stub

	}

}