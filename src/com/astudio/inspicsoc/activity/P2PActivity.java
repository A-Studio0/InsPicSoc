package com.astudio.inspicsoc.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.adapter.CatAdapter;
import com.astudio.inspicsoc.model.Cat;

public class P2PActivity extends Activity {

	private String[] data = { "cc", "totoro", "kitty", "nyankosensi", "happy",
			"chi", "doraemon", "mica", "garfield", "luoxiaohei" };
	private List<Cat> catList = new ArrayList<Cat>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.p2pfriend);
		initCats();
		/*
		 * ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		 * MainActivity.this,android.R.layout.simple_list_item_1,data);
		 */
		CatAdapter adapter = new CatAdapter(P2PActivity.this,
				R.layout.friend_item, catList);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cat cat = catList.get(position);
				Toast.makeText(P2PActivity.this, cat.getName(),
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
