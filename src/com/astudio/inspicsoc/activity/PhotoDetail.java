package com.astudio.inspicsoc.activity;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class PhotoDetail extends Activity {
	private ImageView photo = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_detail);
			photo = (ImageView) this.findViewById(R.id.photo);
			photo.setBackgroundResource(R.drawable.img07);
		
		
	}
}
