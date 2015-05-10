package com.astudio.inspicsoc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.astudio.inspicsoc.R;

public class MyInfoActivity extends Activity implements OnClickListener {

	private ImageButton back = null;
	private View settingBtnLayout;
	private Activity mActivity = this;
	private Button settingsLogoutBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info);
		back = (ImageButton) this.findViewById(R.id.ivTitleBtnLeft);
		back.setOnClickListener(this);
		settingBtnLayout = findViewById(R.id.button_settings_account);

		settingBtnLayout.setOnClickListener(this);
		
		settingsLogoutBtn = (Button)findViewById(R.id.button_settings_logout);
		settingsLogoutBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (arg0.getId()) {
		case R.id.button_settings_account:
			intent = new Intent(MyInfoActivity.this, ModifyActivity.class);
			mActivity.startActivity(intent);
			break;
		case R.id.button_settings_logout:
			intent = new Intent(MyInfoActivity.this, LoginActivity.class);
			mActivity.startActivity(intent);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
		settingBtnLayout.setSelected(false);
	}

}
