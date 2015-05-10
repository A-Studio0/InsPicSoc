package com.astudio.inspicsoc.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;



/*import com.astudio.inspicsoc.activity.VoiceActivity.AlbumAdapter;
import com.astudio.inspicsoc.activity.VoiceActivity.GalleryAdapter;
import com.astudio.inspicsoc.activity.VoiceActivity.LocationAdapter;
import com.astudio.inspicsoc.activity.VoiceActivity.AlbumAdapter.ViewHolder;*/
import com.astudio.inspicsoc.result.LocationResult;
import com.astudio.inspicsoc.utils.ActivityForResultUtil;
import com.astudio.inspicsoc.utils.RecordUtil;
import com.astudio.inspicsoc.utils.TextUtil;
import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.activity.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 录音类
 * 
 */
public class VoiceActivity extends InsActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_activity);
		
	}

		
}
