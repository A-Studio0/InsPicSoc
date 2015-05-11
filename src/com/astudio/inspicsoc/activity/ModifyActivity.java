package com.astudio.inspicsoc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.astudio.inspicsoc.R;

public class ModifyActivity extends InsActivity {
	private ImageView mHead;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_personal_info);
		mHead=(ImageView)findViewById(R.id.face);
		mHead.setImageBitmap(mKXApplication.mHeadBitmap);
	}
}
