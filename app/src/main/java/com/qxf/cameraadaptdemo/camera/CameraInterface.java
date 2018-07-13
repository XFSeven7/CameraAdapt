package com.qxf.cameraadaptdemo.camera;

import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import com.qxf.cameraadaptdemo.utils.CameraUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by qixuefeng on 2018/7/13.
 */

public class CameraInterface {

    private static final String TAG = "CameraInterface";

    private Camera mCamera;
    private Camera.Parameters mParams;
    private boolean isPreviewing = false;
    private static CameraInterface mCameraInterface;

    public interface CamOpenOverCallback {
        void cameraHasOpened();
    }

    public interface CamStartPreviewCallback {
        void postPreview(int width, int height);
    }

    private CameraInterface() {

    }

    public static synchronized CameraInterface getInstance() {
        if (mCameraInterface == null) {
            mCameraInterface = new CameraInterface();
        }
        return mCameraInterface;
    }

    /**
     * 打开Camera
     *
     * @param callback
     */
    public void doOpenCamera(CamOpenOverCallback callback) {
        mCamera = Camera.open(0);
        callback.cameraHasOpened();
    }

    /**
     * 开启预览
     *
     * @param holder
     * @param point
     */
    public void doStartPreview(SurfaceHolder holder, Point point, CamStartPreviewCallback callback) {

        if (isPreviewing) {
            mCamera.stopPreview();
            return;
        }
        if (mCamera != null) {

            mParams = mCamera.getParameters();
            mParams.setPictureFormat(PixelFormat.JPEG);//设置拍照后存储的图片格式

            Camera.Size bestPreviewSize = CameraUtils.getBestPreviewSize(mParams, point);

            Log.e(TAG, "doStartPreview: 选择的预览分辨率 = " + bestPreviewSize.width + " x " + bestPreviewSize.height);

            // 修改Activity中的布局界面
            callback.postPreview(bestPreviewSize.width, bestPreviewSize.height);

            mParams.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);

            mCamera.setDisplayOrientation(90);

            List<String> focusModes = mParams.getSupportedFocusModes();
            if (focusModes.contains("continuous-video")) {
                mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }
            mCamera.setParameters(mParams);

            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();//开启预览
            } catch (IOException e) {
                e.printStackTrace();
            }

            isPreviewing = true;

            mParams = mCamera.getParameters(); //重新get一次
        }
    }

    /**
     * 停止预览，释放Camera
     */
    void doStopCamera() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            isPreviewing = false;
            mCamera.release();
            mCamera = null;
        }
    }

}


