package com.astudio.inspicsoc.activity;

import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.utils.ActivityForResultUtil;
import com.astudio.inspicsoc.utils.CameraManager;
import com.astudio.inspicsoc.utils.PhotoUtil;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class PhotoPingHalfActivity extends Activity {
	private SurfaceView mSurfaceview;
	protected boolean isPreview;
	private SurfaceHolder holder;
	private Button take_photo = null;
	private Button cancel_photo = null;
	private Button upload_photo=null;
	private Bitmap mBitmap = null;
	private ImageView banban = null;
	private Bitmap yiban = null;
	private InsApplication app = null;
	CameraManager cameraManager;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.half_two);
		cameraManager = getCameraManager();
		//��ȡ����ͷ����
		mSurfaceview = (SurfaceView) this.findViewById(R.id.surfaceview_camera_add);
		cameraManager.startCamera();
		banban = (ImageView) this.findViewById(R.id.banban);
		app = (InsApplication) getApplication();
		
		yiban = Bitmap.createBitmap(app.getPhoneAlbum(app.mBitmap), 0, 0, app.getPhoneAlbum(app.mBitmap).getWidth()/2, app.getPhoneAlbum(app.mBitmap).getHeight());
		
		banban.setImageBitmap(yiban);
		
		upload_photo = (Button) this.findViewById(R.id.upload_photo_add);
		upload_photo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(PhotoPingHalfActivity.this,PhotoHalfActivity.class);
				setResult(ActivityForResultUtil.REQUESTCODE_HALFHALF_TWO,intent);
				finish();
			}
		});
		
		take_photo = (Button) this.findViewById(R.id.take_photo_add);
		take_photo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				// TODO Auto-generated method stub
				if (cameraManager.camera != null) {
					cameraManager.camera.autoFocus(null);
					
					cameraManager.camera.takePicture(null, null, new PictureCallback() {
						@Override
						public void onPictureTaken(byte[] data, Camera camera) {
							mBitmap = BitmapFactory.decodeByteArray(data,	0, data.length);
							Matrix m = new Matrix();
					        m.setRotate(90, (float) mBitmap.getWidth() / 2, (float) mBitmap.getHeight() / 2);
					        float targetX, targetY;
					        targetX = mBitmap.getHeight();
					        targetY = 0;
					        final float[] values = new float[9];
					        m.getValues(values);
						    float x1 = values[Matrix.MTRANS_X];
						    float y1 = values[Matrix.MTRANS_Y];
						    m.postTranslate(targetX - x1, targetY - y1);
						    Bitmap bm1 = Bitmap.createBitmap(mBitmap.getHeight(), mBitmap.getWidth(), Bitmap.Config.ARGB_8888);
						    Paint paint = new Paint();
						    Canvas canvas = new Canvas(bm1);
						    canvas.drawBitmap(mBitmap, m, paint);					    
						    mBitmap = Bitmap.createBitmap(bm1, bm1.getWidth()/2, 0, bm1.getWidth()/2, bm1.getHeight());
						    float xRatio = 2*yiban.getHeight()/mBitmap.getHeight();
							Matrix m2 = new Matrix();
							m2.reset();
							m2.setScale(xRatio, xRatio);
							mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), m2, true);							
						    int width =yiban.getWidth() + mBitmap.getWidth();
			                int height = yiban.getHeight();
			                Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			                Canvas canvas2 = new Canvas(result);
			                canvas2.drawBitmap(yiban, 0, 0, null);
			                canvas2.drawBitmap(mBitmap, yiban.getWidth(), 0, null);
			                
							app.mBitmap=PhotoUtil.saveToLocal(result);
							
							upload_photo.setVisibility(Button.VISIBLE);
							cancel_photo.setVisibility(Button.VISIBLE);
							take_photo.setVisibility(Button.GONE);
							
						}
					});
					
				}
			}
			
		});
		
		cancel_photo = (Button) this.findViewById(R.id.cancel_photo_add);
		cancel_photo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//cameraManager.camera.startPreview();
				cameraManager.camera.startPreview();
				
				upload_photo.setVisibility(Button.GONE);
				cancel_photo.setVisibility(Button.GONE);
				take_photo.setVisibility(Button.VISIBLE);
			}
			
		});
		// ��Ԥ����ͼ
		holder = mSurfaceview.getHolder();
		holder.addCallback(new Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				cameraManager.stopPreview();
				cameraManager.setPreviewDisplay(null);
				mSurfaceview = null;
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				cameraManager.setPreviewDisplay(holder);
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				cameraManager.updateParameters();
				cameraManager.startPreview();
			}
		});
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}	
	
	public CameraManager getCameraManager() {
		if (cameraManager == null) {
			cameraManager = new CameraManager();
		}
		return cameraManager;
	}

	public void onCameraSwitchPressed(View view) {
			// cameraManager.changeCamera(videoSurface.getHolder());
			// recorderManager.reset();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		cameraManager.closeCamera();
	}
}
