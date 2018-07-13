# CameraAdaptDemo
Android相机屏幕适配

该项目主要是为了做相机下的屏幕适配

具体思路可以参见我的这篇文章
[Android 相机屏幕适配](https://blog.csdn.net/it_xf/article/details/80996456)


----------

**测试**：
机型：华为荣耀8 （厂商：honor，型号：FRD-AL00） 
相机支持的预览分辨率：

```
1080 x 1920
1080 x 1440
864 x 1536
960 x 1280
720 x 1280
720 x 960
720 x 720
480 x 640
414 x 736
408 x 544
400 x 400
288 x 352
240 x 320
144 x 208
144 x 176
```
手机屏幕分辨率：

```
1080 x 1794
```



首先我们来看看荣耀8的，未适配的情况下的样子

荣耀8 

**适配前**

竖屏：

<img src="https://raw.githubusercontent.com/qixuefeng/CameraAdaptDemo/master/pics/%E8%8D%A3%E8%80%808%E6%9C%AA%E9%80%82%E9%85%8D%E7%AB%96.jpg" title="Logo" width="200" /> 

横屏

<img src="https://raw.githubusercontent.com/qixuefeng/CameraAdaptDemo/master/pics/%E8%8D%A3%E8%80%808%E6%9C%AA%E9%80%82%E9%85%8D%E6%A8%AA.jpg" title="Logo" width="200" /> 

我们可以看到，已经变形，在竖屏的情况下，图像已经被挤扁了，横屏则是被拉伸了


**适配后**

竖屏：

<img src="https://raw.githubusercontent.com/qixuefeng/CameraAdaptDemo/master/pics/%E8%8D%A3%E8%80%808%E5%B7%B2%E9%80%82%E9%85%8D%E7%AB%96.jpg" title="Logo" width="200" /> 

横屏

<img src="https://raw.githubusercontent.com/qixuefeng/CameraAdaptDemo/master/pics/%E8%8D%A3%E8%80%808%E5%B7%B2%E9%80%82%E9%85%8D%E6%A8%AA.jpg" title="Logo" width="200" /> 

结果显而易见，各个方向的显像都是正常的


----------


我们在来看看S8+， S8+的拉伸看着更加明显

机型：三星S8+（厂商：Samsung，型号：SM-G9550）
相机支持的预览分辨率：

```
1080 x 1920
1080 x 1440
1088 x 1088
720 x 1280
720 x 960
720 x 720
480 x 720
480 x 640
288 x 352
240 x 320
144 x 176
```
手机屏幕分辨率 ：

```
1080 x 2094
```


**适配前**

竖屏：

<img src="https://raw.githubusercontent.com/qixuefeng/CameraAdaptDemo/master/pics/%E4%B8%89%E6%98%9Fs8%2B%E6%9C%AA%E9%80%82%E9%85%8D%E7%AB%96.jpg" title="Logo" width="200" /> 

横屏

<img src="https://raw.githubusercontent.com/qixuefeng/CameraAdaptDemo/master/pics/%E4%B8%89%E6%98%9Fs8%2B%E6%9C%AA%E9%80%82%E9%85%8D%E6%A8%AA.jpg" title="Logo" width="200" /> 

可以看到具有全面屏的s8+变形的还挺厉害

**适配后**

竖屏：

<img src="https://raw.githubusercontent.com/qixuefeng/CameraAdaptDemo/master/pics/%E4%B8%89%E6%98%9Fs8%2B%E5%B7%B2%E9%80%82%E9%85%8D%E7%AB%96.jpg" title="Logo" width="200" /> 

横屏

<img src="https://raw.githubusercontent.com/qixuefeng/CameraAdaptDemo/master/pics/%E4%B8%89%E6%98%9Fs8%2B%E5%B7%B2%E9%80%82%E9%85%8D%E6%A8%AA.jpg" title="Logo" width="200" /> 