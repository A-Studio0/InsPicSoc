package com.astudio.inspicsoc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.astudio.inspicsoc.R;

public class MessageCenterActivity extends Activity implements OnClickListener {

	private ImageButton back;
	private View miaoBtnLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_center);
		back = (ImageButton) this.findViewById(R.id.ivTitleBtnLeft);
		back.setOnClickListener(this);
		miaoBtnLayout = LeftSlidingMenuFragment.view
				.findViewById(R.id.miaoBtnLayout);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ivTitleBtnLeft:
			this.finish();
			miaoBtnLayout.setSelected(false);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
		miaoBtnLayout.setSelected(false);
	}

}
