package com.astudio.inspicsoc.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.model.Cat;

public class SelectCatAdapter extends ArrayAdapter<Cat> {
	private int resourceId;

	public SelectCatAdapter(Context context, int textViewResourceId, List<Cat> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Cat cat = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.selectOn = (ImageView) view.findViewById(R.id.selectOn);
			viewHolder.catImage = (ImageView) view.findViewById(R.id.cat_image);
			viewHolder.catName = (TextView) view.findViewById(R.id.cat_name);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		if(cat.isSelectOn()){
			viewHolder.selectOn.setImageResource(R.drawable.selecton);
		}else{
			viewHolder.selectOn.setImageResource(R.drawable.selectoff);
		}
		viewHolder.catImage.setImageResource(cat.getImageId());
		viewHolder.catName.setText(cat.getName());
		return view;
	}

	public class ViewHolder {
		public ImageView selectOn;
		public ImageView catImage;
		public TextView catName;
	}
}
