package com.astudio.inspicsoc.utils;

import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;

@SuppressLint("NewApi")
public class CameraManager {
	public Camera camera = null;
	private Size defaultSize = null;
	private boolean isPreviewOn = false;
	private int cameraFacingType = CameraInfo.CAMERA_FACING_BACK;
	private int frameRate = 30;

	public CameraManager(){
	}
	@SuppressWarnings("deprecation")
	public void startCamera() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			int numberOfCameras = camera.getNumberOfCameras();
			for (int i = 0; i < numberOfCameras; i++) {
				CameraInfo info = new CameraInfo();
				Camera.getCameraInfo(i, info);
				if (info.facing == cameraFacingType) {
					camera = Camera.open(i);
					break;
				}
			}
		} else {
			camera = Camera.open();
		}
		List<Size> sizeList = camera.getParameters().getSupportedPreviewSizes();
		int widthHeigh = 0;
		for (Size s : sizeList) {
			if (s.width * s.height > widthHeigh && s.width<2048 && s.height<2048 ) {
				widthHeigh = s.width * s.height;
				defaultSize = s;
			}
		}
		camera.setDisplayOrientation(90);
		
		Camera.Parameters parameters = camera.getParameters();
		parameters.setPreviewSize(defaultSize.width, defaultSize.height);
		parameters.setPictureSize(defaultSize.width, defaultSize.height);
		camera.setParameters(parameters);
	}

	public void setPreviewDisplay(SurfaceHolder holder) {
		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setPreviewCallBack(PreviewCallback callback) {
		camera.setPreviewCallback(callback);
	}

	public void startPreview() {
		if (!isPreviewOn && camera != null) {
			isPreviewOn = true;
			camera.startPreview();
		}
	}

	public void stopPreview() {
		if (isPreviewOn && camera != null) {
			isPreviewOn = false;
			camera.stopPreview();
		}
	} 

	public Size getDefaultSize() {
		return defaultSize;
	}

	public void closeCamera() {
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	public boolean isUseBackCamera() {
		return cameraFacingType == CameraInfo.CAMERA_FACING_BACK;
	}

	public void useBackCamera() {
		cameraFacingType = CameraInfo.CAMERA_FACING_BACK;
	}

	public void useFrontCamera() {
		cameraFacingType = CameraInfo.CAMERA_FACING_FRONT;
	}

	public void changeCamera(SurfaceHolder holder) {
		if (cameraFacingType == CameraInfo.CAMERA_FACING_BACK) {
			useFrontCamera();
		} else if (cameraFacingType == CameraInfo.CAMERA_FACING_FRONT) {
			useBackCamera();
		}
		closeCamera();
		startCamera();
	}

	public void updateParameters() {
		Camera.Parameters camParams = camera.getParameters();
		camParams.setPreviewSize(defaultSize.width, defaultSize.height);

		Log.v("Camera", "Preview Framerate: " + camParams.getPreviewFrameRate());

		camParams.setPreviewFrameRate(frameRate);
		camera.setParameters(camParams);
	}

	public Camera getCamera() {
		return camera;
	}

}
