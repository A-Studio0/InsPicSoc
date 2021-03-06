package com.astudio.inspicsoc.activity;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astudio.android.bitmapfun.util.ImageFetcher;
import com.astudio.android.bitmapfun.util.ImageResizer;
import com.astudio.dodowaterfall.Helper;
import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.common.InsUrl;
import com.astudio.inspicsoc.model.UserDetail;
import com.astudio.inspicsoc.utils.DateUtil;

public class FriendInfoActivity extends Activity implements OnClickListener {
	protected InsApplication mKXApplication;
	private ImageButton back = null;
	private ImageView headImageView;
	private Button logoutBtn;
	private TextView nickName;
	private TextView personalGraph;
	private TextView gender;
	private TextView email;
	private TextView address;
	private TextView phone;
	private TextView birthday;
	private Activity activity;
	// private ImageFetcher mImageFetcher;
	private String friendUserName;// 好友用户名
	private Activity mActivity = this;
	private RelativeLayout mAccount;
	UserDetail user = new UserDetail();

	private TextView nameTitle;
	private ImageView gone;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info);
		friendUserName = getIntent().getStringExtra("friendUserName");

		logoutBtn = (Button) findViewById(R.id.button_settings_logout);
		logoutBtn.setVisibility(Button.GONE);

		back = (ImageButton) this.findViewById(R.id.ivTitleBtnLeft);
		back.setOnClickListener(this);

		mAccount = (RelativeLayout) findViewById(R.id.button_settings_account);

		nameTitle = (TextView) findViewById(R.id.ivTitleName);
		nameTitle.setText(friendUserName + "信息");

		gone = (ImageView) findViewById(R.id.byebye);
		gone.setVisibility(ImageView.GONE);

		nickName = (TextView) findViewById(R.id.nick);
		nickName.setText(friendUserName);
		personalGraph = (TextView) findViewById(R.id.status);
		gender = (TextView) findViewById(R.id.textView3);
		email = (TextView) findViewById(R.id.email);
		address = (TextView) findViewById(R.id.address);
		phone = (TextView) findViewById(R.id.phone);
		birthday = (TextView) findViewById(R.id.birthday);
		headImageView = (ImageView) findViewById(R.id.face);

		activity = this;
		Intent intent = getIntent();
		friendUserName = intent.getStringExtra("friendUserName");

		ContentTask task = new ContentTask(this, 2);
		task.execute(friendUserName);

	}

	private class ContentTask extends AsyncTask<String, Integer, File> {

		private Context mContext;
		private int mType = 1;

		public ContentTask(Context context, int type) {
			super();
			mContext = context;
			mType = type;
		}

		@Override
		protected File doInBackground(String... params) {
			String path = loadHeadPic(params[0]);
			File data = null;
			if (null != path) {
				data = ImageFetcher.downloadBitmap(
						activity.getApplicationContext(), path);
			}
			if (null != data)
				return data;
			else
				return null;
		}

		@Override
		protected void onPostExecute(File result) {
			if (mType == 1) {

				// mImageFetcher.loadImage(result, headImageView);
				if (null != result) {
					headImageView.setImageBitmap(ImageResizer
							.decodeSampledBitmapFromFile(result.toString(), 80,
									80));
					nickName.setText(friendUserName);
					gender.setText(user.getGender());
					address.setText(user.getAddress());
					personalGraph.setText(user.getPersonalGraph());
					phone.setText(user.getPhone());
					email.setText(user.getEmail());
					birthday.setText(DateUtil.formatDate(DateUtil.parseDateStr(
							user.getBirthDay(), DateUtil.TIME_FORMAT_SEPERATE),
							DateUtil.TIME_FORMAT_SEPERATE));

				}
			} else if (mType == 2) {
				if (null != result) {
					headImageView.setImageBitmap(ImageResizer
							.decodeSampledBitmapFromFile(result.toString(), 80,
									80));
					nickName.setText(friendUserName);
					gender.setText(user.getGender());
					address.setText(user.getAddress());
					email.setText(user.getEmail());
					personalGraph.setText(user.getPersonalGraph());
					phone.setText(user.getPhone());

					birthday.setText(DateUtil.formatDate(DateUtil.parseDateStr(
							user.getBirthDay(), DateUtil.TIME_FORMAT_SEPERATE),
							DateUtil.TIME_FORMAT_SEPERATE));
				}
			}

		}

		@Override
		protected void onPreExecute() {
		}

		public String loadHeadPic(String username) {

			String userDetail = InsUrl.GET_USER_DETAIL.replace("@un", username);

			String json = "";
			if (Helper.checkConnection(mContext)) {
				try {
					json = Helper.getStringFromUrl(userDetail);

					System.out.println("====json====" + json);
				} catch (IOException e) {
					Log.e("IOException is : ", e.toString());
					e.printStackTrace();
					return null;
				}
			}
			Log.d("MyInfoActiivty", "json:" + json);

			try {

				JSONObject jsonObject = new JSONObject(json);
				if (null != jsonObject) {

					user.setUserName(jsonObject.getString("userName"));
					user.setAddress(jsonObject.isNull("address") ? ""
							: jsonObject.getString("address"));
					user.setEmail(jsonObject.isNull("email") ? "" : jsonObject
							.getString("email"));
					user.setPersonalGraph(jsonObject.isNull("personalGraph") ? "这个人很懒，什么都没说"
							: jsonObject.getString("personalGraph"));
					user.setBirthDay(jsonObject.isNull("birthDay") ? ""
							: jsonObject.getString("birthDay"));
					user.setPhone(jsonObject.isNull("phone") ? "" : jsonObject
							.getString("phone"));
					user.setUserNickName(jsonObject.isNull("userNickName") ? ""
							: jsonObject.getString("userNickName"));
					user.setGender(jsonObject.getInt("gender") == 1 ? "男" : "女");
					user.setHeadPic(jsonObject.isNull("headPic") ? null
							: jsonObject.getString("headPic"));
				}

				return user.getHeadPic();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return user.getHeadPic();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ivTitleBtnLeft:
			this.finish();
			break;
		case R.id.button_settings_logout:
			Intent intent1 = new Intent(FriendInfoActivity.this,
					LoginActivity.class);
			mActivity.startActivity(intent1);
			finish();
			break;
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
	}
}