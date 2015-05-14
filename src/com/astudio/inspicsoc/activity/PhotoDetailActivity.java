package com.astudio.inspicsoc.activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.adapter.PhotoDetailAdapter;
import com.astudio.inspicsoc.model.ExchangeItem;
import com.astudio.inspicsoc.model.PerCenItem;
import com.astudio.inspicsoc.model.PhotoDetailItem;

import cn.sharesdk.onekeyshare.*;
import cn.sharesdk.sina.weibo.SinaWeibo;

import java.io.File;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;

import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.ListView;

import android.widget.TextView;

import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.astudio.android.bitmapfun.util.ImageFetcher;
import com.astudio.android.bitmapfun.util.ImageResizer;
import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.common.InsUrl;
import com.astudio.inspicsoc.model.MsgDto;
import com.astudio.inspicsoc.view.RoundedImageView;
import com.google.gson.Gson;

public class PhotoDetailActivity extends Activity implements OnClickListener,
		Callback {

<<<<<<< .mine
	protected InsApplication mKXApplication;




=======
	private String[] data = { "photoDetail01"};
	
	private List<PhotoDetailItem> photoDetailItemList = new ArrayList<PhotoDetailItem>();


>>>>>>> .theirs
	private Button guanzhuBtn;
	private ImageButton commentBtn;
	private ImageButton collectBtn;
	private ImageButton shareBtn;
	private ImageView showComment;
	private TextView description;
	private TextView time;
	private TextView position;
	private TextView viewNum;
	private ImageButton collect;
	private String msgId;
	private String userName;
	private ImageFetcher mImageFetcher;
	private RoundedImageView headImageView;
	private ImageView photo;
	private Activity myActivity;
	private MsgDto msgdto;
	private Handler myHandler;
	private TextView usernameT;
	private File headData;
	private File picData;
	private TextView timeVIew;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_detail);

		PhotoDetailAdapter adapter = new PhotoDetailAdapter(this,
				R.layout.photo_detail_item, photoDetailItemList);
		ListView listView = (ListView) findViewById(R.id.photo_detail);
		listView.setAdapter(adapter);
		
		
		/*ShareSDK.initSDK(this);
		findViewById(R.id.shareweibo).setOnClickListener(this);
		guanzhuBtn = (Button) findViewById(R.id.guanzhu);
		collectBtn = (ImageButton) findViewById(R.id.collect);
		shareBtn = (ImageButton) findViewById(R.id.shareweibo);
<<<<<<< HEAD
		//showComment = (ImageView) findViewById(R.id.photo_comment);
		guanzhuBtn.setOnClickListener(new OnClickListener(){
=======
		showComment = (ImageView) findViewById(R.id.photo_comment);
		description = (TextView) findViewById(R.id.description);
		position = (TextView) findViewById(R.id.position);
		viewNum = (TextView) findViewById(R.id.viewnum);
		collect = (ImageButton) findViewById(R.id.collect);
		commentBtn = (ImageButton) findViewById(R.id.comment);
		headImageView = (RoundedImageView) findViewById(R.id.headImageView);
		usernameT = (TextView) findViewById(R.id.user_name);
		photo = (ImageView) findViewById(R.id.photo);
		timeVIew = (TextView) findViewById(R.id.time);

		mKXApplication = (InsApplication) getApplication();
		myActivity = this;
		Intent intent = getIntent();

		userName = intent.getStringExtra("userName");
		msgId = intent.getStringExtra("msgId");

		if (userName != null && msgId != null) {
			AbHttpUtil httpUtil = AbHttpUtil.getInstance(getApplication());

			String getMagDetailUrl = InsUrl.GET_MSG_DETAIL.replace("@un",
					userName).replace("@mi", msgId);

			httpUtil.get(getMagDetailUrl, new AbStringHttpResponseListener() {
				@Override
				public void onSuccess(int i, String s) {

					if ("fail".equals(s)) {
						AbToastUtil.showToast(getApplicationContext(),
								"获取信息失败……T_T");
						return;
					}

					try {
						// JSONObject newsObject = new JSONObject(s);

						Gson gson = new Gson();
						msgdto = gson.fromJson(s, MsgDto.class);

						Log.e("DTO", msgdto.getHeadPic());

						// mImageFetcher.loadImage(dto.getPics().get(0), photo);
					} catch (Exception e) {
						// Log.e("start new ac :", e.getMessage());
					}
				}

				@Override
				public void onStart() {
					Log.d(getClass().getName(), "调用了OnStart.");
				}

				@Override
				public void onFinish() {

					new Thread() {
						@SuppressWarnings("static-access")
						@Override
						public void run() {
							headData = mImageFetcher.downloadBitmap(
									myActivity.getApplicationContext(),
									msgdto.getHeadPic());
							Log.e("DTO", msgdto.getPics().get(0));
							picData = mImageFetcher.downloadBitmap(myActivity
									.getApplicationContext(), msgdto.getPics()
									.get(0));
							Message msg = myHandler.obtainMessage();
							msg.arg1 = 1;
							myHandler.sendMessage(msg);
						}
					}.start();

				}

				@Override
				public void onFailure(int i, String s, Throwable throwable) {
					AbToastUtil.showToast(getApplicationContext(), "抱歉，出错了！异常:"
							+ s);
				}
			});
		}

		commentBtn.setOnClickListener(new OnClickListener() {
>>>>>>> 1f15566bfd4ade4cdb5aa7313d948f40c6f6f73a

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				collectBtn.setVisibility(View.INVISIBLE);
				shareBtn.setVisibility(View.INVISIBLE);
				commentBtn.setVisibility(View.INVISIBLE);
				showComment.setVisibility(View.VISIBLE);
			}
<<<<<<< HEAD
			
		});*/
		/*
		commentBtn = (ImageButton)findViewById(R.id.comment);
		commentBtn.setOnClickListener(new OnClickListener(){
=======

		});

		if (mKXApplication.userName.equals(userName)) {
			guanzhuBtn.setText("已关注");
			guanzhuBtn.setVisibility(Button.INVISIBLE);
		}
		guanzhuBtn.setOnClickListener(new OnClickListener() {
>>>>>>> 1f15566bfd4ade4cdb5aa7313d948f40c6f6f73a

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				guanzhuBtn.setText("已关注");
				guanzhuBtn.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.guanzhubackground));

				AbHttpUtil httpUtil = AbHttpUtil.getInstance(getApplication());

				String getMagDetailUrl = InsUrl.ADD_CONCERN.replace("@un",
						mKXApplication.userName).replace("@cn", userName);

				httpUtil.get(getMagDetailUrl,
						new AbStringHttpResponseListener() {
							@Override
							public void onSuccess(int i, String s) {

								if ("fail".equals(s)) {
									AbToastUtil.showToast(
											getApplicationContext(),
											"获取信息失败……T_T");
									return;
								}
<<<<<<< .mine
								Toast.makeText(PhotoDetailActivity.this,
										"添加关注成功~", Toast.LENGTH_SHORT).show();
								guanzhuBtn.setVisibility(Button.INVISIBLE);

=======
<<<<<<< HEAD
			
		});*/
		initData();
