package com.astudio.inspicsoc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.astudio.inspicsoc.R;

public class FriendActivity extends Activity implements OnClickListener {

	private ImageButton back = null;
	private View circleBtnLayout;
	private ImageButton invite = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends);
		ShareSDK.initSDK(this);
		back = (ImageButton) this.findViewById(R.id.ivTitleBtnLeft);
		back.setOnClickListener(this);
		circleBtnLayout = LeftSlidingMenuFragment.view
				.findViewById(R.id.circleBtnLayout);
		invite = (ImageButton)this.findViewById(R.id.invite);
		invite.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ivTitleBtnLeft:
			this.finish();
			circleBtnLayout.setSelected(false);
			break;
		case R.id.invite:
			OnekeyShare oks = new OnekeyShare();
			// 分享时Notification的图标和文字
			oks.setNotification(R.drawable.ic_launcher,
					FriendActivity.this.getString(R.string.app_name));
			// text是分享文本，所有平台都需要这个字段
			oks.setText("分享内容");
			// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
			oks.setTitle(getString(R.string.share));
			// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
			//oks.setTitleUrl("http://sharesdk.cn");
			// text是分享文本，所有平台都需要这个字段
			oks.setText("我是分享文本");
			// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
			// oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
			// url仅在微信（包括好友和朋友圈）中使用
			oks.setUrl("http://sharesdk.cn");
			// comment是我对这条分享的评论，仅在人人网和QQ空间使用
			//oks.setComment("我是测试评论文本");
			// site是分享此内容的网站名称，仅在QQ空间使用
			//oks.setSite(getString(R.string.app_name));
			// siteUrl是分享此内容的网站地址，仅在QQ空间使用
			//oks.setSiteUrl("http://sharesdk.cn");

			oks.show(arg0.getContext());
			break;
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
		circleBtnLayout.setSelected(false);
	}

}
