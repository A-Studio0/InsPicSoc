package com.astudio.inspicsoc.activity;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.common.InsUrl;
import com.astudio.inspicsoc.result.LocationResult;
import com.astudio.inspicsoc.service.UploadUtil;
import com.astudio.inspicsoc.service.UploadUtil.OnUploadProcessListener;
import com.astudio.inspicsoc.utils.TextUtil;

/**
 * 照片分享类
 * 
 *
 * 
 */
public class PhotoShareActivity extends InsActivity  {
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoshare_activity);
		

	}

}
