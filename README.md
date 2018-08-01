# Douyv
仿斗鱼的一个直播软件(持续优化更新中...)  
### 初步实现功能  
1.游戏分类显示  
2.不同频道的直播房间加载  
3.直播过程中的大小频切换  
4.小窗口播放  
后续功能如弹幕，直播间关注，直播间搜索，登录功能等等会持续添加  
同时目前的应用我是在我的红米测试机上测试的，系统有是19的api，系统算是比较老的了，仍有很多不足和BUG，后续会慢慢改进  

### 截图  
###全屏播放
![image](https://github.com/zhoutianjie/Douyv/blob/master/Douyv/ScreenShorts/play_fullscreen.png)  
###分类界面  
![image](https://github.com/zhoutianjie/Douyv/blob/master/Douyv/ScreenShorts/classify.png)  
###主界面  
![image](https://github.com/zhoutianjie/Douyv/blob/master/Douyv/ScreenShorts/home.png)  
###正常界面播放  
![image](https://github.com/zhoutianjie/Douyv/blob/master/Douyv/ScreenShorts/play_small.png)  
###悬浮框播放  
![image](https://github.com/zhoutianjie/Douyv/blob/master/Douyv/ScreenShorts/play_window.png)

### 系统架构  
基本上采用的是MVP架构

### 开源项目
网络请求相关okhttp  
图片加载相关Glide(后期可能会换掉)  
数据库相关DBFlow  
刷新相关 SmartRefreshLayout(这个控件后期我会自己实现一下)  
播放器 ijkplayer  
事件总线 EventBus  

### 目前的不足  
播放器的分辨率还存在问题，特别是播放竖屏的直播源，比如颜值区的视频源  
悬浮框播放器的尺寸大小有问题，看的很不舒服，可能不是黄金分割比吧，调试起来有点麻烦  
房间信息加载，显示直播间列表的时候，网络不顺畅的时候item大小是可变的，用起来很不舒服  
。。。

