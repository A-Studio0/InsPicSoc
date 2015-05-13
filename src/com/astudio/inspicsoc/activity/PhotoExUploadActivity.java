package com.astudio.inspicsoc.activity;

import java.io.File;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.utils.ActivityForResultUtil;
import com.astudio.inspicsoc.utils.PhotoUtil;

public class PhotoExUploadActivity extends InsActivity implements
		OnClickListener {

	private ImageButton back; // 顶部返回按钮
	private ImageView addPhoto; // 从本地添加照片按钮
	private ImageButton exphoto_upload; // 发布照片按钮

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
		addPhoto = (ImageView) findViewById(R.id.add_photo);
		exphoto_upload = (ImageButton) findViewById(R.id.exphoto_upload);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.upload_back:
			Intent intent = new Intent(PhotoExUploadActivity.this,
					PhotoExDetailActivity.class);
			startActivity(intent);
			break;
		case R.id.add_photo:
			// this.startActivity(new Intent(PhotoExUploadActivity.this,
			// PhoneAlbumActivity2.class));
			intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(intent,
					ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_LOCATION);
			break;

		case R.id.exphoto_upload:

			// 发布图片及其信息到兑换图片广场。。代码不会。。。
			break;
		default:
			break;
		}
	}

	// 怎么从AlbumActivity2.java获得所选的图片？？？？

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

		/**
		 * 从本地相册选取图片
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
	 * 更新photo
	 */
	private void uploadPhoto(Bitmap bitmap) {
		mKXApplication.mHeadBitmap = bitmap;
		// ((LeftSlidingMenuFragment) mFrag).setHeadBitmap(bitmap);
		addPhoto.setImageBitmap(bitmap);
	}
}
