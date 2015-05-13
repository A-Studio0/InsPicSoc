package com.astudio.inspicsoc.activity;

import java.util.ArrayList;
import java.util.List;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.adapter.CatAdapter;
import com.astudio.inspicsoc.model.Cat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class GroupInfoActivity extends Activity {

	private String groupname = "";
	private ImageButton close = null;
	private TextView invite = null;
	private TextView title = null;
	private ListView listview = null;
	private List<Cat> catList = new ArrayList<Cat>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupinfo);
	    Intent intent = this.getIntent();
	    Bundle bundle=intent.getExtras();
	    groupname=bundle.getString("groupname");
	    
	    initCats();
	    this.close = (ImageButton) this.findViewById(R.id.close_groupinfo);
	    this.invite = (TextView) this.findViewById(R.id.invite_others);
	    this.title = (TextView) this.findViewById(R.id.ivTitleName);
	    title.setText(groupname);
	    close.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GroupInfoActivity.this.finish();
			}
	    	
	    });
	    invite.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(GroupInfoActivity.this,InviteGroupMembersActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("groupname",groupname);
				intent.putExtras(bundle);
				startActivity(intent);
				GroupInfoActivity.this.finish();
			}
	    	
	    });
		CatAdapter adapter = new CatAdapter(GroupInfoActivity.this,
				R.layout.friend_item, catList);
		ListView listView = (ListView) findViewById(R.id.members_list_view);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cat cat = catList.get(position);
				Toast.makeText(GroupInfoActivity.this, cat.getName(),
						Toast.LENGTH_SHORT).show();
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
}
