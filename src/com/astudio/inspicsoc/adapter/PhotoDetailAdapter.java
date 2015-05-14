package com.astudio.inspicsoc.adapter;

import java.util.List;


import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.*;
import cn.sharesdk.sina.weibo.SinaWeibo;





import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.adapter.PhotoDetailAdapter;
import com.astudio.inspicsoc.model.ExchangeItem;
import com.astudio.inspicsoc.model.PerCenItem;
import com.astudio.inspicsoc.model.PhotoDetailItem;


import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.astudio.inspicsoc.activity.PhotoDetailActivity;
import com.astudio.inspicsoc.model.*;

public class PhotoDetailAdapter extends ArrayAdapter<PhotoDetailItem> {//implements OnClickListener{
	
		private int resourceId;
		
		private Button guanzhuBtn;
		private ImageButton commentBtn;
		private ImageButton collectBtn;
		private ImageButton shareBtn;
		private ImageView showComment;
		
		public PhotoDetailAdapter(Context context, int textViewResourceId,
				List<PhotoDetailItem> objects) {
			super(context, textViewResourceId, objects);
			//super();
			resourceId = textViewResourceId;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			PhotoDetailItem PhotoDetailItem = getItem(position);
			View view;
			ViewHolder viewHolder;
			if (convertView == null) {
				view = LayoutInflater.from(getContext()).inflate(resourceId, null);
				viewHolder = new ViewHolder();
				
			
				viewHolder.PhotoDetailItemUserHead = (ImageView) view.findViewById(R.id.PhotoDetailitem_userHead);
				viewHolder.PhotoDetailItemImage = (ImageView) view.findViewById(R.id.PhotoDetailitem_image);
				viewHolder.PhotoDetailItemComment = (ImageView) view.findViewById(R.id.PhotoDetailitem_comment);
				viewHolder.PhotoDetailItemUserName = (TextView) view.findViewById(R.id.PhotoDetailitem_userName);
				viewHolder.PhotoDetailItemDescription = (TextView) view.findViewById(R.id.PhotoDetailitem_description);
				viewHolder.PhotoDetailItemPosition = (TextView) view.findViewById(R.id.PhotoDetailitem_position);
				viewHolder.PhotoDetailItemDate = (TextView) view.findViewById(R.id.PhotoDetailitem_date);
				viewHolder.PhotoDetailItemViewNum = (TextView) view.findViewById(R.id.PhotoDetailitem_viewNum);
				viewHolder.PhotoDetailItemTag = (TextView) view.findViewById(R.id.PhotoDetailitem_tag);

				ShareSDK.initSDK(getContext());
				//findViewById(R.id.shareweibo).setOnClickListener(getContext());
				viewHolder.PhotoDetailItemguanzhu  = (Button)view.findViewById(R.id.guanzhu);
				viewHolder.PhotoDetailItemCollect = (ImageButton) view.findViewById(R.id.collect);
				viewHolder.PhotoDetailItemShare = (ImageButton) view.findViewById(R.id.shareweibo);
				/*
				guanzhuBtn.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//guanzhuBtn.setText("已关注");
						//guanzhuBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.guanzhubackground));
						//Toast.makeText(this, "添加关注成功~", Toast.LENGTH_SHORT).show();
					}
					
				});*/
				
				view.setTag(viewHolder);
			} else {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
			}
			viewHolder.PhotoDetailItemUserHead.setImageResource(PhotoDetailItem.getUserHead());
			viewHolder.PhotoDetailItemImage.setImageResource(PhotoDetailItem.getImageId());
			viewHolder.PhotoDetailItemComment.setImageResource(PhotoDetailItem.getComment());
			viewHolder.PhotoDetailItemUserName.setText(PhotoDetailItem.getUserName());
			viewHolder.PhotoDetailItemDescription.setText(PhotoDetailItem.getDescription());
			viewHolder.PhotoDetailItemPosition.setText(PhotoDetailItem.getPosition());
			viewHolder.PhotoDetailItemDate.setText(PhotoDetailItem.getDate());
			viewHolder.PhotoDetailItemViewNum.setText(PhotoDetailItem.getViewNum());
			viewHolder.PhotoDetailItemTag.setText(PhotoDetailItem.getTag());
			viewHolder.PhotoDetailItemShare.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					OnekeyShare oks = new OnekeyShare();
					// 分享时Notification的图标和文字
					//oks.setNotification(R.drawable.ic_launcher,
					//		PhotoDetailActivity.this.getString(R.string.app_name));
					// text是分享文本，所有平台都需要这个字段
					oks.setText("分享内容");
					// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
					//oks.setTitle(getString(R.string.share));

					// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
					oks.setTitleUrl("http://sharesdk.cn");
					// text是分享文本，所有平台都需要这个字段
					oks.setText("我是分享文本");
					// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
					// oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
					// url仅在微信（包括好友和朋友圈）中使用
					oks.setUrl("http://sharesdk.cn");
					// comment是我对这条分享的评论，仅在人人网和QQ空间使用
					oks.setComment("我是测试评论文本");
					// site是分享此内容的网站名称，仅在QQ空间使用
					//oks.setSite(getString(R.string.app_name));
					// siteUrl是分享此内容的网站地址，仅在QQ空间使用
					oks.setSiteUrl("http://sharesdk.cn");

					oks.show(v.getContext());
				}
				
			});
			viewHolder.PhotoDetailItemguanzhu.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//guanzhuBtn.setText("已关注");
					//guanzhuBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.guanzhubackground));
					Toast.makeText(getContext(), "添加关注成功~", Toast.LENGTH_SHORT).show();
				}
			});
			
			return view;
		}


		/*
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.shareweibo:
				OnekeyShare oks = new OnekeyShare();
				// 分享时Notification的图标和文字
				oks.setNotification(R.drawable.ic_launcher,
						PhotoDetailActivity.this.getString(R.string.app_name));
				// text是分享文本，所有平台都需要这个字段
				oks.setText("分享内容");
				// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
				oks.setTitle(getString(R.string.share));

				// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
				oks.setTitleUrl("http://sharesdk.cn");
				// text是分享文本，所有平台都需要这个字段
				oks.setText("我是分享文本");
				// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
				// oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
				// url仅在微信（包括好友和朋友圈）中使用
				oks.setUrl("http://sharesdk.cn");
				// comment是我对这条分享的评论，仅在人人网和QQ空间使用
				oks.setComment("我是测试评论文本");
				// site是分享此内容的网站名称，仅在QQ空间使用
				oks.setSite(getString(R.string.app_name));
				// siteUrl是分享此内容的网站地址，仅在QQ空间使用
				oks.setSiteUrl("http://sharesdk.cn");

				oks.show(v.getContext());
				break;
			}
		*/
		class ViewHolder {
			
			ImageView PhotoDetailItemUserHead;
			ImageView PhotoDetailItemImage;
			ImageView PhotoDetailItemComment;
			TextView PhotoDetailItemUserName;
			TextView PhotoDetailItemDescription;
			TextView PhotoDetailItemPosition;
			TextView PhotoDetailItemDate;
			TextView PhotoDetailItemViewNum;
			TextView PhotoDetailItemTag;
			
			Button PhotoDetailItemguanzhu;
			ImageButton PhotoDetailItemCollect;
			ImageButton PhotoDetailItemShare;
			
			

		}
	}

