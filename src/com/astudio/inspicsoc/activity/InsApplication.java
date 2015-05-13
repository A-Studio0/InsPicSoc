package com.astudio.inspicsoc.activity;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.model.Cat;
import com.astudio.inspicsoc.model.HalfItem;
import com.astudio.inspicsoc.model.PerCenItem;
import com.astudio.inspicsoc.utils.PhotoUtil;
import com.astudio.inspicsoc.websocket.LooperExecutor;
import com.astudio.inspicsoc.websocket.WebSocketClient;
import com.astudio.inspicsoc.websocket.WebSocketClient.WebSocketEvents;

/**
 * 存放共有的数据
 * 
 * @author rendongwei
 * 
 */
public class InsApplication extends Application implements WebSocketEvents {

	/**
	 * Push Server
	 */
	private static final String PUSHSERVER = "ws://192.168.191.7:3000";
	private LinkedList<String> sendQueue = new LinkedList<String>();
	private WebSocketClient websocketclient = null;
	private Toast logToast;
	private LooperExecutor executor = null;

	/**
	 * Notification
	 */
	NotificationCompat.Builder mBuilder;
	/** Notification的ID */
	int notifyId = 100;
	/** Notification管理 */
	public NotificationManager mNotificationManager;
	/**
	 * 消息列表
	 */
	public List<Cat> messageList = new ArrayList<Cat>();
	/**
	 * 用户头像
	 */
	public String mHeadBitmap;

	public String userName;
	
	/**
	 * 一半一半数据
	 */
	public String mHeadYiBanTemp;
	public String mBitmap = null;
	public String mName;
	public String mNameYiBanTemp;
	public List<HalfItem> HalfItemList = new ArrayList<HalfItem>();
	
	
	/**
	 * 个人主页数据
	 */
	public List<PerCenItem> PerCenItemList = new ArrayList<PerCenItem>();
	
	
	/**
	 * 手机SD卡图片缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mPhoneAlbumCache = new HashMap<String, SoftReference<Bitmap>>();
	
	/**
	 * 手机SD卡图片的路径
	 */
	public Map<String, List<Map<String, String>>> mPhoneAlbum = new HashMap<String, List<Map<String, String>>>();

	/**
	 * 存放拍照上传的照片路径
	 */
	public String mUploadPhotoPath;
	/**
	 * 存放本地选取的照片集合
	 */
	public List<Map<String, String>> mAlbumList = new ArrayList<Map<String, String>>();

