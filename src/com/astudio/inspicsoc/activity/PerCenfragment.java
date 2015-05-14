package com.astudio.inspicsoc.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.astudio.android.bitmapfun.util.ImageFetcher;
import com.astudio.android.bitmapfun.util.ImageResizer;
import com.astudio.dodowaterfall.Helper;
import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.adapter.PerCenItemAdapter;
import com.astudio.inspicsoc.common.InsUrl;
import com.astudio.inspicsoc.model.MsgDto;
import com.astudio.inspicsoc.view.RoundedImageView;

public class PerCenfragment extends Fragment {
	private RoundedImageView mHead;
	private InsApplication mApp;
	public static PerCenItemAdapter adapter;
	private ImageFetcher mImageFetcher;
	private Activity mActivity;
	List<MsgDto> Msgs = new ArrayList<MsgDto>();
	private String headpic;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.person_center_fragment, container,
				false);
		mApp = (InsApplication) getActivity().getApplication();
		mActivity = this.getActivity();
		mHead = (RoundedImageView) v.findViewById(R.id.headImageView);
		ContentTask task = new ContentTask(mActivity, 2);
		Log.e("userName", mApp.userName);
		task.execute(InsUrl.GET_USERMSGLIST.replace("@un", mApp.userName));
		/*
		 * ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		 * MainActivity.this,android.R.layout.simple_list_item_1,data);
		 */
		// View view = null;
		adapter = new PerCenItemAdapter(getActivity(), R.layout.percen_item,
				mApp.PerCenItemList, mApp);
		ListView listView = (ListView) v.findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		/*
		 * listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		 * { public void onItemClick(AdapterView<?> parent, View view,int
		 * position,long id){ PerCenItem PerCenItem =
		 * PerCenItemList.get(position);
		 * 
		 * } });
		 */

		return v;

	}

	private class ContentTask extends AsyncTask<String, Integer, List<MsgDto>> {

		private Context mContext;
		private int mType = 1;

		public ContentTask(Context context, int type) {
			super();
			mContext = context;
			mType = type;
		}

		@Override
		protected List<MsgDto> doInBackground(String... params) {
			try {
				return parseNewsJSON(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<MsgDto> result) {
			if (mType == 1) {

			} else if (mType == 2) {
				mHead.setImageBitmap(ImageResizer.decodeSampledBitmapFromFile(
						headpic, 130, 130));
			}

		}

		@Override
		protected void onPreExecute() {
		}

		public List<MsgDto> parseNewsJSON(String url) throws IOException {

			String userheapPicpath = InsUrl.GET_HEADPIC.replace("@un",
					mApp.userName);
			String json = "";
			if (Helper.checkConnection(mContext)) {
				try {
					json = Helper.getStringFromUrl(InsUrl.GET_USERMSGLIST
							.replace("@un", mApp.userName));
					File data = ImageFetcher.downloadBitmap(
							mActivity.getApplicationContext(),
							Helper.getStringFromUrl(userheapPicpath));
					headpic = data.toString();
					System.out.println("====json====" + json);
				} catch (IOException e) {
					Log.e("IOException is : ", e.toString());
					e.printStackTrace();
					return Msgs;
				}
			}
			Log.d("MainActiivty", "json:" + json);

			try {
				if (null != json) {
					// JSONObject newsObject = new JSONObject(json);
					// JSONObject jsonObject = newsObject.getJSONObject("data");
					JSONArray blogsJson = new JSONArray(json);

					for (int i = 0; i < blogsJson.length(); i++) {
						JSONObject newObject = blogsJson.getJSONObject(i);
						MsgDto newsInfo1 = new MsgDto();
						newsInfo1.setMsgId((newObject.isNull("msgId") ? ""
								: newObject.getString("msgId")));

						JSONArray smallpics = newObject.getJSONArray("pics");
						newsInfo1.setSinglePic((smallpics.getString(0)
								.isEmpty() ? "" : smallpics.getString(0)));

						newsInfo1.setHeadPic(newObject.isNull("headPic") ? ""
								: newObject.getString("headPic"));
						newsInfo1.setContent(newObject.isNull("content") ? ""
								: newObject.getString("content"));
						newsInfo1.setCommentsNum(newObject
								.getInt("commentsNum"));
						newsInfo1.setUserName(newObject.isNull("userName") ? ""
								: newObject.getString("userName"));
						newsInfo1.setUserNickName(newObject
								.isNull("userNickName") ? "" : newObject
								.getString("userNickName"));
						newsInfo1.setTime(newObject.getString("time"));
						File data = ImageFetcher.downloadBitmap(
								mActivity.getApplicationContext(),
								newsInfo1.getHeadPic());
						newsInfo1.setHeadPic(data.toString());
						Msgs.add(newsInfo1);
					}
					Log.e("msgsSize", String.valueOf(Msgs.size()));
					return Msgs;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return Msgs;

		}
	}

	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * action bar item clicks here. The action bar will // automatically handle
	 * clicks on the Home/Up button, so long // as you specify a parent activity
	 * in AndroidManifest.xml. int id = item.getItemId(); if (id ==
	 * R.id.action_settings) { return true; } return
	 * super.onOptionsItemSelected(item); }
	 */

	// public class PerCenItemAdapter extends ArrayAdapter<MsgDto> {
	// private int resourceId;
	// private InsApplication myApp;
	// private List<MsgDto> mInfos;
	//
	// public PerCenItemAdapter(Context context, int textViewResourceId,
	// List<MsgDto> msgs, InsApplication mApp) {
	// super(context, textViewResourceId, msgs);
	// resourceId = textViewResourceId;
	// this.myApp = mApp;
	// mInfos = msgs;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// View view;
	// ViewHolder viewHolder;
	// final MsgDto BriefMsg = mInfos.get(position);
	// if (convertView == null) {
	// view = LayoutInflater.from(getContext()).inflate(resourceId,
	// null);
	// viewHolder = new ViewHolder();
	// viewHolder.PerCenItemImage = (ImageView) view
	// .findViewById(R.id.percenitem_image);
	// viewHolder.PerCenItemComment = (ImageView) view
	// .findViewById(R.id.percenitem_comment);
	// viewHolder.PerCenItemDescription = (TextView) view
	// .findViewById(R.id.percenitem_description);
	// viewHolder.PerCenItemPosition = (TextView) view
	// .findViewById(R.id.percenitem_position);
	// viewHolder.PerCenItemDate = (TextView) view
	// .findViewById(R.id.percenitem_date);
	// viewHolder.PerCenItemCollectNum = (TextView) view
	// .findViewById(R.id.percenitem_collectNum);
	// viewHolder.PerCenItemViewNum = (TextView) view
	// .findViewById(R.id.percenitem_viewNum);
	// viewHolder.PerCenItemVoiceLayout = (LinearLayout) view
	// .findViewById(R.id.voice_display_voice_layout);
	// viewHolder.PerCenItemVoicePlay = (ImageView) view
	// .findViewById(R.id.voice_display_voice_play);
	// viewHolder.PerCenItemVoiceProgressBar = (ProgressBar) view
	// .findViewById(R.id.voice_display_voice_progressbar);
	// viewHolder.PerCenItemVoiceTime = (TextView) view
	// .findViewById(R.id.voice_display_voice_time);
	// view.setTag(viewHolder);
	// } else {
	// view = convertView;
	// viewHolder = (ViewHolder) view.getTag();
	// }
	// mImageFetcher.loadImage(BriefMsg.getSinglePic(),
	// viewHolder.PerCenItemImage);
	// // viewHolder.PerCenItemImage.setImageBitmap(myApp
	// // .getPhoneAlbum(PerCenItem.getImageId()));
	// // if (PerCenItem.getComment() != 0) {
	// // viewHolder.PerCenItemComment.setImageResource(PerCenItem
	// // .getComment());
	// // viewHolder.PerCenItemComment.setVisibility(ImageView.VISIBLE);
	// // } else {
	// // viewHolder.PerCenItemComment.setVisibility(ImageView.GONE);
	// // }
	// if (null == BriefMsg.getContent()) {
	// viewHolder.PerCenItemDescription
	// .setVisibility(TextView.INVISIBLE);
	// } else {
	// viewHolder.PerCenItemDescription
	// .setVisibility(TextView.VISIBLE);
	// viewHolder.PerCenItemDescription.setText(BriefMsg.getContent());
	// }
	//
	// if (null == BriefMsg.getLocationName()) {
	// viewHolder.PerCenItemPosition.setVisibility(TextView.INVISIBLE);
	// } else {
	// viewHolder.PerCenItemPosition.setVisibility(TextView.VISIBLE);
	// viewHolder.PerCenItemPosition.setText(BriefMsg
	// .getLocationName());
	// }
	//
	// viewHolder.PerCenItemDate.setText(BriefMsg.getTime());
	// viewHolder.PerCenItemCollectNum.setText(BriefMsg.getCommentsNum());
	// if (null == BriefMsg.getVoice()) {
	// viewHolder.PerCenItemVoiceLayout
	// .setVisibility(LinearLayout.GONE);
	// } else {
	// // viewHolder.PerCenItemVoiceTime.setText((int) PerCenItem
	// // .getPlayTimeId() + "″");
	// // viewHolder.PerCenItemVoiceProgressBar.setMax((int) PerCenItem
	// // .getPlayTimeId());
	// // viewHolder.PerCenItemVoiceProgressBar.setProgress(0);
	// // viewHolder.PerCenItemVoiceLayout
	// // .setVisibility(LinearLayout.VISIBLE);
	// // viewHolder.PerCenItemVoicePlay
	// // .setOnClickListener(new myListener(
	// // viewHolder.PerCenItemVoicePlay,
	// // viewHolder.PerCenItemVoiceProgressBar,
	// // PerCenItem.getPlayTimeId(), PerCenItem
	// // .getVoiceId()));
	// }
	//
	// return view;
	// }
	//
	// class ViewHolder {
	// ImageView PerCenItemImage;
	// ImageView PerCenItemComment;
	// TextView PerCenItemDescription;
	// TextView PerCenItemPosition;
	// TextView PerCenItemDate;
	// TextView PerCenItemCollectNum;
	// TextView PerCenItemViewNum;
	// LinearLayout PerCenItemVoiceLayout;
	// ImageView PerCenItemVoicePlay;
	// ProgressBar PerCenItemVoiceProgressBar;
	// TextView PerCenItemVoiceTime;
	// }
	//
	// class myListener implements OnClickListener {
	// private ImageView PerCenItemVoicePlayTemp;
	// private ProgressBar PerCenItemVoiceProgressBarTemp;
	// private float mPlayTime;
	// private String mCurrentVoicePath;
	// private boolean mPlayState; // 播放状态
	// private int mPlayCurrentPosition;// 当前播放的时间
	// private MediaPlayer mMediaPlayer;
	//
	// myListener(ImageView PerCenItemVoicePlayTemp,
	// ProgressBar PerCenItemVoiceProgressBarTemp,
	// float mPlayTime, String mCurrentVoicePath) {
	// this.PerCenItemVoicePlayTemp = PerCenItemVoicePlayTemp;
	// this.PerCenItemVoiceProgressBarTemp = PerCenItemVoiceProgressBarTemp;
	// this.mPlayTime = mPlayTime;
	// this.mCurrentVoicePath = mCurrentVoicePath;
	// }
	//
	// @Override
	// public void onClick(View v) {
	// // 播放录音
	// if (!mPlayState) {
	// mMediaPlayer = new MediaPlayer();
	// try {
	// // 添加录音的路径
	// mMediaPlayer.setDataSource(mCurrentVoicePath);
	// // 准备
	// mMediaPlayer.prepare();
	// // 播放
	// mMediaPlayer.start();
	// // 根据时间修改界面
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	//
	// PerCenItemVoiceProgressBarTemp
	// .setMax((int) mPlayTime);
	// mPlayCurrentPosition = 0;
	// while (mMediaPlayer.isPlaying()) {
	// mPlayCurrentPosition = mMediaPlayer
	// .getCurrentPosition() / 1000;
	// PerCenItemVoiceProgressBarTemp
	// .setProgress(mPlayCurrentPosition);
	// }
	// }
	// }).start();
	// // 修改播放状态
	// mPlayState = true;
	// // 修改播放图标
	// PerCenItemVoicePlayTemp
	// .setImageResource(R.drawable.globle_player_btn_stop);
	//
	// mMediaPlayer
	// .setOnCompletionListener(new OnCompletionListener() {
	// // 播放结束后调用
	// @Override
	// public void onCompletion(MediaPlayer mp) {
	// // 停止播放
	// mMediaPlayer.stop();
	// // 修改播放状态
	// mPlayState = false;
	// // 修改播放图标
	// PerCenItemVoicePlayTemp
	// .setImageResource(R.drawable.globle_player_btn_play);
	// // 初始化播放数据
	// mPlayCurrentPosition = 0;
	// PerCenItemVoiceProgressBarTemp
	// .setProgress(mPlayCurrentPosition);
	// }
	// });
	//
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (IllegalStateException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// } else {
	// if (mMediaPlayer != null) {
	// // 根据播放状态修改显示内容
	// if (mMediaPlayer.isPlaying()) {
	// mPlayState = false;
	// mMediaPlayer.stop();
	// PerCenItemVoicePlayTemp
	// .setImageResource(R.drawable.globle_player_btn_play);
	// mPlayCurrentPosition = 0;
	// PerCenItemVoiceProgressBarTemp
	// .setProgress(mPlayCurrentPosition);
	// } else {
	// mPlayState = false;
	// PerCenItemVoicePlayTemp
	// .setImageResource(R.drawable.globle_player_btn_play);
	// mPlayCurrentPosition = 0;
	// PerCenItemVoiceProgressBarTemp
	// .setProgress(mPlayCurrentPosition);
	// }
	// }
	// }
	// }
	// }
	// }
}
