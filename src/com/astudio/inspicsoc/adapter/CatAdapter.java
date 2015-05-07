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
import com.astudio.inspicsoc.model.Cat;

public class CatAdapter extends ArrayAdapter<Cat> {
	private int resourceId;

	public CatAdapter(Context context, int textViewResourceId, List<Cat> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Cat cat = getItem(position);// ��ȡ��ǰFruitʵ��
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.catImage = (ImageView) view.findViewById(R.id.cat_image);
			viewHolder.catName = (TextView) view.findViewById(R.id.cat_name);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();// ���»�ȡviewHolder
		}
		viewHolder.catImage.setImageResource(cat.getImageId());
		viewHolder.catName.setText(cat.getName());
		return view;
	}

	class ViewHolder {
		ImageView catImage;
		TextView catName;
	}
}