	@Override
	public void onCreate() {
		super.onCreate();
		/**
		 * 初始化默认数据
		 */
		mHeadBitmap=PhotoUtil.saveToLocal(PhotoUtil.toRoundCorner(
				BitmapFactory.decodeResource(getResources(), R.drawable.img_test_face),
				15));
		mName="麦兜兜";
		PerCenItem percen1 = new PerCenItem(PhotoUtil.saveToLocal(BitmapFactory.decodeResource(getResources(), R.drawable.pinpho1)),"this is description","120.19, 30.26",
				"2015-05-12","收藏数：128","浏览数：834",R.drawable.percen_comment1,null,0);
		PerCenItemList.add(percen1);
		PerCenItem percen2 = new PerCenItem(PhotoUtil.saveToLocal(BitmapFactory.decodeResource(getResources(), R.drawable.pinpho3)),"this is description","116.34, 39.97",
				"2015-05-12","收藏数：456","浏览数：930",R.drawable.percen_comment2,null,0);
		PerCenItemList.add(percen2);

		PerCenItem percen3 = new PerCenItem(PhotoUtil.saveToLocal(BitmapFactory.decodeResource(getResources(), R.drawable.pinpho2)),"this is description","118.98, 34.67",
				"2015-05-12","收藏数：532","浏览数：912",R.drawable.percen_comment1,null,0);
		PerCenItemList.add(percen3);

		PerCenItem percen4 = new PerCenItem(PhotoUtil.saveToLocal(BitmapFactory.decodeResource(getResources(), R.drawable.head_default_miao)),"this is description","121.46, 23.90",
				"2015-05-12","收藏数：888","浏览数：962",R.drawable.percen_comment2,null,0);
		PerCenItemList.add(percen4);
		
		PerCenItem percen5 = new PerCenItem(PhotoUtil.saveToLocal(BitmapFactory.decodeResource(getResources(), R.drawable.pinpho4)),"this is description","115.98, 37.67",
				"2015-05-12","收藏数：562","浏览数：812",R.drawable.percen_comment1,null,0);
		PerCenItemList.add(percen5);
		/**
		 * 初始化所有的数据信息
		 */
//		try {
//			mWallpagersName = getAssets().list("wallpaper");
//			mTitleWallpagersName = getAssets().list("title_wallpager");
//			mAvatars = getAssets().list("avatar");
//			mPublicPageAvatars = getAssets().list("publicpage_avatar");
//			mPhotosName = getAssets().list("photo");
//			mViewedName = getAssets().list("viewed");
//			mViewedHotName = getAssets().list("viewed_hot");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		/**
		 * 初始化Push
		 */
		executor = new LooperExecutor();
		executor.requestStart();
		websocketclient = new WebSocketClient(this);
		try {
			websocketclient.connect(PUSHSERVER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setContentTitle("测试标题")
				.setContentText("测试内容")
				.setContentIntent(
						getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
				// .setNumber(number)//显示数量
				.setTicker("测试通知来啦")// 通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				// .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
				.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_VIBRATE)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
				// requires VIBRATE permission
				.setSmallIcon(R.drawable.ic_launcher);
	}


	/**
	 * 根据地址获取手机SD卡图片
	 */
	public Bitmap getPhoneAlbum(String path) {
		Bitmap bitmap = null;
		if (mPhoneAlbumCache.containsKey(path)) {
			SoftReference<Bitmap> reference = mPhoneAlbumCache.get(path);
			bitmap = reference.get();
			if (bitmap != null) {
				return bitmap;
			}
		}
		bitmap = BitmapFactory.decodeFile(path);
		mPhoneAlbumCache.put(path, new SoftReference<Bitmap>(bitmap));
		return bitmap;
	}

	/*
	 * (non-Javadoc) 推送服务器接口
	 * 
	 * @see com.astudio.inspicsoc.websocket.WebSocketClient.WebSocketEvents#
	 * onWebSocketOpen()
	 */

	@Override
	public void onWebSocketOpen() {
		// TODO Auto-generated method stub
		logAndToast("OPEN");
	}

	@Override
	public void onWebSocketMessage(String message) {
		// TODO Auto-generated method stub
		this.showIntentActivityNotify(message);
		Cat cc = new Cat(message, R.drawable.cc);
		messageList.add(cc);
	}

	@Override
	public void onWebSocketClose() {
		// TODO Auto-generated method stub
		disconnectAndExit();
		logAndToast("CLOSE");
	}

	@Override
	public void onWebSocketError(String description) {
		// TODO Auto-generated method stub
		disconnectAndExit();
		logAndToast("ERROR");
	}

	private void logAndToast(String msg) {
		if (logToast != null) {
			logToast.cancel();
		}
		logToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		logToast.show();
	}

	public synchronized void sendMessage(String msg) {
		logAndToast(msg);
		websocketclient.send(msg);
		// synchronized (sendQueue) {
		// sendQueue.add(msg);
		// }
		// requestQueueDrainInBackground();
	}

	private void requestQueueDrainInBackground() {
		(new AsyncTask<Void, Void, Void>() {
			@Override
			public Void doInBackground(Void... unused) {
				maybeDrainQueue();
				return null;
			}
		}).execute();
	}

	// Send all queued messages if connected to the room.
	private void maybeDrainQueue() {
		synchronized (sendQueue) {
			for (String msg : sendQueue) {
				websocketclient.send(msg);
			}
			sendQueue.clear();
		}
	}

	private void disconnectAndExit() {
		executor.requestStop();
		if (websocketclient != null) {
			websocketclient.disconnect(true);
			websocketclient = null;
		}
	}

	/** 显示通知栏点击跳转到指定Activity */
	public void showIntentActivityNotify(String message) {
		// Notification.FLAG_ONGOING_EVENT --设置常驻
		// Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
		// notification.flags = Notification.FLAG_AUTO_CANCEL;
		// //在通知栏上点击此通知后自动清除此通知
		mBuilder.setAutoCancel(true)
				// 点击后让通知将消失
				.setContentTitle("InsPicSoc").setContentText(message)
				.setTicker("你的好友发来一张图");
		// 点击的意图ACTION是跳转到Intent
		Intent resultIntent = new Intent(this, PhotoDetailActivity.class);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		mNotificationManager.notify(notifyId, mBuilder.build());
	}

	public PendingIntent getDefalutIntent(int flags) {
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,
				new Intent(), flags);
		return pendingIntent;
	}
}
