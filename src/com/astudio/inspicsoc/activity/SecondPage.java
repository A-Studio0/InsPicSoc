/**
 * 
 */
package com.astudio.inspicsoc.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.astudio.android.bitmapfun.util.ImageFetcher;
import com.astudio.dodowaterfall.Helper;
import com.astudio.dodowaterfall.widget.ScaleImageView;
import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.model.DuitangInfo;
import com.astudio.inspicsoc.view.XListView;
import com.astudio.inspicsoc.view.XListView.IXListViewListener;
import com.astudio.internal.PLA_AdapterView;
import com.astudio.internal.PLA_AdapterView.OnItemClickListener;

public class SecondPage extends Fragment implements IXListViewListener {

	private ImageFetcher mImageFetcher;
	private XListView mAdapterView = null;
	private StaggeredAdapter mAdapter = null;
	private int currentPage = 0;
	ContentTask task = new ContentTask(this.getActivity(), 2);
	public static Activity myActivity;

	private class ContentTask extends
			AsyncTask<String, Integer, List<DuitangInfo>> {

		private Context mContext;
		private int mType = 1;

		public ContentTask(Context context, int type) {
			super();
			mContext = context;
			mType = type;
		}

		@Override
		protected List<DuitangInfo> doInBackground(String... params) {
			try {
				return parseNewsJSON(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<DuitangInfo> result) {
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

		public List<DuitangInfo> parseNewsJSON(String url) throws IOException {
			List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();
			String json = "";
			if (Helper.checkConnection(mContext)) {
				try {
					json = Helper.getStringFromUrl(url);

				} catch (IOException e) {
					Log.e("IOException is : ", e.toString());
					e.printStackTrace();
					return duitangs;
				}
			}
			Log.d("MainActiivty", "json:" + json);

			try {
				if (null != json) {
					JSONObject newsObject = new JSONObject(json);
					JSONObject jsonObject = newsObject.getJSONObject("data");
					JSONArray blogsJson = jsonObject.getJSONArray("blogs");

					for (int i = 0; i < blogsJson.length(); i++) {
						JSONObject newsInfoLeftObject = blogsJson
								.getJSONObject(i);
						DuitangInfo newsInfo1 = new DuitangInfo();
						newsInfo1
								.setAlbid(newsInfoLeftObject.isNull("albid") ? ""
										: newsInfoLeftObject.getString("albid"));
						newsInfo1
								.setIsrc(newsInfoLeftObject.isNull("isrc") ? ""
										: newsInfoLeftObject.getString("isrc"));
						newsInfo1.setMsg(newsInfoLeftObject.isNull("msg") ? ""
								: newsInfoLeftObject.getString("msg"));
						// newsInfo1.setHeight(newsInfoLeftObject.getInt("iht"));
						newsInfo1.setHeight(200);
						duitangs.add(newsInfo1);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return duitangs;
		}
	}

	/**
	 * 娣诲姞鍐呭
	 * 
	 * @param pageindex
	 * @param type
	 *            1涓轰笅鎷夊埛鏂� 2涓哄姞杞芥洿澶�
	 */
	private void AddItemToContainer(int pageindex, int type) {
		if (task.getStatus() != Status.RUNNING) {
			// 鍥剧墖鍦ㄦ湇鍔�?�櫒涓婄殑鍦板潃
			String url = "http://www.duitang.com/album/1733789/masn/p/"
					+ pageindex + "/24/";
			Log.d("MainActivity", "current url:" + url);
			// 璇锋眰鐨勬柟娉曟槸HTTP璇锋眰锛屾搷浣滃皝瑁呭湪Helper绫讳�??
			ContentTask task = new ContentTask(this.getActivity(), type);
			task.execute(url);

		}
	}

	public class StaggeredAdapter extends BaseAdapter {
		private Context mContext;
		private LinkedList<DuitangInfo> mInfos;
		private XListView mListView;

		public StaggeredAdapter(Context context, XListView xListView) {
			mContext = context;
			mInfos = new LinkedList<DuitangInfo>();
			mListView = xListView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			DuitangInfo duitangInfo = mInfos.get(position);

			if (convertView == null) {
				LayoutInflater layoutInflator = LayoutInflater.from(parent
						.getContext());
				convertView = layoutInflator.inflate(R.layout.infos_list, null);
				holder = new ViewHolder();
				holder.imageView = (ScaleImageView) convertView
						.findViewById(R.id.news_pic);
				holder.contentView = (TextView) convertView
						.findViewById(R.id.msg_conetnt);
				convertView.setTag(holder);
			}

			holder = (ViewHolder) convertView.getTag();
			holder.imageView.setImageWidth(duitangInfo.getWidth());
			holder.imageView.setImageHeight(duitangInfo.getHeight());
			holder.contentView.setText(duitangInfo.getMsg());
			mImageFetcher.loadImage(duitangInfo.getIsrc(), holder.imageView);
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

		public void addItemLast(List<DuitangInfo> datas) {
			mInfos.addAll(datas);
		}

		public void addItemTop(List<DuitangInfo> datas) {
			for (DuitangInfo info : datas) {
				mInfos.addFirst(info);
			}
		}
	}

	private class ViewHolder {
		ScaleImageView imageView;
		TextView contentView;
		TextView timeView;
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
		mAdapterView = (XListView) v.findViewById(R.id.list);
		mAdapterView.setPullLoadEnable(true);
		mAdapterView.setXListViewListener(this);
		// mLeftMenu = (SlidingMenu) v.findViewById(R.id.id_menu);
		// 鐐瑰嚮Item鍝嶅簲浜嬩欢
		mAdapterView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(PLA_AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ViewHolder holder = (ViewHolder) view.getTag();
				Toast.makeText(myActivity.getApplicationContext(),
						holder.contentView.getText().toString(),
						Toast.LENGTH_LONG).show();
			}

		});

		mAdapter = new StaggeredAdapter(v.getContext(), mAdapterView);

		mImageFetcher = new ImageFetcher(v.getContext(), 240);
		mImageFetcher.setLoadingImage(R.drawable.empty_photo);
		return v;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { return
	 * super.onCreateOptionsMenu(menu); }
	 */

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
