package com.qxf.cameraadaptdemo.utils;

import android.graphics.Point;
import android.hardware.Camera;
import android.util.Log;

import java.util.List;

/**
 * Created by qixuefeng on 2018/7/13.
 */

public class CameraUtils {

    private static final String TAG = "CameraUtils";

    /**
     * 选择最佳相机预览分辨率
     *
     * @param parameters 相机参数
     * @param point      手机分辨率 宽度：point.x，高度point.y
     * @return 最佳相机分辨率
     */
    public static Camera.Size getBestPreviewSize(Camera.Parameters parameters, Point point) {

        List<Camera.Size> list = parameters.getSupportedPreviewSizes();

        int max = 0;
        int index = -1;

        for (int i = 0; i < list.size(); i++) {
            int height = list.get(i).height;
            int width = list.get(i).width;

            // 当手机分辨率和预览分辨率相同的时候，直接返回该预览分辨率
            // 否则选择最大分辨率
            if (point.x == width && point.y == height) {
                return list.get(index);
            }

            Log.e(TAG, "getBestPreviewSize: 相机分辨率 = " + height + " x " + width);

            int i1 = height * width;
            if (i1 > max) {
                max = i1;
                index = i;
            }
        }
        return list.get(index);

    }

}
