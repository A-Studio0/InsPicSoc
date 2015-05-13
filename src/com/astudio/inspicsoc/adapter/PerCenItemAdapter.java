package com.astudio.inspicsoc.adapter;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.activity.InsApplication;
import com.astudio.inspicsoc.model.PerCenItem;

public class PerCenItemAdapter extends ArrayAdapter<PerCenItem> {
	private int resourceId;
	private InsApplication myApp;

	public PerCenItemAdapter(Context context, int textViewResourceId,
			List<PerCenItem> objects,InsApplication mApp) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
		this.myApp = mApp;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PerCenItem PerCenItem = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.PerCenItemImage = (ImageView) view.findViewById(R.id.percenitem_image);
			viewHolder.PerCenItemComment = (ImageView) view.findViewById(R.id.percenitem_comment);
			viewHolder.PerCenItemDescription = (TextView) view.findViewById(R.id.percenitem_description);
			viewHolder.PerCenItemPosition = (TextView) view.findViewById(R.id.percenitem_position);
			viewHolder.PerCenItemDate = (TextView) view.findViewById(R.id.percenitem_date);
			viewHolder.PerCenItemCollectNum = (TextView) view.findViewById(R.id.percenitem_collectNum);
			viewHolder.PerCenItemViewNum = (TextView) view.findViewById(R.id.percenitem_viewNum);
			viewHolder.PerCenItemVoiceLayout=(LinearLayout)view.findViewById(R.id.voice_display_voice_layout);
			viewHolder.PerCenItemVoicePlay = (ImageView) view.findViewById(R.id.voice_display_voice_play);
			viewHolder.PerCenItemVoiceProgressBar = (ProgressBar) view.findViewById(R.id.voice_display_voice_progressbar);
			viewHolder.PerCenItemVoiceTime = (TextView) view.findViewById(R.id.voice_display_voice_time);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.PerCenItemImage.setImageBitmap(myApp.getPhoneAlbum(PerCenItem.getImageId()));
		if(PerCenItem.getComment()!=0){
			viewHolder.PerCenItemComment.setImageResource(PerCenItem.getComment());
			viewHolder.PerCenItemComment.setVisibility(ImageView.VISIBLE);
		}
		else{
			viewHolder.PerCenItemComment.setVisibility(ImageView.GONE);
		}
		if(null==PerCenItem.getDescription()){
			viewHolder.PerCenItemDescription.setVisibility(TextView.INVISIBLE);
		}
		else{
			viewHolder.PerCenItemDescription.setVisibility(TextView.VISIBLE);
			viewHolder.PerCenItemDescription.setText(PerCenItem.getDescription());
		}
		viewHolder.PerCenItemPosition.setText(PerCenItem.getPosition());
		viewHolder.PerCenItemDate.setText(PerCenItem.getDate());
		viewHolder.PerCenItemCollectNum.setText(PerCenItem.getCollectNum());
		viewHolder.PerCenItemViewNum.setText(PerCenItem.getViewNum());
		if(null==PerCenItem.getVoiceId()){
			viewHolder.PerCenItemVoiceLayout.setVisibility(LinearLayout.GONE);
		}
		else{
			viewHolder.PerCenItemVoiceTime.setText((int) PerCenItem.getPlayTimeId() + "″");
			viewHolder.PerCenItemVoiceProgressBar.setMax((int) PerCenItem.getPlayTimeId());
			viewHolder.PerCenItemVoiceProgressBar.setProgress(0);
			viewHolder.PerCenItemVoiceLayout.setVisibility(LinearLayout.VISIBLE);
			viewHolder.PerCenItemVoicePlay.setOnClickListener(new myListener(viewHolder.PerCenItemVoicePlay,viewHolder.PerCenItemVoiceProgressBar,
					PerCenItem.getPlayTimeId(),PerCenItem.getVoiceId()));
		}
		
		return view;
	}

	class ViewHolder {
		ImageView PerCenItemImage;
		ImageView PerCenItemComment;
		TextView PerCenItemDescription;
		TextView PerCenItemPosition;
		TextView PerCenItemDate;
		TextView PerCenItemCollectNum;
		TextView PerCenItemViewNum;
		LinearLayout PerCenItemVoiceLayout;
		ImageView PerCenItemVoicePlay;
		ProgressBar PerCenItemVoiceProgressBar;
		TextView PerCenItemVoiceTime;
	}
	
	class myListener implements OnClickListener{
		private ImageView PerCenItemVoicePlayTemp;
		private ProgressBar PerCenItemVoiceProgressBarTemp;
		private float mPlayTime;
		private String mCurrentVoicePath;
		private boolean mPlayState; // 播放状态
		private int mPlayCurrentPosition;// 当前播放的时间
		private MediaPlayer mMediaPlayer;
		
		myListener(ImageView PerCenItemVoicePlayTemp,ProgressBar PerCenItemVoiceProgressBarTemp,float mPlayTime,String mCurrentVoicePath){
			this.PerCenItemVoicePlayTemp=PerCenItemVoicePlayTemp;
			this.PerCenItemVoiceProgressBarTemp=PerCenItemVoiceProgressBarTemp;
			this.mPlayTime=mPlayTime;
			this.mCurrentVoicePath=mCurrentVoicePath;
		}
		
		public void onClick(View v) {
			// 播放录音
			if (!mPlayState) {
				mMediaPlayer = new MediaPlayer();
				try {
					// 添加录音的路径
					mMediaPlayer.setDataSource(mCurrentVoicePath);
					// 准备
					mMediaPlayer.prepare();
					// 播放
					mMediaPlayer.start();
					// 根据时间修改界面
					new Thread(new Runnable() {

						public void run() {

							PerCenItemVoiceProgressBarTemp
									.setMax((int) mPlayTime);
							mPlayCurrentPosition = 0;
							while (mMediaPlayer.isPlaying()) {
								mPlayCurrentPosition = mMediaPlayer
										.getCurrentPosition() / 1000;
								PerCenItemVoiceProgressBarTemp
										.setProgress(mPlayCurrentPosition);
							}
						}
					}).start();
					// 修改播放状态
					mPlayState = true;
					// 修改播放图标
					PerCenItemVoicePlayTemp
							.setImageResource(R.drawable.globle_player_btn_stop);

					mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
								// 播放结束后调用
								public void onCompletion(MediaPlayer mp) {
									// 停止播放
									mMediaPlayer.stop();
									// 修改播放状态
									mPlayState = false;
									// 修改播放图标
									PerCenItemVoicePlayTemp
											.setImageResource(R.drawable.globle_player_btn_play);
									// 初始化播放数据
									mPlayCurrentPosition = 0;
									PerCenItemVoiceProgressBarTemp
											.setProgress(mPlayCurrentPosition);
								}
							});

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				if (mMediaPlayer != null) {
					// 根据播放状态修改显示内容
					if (mMediaPlayer.isPlaying()) {
						mPlayState = false;
						mMediaPlayer.stop();
						PerCenItemVoicePlayTemp
								.setImageResource(R.drawable.globle_player_btn_play);
						mPlayCurrentPosition = 0;
						PerCenItemVoiceProgressBarTemp
								.setProgress(mPlayCurrentPosition);
					} else {
						mPlayState = false;
						PerCenItemVoicePlayTemp
								.setImageResource(R.drawable.globle_player_btn_play);
						mPlayCurrentPosition = 0;
						PerCenItemVoiceProgressBarTemp
								.setProgress(mPlayCurrentPosition);
					}
				}
			}
		}
	}
}
