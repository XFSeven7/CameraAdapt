package com.qxf.cameraadaptdemo;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.qxf.cameraadaptdemo.camera.CameraInterface;
import com.qxf.cameraadaptdemo.camera.CameraSurfaceView;
import com.qxf.cameraadaptdemo.utils.DisplayUtil;

public class CameraActivity extends AppCompatActivity implements CameraInterface.CamOpenOverCallback, CameraInterface.CamStartPreviewCallback {

    private static final String TAG = "CameraActivity";

    private CameraSurfaceView cameraSurfaceView;

    private Thread openThread;

    private RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        initView();

        cameraSurfaceView = findViewById(R.id.camera);

        openThread = new Thread() {
            @Override
            public void run() {
                CameraInterface.getInstance().doOpenCamera(CameraActivity.this);
            }
        };

    }

    /**
     * @param context
     * @param width   相机预览分辨率 宽 可能为1920 相机长宽为手机水平状态
     * @param height  相机预览分辨率 高 可能为1080
     */
    private void setSize(final Context context, final int width, final int height) {
        cameraSurfaceView.post(new Runnable() {
            @Override
            public void run() {

                Point screenMetrics = DisplayUtil.getScreenMetrics(context);

                Log.e(TAG, "run: 手机分辨率 = " + screenMetrics.x + " x " + screenMetrics.y);
                Log.e(TAG, "run: 相机预览分辨率 = " + width + " x " + height);

                ViewGroup.LayoutParams layoutParams1 = root.getLayoutParams();
                ViewGroup.LayoutParams layoutParams2 = cameraSurfaceView.getLayoutParams();

                int fixWidth = 0;
                int fixHeight = 0;

                float screenRate = screenMetrics.y * 1.0f / screenMetrics.x;
                float previewRate = width * 1.0f / height;

                Log.e(TAG, "run: screenRate = " + screenRate);
                Log.e(TAG, "run: previewRate = " + previewRate);

                if (screenRate > previewRate) {
                    fixWidth = screenMetrics.y * screenMetrics.x / width;
                    fixHeight = screenMetrics.y;
                } else if (screenRate < previewRate) {// screenRate <= previewRate
                    fixWidth = screenMetrics.x;
                    fixHeight = (int) (screenMetrics.x * previewRate);
                } else if (screenRate == previewRate) {
                    fixWidth = height;
                    fixHeight = width;
                }

                layoutParams1.width = fixWidth;
                layoutParams1.height = fixHeight;
                root.setLayoutParams(layoutParams1);

                layoutParams2.width = fixWidth;
                layoutParams2.height = fixHeight;
                cameraSurfaceView.setLayoutParams(layoutParams2);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        openThread.start();
    }

    @Override
    public void cameraHasOpened() {
        SurfaceHolder holder = cameraSurfaceView.getSurfaceHolder();
        CameraInterface.getInstance().doStartPreview(holder, DisplayUtil.getScreenMetrics(this), this);
    }

    private void initView() {
        root = findViewById(R.id.root);
    }

    @Override
    public void postPreview(int width, int height) {
        setSize(this, width, height);
    }
}
