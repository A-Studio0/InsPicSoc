package com.astudio.inspicsoc.activity;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astudio.android.bitmapfun.util.ImageFetcher;
import com.astudio.dodowaterfall.Helper;
import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.common.InsUrl;
import com.astudio.inspicsoc.view.RoundedImageView;

@SuppressLint("ValidFragment")
public class LeftSlidingMenuFragment extends Fragment implements
		OnClickListener {
	protected InsApplication mKXApplication;
	private View miaoBtnLayout;
	private View circleBtnLayout;
	private View settingBtnLayout;
	// private View inviteFriendBtnLayout;
	private MainActivity activity;
	private ImageFetcher mImageFetcher;
	private TextView nickName;
	private String userName;

	private RoundedImageView headImageView;
	public static View view;
	ContentTask task = new ContentTask(this.getActivity(), 2);

	// private ;

	public LeftSlidingMenuFragment(MainActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mKXApplication = (InsApplication) this.getActivity().getApplication();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.main_left_fragment, container, false);
		miaoBtnLayout = view.findViewById(R.id.miaoBtnLayout);
		miaoBtnLayout.setOnClickListener(this);
		circleBtnLayout = view.findViewById(R.id.circleBtnLayout);
		circleBtnLayout.setOnClickListener(this);
		settingBtnLayout = view.findViewById(R.id.settingBtnLayout);
		settingBtnLayout.setOnClickListener(this);

		nickName = (TextView) view.findViewById(R.id.nickNameTextView);

		headImageView = (RoundedImageView) view
				.findViewById(R.id.headImageView);

		mImageFetcher = new ImageFetcher(headImageView.getContext(), 240);

		task = new ContentTask(this.getActivity(), 2);
		userName = mKXApplication.userName;
		task.execute(userName);

		// settingBtnLayout = view.findViewById(R.id.inviteFriendBtnLayout);
		// settingBtnLayout.setOnClickListener(this);

		System.out.println();
		return view;
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
			File data = mImageFetcher.downloadBitmap(
					activity.getApplicationContext(), path);
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
					headImageView.setImageBitmap(mImageFetcher
							.decodeSampledBitmapFromFile(result.toString(), 80,
									80));
					nickName.setText(userName);
				}
			} else if (mType == 2) {
				if (null != result) {
					headImageView.setImageBitmap(mImageFetcher
							.decodeSampledBitmapFromFile(result.toString(), 80,
									80));
					nickName.setText(userName);
				}
			}

		}

		@Override
		protected void onPreExecute() {
		}

		public String loadHeadPic(String username) {

			String userheapPicpath = InsUrl.GET_HEADPIC
					.replace("@un", username);

			String json = "";
			if (Helper.checkConnection(mContext)) {
				try {
					json = Helper.getStringFromUrl(userheapPicpath);

					System.out.println("====json====" + json);
				} catch (IOException e) {
					Log.e("IOException is : ", e.toString());
					e.printStackTrace();
					return userheapPicpath;
				}
			}
			Log.d("MainActiivty", "json:" + json);
			return json;

		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.miaoBtnLayout:
			intent = new Intent(activity, MessageCenterActivity.class);
			startActivity(intent);
			miaoBtnLayout.setSelected(true);
			circleBtnLayout.setSelected(false);
			settingBtnLayout.setSelected(false);
			break;
		case R.id.circleBtnLayout:
			intent = new Intent(activity, FriendActivity.class);
			startActivity(intent);
			miaoBtnLayout.setSelected(false);
			circleBtnLayout.setSelected(true);
			settingBtnLayout.setSelected(false);
			break;
		case R.id.settingBtnLayout:
			intent = new Intent(activity, MyInfoActivity.class);
			startActivity(intent);
			miaoBtnLayout.setSelected(false);
			circleBtnLayout.setSelected(false);
			settingBtnLayout.setSelected(true);
			break;

		default:
			break;
		}

	}
}
