package com.astudio.inspicsoc.activity;


import com.astudio.inspicsoc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddGroupActivity extends Activity {
	
	private ImageButton close = null;
	private TextView next = null;
	private EditText groupname = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriendgroup);
		groupname = (EditText) this.findViewById(R.id.edit_qun_name);
		close = (ImageButton) this.findViewById(R.id.close_addfriendgroup);
		close.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AddGroupActivity.this.finish();
			}
			
		});
		next = (TextView)this.findViewById(R.id.invite_group_members);
		next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				InsApplication app = (InsApplication) getApplication();
				Intent intent = new Intent(AddGroupActivity.this,InviteGroupMembersActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("groupname",groupname.getText().toString());
				intent.putExtras(bundle);
				startActivity(intent);
				AddGroupActivity.this.finish();
			}
			
		});
	}
}
