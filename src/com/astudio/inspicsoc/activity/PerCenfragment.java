package com.astudio.inspicsoc.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.adapter.PerCenItemAdapter;
import com.astudio.inspicsoc.model.PerCenItem;
import com.astudio.inspicsoc.view.RoundedImageView;

public class PerCenfragment extends Fragment {
	private RoundedImageView mHead;
	private InsApplication mApp;
	public static PerCenItemAdapter adapter;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.person_center_fragment, container,
				false);
		mApp = (InsApplication) getActivity().getApplication();
//		mHead = (RoundedImageView) v.findViewById(R.id.headImageView);
//		mHead.setImageBitmap(mApp.mHeadBitmap);
		/*
		 * ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		 * MainActivity.this,android.R.layout.simple_list_item_1,data);
		 */
		// View view = null;
		adapter = new PerCenItemAdapter(getActivity(),
				R.layout.percen_item, mApp.PerCenItemList,mApp);
		ListView listView = (ListView) v.findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		/*
		 * listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		 * { public void onItemClick(AdapterView<?> parent, View view,int
		 * position,long id){ PerCenItem PerCenItem =
		 * PerCenItemList.get(position);
		 * 
		 * } });
		 */

		return v;

	}

	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * action bar item clicks here. The action bar will // automatically handle
	 * clicks on the Home/Up button, so long // as you specify a parent activity
	 * in AndroidManifest.xml. int id = item.getItemId(); if (id ==
	 * R.id.action_settings) { return true; } return
	 * super.onOptionsItemSelected(item); }
	 */
}
