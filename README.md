# cordova-plugin-yonyou-push
用友推送插件

##说明

1，把原推送项目中ExampleApplication中初始化的方法和MainActivity中onStart中初始化服务方法，
放入PushNotification.java插件类中的初始化方法中，当前activity通过cordova.getActivity()获取，
当前应用context通过cordova.getActivity().getApplicationContext()获取。

2，通过PushNotification.js中的init方法跳转到PushNotification.java中的init方法完成推送服务的开启。

3，由于原推送项目中打开消息只能直接跳转到其自定义的activity中，获取消息id只能从继承的activity中获取，
不易把消息传回网页，所以还是想通过广播接收器接收消息，然后传回网页。

4，又由于原推送项目中没有接收打开消息的广播接收器，但是jar包中有接收消息的广播接收器，
所以把jar包中的类提取出来修改，在接收消息的广播接收器中发送打开消息的广播，
新建打开消息的广播接收器，接收到消息，传回网页。

5，广播接收器处理消息

     广播接收器MyPushMessageReceiver的transmitOpen方法传递通知数据给PushNotification.java
     
     PushNotification.java的instance.webView.sendJavascript(js)方法传递数据给前端
     
     前端的方法document.addEventListener('upush.NotificationReceived', function (e) {}, false)注册监听接收数据
     
6，由于应用内部类的action需要用到应用的包名，所以manifest中的两个action前半部分要用manifest中的package替换下

<action android:name="com.test.myapp.NOTIFICATION_PUSH_SERVICE" />

和<action android:name="com.test.myapp.PUSH_LISTENSERVICE" />。

在插件配置文件中修改好再打包成apk就好了。

7，注意与百度推送不同，百度推送只需要本地应用和服务端应用配置的api_key一致就行，用友推送要APP_ID和应用包名package都

一致（如果包名不一致，PushServiceManager的136行会包空指针错）
