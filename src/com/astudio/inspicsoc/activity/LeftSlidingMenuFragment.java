package com.astudio.inspicsoc.activity;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.utils.ActivityForResultUtil;

@SuppressLint("ValidFragment")
public class LeftSlidingMenuFragment extends Fragment implements
		OnClickListener {
	private View miaoBtnLayout;
	private View circleBtnLayout;
	private View settingBtnLayout;
	public static View view;
	private MainActivity mActivity;
	private ImageView mHead;
	private Context mContext;
	private InsApplication mKXApplication;

	public LeftSlidingMenuFragment(MainActivity activity,Context context,InsApplication mKXApplication) {
		this.mActivity = activity;
		this.mContext=context;
		this.mKXApplication=mKXApplication;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
		mHead=(ImageView) view.findViewById(R.id.headImageView);
		mHead.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.miaoBtnLayout:
			intent = new Intent(mActivity, MessageCenterActivity.class);
			startActivity(intent);
			miaoBtnLayout.setSelected(true);
			circleBtnLayout.setSelected(false);
			settingBtnLayout.setSelected(false);
			break;
		case R.id.circleBtnLayout:
			intent = new Intent(mActivity, FriendActivity.class);
			startActivity(intent);
			miaoBtnLayout.setSelected(false);
			circleBtnLayout.setSelected(true);
			settingBtnLayout.setSelected(false);
			break;
		case R.id.settingBtnLayout:
			intent = new Intent(mActivity, MyInfoActivity.class);
			startActivity(intent);
			miaoBtnLayout.setSelected(false);
			circleBtnLayout.setSelected(false);
			settingBtnLayout.setSelected(true);
			break;
		case R.id.headImageView:
			new AlertDialog.Builder(mContext).setTitle("修改头像")
			.setItems(
					new String[] {"拍照上传", "上传手机中的照片" },
					new DialogInterface.OnClickListener() {

						public void onClick(
							DialogInterface dialog, int which) {
							Intent intent = null;
							switch (which) {
							case 0:
								intent = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								File dir = new File(
										"/sdcard/InsPicSoc/Camera/");
								if (!dir.exists()) {
									dir.mkdirs();
								}
								mKXApplication.mUploadPhotoPath = "/sdcard/InsPicSoc/Camera/"
										+ UUID.randomUUID()
												.toString();
								File file = new File(
										mKXApplication.mUploadPhotoPath);
								if (!file
										.exists()) {
									try {
										file.createNewFile();
									} catch (IOException e) {

									}
								}
								intent.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(file));
								mActivity.startActivityForResult(
												intent,
												ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CAMERA);
								break;

							case 1:
								intent = new Intent(
										Intent.ACTION_PICK,
										null);
								intent.setDataAndType(
										MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
										"image/*");
								mActivity.startActivityForResult(
												intent,
												ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_LOCATION);
								break;
							}
						}
					})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {

						public void onClick(
							DialogInterface dialog,
							int which) {
							dialog.cancel();
						}
					}).create().show();
			break;
		default:
			break;
		}
	}
	
	public void setHeadBitmap(Bitmap bitmap){
		mHead.setImageBitmap(bitmap);
	}
}
