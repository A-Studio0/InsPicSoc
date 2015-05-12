package com.astudio.inspicsoc.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.model.ExchangeItem;

public class Photo_exchange extends ListActivity {

	private List<ExchangeItem> ItemList = new ArrayList<ExchangeItem>();
	// 发布按钮
	private Button upload;
	// 返回按钮
	private Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_exchange);

		initData();

		setListAdapter(new PhotoExchangeItemAdapter(this));
		// 发布
		upload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Photo_exchange.this,
						PhotoExUploadActivity.class);
				startActivity(intent);
			}

		});
	}

	private void initData() {
		upload = (Button) findViewById(R.id.px_upload);
		back = (Button) findViewById(R.id.photo_exchange_back);
	}

	private class PhotoExchangeItemAdapter extends BaseAdapter {
		private Context mContext;

		public PhotoExchangeItemAdapter(Context context) {
			this.mContext = context;
		}

		/**
		 * 元素的个数
		 */
		public int getCount() {
			return names.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		// 用以生成在ListView中展示的一个个元素View
		public View getView(int position, View convertView, ViewGroup parent) {
			// 优化ListView
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.exchange_item, null);
				ViewHolder viewCache = new ViewHolder();
				viewCache.nameHolder = (TextView) convertView
						.findViewById(R.id.user_name);
				viewCache.explHolder = (TextView) convertView
						.findViewById(R.id.cexpl);
				viewCache.headHolder = (ImageView) convertView
						.findViewById(R.id.head_view);
				viewCache.ImageHolder = (ImageView) convertView
						.findViewById(R.id.excitem);

				convertView.setTag(viewCache);
			}
			ViewHolder cache = (ViewHolder) convertView.getTag();

			// 设置文本和图片，然后返回这个View，用于ListView的Item的展示
			cache.nameHolder.setText(names[position]);
			cache.explHolder.setText(expl[position]);
			cache.headHolder.setImageResource(headImages[position]);
			cache.ImageHolder.setImageResource(images[position]);

			return convertView;
		}

		class ViewHolder {
			public ImageView ImageHolder;
			public ImageView headHolder;
			public TextView pointHolder;
			public TextView nameHolder;
			public TextView explHolder;

		}

		// 展示的
		private String[] names = new String[] { "HeyJim", "luffy", "miao",
				"mike" };
		//
		private String[] expl = new String[] { "换一张油彩或50积分", "换一张风景画或5积分",
				"换一张油彩或30积分", "50积分" };
		// 展示的图片
		private int[] headImages = new int[] { R.drawable.head,
				R.drawable.head_default_yixin, R.drawable.ic_people,
				R.drawable.ic_talktop };
		private int[] images = new int[] { R.drawable.example,
				R.drawable.example2, R.drawable.example3, R.drawable.example4 };
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(Photo_exchange.this,
				PhotoExDetailActivity.class);
		startActivity(intent);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.px_upload:
			Intent intent = new Intent(Photo_exchange.this,
					PhotoExUploadActivity.class);
			startActivity(intent);
			break;
		case R.id.photo_exchange_back:
			//要返回到哪个Activity？？从FindFragment.java过来
		//	Intent intent1 = new Intent(Photo_exchange.this,
		//			PhotoExUploadActivity.class);
		//	startActivity(intent1);
		//	break;
		default:
			break;
		}
	}

}
