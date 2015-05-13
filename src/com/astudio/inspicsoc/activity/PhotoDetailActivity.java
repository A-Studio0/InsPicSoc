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
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class PhotoDetailActivity extends Activity implements OnClickListener,
		Callback {
	private String[] data = { "photoDetail01"};
	
	private List<PhotoDetailItem> photoDetailItemList = new ArrayList<PhotoDetailItem>();
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
		guanzhuBtn = (Button)findViewById(R.id.guanzhu);
		collectBtn = (ImageButton) findViewById(R.id.collect);
		shareBtn = (ImageButton) findViewById(R.id.shareweibo);
		//showComment = (ImageView) findViewById(R.id.photo_comment);
		guanzhuBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				guanzhuBtn.setText("已关注");
				guanzhuBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.guanzhubackground));
				Toast.makeText(PhotoDetailActivity.this, "添加关注成功~", Toast.LENGTH_SHORT).show();
			}
			
		});*/
		/*
		commentBtn = (ImageButton)findViewById(R.id.comment);
		commentBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				collectBtn.setVisibility(View.INVISIBLE) ;
				shareBtn.setVisibility(View.INVISIBLE) ;
				commentBtn.setVisibility(View.INVISIBLE) ;
				showComment.setVisibility(View.VISIBLE);
			}
			
		});*/
		initData();


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
}
