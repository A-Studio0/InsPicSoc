package com.astudio.inspicsoc.view;

import com.astudio.inspicsoc.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class CornerListView extends ListView {
 
    public CornerListView(Context context) {
        super(context);
    }
 
    public CornerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    public CornerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
      
       
        this.setBackgroundResource(R.drawable.find_background);
        
    }
 
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                int itemnum = pointToPosition(x, y);
 
                if (itemnum == AdapterView.INVALID_POSITION)
                        break;                
                else
                { 
                	if (itemnum == 0){
                        if (itemnum == (getAdapter().getCount()-1)) {
                            //
                            setSelector(R.drawable.corner_list_single_pressed);
                        } else {
                            //
                            setSelector(R.drawable.corner_list_first_pressed);
                        }
                } else if (itemnum==(getAdapter().getCount()-1)){
                     //
                    setSelector(R.drawable.corner_list_last_pressed);
                } else {
                    //
                    setSelector(R.drawable.corner_list_pressed);
                }
                	/*
                        if(itemnum==0){
                                if(itemnum==(getAdapter().getCount()-1)){                                   
                                    setSelector(R.drawable.app_list_corner_round);
                                }else{
                                    setSelector(R.drawable.app_list_corner_round_top);
                                }
                        }else if(itemnum==(getAdapter().getCount()-1))
                                setSelector(R.drawable.app_list_corner_round_bottom);
                        else{                           
                            setSelector(R.drawable.app_list_corner_shape);
                        }*/
                }
 
                break;
        case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
    
}