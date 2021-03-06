package com.astudio.inspicsoc.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.json.JSONArray;

import com.astudio.inspicsoc.utils.ActivityForResultUtil;
import com.astudio.inspicsoc.utils.RecordUtil;
import com.astudio.inspicsoc.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 录音类
 * 
 */
public class VoiceActivity extends Activity {
	private LinearLayout mParent;
//	private EditText mContent;
//	private LinearLayout mDisplayVoiceLayout;
//	private ImageView mDisplayVoicePlay;
//	private ProgressBar mDisplayVoiceProgressBar;
//	private TextView mDisplayVoiceTime;
//	private Button send;
//	private Button mUpload;
	
//	private Gallery voiceGallery;
//	private TextView title;
//	private EditText inputTitle;
	private Button mCancel;
	
	private Button mRecord;
	private RelativeLayout mRecordLayout;
	private ImageView mRecordVolume;
	private ImageView mRecordLight_1;
	private ImageView mRecordLight_2;
	private ImageView mRecordLight_3;
	private TextView mRecordTime;
	private ProgressBar mRecordProgressBar;

	private Animation mRecordLight_1_Animation;
	private Animation mRecordLight_2_Animation;
	private Animation mRecordLight_3_Animation;

//	private MediaPlayer mMediaPlayer;
	private RecordUtil mRecordUtil;
	private static final int MAX_TIME = 60;// 最长录音时间
	private static final int MIN_TIME = 2;// 最短录音时间

