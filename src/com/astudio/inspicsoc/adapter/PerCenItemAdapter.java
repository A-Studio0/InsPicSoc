package com.astudio.inspicsoc.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
			viewHolder.PerCenItemImage = (ImageView) view.findViewById(R.id.percenitem_image);
			viewHolder.PerCenItemComment = (ImageView) view.findViewById(R.id.percenitem_comment);
			viewHolder.PerCenItemDescription = (TextView) view.findViewById(R.id.percenitem_description);
			viewHolder.PerCenItemPosition = (TextView) view.findViewById(R.id.percenitem_position);
			viewHolder.PerCenItemDate = (TextView) view.findViewById(R.id.percenitem_date);
			viewHolder.PerCenItemCollectNum = (TextView) view.findViewById(R.id.percenitem_collectNum);
			viewHolder.PerCenItemViewNum = (TextView) view.findViewById(R.id.percenitem_viewNum);

			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.PerCenItemImage.setImageResource(PerCenItem.getImageId());
		viewHolder.PerCenItemComment.setImageResource(PerCenItem.getComment());
		viewHolder.PerCenItemDescription.setText(PerCenItem.getDescription());
		viewHolder.PerCenItemPosition.setText(PerCenItem.getPosition());
		viewHolder.PerCenItemDate.setText(PerCenItem.getDate());
		viewHolder.PerCenItemCollectNum.setText(PerCenItem.getCollectNum());
		viewHolder.PerCenItemViewNum.setText(PerCenItem.getViewNum());
		
		return view;
	}

	class ViewHolder {
		ImageView PerCenItemImage;
		ImageView PerCenItemComment;
		TextView PerCenItemDescription;
		TextView PerCenItemPosition;
		TextView PerCenItemDate;
		TextView PerCenItemCollectNum;
		TextView PerCenItemViewNum;

	}
}