>>>>>>> .theirs

<<<<<<< .mine
							}

							@Override
							public void onStart() {
								Log.d(getClass().getName(), "调用了OnStart.");
							}

							@Override
							public void onFinish() {

							}

							@Override
							public void onFailure(int i, String s,
									Throwable throwable) {
								AbToastUtil.showToast(getApplicationContext(),
										"抱歉，出错了！异常:" + s);
							}
						});
=======



















>>>>>>> .theirs

<<<<<<< .mine
			}

		});

=======
		//});



>>>>>>> .theirs
		myHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.arg1 == 1) {
					headImageView.setImageBitmap(ImageResizer
							.decodeSampledBitmapFromFile(headData.toString(),
									80, 80));
					photo.setImageBitmap(ImageResizer
							.decodeSampledBitmapFromFile(picData.toString(),
									300, 300));
					if (msgdto.getContent() != null)
						description.setText(msgdto.getContent());
					if (msgdto.getLocationName() != null)
						position.setText(msgdto.getLocationName());
					usernameT.setText(userName);
					timeVIew.setText(msgdto.getTime());

				}
			}
		};
	}

	
	
	private void initData() {
		PhotoDetailItem photoDetail01 = new PhotoDetailItem(R.drawable.head_default_miao,"miao",R.drawable.pinpho1,
				"this is description","120.19, 30.26",
				"2015-05-12","浏览数：834",R.drawable.percen_comment1,"#thisIsTag#");
		photoDetailItemList.add(photoDetail01);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.shareweibo:
			OnekeyShare oks = new OnekeyShare();
			// 分享时Notification的图标和文字
			oks.setNotification(R.drawable.ic_launcher,
					PhotoDetailActivity.this.getString(R.string.app_name));
			// text是分享文本，所有平台都需要这个字段
			oks.setText("分享内容");
			// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
			oks.setTitle(getString(R.string.share));

			// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
			oks.setTitleUrl("http://sharesdk.cn");
			// text是分享文本，所有平台都需要这个字段
			oks.setText("我是分享文本");
			// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
			// oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
			// url仅在微信（包括好友和朋友圈）中使用
			oks.setUrl("http://sharesdk.cn");
			// comment是我对这条分享的评论，仅在人人网和QQ空间使用
			oks.setComment("我是测试评论文本");
			// site是分享此内容的网站名称，仅在QQ空间使用
			oks.setSite(getString(R.string.app_name));
			// siteUrl是分享此内容的网站地址，仅在QQ空间使用
			oks.setSiteUrl("http://sharesdk.cn");

			oks.show(v.getContext());
			break;
		}
	}

	@Override
	protected void onDestroy() {
		ShareSDK.stopSDK(this);
		super.onDestroy();

	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
	}

}
