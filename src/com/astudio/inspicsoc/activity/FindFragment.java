package com.astudio.inspicsoc.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.adapter.PerCenItemAdapter;
import com.astudio.inspicsoc.model.ActionItem;
import com.astudio.inspicsoc.view.CornerListView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


@SuppressLint("ValidFragment")
public class FindFragment extends Fragment {
	  private CornerListView cornerListView = null;
	  private MainActivity activity;
	   
	  private int[] images = new int[]{R.drawable.nearimg, R.drawable.pingtu,
	            R.drawable.quanquan, R.drawable.changeimg,R.drawable.yaoyiyao };
	        
	        private String[] texts= new String[]{"附近的图", "一半一半",
	            "圈圈", "照片兑换","摇图",};
	   // private List<Map<String,String>> listData = null;
	    List<Map<String, Object>> listData = null;
	    private SimpleAdapter adapter = null;
		@SuppressLint("ValidFragment")
		public FindFragment(MainActivity activity) {
				this.activity = activity;
		}
   
	public FindFragment() {
			// TODO Auto-generated constructor stub
		}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.main_tab_setting, container,
				false);

		
        cornerListView = (CornerListView)v.findViewById(R.id.setting_list);
        setListData();
      /*
        adapter = new SimpleAdapter(getApplicationContext(), 
        		listData, R.layout.main_tab_setting_list_item , 
        		new String[]{"img","text"}, new int[]{R.id.img,R.id.setting_list_item_text});
        		*/
        /*MyAdapter adapter = new MyAdapter(getActivity(),
				R.layout.main_tab_setting_list_item, listData);*/
        //MyAdapter adapter = new MyAdapter(getActivity());
      
        
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), 
        		listData,// //需要绑定的数据 
                R.layout.main_tab_setting_list_item,//每一行的布局
                new String[] { "img", "text" }, //动态数组中的数据源的键对应到定义布局的View中
                new int[] { R.id.img, R.id.setting_list_item_text});
        cornerListView.setAdapter(adapter);
               cornerListView.setOnItemClickListener(new OnItemClickListener() {

                         @Override
                         public void onItemClick(AdapterView<?> parent, View view,
                                         int position, long id) {
                        	 Intent intent;
                        	 if( position==0){
                                 intent = new Intent(getActivity(),BaiduMapActivity.class);
                                 startActivity(intent);
                        	 }
                        	 if( position==1){
                                 intent = new Intent(getActivity(),PhotoHalfActivity.class);
                                 startActivity(intent);
                        	 }
                        	 if( position==2){
                                 intent = new Intent(getActivity(),PhotoCircleActivity.class);
                                 startActivity(intent);
                        	 }
                        	 if( position==3){
                                 intent = new Intent(getActivity(),Photo_exchange.class);
                                 startActivity(intent);
                        	 }
                        	 if( position==4){
                                 intent = new Intent(getActivity(),ShakeActivity.class);
                                 startActivity(intent);
                        	 }
                        
                         }
                 });
            
		return v;

	}
	
	

     
    /**
     * 设置列表数据
     */
    private List<Map<String, Object>> setListData() {
        listData = new ArrayList<Map<String, Object>>();
        //listData = new ArrayList<Map<String,String>>();
        //Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < 5; i++) 
        {
        	Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", images[i]);
            map.put("text", texts[i]);
            listData.add(map);
        }
/*
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("text", "附近的图");
        map.put("img", R.drawable.nearimg);
        listData.add(map);
         
        map = new HashMap<String, Object>();
        map.put("text", "一半一半");
        map.put("img", R.drawable.pingtu);
        listData.add(map);
 
        map = new HashMap<String, Object>();
        map.put("text", "圈圈");
        map.put("img", R.drawable.quanquan);
        listData.add(map);
 
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.changeimg);
        map.put("text", "照片兑换");
        listData.add(map);
 
        map = new HashMap<String, Object>();
        map.put("text", "摇图");
        map.put("img", R.drawable.yaoyiyao);
        listData.add(map);
        */
        
        return listData;
    }
    
    // ListView 中某项被选中后的逻辑
    protected void onListItemClick(ListView l, View v, int position, long id) {
         
        Log.v("MyListView4-click", (String)listData.get(position).get("text"));
    }
    
     
     
     
    public final class ViewHolder{
        public ImageView img;
        public TextView text;
        //public TextView info;
        public Button viewBtn;
    }
     
     
    public class MyAdapter extends BaseAdapter{
 
        private LayoutInflater mInflater;
         
         
        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return listData.size();
        }
 
        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }
 
        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }
      
        public View getView(int position, View convertView, ViewGroup parent) {
        	 ViewHolder holder = null;
             if (convertView == null) {
                  
                 holder=new ViewHolder();  
                  
                 convertView = mInflater.inflate(R.layout.main_tab_setting_list_item, null);
                 holder.img = (ImageView)convertView.findViewById(R.id.img);
                 holder.text = (TextView)convertView.findViewById(R.id.setting_list_item_text);
                 //holder.viewBtn = (Button)convertView.findViewById(R.id.view_btn);
                 convertView.setTag(holder);
                  
             }else {
                  
                 holder = (ViewHolder)convertView.getTag();
             }
              
              
             holder.img.setBackgroundResource((Integer)listData.get(position).get("img"));
             holder.text.setText((String)listData.get(position).get("text"));

            if (convertView == null) {
                    //这里是获取的布局，也就是要显示的item
                convertView = mInflater.inflate(R.layout.main_tab_setting_list_item, null);
               
            }
            convertView.setOnClickListener(new View.OnClickListener() {  
                @Override
                public void onClick(View v) {
                    //这里是点击后处理的代码
                	 //showInfo();
                	//Intent intent = new Intent(getActivity(),MainActivity.class);
    				//startActivity(intent);
    				
    				
                }
            });
            return convertView;
        }
      
         
    }
}
