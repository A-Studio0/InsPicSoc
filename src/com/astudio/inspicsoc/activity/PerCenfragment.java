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

public class PerCenfragment extends Fragment {
	private String[] data = { "percen1", "percen2" ,"percen3","percen4","percen5"};
	private List<PerCenItem> PerCenItemList = new ArrayList<PerCenItem>();

	private void initPerCenItems() {
		PerCenItem percen1 = new PerCenItem(R.drawable.pinpho1,"this is description","120.19, 30.26",
				"2015-05-12","收藏数：128","浏览数：834",R.drawable.percen_comment1);
		PerCenItemList.add(percen1);
		PerCenItem percen2 = new PerCenItem(R.drawable.pinpho3,"this is description","116.34, 39.97",
				"2015-05-12","收藏数：456","浏览数：930",R.drawable.percen_comment2);
		PerCenItemList.add(percen2);

		PerCenItem percen3 = new PerCenItem(R.drawable.pinpho2,"this is description","118.98, 34.67",
				"2015-05-12","收藏数：532","浏览数：912",R.drawable.percen_comment1);
		PerCenItemList.add(percen3);

		PerCenItem percen4 = new PerCenItem(R.drawable.head_default_miao,"this is description","121.46, 23.90",
				"2015-05-12","收藏数：888","浏览数：962",R.drawable.percen_comment2);
		PerCenItemList.add(percen4);
		
		PerCenItem percen5 = new PerCenItem(R.drawable.pinpho4,"this is description","115.98, 37.67",
				"2015-05-12","收藏数：562","浏览数：812",R.drawable.percen_comment1);
		PerCenItemList.add(percen5);
	}

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

		initPerCenItems();// 初始化数据
		/*
		 * ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		 * MainActivity.this,android.R.layout.simple_list_item_1,data);
		 */
		// View view = null;
		PerCenItemAdapter adapter = new PerCenItemAdapter(getActivity(),
				R.layout.percen_item, PerCenItemList);
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
