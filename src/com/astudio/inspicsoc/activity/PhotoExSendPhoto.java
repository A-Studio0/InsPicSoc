package com.astudio.inspicsoc.activity;

import com.astudio.inspicsoc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

//以P2P的形式发图
public class PhotoExSendPhoto extends Activity implements OnClickListener {

	// 顶部返回按钮
	private ImageButton back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_ex_send_photo);

		initData();
		back.setOnClickListener(this);
	}

	private void initData() {
		back = (ImageButton) findViewById(R.id.photo_back);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.photo_back:
			Intent intent = new Intent(PhotoExSendPhoto.this,
					PhotoExDetailActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
