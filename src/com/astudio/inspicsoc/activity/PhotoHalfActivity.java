package com.astudio.inspicsoc.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.model.HalfItem;
import com.astudio.inspicsoc.utils.ActivityForResultUtil;
import com.astudio.inspicsoc.view.RoundedImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoHalfActivity extends Activity implements OnClickListener{
	private Button back;
	private ImageButton addPhotoHalfBtn;

	private ImageView imageView = null;
	private Bitmap mBitmap = null;
	private InsApplication myApp;
	private HalfItemAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_half);
		myApp = (InsApplication)getApplication();
		
		imageView = (ImageView)findViewById(R.id.take_photo_half);
		back = (Button) this.findViewById(R.id.photo_half_back);
		back.setOnClickListener(this);
		
         /*
		Intent intent = getIntent();
		if(intent != null){
			
			mBitmap = intent.getParcelableExtra("iamgeview");
			imageView.setImageBitmap(mBitmap);
		}*/
		

//		HalfItemList.add(new HalfItem(R.drawable.ping1));
//		HalfItemList.add(new HalfItem(R.drawable.ping2));
//		HalfItemList.add(new HalfItem(R.drawable.ping3));
//		HalfItemList.add(new HalfItem(R.drawable.ping4));
//		HalfItemList.add(new HalfItem(R.drawable.ping5));
//		HalfItemList.add(new HalfItem(R.drawable.ping6));
//		HalfItemList.add(new HalfItem(R.drawable.ping7));
//		HalfItemList.add(new HalfItem(R.drawable.ping8));

		adapter = new HalfItemAdapter(this,
				R.layout.photo_half_item, myApp.HalfItemList);
		ListView listView = (ListView) findViewById(R.id.half_list_view);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                // TODO Auto-generated method stub
				if(null==myApp.HalfItemList.get(arg2).getTwoNameId()){
//					myApp.mHeadYiBanTemp= Bitmap.createBitmap(HalfItemList.get(arg2).getOneHeadId(), 0, 0, HalfItemList.get(arg2).getOneHeadId().getWidth(), HalfItemList.get(arg2).getOneHeadId().getHeight());
					myApp.mHeadYiBanTemp=myApp.HalfItemList.get(arg2).getOneHeadId();
					myApp.mNameYiBanTemp=myApp.HalfItemList.get(arg2).getOneNameId();
					myApp.mBitmap=myApp.HalfItemList.get(arg2).getImageId();
					Intent intent = new Intent(PhotoHalfActivity.this,PhotoPingHalfActivity.class);
					startActivityForResult(intent,0);
				}
            }
			
		});
		
		addPhotoHalfBtn = (ImageButton)this.findViewById(R.id.add_photo_half);
		addPhotoHalfBtn.setOnClickListener(this);
	}
	@Override
	protected void onResume(){
		super.onResume();
	
	}
	private class HalfItemAdapter extends ArrayAdapter<HalfItem> {
		private int resourceId;

		public HalfItemAdapter(Context context, int textViewResourceId,
				List<HalfItem> objects) {
			super(context, textViewResourceId, objects);
			resourceId = textViewResourceId;
		}
		
	

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HalfItem halfItem = getItem(position);
			View view;
			ViewHolder viewHolder;
			if (convertView == null) {
				view = LayoutInflater.from(getContext()).inflate(resourceId,
						null);
				viewHolder = new ViewHolder();
				viewHolder.PerCenItemImage = (ImageView) view
						.findViewById(R.id.percenitem_image);
				viewHolder.oneHeadItemImage=(RoundedImageView) view.findViewById(R.id.avator);
				viewHolder.twoHeadItemImage=(RoundedImageView) view.findViewById(R.id.avator1);
				viewHolder.oneName=(TextView) view.findViewById(R.id.nickNameTextView);
				viewHolder.twoName=(TextView) view.findViewById(R.id.nickNameTextView1);
				viewHolder.bottomImage=(ImageView) view.findViewById(R.id.bottom_half);
				
				view.setTag(viewHolder);
			} else {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
			}
			viewHolder.oneHeadItemImage.setImageBitmap(myApp.getPhoneAlbum(halfItem.getOneHeadId()));
			if(halfItem.getTwoHeadId()!=null){
				viewHolder.twoHeadItemImage.setImageBitmap(myApp.getPhoneAlbum(halfItem.getTwoHeadId()));
				viewHolder.twoHeadItemImage.setVisibility(RoundedImageView.VISIBLE);
			}
			else{
				viewHolder.twoHeadItemImage.setVisibility(RoundedImageView.INVISIBLE);
			}
			viewHolder.oneName.setText(halfItem.getOneNameId());
			if(halfItem.getTwoNameId()!=null){
				viewHolder.twoName.setText(halfItem.getTwoNameId());
			}
			else{
				viewHolder.twoName.setText("");
			}
			viewHolder.PerCenItemImage.setImageBitmap(myApp.getPhoneAlbum(halfItem.getImageId()));
			viewHolder.bottomImage.setImageResource(halfItem.getBottomId());
			return view;
		}

		class ViewHolder {
			ImageView PerCenItemImage;
			RoundedImageView oneHeadItemImage;
			RoundedImageView twoHeadItemImage;
			TextView oneName;
			TextView twoName;
			ImageView bottomImage;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
			case R.id.add_photo_half:
				Intent intent = new Intent(PhotoHalfActivity.this,PhotoTakeHalfActivity.class);
				startActivityForResult(intent,0);
				break;
			case R.id.photo_half_back:
				this.finish();
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==0){
			if (resultCode == ActivityForResultUtil.REQUESTCODE_HALFHALF_ONE) {
				adapter.notifyDataSetChanged();
				myApp.HalfItemList.add(0,new HalfItem(myApp.mBitmap,myApp.mHeadBitmap,myApp.mName,null,null));
			}
			else if (resultCode == ActivityForResultUtil.REQUESTCODE_HALFHALF_TWO) {
				adapter.notifyDataSetChanged();
				myApp.HalfItemList.add(0,new HalfItem(myApp.mBitmap,myApp.mHeadYiBanTemp,myApp.mNameYiBanTemp,myApp.mHeadBitmap,myApp.mName));
			}
			
		}
	}
}