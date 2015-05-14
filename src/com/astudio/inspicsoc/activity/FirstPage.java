/**
 * 
 */
package com.astudio.inspicsoc.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.astudio.android.bitmapfun.util.ImageFetcher;
import com.astudio.android.bitmapfun.util.ImageResizer;
import com.astudio.dodowaterfall.Helper;
import com.astudio.dodowaterfall.widget.ScaleImageView;
import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.common.InsUrl;
import com.astudio.inspicsoc.model.BriefMsg;
import com.astudio.inspicsoc.view.RoundedImageView;
import com.astudio.inspicsoc.view.XListView;
import com.astudio.inspicsoc.view.XListView.IXListViewListener;
import com.astudio.internal.PLA_AdapterView;
import com.astudio.internal.PLA_AdapterView.OnItemClickListener;

/**
 * @author Luffy
 *
 */
public class FirstPage extends Fragment implements IXListViewListener {

	private ImageFetcher mImageFetcher;
	private XListView mAdapterView = null;
	private StaggeredAdapter mAdapter = null;
	private int currentPage = 0;
	ContentTask task = new ContentTask(this.getActivity(), 2);
	public static Activity myActivity;
	List<BriefMsg> Msgs = new ArrayList<BriefMsg>();

	private class ContentTask extends
			AsyncTask<String, Integer, List<BriefMsg>> {

		private Context mContext;
		private int mType = 1;

		public ContentTask(Context context, int type) {
			super();
			mContext = context;
			mType = type;
		}

		@Override
		protected List<BriefMsg> doInBackground(String... params) {
			try {
				return parseNewsJSON(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<BriefMsg> result) {
			if (mType == 1) {

				mAdapter.addItemTop(result);
				mAdapter.notifyDataSetChanged();
				mAdapterView.stopRefresh();

			} else if (mType == 2) {
				mAdapterView.stopLoadMore();
				mAdapter.addItemLast(result);
				mAdapter.notifyDataSetChanged();
			}

		}

		@Override
		protected void onPreExecute() {
		}

		public List<BriefMsg> parseNewsJSON(String url) throws IOException {

			String json = "";
			if (Helper.checkConnection(mContext)) {
				try {
					json = Helper.getStringFromUrl(url);

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
						BriefMsg newsInfo1 = new BriefMsg();
						newsInfo1.setMsgId((newObject.isNull("msgId") ? ""
								: newObject.getString("msgId")));

						JSONArray smallpics = newObject.getJSONArray("pics");
						newsInfo1.setSmallfirstPic(smallpics.getString(0)
								.isEmpty() ? "" : smallpics.getString(0));

						newsInfo1.setHeadPic(newObject.isNull("headPic") ? ""
								: newObject.getString("headPic"));
						newsInfo1.setText(newObject.isNull("content") ? ""
								: newObject.getString("content"));
						newsInfo1
								.setCommentNum(newObject.getInt("commentsNum"));
						newsInfo1.setUserName(newObject.isNull("userName") ? ""
								: newObject.getString("userName"));
						newsInfo1.setUserNickName(newObject
								.isNull("userNickName") ? "" : newObject
								.getString("userNickName"));
						File data = ImageFetcher.downloadBitmap(
								myActivity.getApplicationContext(),
								newsInfo1.getHeadPic());
						newsInfo1.setHeadPic(data.toString());
						Msgs.add(newsInfo1);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return Msgs;
		}
	}

	private void AddItemToContainer(int pageindex, int type) {
		if (task.getStatus() != Status.RUNNING) {
			String url = InsUrl.GET_FIRSTPAGE_MSG;
			Log.d("MainActivity", "current url:" + url);
			ContentTask task = new ContentTask(this.getActivity(), type);
			task.execute(url);

		}
	}

	public float computePicHeight(float width, float height) {
		float h = height;
		h = height * (200 / width);
		return h;
	}

	public class StaggeredAdapter extends BaseAdapter {
		private Context mContext;
		private LinkedList<BriefMsg> mInfos;
		private XListView mListView;

		public StaggeredAdapter(Context context, XListView xListView) {
			mContext = context;
			mInfos = new LinkedList<BriefMsg>();
			mListView = xListView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			final BriefMsg BriefMsg = mInfos.get(position);

			if (convertView == null) {
				LayoutInflater layoutInflator = LayoutInflater.from(parent
						.getContext());
				convertView = layoutInflator.inflate(R.layout.infos_list, null);
				holder = new ViewHolder();
				holder.imageView = (ScaleImageView) convertView
						.findViewById(R.id.news_pic);
				holder.contentView = (TextView) convertView
						.findViewById(R.id.msg_conetnt);
				holder.headPic = (RoundedImageView) convertView
						.findViewById(R.id.avator);
				holder.nickName = (TextView) convertView
						.findViewById(R.id.nickNameTextView);
				holder.numView = (TextView) convertView
						.findViewById(R.id.comment_num);
				convertView.setTag(holder);
			}

			holder = (ViewHolder) convertView.getTag();
			// mImageFetcher.loadImage(, holder.headPic);

			holder.headPic
					.setImageBitmap(ImageResizer.decodeSampledBitmapFromFile(
							BriefMsg.getHeadPic(), 80, 80));
			holder.nickName.setText(BriefMsg.getUserNickName());
			// holder.numView.setText(BriefMsg.getCommentNum() == null ? 0
			// : BriefMsg.getCommentNum());
			holder.contentView.setText(BriefMsg.getText());
			mImageFetcher.loadImage(BriefMsg.getSmallfirstPic(),
					holder.imageView);
			holder.username = BriefMsg.getUserName();
			holder.msgId = BriefMsg.getMsgId();
			return convertView;
		}

		@Override
		public int getCount() {
			return mInfos.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mInfos.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		public void addItemLast(List<BriefMsg> datas) {
			mInfos.addAll(datas);
		}

		public void addItemTop(List<BriefMsg> datas) {
			for (BriefMsg info : datas) {
				mInfos.addFirst(info);
			}
		}
	}

	private class ViewHolder {
		ScaleImageView imageView;
		TextView contentView;
		TextView timeView;
		RoundedImageView headPic;
		TextView nickName;
		TextView numView;
		String username;
		String msgId;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		myActivity = activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.main_center_layout, container, false);
		mAdapterView = (XListView) v.findViewById(R.id.list);
		mAdapterView.setPullLoadEnable(true);
		mAdapterView.setXListViewListener(this);
		mAdapterView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(PLA_AdapterView<?> parent, View view,
					int position, long id) {
				BriefMsg msg = Msgs.get(position - 1);

				ViewHolder holder = (ViewHolder) view.getTag();
				String userName = holder.username;
				String msgId = holder.msgId;

				Intent intent = new Intent();

				intent.putExtra("userName", userName);

				intent.putExtra("msgId", msgId);

				Log.e("msgId", msgId);
				Log.e("userName", userName);
				intent.setClass(myActivity, PhotoDetailActivity.class);
				startActivity(intent);
			}

		});

		mAdapter = new StaggeredAdapter(v.getContext(), mAdapterView);

		mImageFetcher = new ImageFetcher(v.getContext(), 240);
		// mImageFetcher.setLoadingImage(R.drawable.empty_photo);
		return v;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return true;
	}

	@Override
	public void onResume() {
		super.onResume();
		mImageFetcher.setExitTasksEarly(false);
		mAdapterView.setAdapter(mAdapter);
		AddItemToContainer(currentPage, 2);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onRefresh() {
		AddItemToContainer(++currentPage, 1);

	}

	@Override
	public void onLoadMore() {
		AddItemToContainer(++currentPage, 2);

	}

}
