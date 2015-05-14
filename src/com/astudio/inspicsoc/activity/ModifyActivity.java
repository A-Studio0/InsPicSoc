package com.astudio.inspicsoc.activity;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.astudio.android.bitmapfun.util.ImageFetcher;
import com.astudio.android.bitmapfun.util.ImageResizer;
import com.astudio.dodowaterfall.Helper;
import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.common.InsUrl;
import com.astudio.inspicsoc.model.UserDetail;

public class ModifyActivity extends InsActivity implements OnClickListener {
	private ImageView mHead;
	private Button back;
	private Button modifybtn;
	private TextView editnickName;
	private TextView editgender;
	private TextView editemail;
	private TextView editaddress;
	private TextView editphone;
	private TextView editbirthday;
	private Activity mActivity = this;
	private String username;
	UserDetail user = new UserDetail();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_personal_info);
		mHead = (ImageView) findViewById(R.id.face);
		mHead.setImageBitmap(mKXApplication
				.getPhoneAlbum(mKXApplication.mHeadBitmap));
		back = (Button) this.findViewById(R.id.friends_back);
		modifybtn = (Button) findViewById(R.id.button_modify);
		editnickName = (TextView) findViewById(R.id.editname);
		editgender = (TextView) findViewById(R.id.editgender);
		editemail = (TextView) findViewById(R.id.editemail);
		editaddress = (TextView) findViewById(R.id.editaddress);
		editphone = (TextView) findViewById(R.id.editphone);
		editbirthday = (TextView) findViewById(R.id.editbirthday);
		mHead.setImageBitmap(mKXApplication
				.getPhoneAlbum(mKXApplication.mHeadBitmap));

		username = mKXApplication.userName;

		ContentTask task = new ContentTask(this, 2);
		task.execute(username);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.friends_back:
			this.finish();
			break;
		case R.id.button_modify:
			break;
		}
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
						mActivity.getApplicationContext(), path);
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
					mHead.setImageBitmap(ImageResizer
							.decodeSampledBitmapFromFile(result.toString(), 80,
									80));
					editnickName.setText(user.getUserNickName());
					editgender.setText(user.getGender());
					editaddress.setText(user.getAddress());
					// personalGraph.setText(user.getPersonalGraph());
					editphone.setText(user.getPhone());
					editemail.setText(user.getEmail());
					editbirthday.setText(user.getBirthDay());

				}
			} else if (mType == 2) {
				if (null != result) {
					mHead.setImageBitmap(ImageResizer
							.decodeSampledBitmapFromFile(result.toString(), 80,
									80));
					editnickName.setText(user.getUserNickName());
					editgender.setText(user.getGender());
					editaddress.setText(user.getAddress());
					// personalGraph.setText(user.getPersonalGraph());
					editphone.setText(user.getPhone());

					editbirthday.setText(user.getBirthDay());
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
}
