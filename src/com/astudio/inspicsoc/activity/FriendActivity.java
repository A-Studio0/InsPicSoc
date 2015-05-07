package com.astudio.inspicsoc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.astudio.inspicsoc.R;

public class FriendActivity extends Activity implements OnClickListener {

	private ImageButton back = null;
	private View circleBtnLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends);
		back = (ImageButton) this.findViewById(R.id.ivTitleBtnLeft);
		back.setOnClickListener(this);
		circleBtnLayout = LeftSlidingMenuFragment.view
				.findViewById(R.id.circleBtnLayout);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ivTitleBtnLeft:
			this.finish();
			circleBtnLayout.setSelected(false);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
		circleBtnLayout.setSelected(false);
	}

}
