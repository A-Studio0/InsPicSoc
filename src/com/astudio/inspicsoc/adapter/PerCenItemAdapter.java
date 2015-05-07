package com.astudio.inspicsoc.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.model.PerCenItem;

public class PerCenItemAdapter extends ArrayAdapter<PerCenItem> {
	private int resourceId;

	public PerCenItemAdapter(Context context, int textViewResourceId,
			List<PerCenItem> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PerCenItem PerCenItem = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.PerCenItemImage = (ImageView) view
					.findViewById(R.id.percenitem_image);

			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.PerCenItemImage.setImageResource(PerCenItem.getImageId());
		return view;
	}

	class ViewHolder {
		ImageView PerCenItemImage;

	}
}
