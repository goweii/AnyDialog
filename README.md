- # AnyDialog 使用说明

     Android高定制性，高易用性Dialog。

     [GitHub主页](https://github.com/goweii/SwipeDragTreeRecyclerView)
     [Demo下载](https://github.com/goweii/AnyDialog/raw/master/app/release/app-release.apk)

     ## **简介**

     - 链式调用
     - 一行代码实现背景高斯模糊
     - 可自定义进出场动画
     - 可自由控制显示大小和位置

     ## **截图**

     ![demo_1.0.gif](https://upload-images.jianshu.io/upload_images/9231307-5633833b1ecb5617.gif?imageMogr2/auto-orient/strip)

     ## **使用说明**

     - ### 添加jitpack库

     ```java
         //build.gradle(Project:)
         allprojects {
             repositories {
                 ...
                 maven { url 'https://www.jitpack.io' }
             }
         }
     ```

     - ### 添加依赖

     ```java
         //build.gradle(Module:)
         dependencies {
            compile 'com.github.goweii:SwipeDragTreeRecyclerView:v1.2.0'
         }
     ```

     - ### **新建XML布局文件**

       在布局文件根节点设置layout_width，layout_height，layout_margin等属性控制dialog的显示大小

     ```xml
     <?xml version="1.0" encoding="utf-8"?>
     <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
         xmlns:tools="http://schemas.android.com/tools"
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         android:layout_margin="20dp"
         android:background="@color/colorAccent"
         android:gravity="center"
         android:orientation="vertical">
     
         <TextView
             android:id="@+id/tv_1"
             android:layout_width="match_parent"
             android:layout_height="wrap_content"
             android:background="@color/colorPrimary"
             android:gravity="center"
             tools:text="测试数据1" />
     
         <ImageView
             android:id="@+id/iv_1"
             android:layout_width="match_parent"
             android:layout_height="wrap_content"
             android:gravity="center"
             android:scaleType="centerCrop"
             tools:src="@mipmap/ic_launcher" />
     
         <Button
             android:id="@+id/btn_1"
             android:layout_width="match_parent"
             android:layout_height="wrap_content"
             android:text="按钮1" />
     
     </LinearLayout>
     ```

     - ### **在代码中调用**

     ```java
      AnyDialog.with(MainActivity.this)
               .contentView(R.layout.dialog_test_1)
               // .backgroundBlur(20)	// 设置背景模糊度
               .backgroundColorInt(0x33000000)
               .touchOutsideCancelable(true)
               .clickBackCancelable(true)
               .bindData(new IDataBinder() {
                    @Override
                    public void bind(AnyDialog anyDialog) {
                        TextView tv_1 = anyDialog.getView(R.id.tv_1);
                        tv_1.setText("这是在bindData（）方法中绑定的数据");
                        ImageView iv_1 = anyDialog.getView(R.id.iv_1);
                        iv_1.setImageResource(R.mipmap.ic_launcher);
                    }
               })
               .onClick(R.id.btn_1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "点击了btn_1",Toast.LENGTH_SHORT).show();
                    }
               })
               .show();
     ```

     - ## **注意**

       发现 bug 请联系 QQ302833254