	private static final int RECORD_NO = 0; // 不在录音
	private static final int RECORD_ING = 1; // 正在录音
	private static final int RECORD_ED = 2; // 完成录音
	private int mRecord_State = 0; // 录音的状态
	private float mRecord_Time;// 录音的时间
	private double mRecord_Volume;// 麦克风获取的音量值
//	private boolean mPlayState; // 播放状态
//	private int mPlayCurrentPosition;// 当前播放的时间
	private static final String PATH = "/sdcard/InsPicSoc/Record/";// 录音存储路径
	private String mRecordPath;// 录音的存储名称
	private int mMAXVolume;// 最大音量高度
	private int mMINVolume;// 最小音量高度

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_activity);
		findViewById();
		setListener();
		init();

	}

	private void findViewById() {
		mParent = (LinearLayout) findViewById(R.id.voice_parent);
//		mContent = (EditText) findViewById(R.id.voice_content);
//		mDisplayVoiceLayout = (LinearLayout) findViewById(R.id.voice_display_voice_layout);
//		mDisplayVoicePlay = (ImageView) findViewById(R.id.voice_display_voice_play);
//		mDisplayVoiceProgressBar = (ProgressBar) findViewById(R.id.voice_display_voice_progressbar);
//		mDisplayVoiceTime = (TextView) findViewById(R.id.voice_display_voice_time);
		mRecord = (Button) findViewById(R.id.voice_record_btn);
		mRecordLayout = (RelativeLayout) findViewById(R.id.voice_record_layout);
		mRecordVolume = (ImageView) findViewById(R.id.voice_recording_volume);
		mRecordLight_1 = (ImageView) findViewById(R.id.voice_recordinglight_1);
		mRecordLight_2 = (ImageView) findViewById(R.id.voice_recordinglight_2);
		mRecordLight_3 = (ImageView) findViewById(R.id.voice_recordinglight_3);
		mRecordTime = (TextView) findViewById(R.id.voice_record_time);
		mRecordProgressBar = (ProgressBar) findViewById(R.id.voice_record_progressbar);
		mCancel = (Button) findViewById(R.id.photovoice_cancel);
//		send=(Button) findViewById(R.id.voice_send);
		
//		mUpload = (Button) findViewById(R.id.photoshare_upload);
//		voiceGallery=(Gallery)findViewById(R.id.photoshare_display);
		//title=(TextView)findViewById(R.id.photoshare_display);
		//inputTitle=(EditText)findViewById(R.id.photoshare_display);
	}

	/**
	 * 监听事件
	 */
	private void setListener() {
//		mUpload.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// 判断手机相册界面是否关闭,如果没关闭则关闭
//				try {
//					if (!PhoneAlbumActivity.mInstance.isFinishing()) {
//						PhoneAlbumActivity.mInstance.finish();
//					}
//
//				} catch (Exception e) {
//
//				}
//
//				// 显示提示信息并关闭当前界面
//				Toast.makeText(VoiceActivity.this.getApplicationContext(),
//						"上传图片成功", Toast.LENGTH_SHORT).show();
//				VoiceActivity.this.finish();
//				
//			}
//		});
		mCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mRecord.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				// 开始录音
				case MotionEvent.ACTION_DOWN:
					if (mRecord_State != RECORD_ING) {
						// 开始动画效果
						startRecordLightAnimation();
						// 修改录音状态
						mRecord_State = RECORD_ING;
						// 设置录音保存路径
						mRecordPath = PATH + UUID.randomUUID().toString()
								+ ".amr";
						// 实例化录音工具类
						mRecordUtil = new RecordUtil(mRecordPath);
						try {
							// 开始录音
							mRecordUtil.start();
						} catch (IOException e) {
							e.printStackTrace();
						}
						new Thread(new Runnable() {

							public void run() {
								// 初始化录音时间
								mRecord_Time = 0;
								while (mRecord_State == RECORD_ING) {
									// 大于最大录音时间则停止录音
									if (mRecord_Time >= MAX_TIME) {
										mRecordHandler.sendEmptyMessage(0);
									} else {
										try {
											// 每隔200毫秒就获取声音音量并更新界面显示
											Thread.sleep(200);
											mRecord_Time += 0.2;
											if (mRecord_State == RECORD_ING) {
												mRecord_Volume = mRecordUtil
														.getAmplitude();
												mRecordHandler
														.sendEmptyMessage(1);
											}
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}).start();
					}
					break;
				// 停止录音
				case MotionEvent.ACTION_UP:
					if (mRecord_State == RECORD_ING) {
						// 停止动画效果
						stopRecordLightAnimation();
						// 修改录音状态
						mRecord_State = RECORD_ED;
						try {
							// 停止录音
							mRecordUtil.stop();
							// 初始录音音量
							mRecord_Volume = 0;
						} catch (IOException e) {
							e.printStackTrace();
						}
						// 如果录音时间小于最短时间
						if (mRecord_Time <= MIN_TIME) {
							// 显示提醒
							Toast.makeText(VoiceActivity.this, "录音时间过短",
									Toast.LENGTH_SHORT).show();
							// 修改录音状态
							mRecord_State = RECORD_NO;
							// 修改录音时间
							mRecord_Time = 0;
							// 修改显示界面
							mRecordTime.setText("0″");
							mRecordProgressBar.setProgress(0);
							// 修改录音声音界面
							ViewGroup.LayoutParams params = mRecordVolume
									.getLayoutParams();
							params.height = 0;
							mRecordVolume.setLayoutParams(params);
						} else {
							// 录音成功,则显示录音成功后的界面
							if(mRecordPath!=null){
								Intent i=new Intent();
								i.setClass(VoiceActivity.this,PhotoShareActivity.class);
								Bundle bundle=new Bundle();
								bundle.putString("voicePath",mRecordPath);
								bundle.putString("recordTime",String.valueOf(mRecord_Time));
								i.putExtras(bundle); 
								setResult(ActivityForResultUtil.REQUESTCODE_VOICE,i);
								finish();
							}
//							mRecordLayout.setVisibility(View.GONE);
//							mRecord.setVisibility(View.GONE);
//							mDisplayVoiceLayout.setVisibility(View.VISIBLE);
//							voiceGallery.setVisibility(View.VISIBLE);
//							mDisplayVoicePlay
//									.setImageResource(R.drawable.globle_player_btn_play);
//							mDisplayVoiceProgressBar.setMax((int) mRecord_Time);
//							mDisplayVoiceProgressBar.setProgress(0);
//							mDisplayVoiceTime.setText((int) mRecord_Time + "″");
//							send.setVisibility(View.VISIBLE);
						}
					}
					break;
				}
				return false;
			}
		});
//		mDisplayVoicePlay.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				// 播放录音
//				if (!mPlayState) {
//					mMediaPlayer = new MediaPlayer();
//					try {
//						// 添加录音的路径
//						mMediaPlayer.setDataSource(mRecordPath);
//						// 准备
//						mMediaPlayer.prepare();
//						// 播放
//						mMediaPlayer.start();
//						// 根据时间修改界面
//						new Thread(new Runnable() {
//
//							public void run() {
//
//								mDisplayVoiceProgressBar
//										.setMax((int) mRecord_Time);
//								mPlayCurrentPosition = 0;
//								while (mMediaPlayer.isPlaying()) {
//									mPlayCurrentPosition = mMediaPlayer
//											.getCurrentPosition() / 1000;
//									mDisplayVoiceProgressBar
//											.setProgress(mPlayCurrentPosition);
//								}
//							}
//						}).start();
//						// 修改播放状态
//						mPlayState = true;
//						// 修改播放图标
//						mDisplayVoicePlay
//								.setImageResource(R.drawable.globle_player_btn_stop);
//
//						mMediaPlayer
//								.setOnCompletionListener(new OnCompletionListener() {
//									// 播放结束后调用
//									public void onCompletion(MediaPlayer mp) {
//										// 停止播放
//										mMediaPlayer.stop();
//										// 修改播放状态
//										mPlayState = false;
//										// 修改播放图标
//										mDisplayVoicePlay
//												.setImageResource(R.drawable.globle_player_btn_play);
//										// 初始化播放数据
//										mPlayCurrentPosition = 0;
//										mDisplayVoiceProgressBar
//												.setProgress(mPlayCurrentPosition);
//									}
//								});
//
//					} catch (IllegalArgumentException e) {
//						e.printStackTrace();
//					} catch (IllegalStateException e) {
//						e.printStackTrace();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				} else {
//					if (mMediaPlayer != null) {
//						// 根据播放状态修改显示内容
//						if (mMediaPlayer.isPlaying()) {
//							mPlayState = false;
//							mMediaPlayer.stop();
//							mDisplayVoicePlay
//									.setImageResource(R.drawable.globle_player_btn_play);
//							mPlayCurrentPosition = 0;
//							mDisplayVoiceProgressBar
//									.setProgress(mPlayCurrentPosition);
//						} else {
//							mPlayState = false;
//							mDisplayVoicePlay
//									.setImageResource(R.drawable.globle_player_btn_play);
//							mPlayCurrentPosition = 0;
//							mDisplayVoiceProgressBar
//									.setProgress(mPlayCurrentPosition);
//						}
//					}
//				}
//			}
//		});
	}

	private void init() {
		// 设置当前的最小声音和最大声音值
		mMINVolume = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4.5f, getResources()
						.getDisplayMetrics());
		mMAXVolume = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 65f, getResources()
						.getDisplayMetrics());
	}

	/**
	 * 用来控制动画效果
	 */
	Handler mRecordLightHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				if (mRecord_State == RECORD_ING) {
					mRecordLight_1.setVisibility(View.VISIBLE);
					mRecordLight_1_Animation = AnimationUtils.loadAnimation(
							VoiceActivity.this, R.anim.voice_anim);
					mRecordLight_1.setAnimation(mRecordLight_1_Animation);
					mRecordLight_1_Animation.startNow();
				}
				break;

			case 1:
				if (mRecord_State == RECORD_ING) {
					mRecordLight_2.setVisibility(View.VISIBLE);
					mRecordLight_2_Animation = AnimationUtils.loadAnimation(
							VoiceActivity.this, R.anim.voice_anim);
					mRecordLight_2.setAnimation(mRecordLight_2_Animation);
					mRecordLight_2_Animation.startNow();
				}
				break;
			case 2:
				if (mRecord_State == RECORD_ING) {
					mRecordLight_3.setVisibility(View.VISIBLE);
					mRecordLight_3_Animation = AnimationUtils.loadAnimation(
							VoiceActivity.this, R.anim.voice_anim);
					mRecordLight_3.setAnimation(mRecordLight_3_Animation);
					mRecordLight_3_Animation.startNow();
				}
				break;
			case 3:
				if (mRecordLight_1_Animation != null) {
					mRecordLight_1.clearAnimation();
					mRecordLight_1_Animation.cancel();
					mRecordLight_1.setVisibility(View.GONE);

				}
				if (mRecordLight_2_Animation != null) {
					mRecordLight_2.clearAnimation();
					mRecordLight_2_Animation.cancel();
					mRecordLight_2.setVisibility(View.GONE);
				}
				if (mRecordLight_3_Animation != null) {
					mRecordLight_3.clearAnimation();
					mRecordLight_3_Animation.cancel();
					mRecordLight_3.setVisibility(View.GONE);
				}

				break;
			}
		}
	};
	/**
	 * 用来控制录音
	 */
	Handler mRecordHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				if (mRecord_State == RECORD_ING) {
					// 停止动画效果
					stopRecordLightAnimation();
					// 修改录音状态
					mRecord_State = RECORD_ED;
					try {
						// 停止录音
						mRecordUtil.stop();
						// 初始化录音音量
						mRecord_Volume = 0;
					} catch (IOException e) {
						e.printStackTrace();
					}
					// 根据录音修改界面显示内容
//					mRecordLayout.setVisibility(View.GONE);
//					mRecord.setVisibility(View.GONE);
//					mDisplayVoiceLayout.setVisibility(View.VISIBLE);
//					mDisplayVoicePlay
//							.setImageResource(R.drawable.globle_player_btn_play);
//					mDisplayVoiceProgressBar.setMax((int) mRecord_Time);
//					mDisplayVoiceProgressBar.setProgress(0);
//					mDisplayVoiceTime.setText((int) mRecord_Time + "″");
				}
				break;

			case 1:
				// 根据录音时间显示进度条
				mRecordProgressBar.setProgress((int) mRecord_Time);
				// 显示录音时间
				mRecordTime.setText((int) mRecord_Time + "″");
				// 根据录音声音大小显示效果
				ViewGroup.LayoutParams params = mRecordVolume.getLayoutParams();
				if (mRecord_Volume < 200.0) {
					params.height = mMINVolume;
				} else if (mRecord_Volume > 200.0 && mRecord_Volume < 400) {
					params.height = mMINVolume * 2;
				} else if (mRecord_Volume > 400.0 && mRecord_Volume < 800) {
					params.height = mMINVolume * 3;
				} else if (mRecord_Volume > 800.0 && mRecord_Volume < 1600) {
					params.height = mMINVolume * 4;
				} else if (mRecord_Volume > 1600.0 && mRecord_Volume < 3200) {
					params.height = mMINVolume * 5;
				} else if (mRecord_Volume > 3200.0 && mRecord_Volume < 5000) {
					params.height = mMINVolume * 6;
				} else if (mRecord_Volume > 5000.0 && mRecord_Volume < 7000) {
					params.height = mMINVolume * 7;
				} else if (mRecord_Volume > 7000.0 && mRecord_Volume < 10000.0) {
					params.height = mMINVolume * 8;
				} else if (mRecord_Volume > 10000.0 && mRecord_Volume < 14000.0) {
					params.height = mMINVolume * 9;
				} else if (mRecord_Volume > 14000.0 && mRecord_Volume < 17000.0) {
					params.height = mMINVolume * 10;
				} else if (mRecord_Volume > 17000.0 && mRecord_Volume < 20000.0) {
					params.height = mMINVolume * 11;
				} else if (mRecord_Volume > 20000.0 && mRecord_Volume < 24000.0) {
					params.height = mMINVolume * 12;
				} else if (mRecord_Volume > 24000.0 && mRecord_Volume < 28000.0) {
					params.height = mMINVolume * 13;
				} else if (mRecord_Volume > 28000.0) {
					params.height = mMAXVolume;
				}
				mRecordVolume.setLayoutParams(params);
				break;
			}
		}

	};

	/**
	 * 开始动画效果
	 */
	private void startRecordLightAnimation() {
		mRecordLightHandler.sendEmptyMessageDelayed(0, 0);
		mRecordLightHandler.sendEmptyMessageDelayed(1, 1000);
		mRecordLightHandler.sendEmptyMessageDelayed(2, 2000);
	}

	/**
	 * 停止动画效果
	 */
	private void stopRecordLightAnimation() {
		mRecordLightHandler.sendEmptyMessage(3);
	}
}