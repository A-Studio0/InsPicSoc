package com.astudio.inspicsoc.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.common.InsUrl;
import com.astudio.inspicsoc.model.PerCenItem;
import com.astudio.inspicsoc.service.UploadUtil;
import com.astudio.inspicsoc.service.UploadUtil.OnUploadProcessListener;
import com.astudio.inspicsoc.utils.ActivityForResultUtil;
import com.astudio.inspicsoc.utils.TextUtil;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * 照片分享类
 * 
 *
 * 
 */

public class PhotoShareActivity extends InsActivity implements
		OnUploadProcessListener {
	private Button mCancel;
	private Button mUpload;
	private Gallery mDisplay;
	private ImageView mDisplaySingle;
	private ImageView mUgcVoice;
	private ImageView mUgcVoiceDelete;
	// private TextView mLocation;
	// private Button mDelete;
	// private TextView mAlbum;
	private LinearLayout mDisplayVoiceLayout;
	private ImageView mDisplayVoicePlay;
	private ProgressBar mDisplayVoiceProgressBar;
	private TextView mDisplayVoiceTime;

	private GalleryAdapter mAdapter;

	private int mCurrentPosition;// 当前图片的编号
	private String mCurrentPath;// 当前图片的地址
	private int mLocationPosition;// 当前选择的地理位置在列表的位置
	private String[] mAlbums = new String[] { "手机相册" };// 相册
	private int mAlbumPosition;// 当前选择的相册在列表的位置

	private List<File> pics = new ArrayList<File>();
	
	private String mCurrentVoicePath;
	private float mPlayTime;

	private boolean mPlayState; // 播放状态
	private int mPlayCurrentPosition;// 当前播放的时间
	private MediaPlayer mMediaPlayer;
	
	private static final String TAG = "uploadImage";
	
	private LocationClient locationClient=null;
	private static final int UPDATE_TIME = 5000;
    private String mCurrentTime;//当前时间
    private double mCurrentLat;//纬度
    private double mCurrentLon;//经度
    private String mCurrentAddress;//当前地址
    
    private LinearLayout mLocationText;
    private TextView mAddressText;
    private ImageView mDinwei;
    private ImageView mDinweiDelete;
    private boolean isNeedDinwei=false;
    
    private EditText mMiaoshu;

	/**
	 * 去上传文件
	 */
	protected static final int TO_UPLOAD_FILE = 1;
	/**
	 * 上传文件响应
	 */
	protected static final int UPLOAD_FILE_DONE = 2; //
	/**
	 * 选择文件
	 */
	public static final int TO_SELECT_PHOTO = 3;
	/**
	 * 上传初始化
	 */
	private static final int UPLOAD_INIT_PROCESS = 4;
	/**
	 * 上传中
	 */
	private static final int UPLOAD_IN_PROCESS = 5;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoshare_activity);
		findViewById();
		setListener();
		init();
		
		locationClient = new LocationClient(getApplicationContext());
        //设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);
        option.setOpenGps(true);        //是否打开GPS
        option.setCoorType("bd09ll");       //设置返回值的坐标类型。
//        option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级
        option.setProdName("InsPicSoc"); //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setScanSpan(UPDATE_TIME);    //设置定时定位的时间间隔。单位毫秒
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        locationClient.setLocOption(option);
        locationClient.start();
        locationClient.requestLocation();
        
      //注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {
             
            @Override
            public void onReceiveLocation(BDLocation location) {
                // TODO Auto-generated method stub
                if (location == null) {
                    return;
                }
                mCurrentTime=location.getTime();
                mCurrentLat=Double.valueOf(location.getLatitude());
                mCurrentLon=Double.valueOf(location.getLongitude());
                if (location.getLocType() == BDLocation.TypeNetWorkLocation){
                	mCurrentAddress=location.getAddrStr();
                }
//                System.out.println(mCurrentTime+" "+mCurrentLat+" "+mCurrentLon+" "+mCurrentAddress);
            }
             
            public void onReceivePoi(BDLocation poiLocation) {
            
            }            
        });
	}

	private void findViewById() {
		mCancel = (Button) findViewById(R.id.photoshare_cannel);
		mUpload = (Button) findViewById(R.id.photoshare_upload);
		mDisplay = (Gallery) findViewById(R.id.photoshare_display);
		mDisplaySingle = (ImageView) findViewById(R.id.photoshare_display_single);
		mUgcVoice = (ImageView) findViewById(R.id.ugc_voice);
		mUgcVoiceDelete=(ImageView)findViewById(R.id.ugc_voice_delete);
		mDisplayVoiceLayout = (LinearLayout) findViewById(R.id.voice_display_voice_layout);
		mDisplayVoicePlay = (ImageView) findViewById(R.id.voice_display_voice_play);
		mDisplayVoiceProgressBar = (ProgressBar) findViewById(R.id.voice_display_voice_progressbar);
		mDisplayVoiceTime = (TextView) findViewById(R.id.voice_display_voice_time);
		mLocationText=(LinearLayout)findViewById(R.id.location);
		mAddressText=(TextView)findViewById(R.id.address);
		mDinwei=(ImageView)findViewById(R.id.dinwei);
		mDinweiDelete=(ImageView)findViewById(R.id.dinwei_delete);
		mMiaoshu=(EditText)findViewById(R.id.miaoshu);
		// mLocation = (TextView) findViewById(R.id.photoshare_location);
		// mDelete = (Button) findViewById(R.id.photoshare_location_delete);
		// mAlbum = (TextView) findViewById(R.id.photoshare_album);
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
		mDinwei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mCurrentAddress!=null){
					isNeedDinwei=true;
					mDinwei.setVisibility(ImageView.GONE);
					mDinweiDelete.setVisibility(ImageView.VISIBLE);
            		mLocationText.setVisibility(LinearLayout.VISIBLE);
            		mAddressText.setText(mCurrentAddress);
            	}
				else{
					mLocationText.setVisibility(LinearLayout.VISIBLE);
            		mAddressText.setText("抱歉,获取定位失败");
				}
			}
		});
		mDinweiDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isNeedDinwei=false;
				mDinwei.setVisibility(ImageView.VISIBLE);
				mDinweiDelete.setVisibility(ImageView.GONE);
        		mLocationText.setVisibility(LinearLayout.GONE);
			}
		});
		mUgcVoice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				PhotoShareActivity.this.finish();
				Intent i=new Intent();
				i.setClass(PhotoShareActivity.this,VoiceActivity.class);
				startActivityForResult(i,0);
			}
		});
		mCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 关闭当前界面
				showToast();
				finish();
			}
		});
		mUpload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 判断手机相册界面是否关闭,如果没关闭则关闭
