package com.astudio.inspicsoc.activity;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.utils.ActivityForResultUtil;
import com.astudio.inspicsoc.utils.PhotoUtil;

public class MenuActivity extends InsActivity implements OnClickListener {

	private Button closeMenu;
	private Button photoA;
	private Button photoB;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		closeMenu = (Button) findViewById(R.id.close_photo);
		photoA = (Button) findViewById(R.id.photo_a);
		photoB = (Button) findViewById(R.id.photo_b);
		closeMenu.setOnClickListener(this);
		photoA.setOnClickListener(this);
		photoB.setOnClickListener(this);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		// 关闭窗体动画显示
		super.finish();
		this.overridePendingTransition(0, R.anim.activity_close);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// Do something.
			this.finish();// 直接调用杀死当前activity方法.
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.close_photo:
			this.finish();
			break;
		case R.id.photo_a:
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File dir = new File("/sdcard/InsPicSoc/Camera/");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			mKXApplication.mUploadPhotoPath = "/sdcard/InsPicSoc/Camera/"
					+ UUID.randomUUID().toString();
			File file = new File(mKXApplication.mUploadPhotoPath);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {

				}
			}
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			this.startActivityForResult(intent,
					ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_CAMERA);
			break;
		case R.id.photo_b:
			intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			this.startActivityForResult(intent,ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_ALBUM);
//			this.startActivity(new Intent(MenuActivity.this,
//					PhoneAlbumActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		/**
		 * 通过照相上传图片
		 */
		case ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_CAMERA:
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					Toast.makeText(this, "SD不可用", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent();
				intent.setClass(MenuActivity.this, ImageFilterActivity.class);
				String path = PhotoUtil.saveToLocal(PhotoUtil.createBitmap(
						mKXApplication.mUploadPhotoPath, mScreenWidth,
						mScreenHeight));
				intent.putExtra("path", path);
				startActivityForResult(intent,ActivityForResultUtil.REQUESTCODE_MENU);
			} else {
				Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
				this.finish();
			}
			break;
		case ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_ALBUM:
			if(resultCode == RESULT_OK && null!=data) {
				Uri selectedImage = data.getData();
			    String[] filePathColumns={MediaStore.Images.Media.DATA};
			    Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null,null, null);
			    c.moveToFirst();
			    int columnIndex = c.getColumnIndex(filePathColumns[0]);
			    String picturePath = c.getString(columnIndex);
			    c.close();
			    
			    Intent intent = new Intent();
			
				intent.setClass(MenuActivity.this, ImageFilterActivity.class);
				String path = PhotoUtil.saveToLocal(PhotoUtil.createBitmap(
						picturePath, mScreenWidth,
						mScreenHeight));
				intent.putExtra("path", path);
				// 开始跳转界面
				startActivityForResult(intent,ActivityForResultUtil.REQUESTCODE_MENU);
			} else {
				Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
				this.finish();
			}
			break;
		case ActivityForResultUtil.REQUESTCODE_MENU:
			if(resultCode==RESULT_OK){
				Intent i=new Intent();
				i.setClass(MenuActivity.this,MainActivity.class);
				setResult(RESULT_OK,i);
			}
			this.finish();
			break;
		}
	}
}
