package com.astudio.inspicsoc.activity;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

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
	private static final String PUSHSERVER = "ws://192.168.191.1:3000";
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
	public String[] mTitleWallpagersName;
	public List<Cat> messageList = new ArrayList<Cat>();
	public Vector<Cat> group = new Vector<Cat>(); 
	public HashMap<String,Vector<Cat>> groupMembers = new HashMap<String,Vector<Cat>>();
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
	
	public Bitmap mDefault_Wallpager;
	
	public Bitmap mDefault_TitleWallpager;
	
	public Bitmap mDefault_Avatar;
	
	public Bitmap mDefault_Photo;
	
	public HashMap<String, SoftReference<Bitmap>> mWallpagersCache = new HashMap<String, SoftReference<Bitmap>>();

	public String[] mAvatars;
	public String[] mGiftsName;
	public HashMap<String, SoftReference<Bitmap>> mPhotoCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 壁纸名称
	 */
	public String[] mWallpagersName;
	/**
	 * 标题壁纸缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mTitleWallpagersCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 标题壁纸名称
	 */
	
	/**
	 * 当前壁纸编号
	 */
	public int mWallpagerPosition = 0;
	/**
	 * 圆形头像缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mAvatarCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 默认头像缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mDefaultAvatarCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 头像名称
	 */
	public String[] mPublicPageAvatars;
	public String[] mPhotosName;
	public String[] mViewedName;
	public String[] mViewedHotName;

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
				BitmapFactory.decodeResource(getResources(), R.drawable.head_default_miao),
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
		/**
		 * 初始化所有的数据信息
		 */
		try {
			mWallpagersName = getAssets().list("wallpaper");
			mTitleWallpagersName = getAssets().list("title_wallpager");
			mAvatars = getAssets().list("avatar");
			mPublicPageAvatars = getAssets().list("publicpage_avatar");
			mPhotosName = getAssets().list("photo");
			mViewedName = getAssets().list("viewed");
			mViewedHotName = getAssets().list("viewed_hot");
			mGiftsName = getAssets().list("gifts");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		 * 根据壁纸编号获取壁纸
		 */
		public Bitmap getWallpager(int position) {
			try {
				String wallpagerName = mWallpagersName[position];
				Bitmap bitmap = null;
				if (mWallpagersCache.containsKey(wallpagerName)) {
					SoftReference<Bitmap> reference = mWallpagersCache
							.get(wallpagerName);
					bitmap = reference.get();
					if (bitmap != null) {
						return bitmap;
					}
				}
				bitmap = BitmapFactory.decodeStream(getAssets().open(
						"wallpaper/" + wallpagerName));
				mWallpagersCache.put(wallpagerName, new SoftReference<Bitmap>(
						bitmap));
				return bitmap;

			} catch (Exception e) {
				return mDefault_Wallpager;
			}
		}

		/**
		 * 根据壁纸编号获取标题壁纸
		 */
		public Bitmap getTitleWallpager(int position) {
			try {
				String titleWallpagerName = mTitleWallpagersName[position];
				Bitmap bitmap = null;
				if (mTitleWallpagersCache.containsKey(titleWallpagerName)) {
					SoftReference<Bitmap> reference = mTitleWallpagersCache
							.get(titleWallpagerName);
					bitmap = reference.get();
					if (bitmap != null) {
						return bitmap;
					}
				}
				bitmap = BitmapFactory.decodeStream(getAssets().open(
						"title_wallpager/" + titleWallpagerName));
				mTitleWallpagersCache.put(titleWallpagerName,
						new SoftReference<Bitmap>(bitmap));
				return bitmap;
			} catch (Exception e) {
				return mDefault_TitleWallpager;
			}
		}

		/**
		 * 根据图片名称获取主页图片
		 */
		// public Bitmap getHome(String photo) {
		// try {
		// String homeName = photo + ".jpg";
		// Bitmap bitmap = null;
		// if (mHomeCache.containsKey(homeName)) {
		// SoftReference<Bitmap> reference = mHomeCache.get(homeName);
		// bitmap = reference.get();
		// if (bitmap != null) {
		// return bitmap;
		// }
		// }
		// bitmap = BitmapFactory.decodeStream(getAssets().open(
		// "home/" + homeName));
		// mHomeCache.put(homeName, new SoftReference<Bitmap>(bitmap));
		// return bitmap;
		// } catch (Exception e) {
		// return BitmapFactory.decodeResource(getResources(),
		// R.drawable.picture_default_fg);
		// }
		// }

		/**
		 * 根据编号获取用户圆形头像
		 */
		public Bitmap getAvatar(int position) {
			try {
				String avatarName = mAvatars[position];
				Bitmap bitmap = null;
				if (mAvatarCache.containsKey(avatarName)) {
					SoftReference<Bitmap> reference = mAvatarCache.get(avatarName);
					bitmap = reference.get();
					if (bitmap != null) {
						return bitmap;
					}
				}
				bitmap = PhotoUtil.toRoundCorner(
						BitmapFactory.decodeStream(getAssets().open(
								"avatar/" + avatarName)), 15);
				mAvatarCache.put(avatarName, new SoftReference<Bitmap>(bitmap));
				return bitmap;
			} catch (Exception e) {
				return mDefault_Avatar;
			}
		}

		/**
		 * 根据编号获取用户默认头像
		 */
		// public Bitmap getDefaultAvatar(int position) {
		// try {
		// String avatarName = mAvatars[position];
		// Bitmap bitmap = null;
		// if (mDefaultAvatarCache.containsKey(avatarName)) {
		// SoftReference<Bitmap> reference = mDefaultAvatarCache
		// .get(avatarName);
		// bitmap = reference.get();
		// if (bitmap != null) {
		// return bitmap;
		// }
		// }
		// bitmap = BitmapFactory.decodeStream(getAssets().open(
		// "avatar/" + avatarName));
		// mDefaultAvatarCache.put(avatarName, new SoftReference<Bitmap>(
		// bitmap));
		// return bitmap;
		// } catch (Exception e) {
		// return BitmapFactory
		// .decodeResource(getResources(), R.drawable.head);
		// }
		// }


		/**
		 * 根据编号获取照片
		 */
		public Bitmap getPhoto(int position) {
			try {
				String photosName = mPhotosName[position];
				Bitmap bitmap = null;
				if (mPhotoCache.containsKey(photosName)) {
					SoftReference<Bitmap> reference = mPhotoCache.get(photosName);
					bitmap = reference.get();
					if (bitmap != null) {
						return bitmap;
					}
				}
				bitmap = BitmapFactory.decodeStream(getAssets().open(
						"photo/" + photosName));
				mPhotoCache.put(photosName, new SoftReference<Bitmap>(bitmap));
				return bitmap;
			} catch (Exception e) {
				return mDefault_Photo;
			}
		}
		/**
		 * 根据图片名称获取附近照片的图片
		 */
		// public Bitmap getNearbyPhoto(String position) {
		// try {
		// String nearbyPhotoName = position + ".jpg";
		// Bitmap bitmap = null;
		// if (mNearbyPhoto.containsKey(nearbyPhotoName)) {
		// SoftReference<Bitmap> reference = mNearbyPhoto
		// .get(nearbyPhotoName);
		// bitmap = reference.get();
		// if (bitmap != null) {
		// return bitmap;
		// }
		// }
		// bitmap = BitmapFactory.decodeStream(getAssets().open(
		// "nearby_photo/" + nearbyPhotoName));
		// mNearbyPhoto
		// .put(nearbyPhotoName, new SoftReference<Bitmap>(bitmap));
		// return bitmap;
		// } catch (Exception e) {
		// return BitmapFactory.decodeResource(getResources(),
		// R.drawable.lbs_checkin_photo_icon);
		// }
		// }

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
			JSONObject msg = null;
			try {
				msg = new JSONObject(message);
			} catch (JSONException e) {
				e.printStackTrace();
				return;
			}
			String content = "";
			try {
				content = msg.getString("content");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			this.showIntentActivityNotify(content);
			Cat cc = new Cat(content, R.drawable.cc);
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
		
		public void sendP2PMessageTo(String toId,String content){
			JSONObject msg = new JSONObject();
			try {
				msg.put("type", "p2pmessage");
				msg.put("fromId", this.userName);
				msg.put("toId", toId);
				msg.put("content",content);
			} catch (JSONException e) {
				logAndToast("JSONObject出错!!!");
			}
			this.sendMessage(msg.toString());
		}
		
		public void loginPushServer(){
			JSONObject msg = new JSONObject();
			try {
				msg.put("type", "login");
				msg.put("username", this.userName);
			} catch (JSONException e) {
				
			}
			this.sendMessage(msg.toString());
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
			try{
				websocketclient.send(msg);
			}catch(Exception e){
				logAndToast("发送失败!!!");
			}
			
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