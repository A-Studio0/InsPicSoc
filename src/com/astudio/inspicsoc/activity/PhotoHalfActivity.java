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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.model.HalfItem;

public class PhotoHalfActivity extends Activity {
	private Button back;

	private List<HalfItem> HalfItemList = new ArrayList<HalfItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_half);

		HalfItemList.add(new HalfItem(R.drawable.ping1));
		HalfItemList.add(new HalfItem(R.drawable.ping2));

		HalfItemList.add(new HalfItem(R.drawable.ping3));
		HalfItemList.add(new HalfItem(R.drawable.ping4));
		HalfItemList.add(new HalfItem(R.drawable.ping5));
		HalfItemList.add(new HalfItem(R.drawable.ping6));
		HalfItemList.add(new HalfItem(R.drawable.ping7));
		HalfItemList.add(new HalfItem(R.drawable.ping8));

		HalfItemAdapter adapter = new HalfItemAdapter(this,
				R.layout.percen_item, HalfItemList);
		ListView listView = (ListView) findViewById(R.id.half_list_view);
		listView.setAdapter(adapter);
	}

	private class HalfItemAdapter extends ArrayAdapter<HalfItem> {
		private int resourceId;

		public HalfItemAdapter(Context context, int textViewResourceId,
				List<HalfItem> objects) {
			super(context, textViewResourceId, objects);
			resourceId = textViewResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HalfItem halfItem = getItem(position);
			View view;
			ViewHolder viewHolder;
			if (convertView == null) {
				view = LayoutInflater.from(getContext()).inflate(resourceId,
						null);
				viewHolder = new ViewHolder();
				viewHolder.PerCenItemImage = (ImageView) view
						.findViewById(R.id.percenitem_image);

				view.setTag(viewHolder);
			} else {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
			}
			viewHolder.PerCenItemImage.setImageResource(halfItem.getImageId());
			return view;
		}

		class ViewHolder {
			ImageView PerCenItemImage;

		}
	}
}