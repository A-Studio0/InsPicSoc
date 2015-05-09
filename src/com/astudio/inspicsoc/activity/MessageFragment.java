package com.astudio.inspicsoc.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.adapter.CatAdapter;
import com.astudio.inspicsoc.model.Cat;

@SuppressLint("ValidFragment")
public class MessageFragment extends Fragment {
	
	private MainActivity activity;
	
	@SuppressLint("ValidFragment")
	public MessageFragment(MainActivity activity){
		this.activity = activity;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_message, container,
				false);

		final InsApplication ins = (InsApplication) activity.getApplication();
		CatAdapter adapter = new CatAdapter(activity.getApplicationContext(),
				R.layout.friend_item, ins.messageList);
		ListView listView = (ListView) v.findViewById(R.id.message_listview);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(activity,PhotoDetailActivity.class);
				Bundle bundle=new Bundle();
				Cat item = ins.messageList.get(position);
				bundle.putSerializable("Name",item.getName());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		return v;

	}

}