//				try {
//					if (!PhoneAlbumActivity.mInstance.isFinishing()) {
//						PhoneAlbumActivity.mInstance.finish();
//					}
//
//				} catch (Exception e) {
//
//				}
				
				
				//图片信息保存在这个activity里
				//mCurrentLat是纬度, mCurrentLon是经度, mCurrentAddress是具体地址
				//isNeedDinwei是用户是否选择定位
				
				PerCenItem percen1 = new PerCenItem(mCurrentPath,
						mMiaoshu.getText().toString(),(isNeedDinwei?mCurrentAddress:""),
						mCurrentTime,"收藏数：0","浏览数：0",0,mCurrentVoicePath,mPlayTime);
				mKXApplication.PerCenItemList.add(0,percen1);
				
				// 显示提示信息并关闭当前界面
				Toast.makeText(PhotoShareActivity.this.getApplicationContext(),
						"上传图片成功", Toast.LENGTH_SHORT).show();
				Intent i=new Intent();
				i.setClass(PhotoShareActivity.this,ImageFilterActivity.class);
				setResult(RESULT_OK,i);
				PhotoShareActivity.this.finish();
			}
		});
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 获取当前的照片编号以及照片地址传递到照片编辑类
				mCurrentPosition = arg2;
				mCurrentPath = mKXApplication.mAlbumList.get(mCurrentPosition)
						.get("image_path");
				Intent intent = new Intent();
				intent.setClass(PhotoShareActivity.this,
						ImageFilterActivity.class);
				intent.putExtra("path", mCurrentPath);
				intent.putExtra("isSetResult", true);
				startActivityForResult(intent, 0);
			}
		});
		mDisplaySingle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 将照片地址传递到照片编辑类
				Intent intent = new Intent();
				intent.setClass(PhotoShareActivity.this,
						ImageFilterActivity.class);
				intent.putExtra("path", mCurrentPath);
				intent.putExtra("isSetResult", true);
				startActivityForResult(intent, 0);
			}
		});
		mDisplayVoicePlay.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {
				// 播放录音
				if (!mPlayState) {
					mMediaPlayer = new MediaPlayer();
					try {
						// 添加录音的路径
						mMediaPlayer.setDataSource(mCurrentVoicePath);
						// 准备
						mMediaPlayer.prepare();
						// 播放
						mMediaPlayer.start();
						// 根据时间修改界面
						new Thread(new Runnable() {
	
							public void run() {
	
								mDisplayVoiceProgressBar
										.setMax((int) mPlayTime);
								mPlayCurrentPosition = 0;
								while (mMediaPlayer.isPlaying()) {
									mPlayCurrentPosition = mMediaPlayer
											.getCurrentPosition() / 1000;
									mDisplayVoiceProgressBar
											.setProgress(mPlayCurrentPosition);
								}
							}
						}).start();
						// 修改播放状态
						mPlayState = true;
						// 修改播放图标
						mDisplayVoicePlay
								.setImageResource(R.drawable.globle_player_btn_stop);
	
						mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
									// 播放结束后调用
									public void onCompletion(MediaPlayer mp) {
										// 停止播放
										mMediaPlayer.stop();
										// 修改播放状态
										mPlayState = false;
										// 修改播放图标
										mDisplayVoicePlay
												.setImageResource(R.drawable.globle_player_btn_play);
										// 初始化播放数据
										mPlayCurrentPosition = 0;
										mDisplayVoiceProgressBar
												.setProgress(mPlayCurrentPosition);
									}
								});
	
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					if (mMediaPlayer != null) {
						// 根据播放状态修改显示内容
						if (mMediaPlayer.isPlaying()) {
							mPlayState = false;
							mMediaPlayer.stop();
							mDisplayVoicePlay
									.setImageResource(R.drawable.globle_player_btn_play);
							mPlayCurrentPosition = 0;
							mDisplayVoiceProgressBar
									.setProgress(mPlayCurrentPosition);
						} else {
							mPlayState = false;
							mDisplayVoicePlay
									.setImageResource(R.drawable.globle_player_btn_play);
							mPlayCurrentPosition = 0;
							mDisplayVoiceProgressBar
									.setProgress(mPlayCurrentPosition);
						}
					}
				}
			}
		});
		mUgcVoiceDelete.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {
				mCurrentVoicePath=null;
				mPlayTime=0;
				mDisplayVoiceLayout.setVisibility(View.GONE);
    			mUgcVoiceDelete.setVisibility(View.GONE);
			}
		});
	}

	private void init() {
		// 判断照片的数量,根据数量选择控件显示,1张图片用ImageView显示,多张用Gallery显示
		if (mKXApplication.mAlbumList.size() > 1) {
			mDisplaySingle.setVisibility(View.GONE);
			mDisplay.setVisibility(View.VISIBLE);
			mCurrentPosition = 0;
			mAdapter = new GalleryAdapter();
			mDisplay.setAdapter(mAdapter);
			mDisplay.setSelection(mCurrentPosition);
		} else if (mKXApplication.mAlbumList.size() == 1) {
			mDisplaySingle.setVisibility(View.VISIBLE);
			mDisplay.setVisibility(View.GONE);
			mCurrentPosition = 0;
			mCurrentPath = mKXApplication.mAlbumList.get(mCurrentPosition).get(
					"image_path");
			mDisplaySingle.setImageBitmap(mKXApplication
					.getPhoneAlbum(mCurrentPath));
		}

		for (int i = 0; i < mKXApplication.mAlbumList.size(); i++) {
			Map<String, String> results = mKXApplication.mAlbumList.get(i);
			File file = new File(results.get("image_path"));
			pics.add(file);
		}

		UploadUtil uploadUtil = UploadUtil.getInstance();
		if (pics.isEmpty()) {
			Log.e("Empty", "true", null);
			return;
		} else {
			Map<String, String> param = new HashMap<String, String>();

			uploadUtil.setOnUploadProcessListener(this); // 设置监听器监听上传状态
			param.put("username", "HeyJim");
			uploadUtil.uploadFiles(pics, InsUrl.UPLOAD_IMAGE_BASE, param);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			// 获取新的照片地址
			mCurrentPath = data.getStringExtra("path");
			Map<String, String> map = mKXApplication.mAlbumList
					.get(mCurrentPosition);
			map.put("image_path", mCurrentPath);
			// 更新界面显示
			if (mKXApplication.mAlbumList.size() > 1) {
				mAdapter.notifyDataSetChanged();
			} else if (mKXApplication.mAlbumList.size() == 1) {
				mDisplaySingle.setImageBitmap(mKXApplication
						.getPhoneAlbum(mCurrentPath));
			}
		}
		else if(requestCode==0&&resultCode == ActivityForResultUtil.REQUESTCODE_VOICE){
			Bundle bundle = data.getExtras();
            mCurrentVoicePath=bundle.getString("voicePath");
            mPlayTime=Float.valueOf(bundle.getString("recordTime"));
            if(mCurrentVoicePath==null){
    			mDisplayVoiceLayout.setVisibility(View.GONE);
    		}
    		else{
    			mDisplayVoiceLayout.setVisibility(View.VISIBLE);
    			mUgcVoiceDelete.setVisibility(View.VISIBLE);
//    			voiceGallery.setVisibility(View.VISIBLE);
    			mDisplayVoicePlay
    					.setImageResource(R.drawable.globle_player_btn_play);
    			mDisplayVoiceProgressBar.setMax((int) mPlayTime);
    			mDisplayVoiceProgressBar.setProgress(0);
    			mDisplayVoiceTime.setText((int) mPlayTime + "″");
    		}
		}
	}

	private class GalleryAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mKXApplication.mAlbumList.size();
		}

		@Override
		public Object getItem(int position) {
			return mKXApplication.mAlbumList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(PhotoShareActivity.this)
						.inflate(R.layout.photoshare_activity_item, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.photoshare_item_image);
				holder.delete = (Button) convertView
						.findViewById(R.id.photoshare_item_delete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Map<String, String> results = mKXApplication.mAlbumList
					.get(position);
			holder.image.setImageBitmap(mKXApplication.getPhoneAlbum(results
					.get("image_path")));
			if (mKXApplication.mAlbumList.size() > 1) {
				holder.delete.setVisibility(View.VISIBLE);
			} else if (mKXApplication.mAlbumList.size() == 1) {
				holder.delete.setVisibility(View.GONE);
			}
			holder.delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mKXApplication.mAlbumList.remove(position);
					notifyDataSetChanged();
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView image;
			Button delete;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mKXApplication.mAlbumList.clear();
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stop();
            locationClient = null;
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
