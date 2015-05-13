package com.astudio.inspicsoc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.astudio.inspicsoc.R;

public class ModifyActivity extends InsActivity implements OnClickListener {
	private ImageView mHead;
	private Button back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_personal_info);
		mHead=(ImageView)findViewById(R.id.face);
		mHead.setImageBitmap(mKXApplication.getPhoneAlbum(mKXApplication.mHeadBitmap));
		back = (Button) this.findViewById(R.id.friends_back);
		back.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.friends_back:
			this.finish();
			break;
		}
	}
}
