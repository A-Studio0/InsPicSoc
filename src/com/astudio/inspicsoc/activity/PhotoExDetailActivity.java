package com.astudio.inspicsoc.activity;

import com.astudio.inspicsoc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class PhotoExDetailActivity extends Activity implements OnClickListener {

	//定义Menu菜单选项的ItemId
    final static int ONE = Menu.FIRST;
    final static int TWO = Menu.FIRST+1;
    //顶部返回按钮
	private ImageButton back;
	//底部“我要兑换”按钮
	private Button commit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_exchange_detail);

		initData();
		back.setOnClickListener(this);
		commit.setOnClickListener(this);
	}

	private void initData() {
		back = (ImageButton) findViewById(R.id.px_back);
        commit = (Button)findViewById(R.id.px_btn);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.px_back:
			Intent intent = new Intent(PhotoExDetailActivity.this,
					Photo_exchange.class);
			startActivity(intent);
			break;
		case R.id.px_btn:
			openOptionsMenu();
			break;
		default:
			break;
		}
	}

	//底部我要兑换按钮弹出的菜单选项
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, ONE, 0, "兑换图片");  //设置文字与图标
        menu.add(0, TWO, 0, "兑换积分");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
        case 1:
        	Intent intent1 = new Intent(PhotoExDetailActivity.this,
        			PhotoExSendPhoto.class);
			startActivity(intent1); 
            break;
        case 2:
        	Intent intent2 = new Intent(PhotoExDetailActivity.this,
					PhotoExSendPoints.class);
			startActivity(intent2);
            break;
        }
		return super.onOptionsItemSelected(item);
	}

}
