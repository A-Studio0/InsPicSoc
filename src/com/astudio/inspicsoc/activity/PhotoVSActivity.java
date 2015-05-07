package com.astudio.inspicsoc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.astudio.inspicsoc.R;

public class PhotoVSActivity extends Activity {

	private ImageButton pk;
	private Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_vs);
		pk = (ImageButton) findViewById(R.id.pkButton);
		pk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(PhotoVSActivity.this,
						PhotoVSResultActivity.class);
				startActivity(it);

			}

		});
	}

}
