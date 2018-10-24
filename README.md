# AnyDialog 使用说明

Android高定制性，高易用性Dialog。



[GitHub主页](https://github.com/goweii/SwipeDragTreeRecyclerView)

[Demo下载](https://github.com/goweii/AnyDialog/raw/master/app/release/app-release.apk)



# 简介

- 链式调用
- 可自定义数据绑定
- 可自定义进出场动画
- 可自由控制显示大小和位置
- 可自定义背景变暗或者显示图片
- 一行代码实现背景高斯模糊
- 避免有导航栏时被遮挡或动画效果覆盖导航栏
- 因刘海屏dialog无法全屏显示，会使背景图无法延伸至刘海区域，若需实现背景显示图片或高斯模糊效果，建议使用[FloatingLayer](https://github.com/goweii/FloatingLayer)



# 截图

截图效果较差，建议下载Demo体验

![demo_1.0.gif](https://upload-images.jianshu.io/upload_images/9231307-5633833b1ecb5617.gif?imageMogr2/auto-orient/strip)

![](https://upload-images.jianshu.io/upload_images/9231307-5e38ce99ab70565d.gif?imageMogr2/auto-orient/strip)



# 使用说明

## 集成

- ### 添加jitpack库

```java
// build.gradle(Project:)
allprojects {
    repositories {
        ...
            maven { url 'https://www.jitpack.io' }
    }
}
```

- ### 添加依赖

  [点击查看最新版本号](https://github.com/goweii/AnyDialog/releases)

```java
// build.gradle(Module:)
dependencies {
    implementation 'com.github.goweii:AnyDialog:最新版本号'
}
```

## 新建布局

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

## 在代码中调用

```java
 AnyDialog.with(MainActivity.this)
          .contentView(R.layout.dialog_test_1)
          // .backgroundBlur(20)	// 设置背景模糊度
          .dimAmount(0.5f)
          .cancelableOnTouchOutside(true)
          .cancelableOnTouchOutside(true)
          .contentAnim(new IAnim() {
              @Override
              public long inAnim(View content) {
                  AnimHelper.startTopInAnim(content, ANIM_DURATION);
                  return ANIM_DURATION;
              }
 
              @Override
              public long outAnim(View content) {
                  AnimHelper.startTopOutAnim(content, ANIM_DURATION);
                  return ANIM_DURATION;
              }
         })
          .bindData(new IDataBinder() {
               @Override
               public void bind(AnyDialog anyDialog) {
                   // 绑定的数据
               }
          })
          .onClick(R.id.btn_1, new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   // 点击事件
               }
          })
          .show();
```



# 常用方法

- **with(@NonNull Context context)**

  创建一个实例对象

- **contentView(View contentView)**

  设置Dialog主体布局

- **contentView(@LayoutRes int contentViewId)**

  设置Dialog主体布局

- **gravity(int gravity)**

  设置Dialog位置，如Gravity.BOTTOM

- **cancelableOnTouchOutside(boolean cancelable)**

  设置点击外部关闭

- **cancelableOnClickKeyBack(boolean cancelable)**

  设置点击返回键关闭

- **bindData(IDataBinder dataBinder)**

  绑定数据

- **onClick(@IdRes int viewId, OnDialogClickListener listener)**

  单个View添加点击事件

- **onClick(OnDialogClickListener listener, @IdRes int... viewIds)**

  多个View添加点击事件

- **onClickToDismiss(@IdRes int... viewIds)**

  点击该View关闭Dialog

- **getView(@IdRes int viewId)**

  获取View，可在IDataBinder接口中获取View

- **getViewHolder()**

  获取ViewHolder

- **onDialogShowListener(OnDialogShowListener onDialogShowListener)**

  dialog显示的回调

  - onShowing()

    刚显示出来，进入动画还未执行

  - onShown()

    进入动画执行完成

- **onDialogDismissListener(OnDialogDismissListener onDialogDismissListener)**

  dialog关闭的回调

  - onDismissing()

    刚开始关闭，关闭动画还未执行

  - onDismissed()

    关闭动画执行完成

- **contentAnim(IAnim contentAnim)**

  设置Dialog主体的进出场动画，会返回主体的根View，可对其自由定义动画

- **contentInAnim(@AnimRes int anim)**

  设置Dialog主体的进场动画

- **contentInAnim(@NonNull Animation anim)**

  设置Dialog主体的进场动画

- **contentOutAnim(@AnimRes int anim)**

  设置Dialog主体的出场动画

- **contentOutAnim(@NonNull Animation anim)**

  设置Dialog主体的出场动画

- **backgroundAnim(IAnim backgroundAnim)**

  设置Dialog背景的进出场动画，会返回背景View，可对其自由定义动画

- **backgroundInAnim(@AnimRes int anim)**

  设置Dialog背景的进场动画

- **backgroundInAnim(@NonNull Animation anim)**

  设置Dialog背景的进场动画

- **backgroundOutAnim(@AnimRes int anim)**

  设置Dialog背景的出场动画

- **backgroundOutAnim(@NonNull Animation anim)**

  设置Dialog背景的出场动画

- **defaultContentAnimDuration(long defaultAnimDuration)**

  设置Dialog主体的进出场动画默认时长

- **defaultBackgroundAnimDuration(long defaultAnimDuration)**

  设置Dialog背景的进出场动画默认时长

- **dimAmount(@FloatRange(from = 0, to = 1) float dimAmount)**

  设置Dialog显示时背景变暗程度，建议不要和下面的backgroundXxx方法同时使用

- **backgroundColorInt(@ColorInt int color)**

  设置Dialog背景颜色，建议为半透明值

- **backgroundColorRes(@ColorRes int color)**

  设置Dialog背景颜色，建议为半透明值

- **backgroundBlurRadius(@FloatRange(from = 0, fromInclusive = false, to = 25) float radius)**

  设置Dialog背景高斯模糊效果的半径

- **backgroundBlurScale(@FloatRange(from = 1) float scale)**

  设置Dialog背景高斯模糊效果Bitmap的缩放因子，当你的半径较大时，建议设置这个，可以加快运行速度

- **backgroundBitmap(Bitmap bitmap)**

  设置Dialog背景显示一个Bitmap

- **backgroundResource(@DrawableRes int resource)**

  设置Dialog背景显示一个资源文件

- **backgroundDrawable(Drawable drawable)**

  设置Dialog背景显示一个Drawable

- **show()**

  显示

- **dismiss()**

  关闭



# 注意

发现 bug 请联系 QQ302833254