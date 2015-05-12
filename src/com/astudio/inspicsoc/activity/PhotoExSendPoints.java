package com.astudio.inspicsoc.activity;

import com.astudio.inspicsoc.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

//以P2P的形式发积分，理想类似支付宝转账。。。
public class PhotoExSendPoints extends Activity implements OnClickListener{
	//顶部返回按钮
	private ImageButton back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_ex_send_points);
		
		initData();
		back.setOnClickListener(this);
	}

	private void initData() {
		back = (ImageButton)findViewById(R.id.points_back);
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.points_back:
			Intent intent = new Intent(PhotoExSendPoints.this,
					PhotoExDetailActivity.class);
			startActivity(intent);
			break; 
		default:
			break;
		}
	}
}
