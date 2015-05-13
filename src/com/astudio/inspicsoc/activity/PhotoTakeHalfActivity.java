package com.astudio.inspicsoc.activity;

import java.io.ByteArrayOutputStream;






import com.astudio.inspicsoc.R;
import com.astudio.inspicsoc.utils.ActivityForResultUtil;
import com.astudio.inspicsoc.utils.CameraManager;
import com.astudio.inspicsoc.utils.PhotoUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class PhotoTakeHalfActivity extends Activity {
	private SurfaceView mSurfaceview;
	private SurfaceHolder holder;
	private Button take_photo = null;
	private Button cancel_photo = null;
	private Button upload_photo = null;
	private Bitmap mBitmap = null;
	
	CameraManager cameraManager;
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.half_one);
		
//		videoSurface = (SurfaceView) findViewById(R.id.surfaceview_camera);
		
		cameraManager = getCameraManager();
		cameraManager.startCamera();
		//��ȡ����ͷ����
		mSurfaceview = (SurfaceView) this.findViewById(R.id.surfaceview_camera);
		upload_photo = (Button) this.findViewById(R.id.upload_photo);
		upload_photo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PhotoTakeHalfActivity.this,PhotoHalfActivity.class);
				setResult(ActivityForResultUtil.REQUESTCODE_HALFHALF_ONE,intent);
				finish();
			}
		});
		
		take_photo = (Button) this.findViewById(R.id.take_photo);
		take_photo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				if (cameraManager.camera != null) {
					cameraManager.camera.autoFocus(null);

					cameraManager.camera.takePicture(null, null, new PictureCallback() {
						@Override
						public void onPictureTaken(byte[] data, Camera camera) {
							mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);								
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
						    mBitmap = Bitmap.createBitmap(bm1, 0, 0, bm1.getWidth(), bm1.getHeight()/2);
							
						    InsApplication app = (InsApplication) getApplication();
							app.mBitmap = PhotoUtil.saveToLocal(mBitmap);
							
							upload_photo.setVisibility(Button.VISIBLE);
							cancel_photo.setVisibility(Button.VISIBLE);
							take_photo.setVisibility(Button.GONE);
						}
					});
				}
			}
		});
		
		cancel_photo = (Button) this.findViewById(R.id.cancel_photo);
		cancel_photo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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
