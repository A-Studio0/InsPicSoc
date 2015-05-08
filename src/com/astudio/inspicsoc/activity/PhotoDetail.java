package com.astudio.inspicsoc.activity;

import com.astudio.android.bitmapfun.util.ImageFetcher;
import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class PhotoDetail extends Activity {
	private ImageView photo = null;
	
	private ImageFetcher mImageFetcher;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_detail);
		photo = (ImageView) this.findViewById(R.id.photo);
		mImageFetcher = new ImageFetcher(this, 240);
		mImageFetcher.setLoadingImage(R.drawable.empty_photo);
		mImageFetcher.loadImage("http://f.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa62c633e9b68f8c5494ee7b0b.jpg", photo);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mImageFetcher.setExitTasksEarly(false);
	}
}
