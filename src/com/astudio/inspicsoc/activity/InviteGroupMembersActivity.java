package com.astudio.inspicsoc.activity;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.adapter.SelectCatAdapter;
import com.astudio.inspicsoc.model.Cat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView;

public class InviteGroupMembersActivity extends Activity {
	
	private List<Integer> listId;
	private ListComparator lc = new ListComparator();
	private ImageButton close = null;
	private TextView next = null;
	private ListView listview = null;
	private List<Cat> catList = new ArrayList<Cat>();
	private SelectCatAdapter adapter = null;
	
	private String groupname = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.invitegroupmembers);
        Intent intent = this.getIntent();
        Bundle bundle=intent.getExtras();
        groupname=bundle.getString("groupname");
        
        initCats();
        listId = new ArrayList<Integer>();
		close = (ImageButton) this.findViewById(R.id.close_invitegroupmembers);
		close.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				InviteGroupMembersActivity.this.finish();
			}
		});
		next = (TextView)this.findViewById(R.id.ok_invitegroupmembers);
		next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				InviteGroupMembersActivity.this.finish();
			}
			
		});
		listview = (ListView) this.findViewById(R.id.invite_group_list_view);
		
		adapter = new SelectCatAdapter(InviteGroupMembersActivity.this,
				R.layout.select_friend_item, catList);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SelectCatAdapter.ViewHolder holder = (SelectCatAdapter.ViewHolder) view.getTag();
				position -= listview.getHeaderViewsCount();
				if (listId.contains(position)) {
					holder.selectOn.setImageResource(R.drawable.selectoff);
					// 已经选中过 取消选中
					listId.remove(listId.indexOf(position));
				} else {
					// 没有选中过
					listId.add(position);
					holder.selectOn.setImageResource(R.drawable.selecton);
				}
				Collections.sort(listId, lc);
			}
		});
		listview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
	
			}
	
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				int lvi = firstVisibleItem + visibleItemCount; // lastVisibleItem
				for (int p : listId) {
					if ((p < lvi) && (p >= firstVisibleItem)) {
						View vw = listview.getChildAt((p - firstVisibleItem));
						ImageView selectOn = (ImageView) vw.findViewById(R.id.selectOn);
						selectOn.setImageResource(R.drawable.selecton);
					}
				}
			}
		});
	}
	
	private void initCats() {
		Cat cc = new Cat("cc", R.drawable.cc);
		catList.add(cc);
		Cat totoro = new Cat("totoro", R.drawable.totoro);
		catList.add(totoro);
		Cat kitty = new Cat("kitty", R.drawable.kitty);
		catList.add(kitty);
		Cat nyankosensi = new Cat("nyankosensi", R.drawable.nyankosensi);
		catList.add(nyankosensi);
		Cat happy = new Cat("happy", R.drawable.happy);
		catList.add(happy);
		Cat chi = new Cat("chi", R.drawable.chi);
		catList.add(chi);
		Cat doraemon = new Cat("doraemon", R.drawable.doraemon);
		catList.add(doraemon);
		Cat mica = new Cat("mica", R.drawable.mica);
		catList.add(mica);
		Cat garfield = new Cat("garfield", R.drawable.garfield);
		catList.add(garfield);
		Cat luoxiaohei = new Cat("luoxiaohei", R.drawable.luoxiaohei);
		catList.add(luoxiaohei);

	}
	
	 // 刷新listview和TextView的显示  
    private void dataChanged() {  
        // 通知listView刷新  
    	adapter.notifyDataSetChanged();  
    } 
    
    private class ListComparator implements Comparator<Integer>{

    	@Override
    	public int compare(Integer a, Integer b) {
    		if(a>b){
    			return 1;
    		}else{
    			return -1;
    		}
    	}

    }
}
