package com.astudio.inspicsoc.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.astudio.inspicsoc.R;
public class ContactActivity extends Activity {
	ListView contactsView;
	ArrayAdapter<String> adapter;
	List<String> contactsList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_contact);
		contactsView = (ListView) findViewById(R.id.contacts_view);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactsList);
		contactsView.setAdapter(adapter);
		readContacts();
	}
	private void readContacts(){
		Cursor cursor = null;
		try{
			cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
			while(cursor.moveToNext()){
				String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				contactsList.add(displayName + " \n" + number);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(cursor!=null){
				cursor.close();
			}
		}
		
	}
}
