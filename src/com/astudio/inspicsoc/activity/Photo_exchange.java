package com.astudio.inspicsoc.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.model.ExchangeItem;

public class Photo_exchange extends Activity {

	private List<ExchangeItem> ItemList = new ArrayList<ExchangeItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_exchange);

		initData();

		PhotoExchangeItemAdapter adapter = new PhotoExchangeItemAdapter(this,
				R.layout.exchange_item, ItemList);
		ListView listView = (ListView) findViewById(R.id.pictrue_exchange);
		listView.setAdapter(adapter);
	}

	private void initData() {
		ItemList.add(new ExchangeItem(1, "HeyJim", "换一张油彩或50积分",
				R.drawable.example, R.drawable.head));
		ItemList.add(new ExchangeItem(1, "luffy", "换一张风景画或5积分",
				R.drawable.example2, R.drawable.head_default_yixin));
		ItemList.add(new ExchangeItem(1, "miao", "换一张油彩或30积分",
				R.drawable.example3, R.drawable.ic_people));
		ItemList.add(new ExchangeItem(1, "mike", "50积分", R.drawable.example4,
				R.drawable.ic_talktop));

	}

	private class PhotoExchangeItemAdapter extends ArrayAdapter<ExchangeItem> {
		private int resourceId;

		public PhotoExchangeItemAdapter(Context context,
				int textViewResourceId, List<ExchangeItem> objects) {
			super(context, textViewResourceId, objects);
			resourceId = textViewResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ExchangeItem exchangeItem = getItem(position);
			View view;
			ViewHolder viewHolder;
			if (convertView == null) {
				view = LayoutInflater.from(getContext()).inflate(resourceId,
						null);
				viewHolder = new ViewHolder();
				viewHolder.headHolder = (ImageView) view
						.findViewById(R.id.head_view);
				viewHolder.nameHolder = (TextView) view
						.findViewById(R.id.user_name);
				viewHolder.explHolder = (TextView) view
						.findViewById(R.id.cexpl);
				viewHolder.ImageHolder = (ImageView) view
						.findViewById(R.id.excitem);

				view.setTag(viewHolder);
			} else {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
			}
			viewHolder.headHolder.setImageResource(exchangeItem.getHeadpicId());
			viewHolder.nameHolder.setText(exchangeItem.getUserName());
			viewHolder.ImageHolder.setImageResource(exchangeItem.getPicId());
			viewHolder.explHolder.setText(exchangeItem.getExplain());
			return view;
		}

		class ViewHolder {
			ImageView ImageHolder;
			ImageView headHolder;
			TextView pointHolder;
			TextView nameHolder;
			TextView explHolder;

		}
	}

}
