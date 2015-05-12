package com.astudio.inspicsoc.activity;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.astudio.inspicsoc.R;

public class PhotoExUploadActivity extends InsActivity implements OnClickListener {
	
	private ImageButton back;   //顶部返回按钮	
	private ImageButton addPhoto;   //从本地添加照片按钮
	private ImageButton exphoto_upload;   //发布照片按钮
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_exchange_upload);

		initData();
		back.setOnClickListener(this);
		addPhoto.setOnClickListener(this);
		exphoto_upload.setOnClickListener(this);
	}

	private void initData() {
		back = (ImageButton) findViewById(R.id.upload_back);
		addPhoto= (ImageButton) findViewById(R.id.add_photo);
		exphoto_upload= (ImageButton) findViewById(R.id.exphoto_upload);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.upload_back:
			Intent intent = new Intent(PhotoExUploadActivity.this,
					PhotoExDetailActivity.class);
			startActivity(intent);
			break;
		case R.id.add_photo:
			this.startActivity(new Intent(PhotoExUploadActivity.this,
					PhoneAlbumActivity2.class)); 
			break; 
		case R.id.exphoto_upload:
			
			//发布图片及其信息到兑换图片广场。。代码不会。。。
			break;
		default:
			break;
		}
	}

	//怎么从AlbumActivity2.java获得所选的图片？？？？
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		 
	}
}
