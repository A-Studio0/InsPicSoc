package com.astudio.inspicsoc.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.astudio.inspicsoc.R;

@SuppressLint("ValidFragment")
public class LeftSlidingMenuFragment extends Fragment implements
		OnClickListener {
	private View miaoBtnLayout;
	private View circleBtnLayout;
	private View settingBtnLayout;
	public static View view;
	private MainActivity activity;

	public LeftSlidingMenuFragment(MainActivity activity) {
		this.activity = activity;
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
		return view;
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
