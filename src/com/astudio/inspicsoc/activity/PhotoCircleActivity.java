package com.astudio.inspicsoc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astudio.inspicsoc.R;

public class PhotoCircleActivity extends Activity implements OnClickListener {
	private Button back;
	RelativeLayout button[] = new RelativeLayout[13];
	TextView title[] = new TextView[13];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.circles);
		back = (Button) this.findViewById(R.id.photo_circle_back);
		back.setOnClickListener(this);

		button[0] = (RelativeLayout) this.findViewById(R.id.imageView1);
		button[1] = (RelativeLayout) this.findViewById(R.id.imageView2);
		button[2] = (RelativeLayout) this.findViewById(R.id.imageView3);
		button[3] = (RelativeLayout) this.findViewById(R.id.imageView4);
		button[4] = (RelativeLayout) this.findViewById(R.id.imageView5);
		button[5] = (RelativeLayout) this.findViewById(R.id.imageView6);
		button[6] = (RelativeLayout) this.findViewById(R.id.imageView7);
		button[7] = (RelativeLayout) this.findViewById(R.id.imageView8);
		button[8] = (RelativeLayout) this.findViewById(R.id.imageView9);
		button[9] = (RelativeLayout) this.findViewById(R.id.imageView10);
		button[10] = (RelativeLayout) this.findViewById(R.id.imageView11);
		button[11] = (RelativeLayout) this.findViewById(R.id.imageView12);
		button[12] = (RelativeLayout) this.findViewById(R.id.imageView13);
		for (int i = 0; i < 13; i++) {
			button[i].setOnClickListener(this);
		}
		title[0] = (TextView) this.findViewById(R.id.title1);
		title[1] = (TextView) this.findViewById(R.id.title2);
		title[2] = (TextView) this.findViewById(R.id.title3);
		title[3] = (TextView) this.findViewById(R.id.title4);
		title[4] = (TextView) this.findViewById(R.id.title5);
		title[5] = (TextView) this.findViewById(R.id.title6);
		title[6] = (TextView) this.findViewById(R.id.title7);
		title[7] = (TextView) this.findViewById(R.id.title8);
		title[8] = (TextView) this.findViewById(R.id.title9);
		title[9] = (TextView) this.findViewById(R.id.title10);
		title[10] = (TextView) this.findViewById(R.id.title11);
		title[11] = (TextView) this.findViewById(R.id.title12);
		title[12] = (TextView) this.findViewById(R.id.title13);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String str = "圈圈们";
		switch (arg0.getId()) {
		case R.id.photo_circle_back:
			this.finish();
			break;
		case R.id.imageView1:
			str = title[0].getText().toString();
			break;
		case R.id.imageView2:
			str = title[1].getText().toString();
			break;
		case R.id.imageView3:
			str = title[2].getText().toString();
			break;
		case R.id.imageView4:
			str = title[3].getText().toString();
			break;
		case R.id.imageView5:
			str = title[4].getText().toString();
			break;
		case R.id.imageView6:
			str = title[5].getText().toString();
			break;
		case R.id.imageView7:
			str = title[6].getText().toString();
			break;
		case R.id.imageView8:
			str = title[7].getText().toString();
			break;
		case R.id.imageView9:
			str = title[8].getText().toString();
			break;
		case R.id.imageView10:
			str = title[9].getText().toString();
			break;
		case R.id.imageView11:
			str = title[10].getText().toString();
			break;
		case R.id.imageView12:
			str = title[11].getText().toString();
			break;
		case R.id.imageView13:
			str = title[12].getText().toString();
			break;
		}
		Intent intent = new Intent(this, QuanquanActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("title", str);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
