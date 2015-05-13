package com.astudio.inspicsoc.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;





import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.utils.*;
import com.astudio.inspicsoc.adapter.SortAdapter;
import com.astudio.inspicsoc.model.SortModel;
import com.astudio.inspicsoc.view.SideBar;
import com.astudio.inspicsoc.view.SideBar.OnTouchingLetterChangedListener;

public class FriendActivity extends Activity implements OnClickListener {

	private ImageButton back = null;
	private View circleBtnLayout;

	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	private ImageButton addContactBtn;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	private ImageButton invite = null;


	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends);
		ShareSDK.initSDK(this);
		back = (ImageButton) this.findViewById(R.id.ivTitleBtnLeft);
		back.setOnClickListener(this);
		circleBtnLayout = LeftSlidingMenuFragment.view
				.findViewById(R.id.circleBtnLayout);
		addContactBtn = (ImageButton) this.findViewById(R.id.add_contact);
		addContactBtn.setOnClickListener(this);
		initViews();
	}


	private void initViews() {
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});

		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
				Toast.makeText(getApplication(),
						((SortModel) adapter.getItem(position)).getName(),
						Toast.LENGTH_SHORT).show();
			}
		});

		SourceDateList = filledData(getResources().getStringArray(R.array.date));

		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);

		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		invite = (ImageButton)this.findViewById(R.id.invite);
		invite.setOnClickListener(this);

	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ivTitleBtnLeft:
			this.finish();
			circleBtnLayout.setSelected(false);
			break;
		case R.id.add_contact:
			Intent i=new Intent();
			i.setClass(FriendActivity.this,ContactActivity.class);
			startActivityForResult(i,0);
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
