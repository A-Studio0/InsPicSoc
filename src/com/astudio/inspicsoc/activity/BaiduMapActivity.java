package com.astudio.inspicsoc.activity;

import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.astudio.inspicsoc.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

public class BaiduMapActivity extends Activity {
	private MapView mMapView = null;
	private BaiduMap baiduMap = null;

	private ImageButton close = null;

	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;

	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位

	private Marker mMarkerA;
	private InfoWindow mInfoWindow;

	private class Pics {
		public int id_pic;
		public String msg;
		public double lon;
		public double lat;

		public Pics(int id_pic, String msg, double lon, double lat) {

			this.id_pic = id_pic;
			this.msg = msg;
			this.lon = lon;
			this.lat = lat;
		}
	};

	// 这里需要传递缩略图
	Vector<Pics> data = new Vector<Pics>();

	/*
	 * data.add(new Pics(R.drawable.xihu1, "miao0", 120.13794422149658,
	 * 30.247111924359572)); data.add(new Pics(R.drawable.xihu2, "miao1",
	 * 120.14631271362304, 30.238158414086715)); data.add(new
	 * Pics(R.drawable.xihu3, "miao2", 120.14516000000003, 30.23389));
	 * data.add(new Pics(R.drawable.xixi1, "miao3", 120.06700150268558,
	 * 30.269469094611388)); data.add(new Pics(R.drawable.xixi2, "miao4",
	 * 120.04683129089359, 30.249007386742594)); data.add(new
	 * Pics(R.drawable.face_5, "miao5", 120.003532, 30.356796)); data.add(new
	 * Pics(R.drawable.face_6, "miao6", 120.070532, 30.476796)); data.add(new
	 * Pics(R.drawable.face_7, "miao7", 120.057532, 30.306796));
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.baidumap);

		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		baiduMap = mMapView.getMap();

		// 开启定位图层
		baiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		// 这里需要传递缩略图
		/* Vector<Pics> data = new Vector<Pics>(); */
		data.add(new Pics(R.drawable.xihu1, "西湖", 120.137944, 30.247111));
		data.add(new Pics(R.drawable.xihu2, "西湖", 120.146312, 30.238158));
		data.add(new Pics(R.drawable.xihu3, "西湖", 120.145160, 30.233890));
		data.add(new Pics(R.drawable.xixi3, "西溪湿地", 120.067001, 30.269469));
		data.add(new Pics(R.drawable.xixi4, "西溪湿地", 120.046831, 30.249007));
		data.add(new Pics(R.drawable.xihu2, "miao5", 120.003532, 30.356796));
		data.add(new Pics(R.drawable.xihu2, "miao6", 120.070532, 30.476796));
		data.add(new Pics(R.drawable.xihu2, "miao7", 120.057532, 30.306796));

		for (int i = 0; i < data.size(); i++) {
			Pics tmp = data.get(i);
			// 定义Maker坐标点
			LatLng point = new LatLng(tmp.lat, tmp.lon);
			// 构建Marker图标
			BitmapDescriptor bitmap = BitmapDescriptorFactory
					.fromResource(tmp.id_pic);
			// 构建MarkerOption，用于在地图上添加Marker
			OverlayOptions option1 = new MarkerOptions().position(point).icon(
					bitmap);

			// 在地图上添加Marker，并显示
			baiduMap.addOverlay(option1);

			// 定义文字所显示的坐标点
			LatLng llText = new LatLng(tmp.lat - 0.01, tmp.lon);
			// 构建文字Option对象，用于在地图上添加文字
			OverlayOptions textOption = new TextOptions().fontSize(24)
					.fontColor(0xFFFF00FF).text(tmp.msg).rotate(0)
					.position(llText);
			// 在地图上添加该文字对象并显示
			baiduMap.addOverlay(textOption);
		}

		// MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		// baiduMap.setMapStatus(msu);
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(final Marker marker) {
				Button button = new Button(getApplicationContext());
				button.setBackgroundResource(R.drawable.popup);
				// ImageView iv = new ImageView(getApplicationContext());

				OnInfoWindowClickListener listener = null;
				// if (marker == mMarkerA) {

				listener = new OnInfoWindowClickListener() {
					@Override
					public void onInfoWindowClick() {
						// 跳转
						baiduMap.hideInfoWindow();
					}
				};
				LatLng ll = marker.getPosition();
				Log.i("Mcdull", String.valueOf(ll.latitude));

				for (int i = 0; i < data.size(); i++) {
					if (marker.getPosition().latitude == data.get(i).lat
							&& marker.getPosition().longitude == data.get(i).lon) {
						button.setText(data.get(i).msg);
						// iv.setBackgroundResource(R.drawable.xihu1);

					}
				}

				mInfoWindow = new InfoWindow(BitmapDescriptorFactory
						.fromView(button), ll, -47, listener);
				baiduMap.showInfoWindow(mInfoWindow);
				// }
				return true;
			}
		});

		close = (ImageButton) this.findViewById(R.id.close_baidumap);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				BaiduMapActivity.this.finish();
			}

		});
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			baiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				baiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		baiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
}
