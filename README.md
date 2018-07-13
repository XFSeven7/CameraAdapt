# CameraAdaptDemo
Android相机屏幕适配


前言
--
本文默认你已经会的Android相机开发，但是苦恼于相机屏幕适配
如果不会相机开发，可以参考以下作者的文章：

 - [Tong ZHAN](https://www.polarxiong.com/)
 
[Android相机开发(一): 最简单的相机](https://www.polarxiong.com/archives/Android%E7%9B%B8%E6%9C%BA%E5%BC%80%E5%8F%91-%E4%B8%80-%E6%9C%80%E7%AE%80%E5%8D%95%E7%9A%84%E7%9B%B8%E6%9C%BA.html)
[Android相机开发(二): 给相机加上偏好设置](https://www.polarxiong.com/archives/Android%E7%9B%B8%E6%9C%BA%E5%BC%80%E5%8F%91-%E4%BA%8C-%E7%BB%99%E7%9B%B8%E6%9C%BA%E5%8A%A0%E4%B8%8A%E5%81%8F%E5%A5%BD%E8%AE%BE%E7%BD%AE.html)
[Android相机开发(三): 实现拍照录像和查看](https://www.polarxiong.com/archives/Android%E7%9B%B8%E6%9C%BA%E5%BC%80%E5%8F%91-%E4%B8%89-%E5%AE%9E%E7%8E%B0%E6%8B%8D%E7%85%A7%E5%BD%95%E5%83%8F%E5%92%8C%E6%9F%A5%E7%9C%8B.html)
[Android相机开发(四): 旋转与纵横比](https://www.polarxiong.com/archives/Android%E7%9B%B8%E6%9C%BA%E5%BC%80%E5%8F%91-%E5%9B%9B-%E6%97%8B%E8%BD%AC%E4%B8%8E%E7%BA%B5%E6%A8%AA%E6%AF%94.html)
[Android相机开发(五): 触摸对焦,触摸测光,二指手势缩放](https://www.polarxiong.com/archives/Android%E7%9B%B8%E6%9C%BA%E5%BC%80%E5%8F%91-%E4%BA%94-%E8%A7%A6%E6%91%B8%E5%AF%B9%E7%84%A6-%E8%A7%A6%E6%91%B8%E6%B5%8B%E5%85%89-%E4%BA%8C%E6%8C%87%E6%89%8B%E5%8A%BF%E7%BC%A9%E6%94%BE.html)
[Android相机开发(六): 高效实时处理预览帧数据](https://www.polarxiong.com/archives/Android%E7%9B%B8%E6%9C%BA%E5%BC%80%E5%8F%91-%E5%85%AD-%E9%AB%98%E6%95%88%E5%AE%9E%E6%97%B6%E5%A4%84%E7%90%86%E9%A2%84%E8%A7%88%E5%B8%A7%E6%95%B0%E6%8D%AE.html)
	 
 - [yanzi1225627](https://my.csdn.net/yanzi1225627)
 
[玩转Android Camera开发(一):Surfaceview预览Camera,基础拍照功能完整demo](https://blog.csdn.net/yanzi1225627/article/details/33028041)
[玩转Android Camera开发(二):使用TextureView和SurfaceTexture预览Camera 基础拍照demo](https://blog.csdn.net/yanzi1225627/article/details/33313707)
[玩转Android Camera开发(三):国内首发---使用GLSurfaceView预览Camera 基础拍照demo](https://blog.csdn.net/yanzi1225627/article/details/33339965/)
[玩转Android Camera开发(四):预览界面四周暗中间亮，只拍摄矩形区域图片(附完整源码)](https://blog.csdn.net/yanzi1225627/article/details/34931759/)
[玩转Android Camera开发(五):基于Google自带算法实时检测人脸并绘制人脸框(网络首发，附完整demo)](https://blog.csdn.net/yanzi1225627/article/details/38098729/)


----------


现在出现了越来越多的屏幕，再也不是那个到处充满1920x1080的时代了，所以给身为手机开发者的我们带来了不少麻烦，从而给我们带来了相机屏幕适配这块的问题，大家都知道，在使用相机的时候，如果不对屏幕做适配，那么在预览的时候，界面就会拉伸，这对用户可不是个好体验。


普通解决方案
--
先说说普通解决方法，我们可以通过camera得到手机摄像头的参数，比如曝光度、照片尺寸、预览尺寸等等的很多信息。所以我们通过以下代码可以得知摄像头到底支持哪些预览分辨率：

```
// 相机所支持的分辨率
Camera.Parameters parameters = mCamera.getParameters();
List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
```
然后我们还可以得到手机的分辨率是多少

```
// 手机分辨率
DisplayMetrics dm = context.getResources().getDisplayMetrics();
int w_screen = dm.widthPixels;
int h_screen = dm.heightPixels;
Point screenRelsolution = new Point(w_screen, h_screen)；
```
所以，我们可以用已知的手机分辨率和相机所支持的预览分辨率做对比，找出合适的预览分辨率为相机做适配。

选择最佳分辨率可以参考[yanzi1225627](https://blog.csdn.net/yanzi1225627)的[方案](https://blog.csdn.net/yanzi1225627/article/details/33028041)，也可以参考我的方案[笑]：

```
	/**
     * 选择最佳相机预览分辨率
     *
     * @param parameters 相机参数
     * @param point      手机分辨率 宽度：point.x，高度point.y
     * @return 最佳相机分辨率
     */
    public static Camera.Size getBestPreviewSize(Camera.Parameters parameters, Point point) {

        double x = point.x; //1080
        double y = point.y; //1920

        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();

        List<Camera.Size> sameWidthList = new ArrayList<>();

        for (Camera.Size size : supportedPreviewSizes) {

            if (x == size.height) {
                sameWidthList.add(size);
            }

        }

        int index = -1;

        int min = -1;

        for (int i = 0; i < sameWidthList.size(); i++) {

            Camera.Size s = sameWidthList.get(i);

            int abs = Math.abs(s.width - point.y);

            if (min == -1) {
                min = abs;
            }

            if (abs <= min) {
                min = abs;
                index = i;
            }

        }

        return sameWidthList.get(index);

    }
```
然后把最佳支持的分辨率设置给相机就行了

```
mParams.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);
```

终极适配方案
--
使用普通适配方案，其实仔细想想，还是会存在一些问题，比如相机支持的预览分辨率里面，可能支持的分辨率里面没有跟手机分辨率相同，所以还是会导致预览的时候界面被拉伸，那么要怎样才能做到完美贴合般的适配呢，先说说思路

假设手机分辨率为1770*1080，但是相机预览分辨率为1920*1080，也就是这个surfaceview为1920*1080，所以可能是这些样子：
![这里写图片描述](https://img-blog.csdn.net/20180711142414114?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0lUX1hG/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
其中红色框为手机分辨率，绿色框为相机预览分辨率，如果把这样SurfaceView放置到手机屏幕中（**绿框填充红框**），界面肯定会被拉长吧，还有可能会是如下情况：
![这里写图片描述](https://img-blog.csdn.net/20180711115553847?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0lUX1hG/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
这样界面肯定就被收窄了（**绿框填充红框**）
所以还是要适配一波，不过这个适配，主要目的是为了在预览的时候，让预览图不变形。那么我们就先来说说原理吧。

**原理**
由于相机预览分辨率中，没有跟手机一样的分辨率，所以，我们的点在于修改手机的界面，让界面妥协。

比如手机的分辨率为1000*2000，而相机预览分辨率为1000*3000，那么我们就要将界面的布局设置为1000*3000，这样一来，在预览的时候，就不会出现界面拉伸的现象了。

如果手机分辨率是1000*1000，而预览分辨率为500*700，我们就要将手机界面的长宽比例设置跟预览分辨率比例一样的，设置为5：7，这里要提一点的就是，不能将手机的界面大小设置为500*700，因为手机分辨率为1000*1000，如果只展示500*700的界面，虽然预览图像是正常了，没有拉伸现象了，但是会出现白边。

所以我们应该将手机的界面大小设置为1000*1400，这样一来，手机整个界面就都是预览界面了。

既然知道了原理解决起来就很容易了。

**实现**

实现步骤：

 1. 得到Camera对象，得到Camera.Parameters对象
 2. 得到相机支持的分辨率，在没有跟手机分辨率相同的情况下，选择最大预览分辨率
 3. 修改界面大小，将界面比例跟预览分辨率比例设置相同
 4. 开启预览

可以使用接口回调的方式，在得到相机预览分辨率的时候，将预览分辨率回调给Activity，然后修改界面大小即可。

所以现在我们获取最佳分辨率的方法应该如下：

```
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
```
获取手机分辨率的方法：

```
public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);
}
```

然后我们可以拟定一个接口，当得到最佳预览分辨率的时候，将该分辨率告知给Activity：

```
public interface CamStartPreviewCallback{
	void postPreview(int width, int height);
}
```
所以告知给Activity的主要方式应该就是：(省略了无数代码)

```
public void startPreview(SurfaceHolder holder, CamStartPreviewCallback callback) {
	...
	mParams = mCamera.getParameters();
	Camera.Size bestPreviewSize = getBestPreviewSize(mParams, point);
	callback.postPreview(bestPreviewSize.width, bestPreviewSize.height); 
	...       
}
```
`postPreview()`的具体实现如下：

```
@Override
public void postPreview(int width, int height) {

	cameraSurfaceView.post(new Runnable() {
            @Override
            public void run() {

                Point screenMetrics = getScreenMetrics(context);

                Log.e(TAG, "run: 手机分辨率 = " + screenMetrics.x + " x " + screenMetrics.y);
                Log.e(TAG, "run: 相机预览分辨率 = " + width + " x " + height);

                ViewGroup.LayoutParams layoutParams1 = root.getLayoutParams();
                ViewGroup.LayoutParams layoutParams2 = cameraSurfaceView.getLayoutParams();

                int fixWidth = 1080;
                int fixHeight = 1920;

                float screenRate = screenMetrics.y * 1.0f / screenMetrics.x;
                float previewRate = width * 1.0f / height;

                Log.e(TAG, "run: screenRate = " + screenRate);
                Log.e(TAG, "run: previewRate = " + previewRate);

                if (screenRate > previewRate) {
                    fixWidth = screenMetrics.y * screenMetrics.x / width;
                    fixHeight = screenMetrics.y;
                } else if (screenRate < previewRate) {
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
```
说明一下里面的root跟cameraSurfaceView是啥，root就是根布局，而cameraSurfaceView自然就是相机预览界面的surfaceview了
布局界面如下：

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/root"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <SurfaceView
            android:id="@+id/cameraSurfaceView"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

    </RelativeLayout>

</RelativeLayout>

```
这样一来，我们的相机屏幕适配就完毕了。

结语
--
其实根据以上内容，大家也看得出来，其实我们在做相机屏幕适配的时候，并不是那种完美的适配，而是由于相机的预览分辨率无法修改，手机界面布局大小做了修改而已，其实这只是手机布局界面的妥协。谁让你不能变呢，那我变就行了呗！
